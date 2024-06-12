package experiments_class;

public class trida {
	private Integer objekt;
	public int obekt_verejny;

	public trida() {
		objekt = 0;
	}

	public trida(int hodnota) {
		objekt = hodnota;
	}

	public trida(int hodnota, int druha_hodnota) {
		objekt = hodnota;
		obekt_verejny = druha_hodnota;
	}

	public void nastavovatko(int cislo) {
		objekt = cislo;
	}

	public Integer Get_R() {
		return objekt;
	}

	public Integer soucet_objektu(int hodnota_objektu) {
		return objekt + obekt_verejny + hodnota_objektu;
	}
}
