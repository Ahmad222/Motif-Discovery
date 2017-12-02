package org.cytoscape.myapp.internal;


import java.util.TreeMap;

public class GraphTree
{

  public GraphTreeNode root;


  // -----------------------------------

  public GraphTree()
  {
	root = new GraphTreeNode();
  }
  public void dispose()
  {
	
		if (root != null)
			root.dispose();
	
  }

  public final void zeroFrequency()
  {
	root.zeroFrequency();
  }
  public final void incrementString(String s)//tangible.RefObject<String> s)
  {
	root.incrementString(0, s);
  }
  public final void setString(String s, int f)
  {
	root.setString(0, s, f);
  }
  public final void addString(String s, int f)
  {
	root.addString(0, s, f);
  }
  public final void showFrequency(int maxsize)
  {
	  char [] s = new char[maxsize * maxsize+1];
	  root.showFrequency(0, s);
	
  }

  public final boolean equal(GraphTree gt, int maxsize)
  {
	//String s = new String(new char[maxsize * maxsize+1]);
	//tangible.RefObject<String> tempRef_s = new tangible.RefObject<String>(s);
	//return root.equal(gt.root, 0, tempRef_s);
	//s = tempRef_s.argValue;
	  char [] s = new char[maxsize * maxsize+1];
	  return root.equal(gt.root, 0, s);
  }
  
  
  public final TreeMap< String, Integer> populateMap(TreeMap< String, Integer> m, int maxsize)
  {
	  char [] s = new char[maxsize * maxsize+1];
	  return root.populateMap(m, maxsize, 0, s);
	
  }
  

  public final int countGraphs()
  {
	return root.countGraphs();
  }
  public final double countOccurrences()
  {
	return root.countOccurrences();
  }
}