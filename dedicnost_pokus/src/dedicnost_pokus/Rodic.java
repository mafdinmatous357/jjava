package dedicnost_pokus;

public class Rodic {
	public String vlakno(String retezec_znaku, int cislo) {
		return retezec_znaku + " " + cislo;
	}

	public void vypis(String retezec_znaku, int cislo) {
		String vysledek = vlakno(retezec_znaku,cislo);
		// TODO Auto-generated method stub
		System.out.println("funguje to" + vysledek);
	}
	public void vypis(String retezec_znaku, String cislo) {
		System.out.println("ono to funguje " + retezec_znaku+ cislo  );
	}

}
