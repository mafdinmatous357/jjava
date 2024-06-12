package dedicnost_pokus;

public class Potomek extends Rodic {
	public String vlakno(String retezec_znaku, int cislo) {
		return retezec_znaku + "-" + cislo;
	}

	public void vypis(String retezec_znaku, int cislo) {
		String vysledek = super.vlakno(retezec_znaku,cislo);
		// TODO Auto-generated method stub
		System.out.println("funguje to" + vysledek);
	}

}
