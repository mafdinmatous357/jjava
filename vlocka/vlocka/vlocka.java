package vlocka;

import java.util.*;
import java.awt.*;
import javax.swing.*;

public class vlocka {

	public static void main(String[] args) {
		vykresli  m = new vykresli();
		JFrame w = new JFrame("A title");
		w.add(m);
		w.setSize(400, 400);
		w.setVisible(true);
		System.out.print("nazdarek");
	}

}
