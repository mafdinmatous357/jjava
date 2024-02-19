package vlocka;

import java.awt.Canvas;
import java.awt.Graphics;
import java.util.*;

public class vykresli extends Canvas {

	private static final long serialVersionUID = 502905880140222188L;

	public void paint(Graphics g) {
		Vector<double[]> body = new Vector<double[]>();
		double bodA[] = {20.0, 80.0};
		double bodB[] = {400.0, 500.0};
		body.add(bodA);
		body.add(bodB);
		for(int i = body.size()-1; i > 0; i--) {
			double A[]=body.get(i-1);
			double B[]=body.get(i);
			double C[]=new double[2];
			double D[]=new double[2];
			double E[]=new double[2];
			D[0]=2.0/3*(B[0]-A[0])+A[0];
			D[1]=2.0/3*(B[1]-A[1])+A[1];
			C[0]=1.0/3*(B[0]-A[0])+A[0];
			C[1]=1.0/3*(B[1]-A[1])+A[1];
			E[0]=(1.0/3*(B[0]-A[0])+A[0])*1.5-(1.0/3*(B[1]-A[1])+A[1])*0.86;
			E[1]=(1.0/3*(B[0]-A[0])+A[0])*0.86+(1.0/3*(B[1]-A[1])+A[1])*1.5;
			body.add(i+1, D);
			body.add(i+1, E);
			body.add(i+1, C);
		}
		// g.drawLine(20, 80, 580, 80);
		for (int i=0;i<body.size()-1;i++) {
			g.drawLine((int)(body.get(i)[0]), (int)(body.get(i)[1]), (int)(body.get(i+1)[0]), (int)(body.get(i+1)[1]));
		}
	}
}
