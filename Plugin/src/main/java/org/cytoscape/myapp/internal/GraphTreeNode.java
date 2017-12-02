package org.cytoscape.myapp.internal;

import java.util.TreeMap;

/* -------------------------------------------------
      _       _     ___                            
 __ _| |_ _ _(_)___/ __| __ __ _ _ _  _ _  ___ _ _ 
/ _` |  _| '_| / -_)__ \/ _/ _` | ' \| ' \/ -_) '_|
\__, |\__|_| |_\___|___/\__\__,_|_||_|_||_\___|_|  
|___/                                          
    
gtrieScanner: quick discovery of network motifs
Released under Artistic License 2.0
(see README and LICENSE)

Pedro Ribeiro - CRACS & INESC-TEC, DCC/FCUP

----------------------------------------------------
Graph (0-1) Tree Implementation

Last Update: 11/02/2012
---------------------------------------------------- */

/* -------------------------------------------------
      _       _     ___                            
 __ _| |_ _ _(_)___/ __| __ __ _ _ _  _ _  ___ _ _ 
/ _` |  _| '_| / -_)__ \/ _/ _` | ' \| ' \/ -_) '_|
\__, |\__|_| |_\___|___/\__\__,_|_||_|_||_\___|_|  
|___/                                          
    
gtrieScanner: quick discovery of network motifs
Released under Artistic License 2.0
(see README and LICENSE)

Pedro Ribeiro - CRACS & INESC-TEC, DCC/FCUP

----------------------------------------------------
Graph (0-1) Tree Implementation

Last Update: 11/02/2012
---------------------------------------------------- */




public class GraphTreeNode
{

  //#include "Isomorphism.h"

  public GraphTreeNode()
  {
	frequency = 0;
	zero = one = null;
  }
  public void dispose()
  {
	if (zero != null)
	{
		if (zero != null)
			zero.dispose();
	}
	if (one != null)
	{
		if (one != null)
			one.dispose();
	}
  }

  public int frequency;
  public GraphTreeNode zero;
  public GraphTreeNode one;

  public final void zeroFrequency()
  {
	frequency = 0;
	if (zero != null)
	{
		zero.zeroFrequency();
	}
	if (one != null)
	{
		one.zeroFrequency();
	}
  }
  public final void incrementString(int pos, String s)
  {
	if (s.charAt(pos) == ' ')//0)
	{
		frequency++;
		//System.out.println("NEW: "+ frequency + "\n");
	}
	else
	{
	  if (s.charAt(pos) == '1')
	  {
		if (one == null)
		{
			one = new GraphTreeNode();
		}
		one.incrementString(pos + 1, s);
	  }
	  else if (s.charAt(pos) == '0')
	  {
		if (zero == null)
		{
			zero = new GraphTreeNode();
		}
		zero.incrementString(pos + 1, s);
	  }
	}
  }
  public final void setString(int pos, String s, int f)
  {
	if (s.charAt(pos) == 0)
	{
		frequency = f;
	}
	else
	{
	  if (s.charAt(pos) == '1')
	  {
		if (one == null)
		{
			one = new GraphTreeNode();
		}
		one.setString(pos + 1, s, f);
	  }
	  else if (s.charAt(pos) == '0')
	  {
		if (zero == null)
		{
			zero = new GraphTreeNode();
		}
		zero.setString(pos + 1, s, f);
	  }
	}
  }
  public final void addString(int pos, String s, int f)
  {
	if (s.charAt(pos) == 0)
	{
		frequency += f;
	}
	else
	{
	  if (s.charAt(pos) == '1')
	  {
		if (one == null)
		{
			one = new GraphTreeNode();
		}
		one.addString(pos + 1, s, f);
	  }
	  else if (s.charAt(pos) == '0')
	  {
		if (zero == null)
		{
			zero = new GraphTreeNode();
		}
		zero.addString(pos + 1, s, f);
	  }
	}
  }
  public final void showFrequency(int pos, char [] s)
  {
	if (zero == null && one == null)
	{
	  s[pos] = 0;
	  System.out.printf(s +" :  "+ frequency +"\n" );
	}
	else
	{
	  if (zero != null)
	  {
		s[pos] = '0';
		zero.showFrequency(pos + 1, s);
	  }
	  if (one != null)
	  {
		s[pos] = '1';
		one.showFrequency(pos + 1, s);
	  }
	}
  }

 
  public final TreeMap< String, Integer> populateMap(TreeMap< String, Integer> m, int size, int pos, char [] s)
  {
	  	
	  //System.out.println(pos+  "   **  "+ "in Populate000 \n");
	if (zero == null && one == null)
	{
	  s[pos] = ' ';//0;
	  /*
	  String tt = "";
	  for(int t = 0; t< pos ; t++)
	  {
		  if(s[t] == '0')
			  tt += "0";
		  
		  if(s[t] == '1')
			  tt += "1";
	  }
	  System.out.println(tt + "in Populate \n");*/
	  char [] s2 = new char [size * size+1]; 
	  s2 = s;
	  //s2 = Isomorphism.canonicalBasedNauty(s, s2, size);
	  /*
	  System.out.println( s.toString() + "in Populate22 \n");
	  String tt2 = "";
	  for(int t = 0; t< pos ; t++)
	  {
		  if(s2[t] == '0')
			  tt2 += "0";
		  
		  if(s2[t] == '1')
			  tt2 += "1";
	  }
	  System.out.println( tt2 + "in Populate33 \n");*/
	  m.put(String.valueOf(s2), frequency);
	  
	}
	else
	{
	  if (zero != null)
	  {
		s[pos] = '0';
		//System.out.println(s.toString() + "   ****   "+  s[pos] +"    ZERO in Populate \n");
		zero.populateMap(m, size, pos + 1, s);
	  }
	  if (one != null)
	  {
		s[pos] = '1';
		//System.out.println(s.toString() + "    ONE in Populate \n");
		one.populateMap(m, size, pos + 1, s);
	  }
	}
	return m;
  }
  

  public final boolean equal(GraphTreeNode gt, int pos, char [] s)
  {
	if (zero == null && gt.zero != null)
	{
		return false;
	}
	if (zero != null && gt.zero == null)
	{
		return false;
	}
	if (one == null && gt.one != null)
	{
		return false;
	}
	if (one != null && gt.one == null)
	{
		return false;
	}

	if (zero == null && one == null)
	{
	  s[pos] = 0;
	  if (frequency != gt.frequency)
	  {
		System.out.printf("NOT EQUAL TREE " + s +" :  "+ frequency + " !=  "+ gt.frequency + "\n");
	  }
	  return (frequency == gt.frequency);
	}
	else
	{
	  boolean fzero;
	  boolean fone;
	  fzero = fone = true;
	  if (zero != null)
	  {
		  s[pos] = '0';
		fzero = zero.equal(gt.zero, pos + 1, s);
	  }
	  if (one != null)
	  {
		  s[pos] = '1';
		fone = one.equal(gt.one, pos + 1, s);
	  }
	  return fzero && fone;
	}
  }
  

  public final int countGraphs()
  {
	int aux = 0;
	if (frequency > 0)
	{
		aux++;
	}
	if (zero != null)
	{
		aux += zero.countGraphs();
	}
	if (one != null)
	{
		aux += one.countGraphs();
	}
	return aux;
  }
  public final double countOccurrences()
  {
	double aux = frequency;
	if (zero != null)
	{
		aux += zero.countOccurrences();
	}
	if (one != null)
	{
		aux += one.countOccurrences();
	}
	return aux;
  }
}