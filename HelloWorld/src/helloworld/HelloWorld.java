package helloworld;

public class HelloWorld {
    public static int spocitej(int hodnota) {
    	int mezivysledek;
    	mezivysledek = 2 * hodnota;
    	switch (mezivysledek) {
    	case 4:
    		return mezivysledek - 2;
    	case 8:
    		return mezivysledek - 3;
    	default:
    			mezivysledek--;
    	}
    	return mezivysledek + 7;
    };
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		cisilka cislo = new cisilka();
		cisilka hodnota = new cisilka();
		int /*hodnota = 7,*/ druha = 2/*, treti = 4*/;
		int neco[] = {3, 2, 14};
		// System.out.println("hodnot apromene je: " + hodnota.toString() + cosig);
		neco[0] = spocitej(12);
		if (neco[1] == druha) {
			System.out.println("pravda "+ neco.length);
		};
		hodnota.longValue();
		cislo.floatValue();
		System.out.println("cislo je "+ cislo.doubleValue());
		System.out.println("hodnota je "+ hodnota.doubleValue());
	}

}
