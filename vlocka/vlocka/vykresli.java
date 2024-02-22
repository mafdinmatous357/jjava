package vlocka;

import java.awt.Canvas;
import java.awt.Graphics;
import java.util.*;

public class vykresli extends Canvas {

	private static final long serialVersionUID = 502905880140222188L;

	public void paint(Graphics g) {
		Vector<double[]> body = new Vector<double[]>();
		double bodA[] = {10.0, 10.0 };
		double bodB[] = {1000.0, 1000.0 };
		body.add(bodA);
		body.add(bodB);
		for (int y = 0; y < 9; y++) {
			for (int i = body.size() - 1; i > 0; i--) {
				double A[] = body.get(i - 1);
				double B[] = body.get(i);
				double C[] = new double[2];
				double D[] = new double[2];
				double E[] = new double[2];
				D[0] = 2.0 / 3 * (B[0] - A[0]) + A[0];
				D[1] = 2.0 / 3 * (B[1] - A[1]) + A[1];
				C[0] = 1.0 / 3 * (B[0] - A[0]) + A[0];
				C[1] = 1.0 / 3 * (B[1] - A[1]) + A[1];
				E[0] = (0.5 * (B[0] - A[0]) ) - (0.289 * (B[1] - A[1]))+A[0];
				E[1] = (0.5 * (B[1] - A[1]) ) + (0.289 * (B[0] - A[0]))+A[1];
				body.add(i, D);
				body.add(i, E);
				body.add(i, C);
			}
		}
		// g.drawLine(20, 80, 580, 80);
		for (int i = 0; i < body.size() - 1; i++) {
			double z[]=body.get(i);
			double k[]=body.get(i+1);
			g.drawLine((int) (z[0]), (int) (z[1]), (int) (k[0]), (int) (k[1]));
		}
	}
}
