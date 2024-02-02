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
		for(int i = body.size()-1; i >=0; i++) {
			double A[]=body.get(i);
			double B[]=body.get(i+1);
			double C[]=new double[2];
			double D[]=new double[2];
			double E[]=new double[2];
			C[0]=1/3*(B[0]-A[0])+A[0];
			C[1]=1/3*(B[1]-A[1])+A[1];
			
		}
		// g.drawLine(20, 80, 580, 80);
		for (int i=0;i<body.size()-1;i++) {
			g.drawLine((int)(body.get(i)[0]), (int)(body.get(i)[1]), (int)(body.get(i+1)[0]), (int)(body.get(i+1)[1]));
		}
	}
}
