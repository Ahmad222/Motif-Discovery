package org.cytoscape.myapp.internal;

import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;


//import edu.ucla.sspace.graph.isomorphism.*;
//import edu.ucla.sspace.graph.*;
//import edu.ucla.sspace.util.primitive.IntSet;


public class ESU {
	public static GraphMatrix g;
	public static boolean dir = false;
	public static String graph_file = new String(new char[100]);
	public static int motif_size;
	public static int graph_size;
	public static int[] current;
	public static int[] ext;
	public static int nextv;
	public static long total_occ = 55;
	public static TreeMap<String, int []> myMap2 = new TreeMap<String, int []>();
	public static double prob = 0;
	public static String[] countSubgraphs(GraphMatrix g1, int k)
	{
		 int i;
		  int[] v = new int[1];
		  g = g1;
		  graph_size = g.numNodes();
		  current = new int[k];
		  ext = new int[graph_size];
		  nextv = 0;
		  total_occ = 0;
		  motif_size = k;
		  long numoccs = 0;
		  TreeMap<String, Long> resultMap = new TreeMap<String, Long>();
		  TrieNode trie = new TrieNode();
		  for (i = 0; i < graph_size; i++)
		  {
			  trie = go2(i, 0, 0, v, trie);
			//go(i, 0, 0, v);
		  }

		  //current = null;
		  //ext = null;
		  
		  char [] ss = new char [ (motif_size * motif_size) +1];
		  resultMap = trie.populateMap(resultMap, motif_size, 0 , ss);
	/*		
			//////////////ISO TEST//////////////
			
			LinkedList<Graph<Edge>> gtest = new LinkedList<Graph<Edge>>();
			LinkedList<Graph<DirectedEdge>> gtestD = new LinkedList<Graph<DirectedEdge>>();
			LinkedList<Long> freqtest = new LinkedList<Long>();
			IsomorphismTester isoTest1 = new VF2IsomorphismTester();
			long startIsoTime = System.nanoTime();
			TreeMap<String, Long> m2 = new TreeMap<String, Long>();
			
			if(dir)
			 {
				 for(Map.Entry<String,Long> entry : resultMap.entrySet()) {
						
					String key = entry.getKey();
					Long value = entry.getValue();
					Graph<DirectedEdge> grT = strToGraphD(key);
														
					gtestD.add(grT);
					freqtest.add(value);
														
				}
													
													
				long partSum = 0;
				char [] partG = new char [ motif_size *  motif_size + 1];
				for(int c3 = 0; c3< gtestD.size() ; c3++)
				{
					Graph<DirectedEdge> gr1 = gtestD.get(c3);
					if(freqtest.get(c3) == 0) continue;
					//m2.put(gr1.toString(), freqtest.get(c3));
					partSum = freqtest.get(c3);
					for(int jj = c3+1; jj< gtestD.size() ; jj++)
					{	if(freqtest.get(jj) == 0) continue;
						Graph<DirectedEdge> gr2 = gtestD.get(jj);
															
						if(isoTest1.areIsomorphic(gr1, gr2)) 
							{
								//System.out.println("Already   " + gr1.toString() + freqtest.get(j));
								partSum += freqtest.get(jj);
								freqtest.set(jj,new Long(0));
							}
					}
					String sFG = graphToStringD(gr1);
														
					m2.put(sFG , partSum );
					//System.out.println("RRR: Adding" + String.valueOf(partG) + "   " +partSum );
				}
			}
			else
			{
				for(Map.Entry<String,Long> entry : resultMap.entrySet()) {
						
					String key = entry.getKey();
					Long value = entry.getValue();
														
					Graph<Edge> grT = strToGraphU(key);
														
					gtest.add(grT);
					freqtest.add(value);
					//System.out.printf(key + " :::: "+value + "\n");
				}
													
													
				long partSum = 0;
				char [] partG = new char [ motif_size *  motif_size + 1];
				for(int c3 = 0; c3< gtest.size() ; c3++)
				{
					Graph<Edge> gr1 = gtest.get(c3);
					if(freqtest.get(c3) == 0) continue;
					//m2.put(gr1.toString(), freqtest.get(c3));
					partSum = freqtest.get(c3);
					for(int jj = c3+1; jj< gtest.size() ; jj++)
					{	if(freqtest.get(jj) == 0) continue;
						Graph<Edge> gr2 = gtest.get(jj);
															
						if(isoTest1.areIsomorphic(gr1, gr2)) 
						{
							//System.out.println("Already   " + gr1.toString() + freqtest.get(j));
							  partSum += freqtest.get(jj);
							 freqtest.set(jj,new Long(0));
						}
					}
					String sFG = graphToStringU(gr1);
														
					m2.put( sFG, partSum );
					//System.out.println("Adding" + String.valueOf(partG) + "   " +partSum );
				}
													
			}
			
			*/
			////////////////////////////////////
			String reqFormatOut [] = reqFormat(resultMap); 
			///////////////////////////////////
		  return reqFormatOut;
	}
	
	public static String[] reqFormat(TreeMap<String, Long> m2) {
		String reqF [] = new String[m2.size()+1];
		// ror[0] = "**"+ 22 +"#" + 11;//ESU.total_occ;
	    //ror[1] = "011101110#21#34";
		reqF[0] = "**"+ m2.size() +"#"+total_occ;
		int i = 1;
		for(Map.Entry<String,Long> entry : m2.entrySet()) {
					
			String key = entry.getKey();
			Long value = entry.getValue();
			
			reqF[i] = key+"#"+ value+"#0";
			i++;
		}
		return reqF;
	}

	public static long countSubgraphsSample(GraphMatrix g1, int k, double p)
	{
	  int i;
	  int[] v = new int[1];
	  g = g1;
	  graph_size = g.numNodes();
	  current = new int[k];
	  ext = new int[graph_size];
	  nextv = 0;
	  total_occ = 0;
	  motif_size = k;
	  long numoccs = 0;
	  prob = p;
	  for (i = 0; i < graph_size; i++)
	  {
		  if(Rand.random.nextDouble()<= prob)
			  numoccs = go1(i, 0, 0, v, numoccs);
		//go(i, 0, 0, v);
	  }

	  //current = null;
	  //ext = null;
	  return numoccs;
	}

	public static long go1(int n, int size, int nextv, int[] ext, long numoccs)
	{
		//System.out.printf("In go1  \n");
		try {
			current[size++] = n;
		} catch (Exception e) {
			System.out.printf("\n \n\n ERROR \n\n \n" + e.toString());
			
		}
	  

	  if (size == motif_size)
	  {
		
		//String s = new String(new char[motif_size * motif_size+1]);
		//s = Isomorphism.canonicalStrNauty(g, current, s);
		//_sg.incrementString(s);
		total_occ++;
		numoccs++;
	  }
	  else
	  {
		int i;
		int j;
		int nextv2 = nextv;
		int[] ext2 = new int[graph_size];

		for (i = 0;i < nextv;i++)
		{
			ext2[i] = ext[i];
		}

		int[] v = g.arrayNeighbours(current[size-1]);
		int num = g.numNeighbours(current[size-1]);


		for (i = 0;i < num;i++)
		{
		  if (v[i] <= current[0])
			  continue;
		  for (j = 0;j + 1 < size;j++)
		  {
		if (g.isConnected(v[i], current[j]))
			break;
		  }
		  if (j + 1 == size)
		  {
			  ext2[nextv2++] = v[i];
		  }
		}

		while (nextv2 > 0)
		{
		  nextv2--;
		  if(Rand.random.nextDouble()<= prob)
		  numoccs =  go1(ext2[nextv2], size, nextv2, ext2, numoccs);
		}
	  }
	  return numoccs;
	}
	
	public static TrieNode go2(int n, int size, int nextv, int[] ext, TrieNode trie)
	{
		//System.out.printf("In go1  \n");
		try {
			current[size++] = n;
		} catch (Exception e) {
			System.out.printf("\n \n\n ERROR \n\n \n" + e.toString());
			
		}
	  

	  if (size == motif_size)
	  {
		
		//String s = new String(new char[motif_size * motif_size+1]);
		//s = Isomorphism.canonicalStrNauty(g, current, s);
		//_sg.incrementString(s);
		  char [] ss = new char[(motif_size * motif_size )+1];
			//Create the graph dir/undir
			boolean flagFound = false;
			boolean adjM[][] = g.adjacencyMatrix();
			int aux = 0;
			for(int i=0 ;i<current.length ; i++)
				{
					for (int ii = 0; ii < current.length; ii++) {
						if (adjM[current[i]][current[ii]])
						{	
							ss[aux++] = '1';
						}
						else
						{
							ss[aux++] = '0';
						}
						
					}
				}
			ss[aux] = 0;
			trie.incrementString(0,ss);
			
			String subG = String.valueOf(ss).trim();
			if (!myMap2.containsKey(subG))//count(s) == 0)
			{
				int [] dd = new int[graph_size]; //NEW
				
				for (int ie = 0; ie < graph_size; ie++)
				{
					dd[ie] = 0;
					//d.nodez[ie] = 0;
				}

				for (int ie = 0; ie < current.length; ie++)
				{
					dd[current[ie]]++;
					//d.nodez[mymap[i]] ++;
					
				}
				
				myMap2.put(subG, dd);//insert(pair<string, nodes>(s, d));
				//printf("Inserting %s \n",s);

			}
			else
			{
				int [] dd = myMap2.get(subG); 
				
				//nodes r = myMap2[s];
				for (int ie = 0; ie <current.length; ie++)
				{
					dd[current[ie]] ++;	
				}
				
				myMap2.put(subG, dd);
				
				
			}
			
			
		total_occ++;
		//numoccs++;
	  }
	  else
	  {
		int i;
		int j;
		int nextv2 = nextv;
		int[] ext2 = new int[graph_size];

		for (i = 0;i < nextv;i++)
		{
			ext2[i] = ext[i];
		}

		int[] v = g.arrayNeighbours(current[size-1]);
		int num = g.numNeighbours(current[size-1]);


		for (i = 0;i < num;i++)
		{
		  if (v[i] <= current[0])
			  continue;
		  for (j = 0;j + 1 < size;j++)
		  {
		if (g.isConnected(v[i], current[j]))
			break;
		  }
		  if (j + 1 == size)
		  {
			  ext2[nextv2++] = v[i];
		  }
		}

		while (nextv2 > 0)
		{
		  nextv2--;
		  trie =  go2(ext2[nextv2], size, nextv2, ext2, trie);
		}
	  }
	  return trie;
	}
	
	
	/*
	
	public static Graph<DirectedEdge> strToGraphD(String s)
	{
		Graph<DirectedEdge> grConverted = new SparseDirectedGraph();
		int cc = 0;
		for(int c1 = 0; c1 < motif_size ; c1 ++)
		{
		  for(int c2 = 0; c2< motif_size ; c2++)
		    {
				if(s.charAt(cc++) == '1')
				grConverted.add(new SimpleDirectedEdge(c1, c2));
			}
		}
		
		return grConverted;
		
	}
	
	public static Graph<Edge> strToGraphU(String s)
	{
		Graph<Edge> grConverted = new SparseUndirectedGraph();
		int cc = 0;
		for(int c1 = 0; c1 < motif_size ; c1 ++)
		{
			for(int c2 = 0; c2< motif_size ; c2++)
			 {
				if(s.charAt(cc++) == '1')
				grConverted.add(new SimpleEdge(c1, c2));
			 }
		}
		
		return grConverted;
		
	}
	
	public static String graphToStringD(Graph<DirectedEdge> gr1)
	{
		char [] partG = new char [ motif_size * motif_size + 1];
		int ctr = 0;
		for(int c4 = 0; c4 <  motif_size ; c4 ++)
		{
			for(int c5 = 0; c5 <  motif_size ; c5 ++)
				{
					SimpleDirectedEdge edg = new SimpleDirectedEdge(c4,c5);
					if(gr1.contains(edg)) partG[ctr++] = '1';
					else  partG[ctr++] = '0';
														
				}
		}
		partG[ctr] = 0;
		
		return String.valueOf(partG); 
	}
	
	public static String graphToStringU(Graph<Edge> gr1)
	{
		char [] partG = new char [ motif_size * motif_size + 1];
		int ctr = 0;
		for(int c4 = 0; c4 <  motif_size ; c4 ++)
		{
			for(int c5 = 0; c5 <  motif_size ; c5 ++)
			{
														
				if(gr1.contains(c4, c5)) partG[ctr++] = '1';
				else  partG[ctr++] = '0';

			}
		}
		partG[ctr] = 0;
		return String.valueOf(partG); 
		
	}
	
	*/
}
