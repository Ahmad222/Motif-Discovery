package org.apache.spark.examples.gtrie;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;


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
	
	/*
	public void census(GraphMatrix g) throws FileNotFoundException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		
		int i;
		int subgraph_size = maxDepth();
		int num_nodes = g.numNodes();
		//printf("in1\n");
		
		_root.zeroFrequency();
		//printf("in2\n");
		
		GTrieNode.mymap = new int[subgraph_size];
		GTrieNode.used = new boolean[num_nodes];
		GTrieNode.numNodes = num_nodes;
		GTrieNode.fastnei = g.matrixNeighbours();
		GTrieNode.adjM = g.adjacencyMatrix();
		GTrieNode.numnei = g.arrayNumNeighbours();
		GTrieNode.writer =  new PrintWriter(GTrieNode.occ_file, "UTF-8");
		
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
					//gTrieNode.goCondUndir1();
				}
			GTrieNode.used[i] = false;
			
		}
		
		GTrieNode.writer.close();
	}
	
	*/
	
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
/*

	public GraphTree populateGraphTree(GraphTree tree, int size) {
		char s [] = new char[size*size + 1];
		s[size*size] = 0;
		tree = _root.child.get(0).populateGraphTree(tree, s, size);
		
		return tree;
	}
*/
}
