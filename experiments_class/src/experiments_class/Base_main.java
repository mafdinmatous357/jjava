package experiments_class;

public class Base_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		trida vysledek = new trida(0, 15);
		trida visledek = new trida(4);
		vysledek.obekt_verejny = 15;
		vysledek.nastavovatko(12);
		int result = vysledek.Get_R();
		System.out.println(result);
		int soucet = vysledek.soucet_objektu(17);
		int soucet2 = visledek.soucet_objektu(17);
		System.out.println(soucet);
		System.out.println(soucet2);

	}

}
