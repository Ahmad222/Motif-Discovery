package org.apache.spark.examples.gtrie;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;
import java.util.LinkedList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GraphUtils
{

  private static int[] _degree;

  private static int[][] _ds;
  private static int[] _neighbours;




  // Compare two integers
  public static int int_compare(Object a, Object b)
  {
	return ((Integer)b) - ((Integer)a);
  }

  // Read file 's', with direction 'dir' to graph 'g'
  public static void readFileTxt(GraphMatrix g, String s, boolean dir, boolean weight, int base) throws IOException
  {
	  BufferedReader br = new BufferedReader(new FileReader(s));
		
	//FILE f = fopen(s, "r");
	

	int i;
	int a;
	int b;
	int c;
	int size;
	int max;
	ArrayList<Integer> va = new ArrayList<Integer>();
	ArrayList<Integer> vb = new ArrayList<Integer>();

	size = max = 0;
	
	
	
	
	
	
	try {
	    
	    String line = br.readLine();
	    if (line == null)
		  {
			  System.exit(1);
		  }
	   
	   
	    String [] ints = new String [3];
	    while (line != null) {
	    	
	        ints = line.split(" ");
	        a = Integer.parseInt(ints[0]);
	        b = Integer.parseInt(ints[1]);
	        c = Integer.parseInt(ints[2]);
	    	va.add(a);
			vb.add(b);
			if (a > max)
			{
				max = a;
			}
			if (b > max)
			{
				max = b;
			}
			size++;
	    	
	        line = br.readLine();
	    }
	    
	} finally {
	    br.close();
	}

	
	if (dir)
	{
		g.createGraph(max, GraphType.DIRECTED);
	}
	else
	{
		g.createGraph(max, GraphType.UNDIRECTED);
	}

	for (i = 0; i < size; i++)
	{
	  if (va.get(i) != vb.get(i))
	  {
		g.addEdge(va.get(i) - 1, vb.get(i) - 1);
	  }
	}

	g.preProcess();
	va.clear();
	vb.clear();
  }

  // Convert adjacency matrix to graph of 'size' nodes
  public static void strToGraph(GraphMatrix g, String s, int size, boolean dir)
  {
	int i;
	int j;

	if (dir)
	{
		g.createGraph(size, GraphType.DIRECTED);
	}
	else
	{
		g.createGraph(size, GraphType.UNDIRECTED);
	}

	for (i = 0; i < size; i++)
	{
	  for (j = 0; j < size; j++)
	  {
		if (s.charAt(i * size + j) == '1')
		{
			g.addEdge(i, j);
		}
	  }
	}
  }


  // Order graph by increasing degree, then by increasing neighbour degree sequence
  public static void orderGraph(GraphMatrix old, GraphMatrix g)
  {
	int i;
	int j;
	int aux;
	int size = old.numNodes();
	int[] degree = new int[size];

	int[][] ds = new int[size][size];
	int[] neighbours = new int[size];
	ArrayList<Integer> v;

	for (i = 0; i < size; i++)
	{
	  degree[i] = old.nodeOutEdges(i) + old.nodeInEdges(i);
	  neighbours[i] = old.numNeighbours(i);
	  ds[i] = new int[neighbours[i]];
	  v = old.neighbours(i);
	  for (j = 0; j < neighbours[i]; j++)
	  {
		ds[i][j] = old.nodeOutEdges(v.get(j)) + old.nodeOutEdges(v.get(j));
	  }
	  //qsort(ds[i], old.numNeighbours(i), Integer.SIZE, _compare_int);
	  Arrays.sort(ds[i]);
	}

	_degree = degree;
	_ds = ds;
	_neighbours = neighbours;
	int[] n = new int[size];
	int[] r = new int[size];
	for (i = 0; i < size; i++)
	{
		n[i] = i;
	}
	Arrays.sort(n);
	//qsort(n, size, Integer.SIZE, _compare_degree);
	for (i = 0; i < size; i++)
	{
		r[n[i]] = i;
	}

	for (i = 0; i < size; i++)
	{
	  ds[i] = null;
	}

	g.createGraph(size, old.type());
	for (i = 0; i < size; i++)
	{
	  v = old.outEdges(i);
	  aux = v.size();
	  for (j = 0; j < aux; j++)
	  {
		g.addEdge(r[i], r[v.get(j)]);
	  }
	  v = old.inEdges(i);
	  aux = v.size();
	  for (j = 0; j < aux; j++)
	  {
		g.addEdge(r[v.get(j)], r[i]);
	  }
	}

  }
  public static int _compare_int(Object a, Object b)
  {
	return ((Integer)a) - (Integer)b;
  }
  public static int _compare_int_descending(Object a, Object b)
  {
	return ((Integer)b) - (Integer)a;
  }
  public static int _compare_degree(Object a, Object b)
  {
	int n1 = (Integer)a;
	int n2 = (Integer)b;

	if (_neighbours[n1] < _neighbours[n2])
	{
		return -1;
	}
	if (_neighbours[n1] > _neighbours[n2])
	{
		return +1;
	}

	return 0;
  }

}
