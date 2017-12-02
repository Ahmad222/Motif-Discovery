package org.cytoscape.myapp.internal;
import javax.swing.*;

import java.awt.*;
import java.awt.geom.*;

public class DrawColors extends JPanel{

	static int high = 100;
	static int width = high;
	static int ratio = 12;
	static int minn = 0;
	static int maxx = 0;
	int numColors = 10;
	int rad = 22;
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g ;
		 float incx = (float) (width/3);
		   float incy = (float) (high/4.5);
		   int x[] = new int[numColors];
		   int y[] = new int[numColors];
		   float d= 0;
		  
		   for (int i=0; i<numColors; i++){
		      if(i<5)
			   {x[i] = (int) (incx);}
		      else {
		    	  x[i] = (int) (3*incx);
			}
		     // y[i] = (int) (((i%5)*incy) + incy);
		      y[i] = (int) (((i%5)*incy) );
		   }
		  
		Color g1 = new Color(126,236,126);
		g2d.setColor(g1);
		g2d.fillOval(x[0], y[0], rad, rad);
		
	   	Color g2 = new Color(94,209,94);
	   	g2d.setColor(g2);
	   	g2d.fillOval(x[1], y[1], rad, rad);
	   	
	   	Color g3 = new Color(64,183,64);
	   	g2d.setColor(g3);
	   	g2d.fillOval(x[2], y[2], rad, rad);
	   	
	   	Color g4 = new Color(37,149,37);
	   	g2d.setColor(g4);
	   	g2d.fillOval(x[3], y[3], rad, rad);
	   	
	   	Color g5 = new Color(19, 121, 19);
	   	g2d.setColor(g5);
	   	g2d.fillOval(x[4], y[4], rad, rad);
	   	
	   	Color g6 = new Color(243,182,184);
	   	g2d.setColor(g6);
	   	g2d.fillOval(x[5], y[5], rad, rad);
	   	
	   	Color g7 = new Color(222,117,121);
	   	g2d.setColor(g7);
	   	g2d.fillOval(x[6], y[6], rad, rad);
	   	
	   	Color g8 = new Color(199,92,95);
	   	g2d.setColor(g8);
	   	g2d.fillOval(x[7], y[7], rad, rad);
	   	
	   	Color g9 = new Color(185,49,54);
	   	g2d.setColor(g9);
	   	g2d.fillOval(x[8], y[8], rad, rad);
	   	
	   	Color g10 = new Color(161,18,23);
	   	g2d.setColor(g10);
	   	g2d.fillOval(x[9], y[9], rad, rad);
	   	
		
		   
		   
	}
	public static void setvals(int rat,int minm , int maxm, int hight1)
	{
		minn = minm;
		maxx = maxm;
		high = (int) (hight1 * 0.85);
		width = high;
		ratio = rat;
	}
	
}
