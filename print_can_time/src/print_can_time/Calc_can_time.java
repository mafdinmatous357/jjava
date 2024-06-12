package print_can_time;

public class Calc_can_time {
	public static int abs(int x) {
		return x < 0 ? -x : x;
	}
	public static long abs(long x) {
		return x < 0 ? -x : x;
	}
	public static int CAN_BaudRates[] = {10000, 20000, 50000, 100000, 125000, 250000, 500000, 800000, 1000000};
	public static CAN_ClockConfig CAN_SpeedTable[][];
	public static int CAN_IP_freq = 40000000;
	public static int NSEGMENT1_MAX = 256;
	public static int NSEGMENT2_MAX = 128;
	public static int NTWJ_MAX = 128;
	public static int NDIV_MAX = 1024;
	public static int DSEGMENT1_MAX = 32;
	public static int DSEGMENT2_MAX = 16;
	public static int DTWJ_MAX = 16;
	public static int DDIV_MAX = 1024;
	public static int ETDCOFF_MAX = 127;
	public static int CAN_getSamplePoint(int rate)
	{
		return rate > 4000000 ? 625 : rate > 2000000 ? 700 : rate > 800000 ? 750 : rate > 500000 ? 800 : 875;
	}
	public static CAN_ClockConfig BSP_CAN_CalculateTimeConfigs(int canclk, int nrate, int drate)
	{
		int ntsegi_max = canclk / nrate;
		int ntsegi_min = canclk / (nrate * NDIV_MAX);
		int dtsegi_max = drate != 0 ? canclk / drate : 0;
		int dtsegi_min = drate != 0 ? canclk / (drate * DDIV_MAX) : 0;
		long best_error = Long.MAX_VALUE;
		long best_nerror = Long.MAX_VALUE;
		CAN_ClockConfig result = null;
		if (ntsegi_max > NSEGMENT1_MAX + NSEGMENT2_MAX) {
			ntsegi_max = NSEGMENT1_MAX + NSEGMENT2_MAX;
		}
		if (dtsegi_max > DSEGMENT1_MAX + DSEGMENT2_MAX) {
			dtsegi_max = DSEGMENT1_MAX + DSEGMENT2_MAX;
		}
		if (ntsegi_min < 8) {
			ntsegi_min = 8;
		}
		if (dtsegi_min < 8) {
			dtsegi_min = 8;
		}
		if (dtsegi_max * 2 + 1 < dtsegi_min) {
			drate = 0;
		}
		for(int ntsegi = ntsegi_max * 2 + 1; ntsegi >= ntsegi_min; ntsegi--) {
			int ntseg = 1 + ntsegi / 2;
			/*if ((nrate == 10000) && (drate == 10000) && (ntseg == 16)) {
				System.out.println(ntsegi);
			}*/
			int ndiv = ntsegi % 2 + canclk / (ntseg * nrate);
			if ((ndiv < 1) || (ndiv > NDIV_MAX)) {
				continue;
			}
			int ncrate = canclk / (ndiv * ntseg);
			if ((abs(ncrate - nrate) * 1000) / nrate > 50) {
				continue;
			}
			int nrsp = CAN_getSamplePoint(nrate);
			int nseg2 = ntseg - (ntseg * nrsp) / 1000;
			if (abs(nrsp - (1000 * (ntseg - nseg2)) / ntseg) >
			    abs(nrsp - (1000 * (ntseg - nseg2 + 1)) / ntseg)) {
				nseg2 -= 1;
			}
			if (NSEGMENT1_MAX < ntseg - nseg2 - 1) {
				nseg2 = ntseg - NSEGMENT1_MAX - 1;
			}
			long nerror = abs((48000L * canclk) / nrate - 48000L * ntseg * ndiv) +
					     abs(((1000L - nrsp) * canclk) / nrate - 1000L * nseg2 * ndiv);
			if (drate == 0) {
				if (nerror < best_nerror) {
					best_nerror = nerror;
					if (result == null) {
						result = new CAN_ClockConfig();
					}
					result.nrate = ncrate;
					result.ndiv = ndiv;
					result.ntseg2 = nseg2;
					result.ntseg1 = ntseg - nseg2 - 1;
					result.drate = 0;
					result.ddiv = 0;
					result.dtseg2 = 0;
					result.dtseg1 = 0;
				}
				continue;
			}

			/*if ((nrate == 10000) && (drate == 10000) && (ncrate == nrate)) {
				System.out.println("n= ntseg=" + ntseg + " ndiv=" + ndiv + " nerror=" + nerror + " " + ncrate + "!=" + nrate + " ; drate=" + drate);
			}*/
			for(int dtsegi = dtsegi_max * 2 + 1; dtsegi >= dtsegi_min; dtsegi--) {
				int dtseg = 1 + dtsegi / 2;
				int ddiv = dtsegi % 2 + canclk / (dtseg * drate);
				if ((ddiv < 1) || (ddiv > DDIV_MAX)) {
					continue;
				}
				int dcrate = canclk / (ddiv * dtseg);
				if ((abs(dcrate - drate) * 1000) / drate > 50) {
					continue;
				}
				int drsp = CAN_getSamplePoint(drate);
				int dseg2 = dtseg - (dtseg * drsp) / 1000;
				if (abs(drsp - (1000 * (dtseg - dseg2)) / dtseg) >
				    abs(drsp - (1000 * (dtseg - dseg2 + 1)) / dtseg)) {
					dseg2 -= 1;
				}
				if (DSEGMENT1_MAX < dtseg - dseg2 - 1) {
					dseg2 = dtseg - DSEGMENT1_MAX - 1;
				}
				long derror = abs((657000L * canclk) / drate - 657000L * dtseg * ddiv) +
						     abs(((1000L - drsp) * canclk) / drate - 1000L * dseg2 * ddiv);
				long error = nerror + derror;
				if (ndiv != ddiv) {
					error += 1000 * ntseg * ndiv;
				}
				/*if ((nrate == 10000) && (drate == 10000) && (dcrate == drate)) {
					System.out.println("d= ntseg=" + ntseg + " ndiv=" + ndiv + " derror=" + derror + " error=" + error + " " + ncrate + "!=" + nrate + " ; " + dcrate + "!=" + drate);
				}*/
				if (error < best_error) {
					best_error = error;
					if (result == null) {
						result = new CAN_ClockConfig();
					}
					result.nrate = ncrate;
					result.ndiv = ndiv;
					result.ntseg2 = nseg2;
					result.ntseg1 = ntseg - nseg2 - 1;
					result.drate = dcrate;
					result.ddiv = ddiv;
					result.dtseg2 = dseg2;
					result.dtseg1 = dtseg - dseg2 - 1;
					/*if ((nrate == 10000) && (drate == 10000)) {
						System.out.println(" > ntseg=" + ntseg + " ndiv=" + ndiv + " error=" + error + " " + result.nrate + "!=" + nrate + " ; " + result.drate + "!=" + drate);
					}*/
				}
			}
		}
		if (result != null) {
			if (result.ntseg1 < result.ntseg2) {
				result.nrjw = result.ntseg1 / 2;
			}
			else {
				result.nrjw = result.ntseg2 / 2;
			}
			if (result.nrjw > NTWJ_MAX) {
				result.nrjw = NTWJ_MAX;
			}
			if (result.nrjw < 1) {
				result.nrjw = 1;
			}
			if (result.dtseg1 < result.dtseg2) {
				result.drjw = result.dtseg1 / 2;
			}
			else {
				result.drjw = result.dtseg2 / 2;
			}
			if (result.drjw > NTWJ_MAX) {
				result.drjw = NTWJ_MAX;
			}
			if (result.drjw < 1) {
				result.drjw = 1;
			}
			result.txdelay = result.dtseg1 * result.ddiv;
			if (result.txdelay > ETDCOFF_MAX) {
				result.txdelay = ETDCOFF_MAX;
			}
			if ((result.nrate != nrate) || (result.drate != drate)) {
				System.out.println("result " + result.nrate + "!=" + nrate + " ; " + result.drate + "!=" + drate);
			}
		}
		return result;
	}
	public static void main(String[] args) {
        CAN_SpeedTable = new CAN_ClockConfig[CAN_BaudRates.length][];
        for(int ni = 0; ni < CAN_BaudRates.length; ni++) {
        	CAN_SpeedTable[ni] = new CAN_ClockConfig[CAN_BaudRates.length + 1];
        	CAN_SpeedTable[ni][0] = BSP_CAN_CalculateTimeConfigs(24000000, CAN_BaudRates[ni], 0);
        	for(int di = ni; di < CAN_BaudRates.length; di++) {
        		CAN_SpeedTable[ni][di + 1] = BSP_CAN_CalculateTimeConfigs(24000000, CAN_BaudRates[ni], CAN_BaudRates[di]);
            }
        }
        for(CAN_ClockConfig[] line : CAN_SpeedTable) {
        	for(CAN_ClockConfig item : line) {
        		if (item != null) {
        			System.out.println("nrate=" + item.nrate + " ndiv=" + item.ndiv + " ntseg1=" + item.ntseg1 + " ntseg2=" + item.ntseg2 + " drate=" + item.drate + " ddiv=" + item.ddiv + " dtseg1=" + item.dtseg1 + " dtseg2=" + item.dtseg2);
        		}
        	}
        }
	}

}
