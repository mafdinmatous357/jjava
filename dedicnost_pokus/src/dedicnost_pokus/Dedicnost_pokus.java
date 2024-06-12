package dedicnost_pokus;

public class Dedicnost_pokus {
	public static void pretizena(Rodic x) {
		x.vypis("\nNa rodici", 7);
	}
	public static void pretizena(Potomek x) {
		x.vypis("\nNa potomku", 9);
	}

	public static void main(String[] args) {
		Rodic A= new Potomek();
		A.vypis("hahoj", "nazdar");
		pretizena(A);
	}

}
