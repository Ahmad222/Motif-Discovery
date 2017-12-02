package org.cytoscape.myapp.internal;
import javax.swing.*;

import java.awt.*;
import java.awt.geom.*;

public class DrawGraph extends JPanel{

	static int high = 110;
	static int width = high;
	static int rad = 12;
	static int size = 0;
	static boolean directed = false; 
	static char adj [] = {'0','1','1','1','0','0','0','0','0','0','0','0','0','0','0','0'};
	
	@Override
	public void paint(Graphics g) {
		//System.out.println("Paint: " + width + "   R:" + rad);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.RED);
		
		 // Compute Nodes
		   float inc = (float) ((180*2.0)/size);
		   int x[] = new int[size];
		   int y[] = new int[size];
		   float d= 0;
		   d= 180 - inc/2;
		   for (int i=0; i<size; i++){//$d=M_PI-$inc/2.0; $i<$k; $i++, $d+=$inc) {
		      x[i] = (int) ((width/2) - (Math.sin(Math.toRadians(d))*(width/2)*(0.8)));
		      y[i] = (int) (high - ((high/2) - (Math.cos(Math.toRadians(d)))*(high/2)*(0.8)));
		      d+= inc;
		   }
		   for (int i=0; i<size; i++){
		   g2d.fillOval(x[i], y[i], 2*rad, 2*rad);
		   }
		   
		   
		   for (int i=0; i<size; i++)
			      for (int j=0; j<size; j++)
			      {
			    	  if (i != j && adj[i*size + j] == '1') {
			    		  if(directed)
			    		  {
			    			 //Line2D lin = new Line2D.Float(x[i]+rad, y[i]+rad,x[j]+rad, y[j]+rad);
				   	       	//	g2d.draw(lin);
			    			  
			    			  DrawArrow(g2d, x[i]+rad, y[i]+rad,x[j]+rad, y[j]+rad);
			    			 
			    		  }
			    		  else
			    		  {
			    			  Line2D lin = new Line2D.Float(x[i]+rad, y[i]+rad,x[j]+rad, y[j]+rad);
				   	       		g2d.draw(lin);
			    		  }
			    		  
			    	  }
			      }
		   
		   
		/*
		g2d.fillOval(0, 0, 30, 30);
		g2d.drawOval(0, 50, 30, 30);		
		g2d.fillRect(50, 0, 30, 30);
		g2d.drawRect(50, 50, 30, 30);

		g2d.draw(new Ellipse2D.Double(0, 100, 30, 30));*/
	}
	public static void setvals(int s, boolean d, char[] adj2, int hight1)
	{
		size = s;
		directed = d;
		adj = adj2;
		high = (int) (hight1 * 0.85);
		width = high;
		rad = high/(3*s);
		//System.out.println("SET: " + width + "   R:" + rad);
	}
	public void DrawArrow(Graphics2D g2d, int x1 , int y1 , int x2 , int y2)
	{
		
		double theta = Math.atan2(y1-y2, x1-x2);
		
		 x2 = (int) (x2+(rad+1)*Math.sin(theta+Math.PI/2));
		 y2 = (int) (y2-(rad+1)*Math.cos(theta+Math.PI/2));
		
		 theta = Math.atan2(y1-y2, x1-x2);
		 //width = (int) (rad*1.5);
		 int angle = 1;
		 int width1 = (int) (rad*1.5);
		 //System.out.println("DRAW: " + width + "   R:" + rad + "   NW:" + width1);
		 
		
		 int a_x1 =  (int) (x2+width1* Math.sin(theta+angle));
		 int a_y1 =  (int) (y2-width1* Math.cos(theta+angle));

		 int a_x2 =  (int) (x2-width1* Math.sin(theta-angle));
		 int a_y2 =  (int) (y2+width1* Math.cos(theta-angle));
		 
		 
		 
		 Line2D lin = new Line2D.Float(x1, y1, x2 , y2);
       		g2d.draw(lin); 
       		
       		
       		
		  
       	 lin = new Line2D.Float(a_x1, a_y1, x2, y2);
	       		g2d.draw(lin); 
		   
	     lin = new Line2D.Float(a_x2, a_y2, x2, y2);
	       		g2d.draw(lin); 
		
	}
	
}
