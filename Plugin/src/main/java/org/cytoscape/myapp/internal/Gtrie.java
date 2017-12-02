package org.cytoscape.myapp.internal;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;


public class Gtrie {
	
	
	private static final int MAX_BUF = 1024;
	private static final char BASE_FIRST = ' ';
	private static final int BASE_FORMAT = 95;
	private static final int BASE_BITS = 0;
	int depth;
	GTrieNode _root = null;
	public Gtrie() throws FileNotFoundException, UnsupportedEncodingException {
		_root = new GTrieNode(0);
		_root.cond_ok = _root.cond_this_ok = true;
	}
/*
	private boolean BIT_VALUE(char aux, int bits) {
		int r = (((aux)>>(bits))&1); 
		
		if(r ==0)return false;
		
		return true;
	}
	*/
	
	
	public void census(GraphMatrix g) throws FileNotFoundException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		
		int i;
		int subgraph_size = maxDepth();
		int num_nodes = g.numNodes();
		//printf("in1\n");
		//System.out.println("max Depth:  " + subgraph_size);
		_root.zeroFrequency();
		//printf("in2\n");
		GTrieNode.mymap = new int[subgraph_size];
		GTrieNode.used = new boolean[num_nodes];
		GTrieNode.numNodes = num_nodes;
		GTrieNode.fastnei = g.matrixNeighbours();
		GTrieNode.adjM = g.adjacencyMatrix();
		GTrieNode.numnei = g.arrayNumNeighbours();
		//GTrieNode.writer =  new PrintWriter(GTrieNode.occ_file, "UTF-8");
		
		//printf("in3\n");
		if (g.type() == GraphType.DIRECTED) GTrieNode.isdir = true;
		else                       			GTrieNode.isdir = false;
		for (i = 0; i<num_nodes; i++)
			GTrieNode.used[i] = false;

		
		LinkedList<GTrieNode> c =  _root.child;
		LinkedList<GTrieNode> c2 =  c.get(0).child;
		
		
		GTrieNode.glk = 1;
		GTrieNode.counterT = 0;
		//printf("in5\n");
		
		for (i = 0; i<num_nodes; i++) {
			GTrieNode.mymap[0] = i;
			GTrieNode.used[i] = true;
			
			if (g.type() == GraphType.DIRECTED)
				for (GTrieNode  gTrieNode : c2) {
					gTrieNode.goCondDir();
				}
			else
				for (GTrieNode  gTrieNode : c2) {
					gTrieNode.goCondUndir();
				}
			GTrieNode.used[i] = false;
			
		}
		
		//GTrieNode.writer.close();
	}
	private int maxDepth() {
		// TODO Auto-generated method stub
		return _root.maxDepth();
		
	}



	public void readFromFile(String s) throws IOException {
		
		FileInputStream fs = new FileInputStream(s);
		char dummy [];
		byte[] buf = new byte[MAX_BUF];
		
		_root.readFromFile(fs);
		//_root.readFromFile(buffReader);
		fs.close();
		//f2.close();
	}
	public void readFromFile2(FileInputStream is) throws IOException {
		FileInputStream fs = is;
		//FileInputStream fs = new FileInputStream(s);
		//char dummy [];
		//byte[] buf = new byte[MAX_BUF];
		
		_root.readFromFile(fs);
		//_root.readFromFile(buffReader);
		fs.close();
		//f2.close();
	}

/*
	public String[] populateGraphTree(int size) {
		TreeMap< String, Long> tree = new TreeMap<String, Long>();
		char s [] = new char[size*size + 1];
		s[size*size] = 0;
		s[0] = '0';
		tree = _root.child.get(0).child.get(0).populateGraphTree(tree, s, size);
		String [] reqF = reqFormat(tree);
		return reqF;
	}
	
	
	public TreeMap<String, Long> populateGraphTree2(int size) {
		TreeMap< String, Long> tree = new TreeMap<String, Long>();
		char s [] = new char[size*size + 1];
		s[size*size] = 0;
		s[0] = '0';
		tree = _root.child.get(0).child.get(0).populateGraphTree(tree, s, size);
		
		return tree;
	}
	*/
	public String[] populateGtrie( int size)
	{
		
		GTrieNode r = _root.child.get(0);
		TreeMap< String, Long> tree = new TreeMap<String, Long>();
		char s [] = new char[size*size + 1];
		s[size*size] = 0;
		s[0] = '0';
		
		
		//System.out.println(("Root child size:  ") + r.child.size());
		for(GTrieNode roro: r.child)
		{
			//System.out.println(("Iter :  ") + roro.child.size());
			int i, pos = roro.depth - 1;

			for (i = 0; i< roro.depth; i++) {
				s[pos*size + i] = roro.out[i] ? '1' : '0';
				s[i*size + pos] = roro.in[i] ? '1' : '0';
			}
			
			tree = roro.populateGtrie(roro, tree, s, size);
		}
		
		//System.out.println(("Count:  ") + tree.size());
		return reqFormat(tree);
	}
	public TreeMap<String, Long> populateGtrie2( int size)
	{
		
		GTrieNode r = _root.child.get(0);
		TreeMap< String, Long> tree = new TreeMap<String, Long>();
		char s [] = new char[size*size + 1];
		s[size*size] = 0;
		s[0] = '0';
		
		
		//System.out.println(("Root child size:  ") + r.child.size());
		for(GTrieNode roro: r.child)
		{
			//System.out.println(("Iter :  ") + roro.child.size());
			int i, pos = roro.depth - 1;

			for (i = 0; i< roro.depth; i++) {
				s[pos*size + i] = roro.out[i] ? '1' : '0';
				s[i*size + pos] = roro.in[i] ? '1' : '0';
			}
			
			tree = roro.populateGtrie(roro, tree, s, size);
		}
		
		//System.out.println(("Count:  ") + tree.size());
		return tree;
	}
	
	
	
	
	public String [] popTemporary()
	{
		String [] reqF = null;//reqFormat(GTrieNode.myOccs);
		return reqF;
	}
	public static String[] reqFormat(TreeMap<String, Long> m2) {
		String reqF [] = new String[m2.size()+1];
		// ror[0] = "**"+ 22 +"#" + 11;//ESU.total_occ;
	    //ror[1] = "011101110#21#34";
		//reqF[0] = "**"+ m2.size() +"#"+GTrieNode.counterT;
		int i = 1;
		int numTypes= 0;
		for(Map.Entry<String,Long> entry : m2.entrySet()) {
					
			String key = entry.getKey();
			Long value = entry.getValue();
			
			if(value > 0)
			{
				numTypes++;
			}
			reqF[i] = key+"#"+ value+"#0";
			i++;
		}
		reqF[0] = "**"+ numTypes +"#"+GTrieNode.counterT;
		return reqF;
	}
	
	
	
	
	
	
	
	public void censusSample(GraphMatrix g, double prob) throws FileNotFoundException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		
		int i;
		int subgraph_size = maxDepth();
		int num_nodes = g.numNodes();
		//printf("in1\n");
		//System.out.println("max Depth:  " + subgraph_size);
		_root.zeroFrequency();
		//printf("in2\n");
		GTrieNode.mymap = new int[subgraph_size];
		GTrieNode.used = new boolean[num_nodes];
		GTrieNode.numNodes = num_nodes;
		GTrieNode.fastnei = g.matrixNeighbours();
		GTrieNode.adjM = g.adjacencyMatrix();
		GTrieNode.numnei = g.arrayNumNeighbours();
		//GTrieNode.writer =  new PrintWriter(GTrieNode.occ_file, "UTF-8");
		GTrieNode.prob = prob;
		//printf("in3\n");
		if (g.type() == GraphType.DIRECTED) GTrieNode.isdir = true;
		else                       			GTrieNode.isdir = false;
		for (i = 0; i<num_nodes; i++)
			GTrieNode.used[i] = false;

		
		LinkedList<GTrieNode> c =  _root.child;
		LinkedList<GTrieNode> c2 =  c.get(0).child;
		
		
		GTrieNode.glk = 1;
		GTrieNode.counterT = 0;
		//printf("in5\n");
		
				for (GTrieNode  gTrieNode : c2) {
					if(Rand.random.nextDouble() <= GTrieNode.prob)
						gTrieNode.goCondSample();
				}
				
	}
	

	
}
