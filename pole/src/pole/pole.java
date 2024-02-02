package pole;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class pole {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Set<Integer> druha = new HashSet<Integer>();
		// promena_pole[2][6] = 11;
		// druha.add(12);
		// druha.add(8);
		// System.out.println("druha je " + druha.toArray(promena_pole)[0][0]);
		//System.out.println("cislo je " + promena_pole[3][6]);
		Integer promena_pole[][] = new Integer[4][];
		promena_pole[0] = new Integer[4];
		promena_pole[1] = new Integer[2];
		promena_pole[2] = new Integer[8];
		promena_pole[3] = new Integer[6];
		for (int a = 0; a < promena_pole.length; a++) {
			for (int b = 0; b < promena_pole[a].length; b++) {
				promena_pole[a][b] = a * promena_pole[a].length + b;
			}
		}
		for(int sloupec = 0;sloupec < promena_pole.length;sloupec++) {
			for(int radek = 0;radek < promena_pole[sloupec].length;radek++) {
				System.out.print(promena_pole[sloupec][radek] + " ");
			};
			System.out.println("");
		}
		DisplayGraphics m = new DisplayGraphics();
		JFrame w = new JFrame("A title");
		w.add(m);
		w.setSize(400, 400);
		w.setVisible(true);
	}

}

