package org.apache.spark.examples.gtrie;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.*;

public class GTrieNode {
	private static final int MAX_BUF = 1024;
	private static final char BASE_FIRST = ' ';
	private static final int BASE_FORMAT = 95;
	private static final int BASE_BITS = 6;
	//public static int[] mymap;
	//public static boolean[] used;
	public static int numNodes;
	public static int[][] fastnei;
	public static boolean[][] adjM;
	public static int[] numnei;
	public static boolean isdir;
	public static int statCounter = 0;
	//public static int glk;
	//public static int counterT = 0;
	int depth;
	double frequency ;
	int gNId;
	//public static LinkedList<GTrieNode []> child = null;
	public LinkedList<GTrieNode> child;
	Pair<Character, Character> p;
	Pair<Integer,Integer> p2;
	LinkedList<LinkedList<Pair>> cond = null;
	 int[] conn = null;
     int nconn;
     boolean is_graph;
	boolean cond_ok;
	int ncond;
	 boolean[] out = null;
     boolean[] in = null;
     int total_in;       // Number of inward  edges
     int total_out;      // Number of outward edges
     int total_edges;    // Number of inward + outward edges
     
     LinkedList< LinkedList<Integer> >   this_node_cond; // This node must be bigger than all these nodes
     boolean cond_this_ok;
     
       
	public GTrieNode(int d) throws FileNotFoundException, UnsupportedEncodingException {
		//writer = new PrintWriter(occ_file, "UTF-8");
		cond  = new LinkedList<LinkedList<Pair>>();
		depth = d;

		is_graph = false;
		frequency = 0L;
		nconn = 0;

		if (d != 0) {
			in = new boolean[d];
			out = new boolean[d];
			conn = new int[d];
		}
		else {
			in = out = null;
			conn = null;
		}

		total_in = total_out = total_edges = 0;
		child = new LinkedList<GTrieNode>();
		child.clear();
		this_node_cond = new LinkedList<LinkedList<Integer>>();
		this_node_cond.clear();

		cond_ok = false;
		cond_this_ok = false;
		gNId = statCounter++;
	}
	public final void readFromFile(FileInputStream inputStream ) throws IOException
	{
	  int nchilds;
	  int i;
	  int j;
	  int pos;
	  int bits;
	  char aux;
	  char [] buff =  new char[MAX_BUF];
	  String buf = new String(new char[MAX_BUF]);
	  byte[] buffer = new byte[MAX_BUF];
	  byte[] buffer2 = new byte[MAX_BUF];
	  GTrieNode c;
	  int total = 0;
      int nRead = 0;
	  boolean cond_ok ;
      int countAux = 0;
      String line = " A";
      while(true)
      {
    	  int nRead1 = inputStream.read(buffer2, 0, 1);
    	  buffer[countAux] = buffer2[0];
    	  
    	  if(buffer[countAux] == 10)
    		  break;
    	  countAux++;
    	  if(nRead1 == -1)
    		  line = null;
      }
      if(line  != null){
    	  
    	for (int totot = 0; totot < MAX_BUF; totot++)
  		{
    		if (buffer[totot] == 0)
  				break;
  		}
   		  int totot = (int)buffer[0];
    	  aux =  (char) ((int)buffer[0] - (int)BASE_FIRST);//(char) buff[0];
    	  if(BIT_VALUE(aux, 0))//(aux == BASE_FIRST)
    	  { is_graph = true;
    	  }
    	  else
    		  is_graph = false;
    	  
    	  int nbytes = aux >> 1;

  		pos = 1;
  		for (i = 0, j = 1, nchilds = 0; i < nbytes; i++, j *= BASE_FORMAT)
  		{
  		  aux = (char) (((int)buffer[pos++]) - ((int)BASE_FIRST));
  		  nchilds += (int)aux * j;
  		}
  		
  	// Connections: outgoing
  			aux = (char) (buffer[pos++] - BASE_FIRST);
  			for (i = 0, bits = 0; i < depth; i++, bits++)
  			{
  			  if (bits == BASE_BITS)
  			  {
  				  aux = (char) (buffer[pos++] - BASE_FIRST);
  				  bits = 0;
  			  }
  			  if (BIT_VALUE(aux, bits))
  			  {
  				  out[i] = true;
  			  }
  			  else
  			  {
  				  out[i] = false;
  			  }
  			}

  			//System.out.println("After OUTGoing: POS: "+ pos + " Depth: " + depth);

  			// Connections: ingoing
  			aux = (char) (buffer[pos++] - BASE_FIRST);
  			for (i = 0, bits = 0; i < depth; i++, bits++)
  			{
  			  if (bits == BASE_BITS)
  			  {
  				  aux = (char) (buffer[pos++] - BASE_FIRST);
  				  bits = 0;
  			  }
  			  if (BIT_VALUE(aux, bits))
  			  {
  				  in[i] = true;
  			  }
  			  else
  			  {
  				  in[i] = false;
  			  }
  			}

  			
  			
  			//System.out.println("After INGoing: POS: "+ pos + " Depth: " + depth);

  			// Previous Conditions
  			int t = buffer[pos];
  			byte tt = buffer[pos];
  			char ttt = (char) buffer[pos];
  			int test = (buffer[pos] - BASE_FIRST);
  			
  			aux = (char) (buffer[pos++] - BASE_FIRST);
  			int tttre = (int) aux;
			if (aux == 0)
  			{
  				this.cond_ok = true;
  				//System.out.println("cond_ok = true");
  			}
  			else
  			{
  				this.cond_ok = false;
  				//System.out.println("cond_ok = false");
  			}

			//System.out.println("Before aux"+ (int)aux + "  POS:"+ pos);
			if(buffer[pos] == 10 || buffer[pos] ==0 ) aux = 0;
  			if (aux > 0)
  			{
  			  ncond = aux;
  			  for (i = 0; i < ncond; i++)
  			  {
  				  LinkedList<Pair> newcond = new LinkedList<Pair>();
	  			while (true)
	  			{
	  				//if(pos < line.length())
	  				int LOLO = buffer[pos];
	  			  aux = (char) ((buffer[pos++] - BASE_FIRST) - 1);
	  			  test = (int)aux;
	  			  //System.out.println("Inside aux "+ (int)aux + "  POS:"+ pos + "Ncond: " + ncond + "Buffer" + LOLO);
	  			  if ((int)aux < 0 || (int)aux > 6000)
	  				  {
	  				  	aux = (char) -1;
	  				  	break;
	  				  
	  				  }
	  			  
	  			  p = new Pair<Character, Character>(' ', ' ');
	  			  p2 = new Pair<Integer, Integer>(0, 0);
	  			  p2.setFirst((int)aux);
	  			  p.setFirst(aux); //= aux;
	  			  //char aux2;
	  			  aux = (char) (buffer[pos++] - BASE_FIRST - 1);
	  			  p.setSecond(aux);
	  			  p2.setSecond((int)aux);
	  			  test = (int)aux;
				  newcond.add(p2);
	  			}
				cond.add(newcond);
	  		}
  			}

  			
  			if (buffer[pos] != '\n')
  			{
  				System.out.println("ERROR");
  			 
  			}

  			
  			
  		// Conn and nconn variables (was missing)
  			for (i = 0; i < depth; i++)
  			{
  			  if (out[i] || in[i])
  			  {
  			  
  				  conn[(nconn)++] = i;
  			  }
  			}


  			for (i = 0; i < nchilds; i++)
  			{
  			  c = new GTrieNode(depth + 1);
  			  c.readFromFile(inputStream);
  			 
  			  this.child.add(c);
  			}

  		  
  			
  			
  			
  			
          // Convert to String so we can display it.
          // Of course you wouldn't want to do this with
          // a 'real' binary file.
          //System.out.println(new String(buffer));
          total += nRead;
      }   

      
     // inputStream.close();        

	  
	
	}
	private boolean BIT_VALUE(char aux, int bits) {
		// TODO Auto-generated method stub
		int r = (((aux)>>(bits))&1); 
		
		if(r ==0)return false;
		
		return true;
		
	}
	public void zeroFrequency() {
		frequency = 0L;
		if(this.child.size() > 0)
		for (GTrieNode gTrieNodes : this.child) {
			gTrieNodes.zeroFrequency();
		}
		
	}
	public void goCondDir(WorkerData data, long sTime, WorkerData state) {
		
		
		int i;
		int j;
		int ci;
		int mylim;
		int glaux;
		int ncand;
		
		int p;
		char ft,st;
		int fft, sst;
			
		mylim =Integer.MAX_VALUE;
		  
		if (!this.cond_ok)
		  {
			
			i = 1;
			for(int jN=0; jN < cond.size()  ;  ++jN )
			{
				glaux = -1;
				
				LinkedList<Pair> jj2N = cond.get(jN);
				Pair kk3 = jj2N.get(jj2N.size()-1);
				int kN = 0;
				for(kN = 0 ; kN < jj2N.size() ; ++kN){
					Pair kk2N = jj2N.get(kN);
					fft =  (int) kk2N.getFirst();//(int) ft;
					sst = (int) kk2N.getSecond();//(int) st;
					int tat = data.mymap[fft];
					int tat2 = data.mymap[sst];
					if ((sst < data.glk) && (data.mymap[fft] > data.mymap[sst]))
						  break;
					else if (sst == data.glk && data.mymap[fft] > glaux)
					{		
						glaux = data.mymap[fft];
					}	
				}
				if(kN > 0)
				if (kk3 == jj2N.get(kN -1))
				  {
					i = 0;
					if (glaux < mylim)
					{
						mylim = glaux;
					}
				  }
			}
			
			if (i != 0)
				return;
		  }
		
		  if (mylim == Integer.MAX_VALUE)
		  {
			  mylim = 0;
		  }
	    
		  ncand = 0;
		  j = ci = Integer.MAX_VALUE;
		  
		  for (i = 0; i < nconn; i++)
		  {
			glaux = numnei[data.mymap[conn[i]]];
			if (glaux < j)
			{
			  ci = data.mymap[conn[i]];
			  j = glaux;
			}
		  }
	    
		  glaux = j;
		  ncand = ci;
		 for(ci = glaux -1 ; ci >= 0 ; ci--)
		  {
			i= fastnei[ncand][ci];
			if (i < mylim)
				break;
			if (data.used[i])
				continue;
			//////// save state
			if((System.nanoTime() - sTime) > Gtries.broadcastStepTime.value()) 
		      {
				  String nodes = String.valueOf(i);//fastnei[ncand][ci]);
				  ci--;
				  int ctr = 0;
				  for(; ci >= 0 ; ci--)
					{
						nodes += "-" + fastnei[ncand][ci];
						ctr++;
					}
					  state = createWorkFromArray(state, nodes, this, mylim,  data.mymap); //data.glk,
					  return;
				  
			  }
			///////// Finish saving state
		
		//System.out.println("GLK:  "+ data.glk +" mymap length" + data.mymap.length);
		//System.out.println("111Worker:  "+ data.wid + "   GLK: " + data.glk + "    Depth: " + this.depth + "   GNode: " + this.gNId );
			data.glk = this.depth -1;///// TEST
			data.mymap[data.glk] = i;
	    
		
			boolean test;
				
			for (j = 0; j < data.glk; j++)
			{
				
				test = adjM[data.mymap[j]][i];
				if (in[j] != test)
					break;
			}	
			
			if (j < data.glk)
				continue;
			
			boolean b = adjM[i][0];
			//boolean test;
			for (j = 0; j < data.glk; j++)
			{
				
				test =  adjM[i][data.mymap[j]]; 
			  if (out[j] != test)
				  break;
			}
			
			if (j < data.glk)
				continue;
		
			
			
			if (is_graph)
			{
				data.frequencies[this.gNId]++;
				
				
			}
	    
			data.used[i] = true;
			data.glk++;
			
			
			
			for (GTrieNode gTrieNodes : child) {
				//System.out.println(data.wid + "   Current iD:  " +gTrieNodes.gNId);
				gTrieNodes.goCondDir(data, sTime, state); 
			}
			
			data.glk--;
			data.used[i] = false;
		  }
		
	
	  }
	  
	
	public void goCondUndir(WorkerData data, long sTime, WorkerData state) {
		
		int i;
		int j;
		int ci;
		int mylim;
		int glaux;
		int ncand;
		
		int p;
		char ft,st;
		int fft, sst;
			
		mylim =Integer.MAX_VALUE;
		  
		if (!this.cond_ok)
		  {
			
			i = 1;
			for(int jN=0; jN < cond.size()  ;  ++jN )
			{
				glaux = -1;
				
				LinkedList<Pair> jj2N = cond.get(jN);
				Pair kk3 = jj2N.get(jj2N.size()-1);
				int kN = 0;
				for(kN = 0 ; kN < jj2N.size() ; ++kN){
					Pair kk2N = jj2N.get(kN);
					fft =  (int) kk2N.getFirst();//(int) ft;
					sst = (int) kk2N.getSecond();//(int) st;
					int tat = data.mymap[fft];
					int tat2 = data.mymap[sst];
					if ((sst < data.glk) && (data.mymap[fft] > data.mymap[sst]))
						  break;
					else if (sst == data.glk && data.mymap[fft] > glaux)
					{		
						glaux = data.mymap[fft];
					}	
				}
				if(kN > 0)
				if (kk3 == jj2N.get(kN -1))
				  {
					i = 0;
					if (glaux < mylim)
					{
						mylim = glaux;
					}
				  }
			}
			
			if (i != 0)
				return;
		  }
		
		  if (mylim == Integer.MAX_VALUE)
		  {
			  mylim = 0;
		  }
	    
		  ncand = 0;
		  j = ci = Integer.MAX_VALUE;
		  
		  for (i = 0; i < nconn; i++)
		  {
			glaux = numnei[data.mymap[conn[i]]];
			if (glaux < j)
			{
			  ci = data.mymap[conn[i]];
			  j = glaux;
			}
		  }
	    
		  glaux = j;
		  ncand = ci;
		 for(ci = glaux -1 ; ci >= 0 ; ci--)
		  {
			i= fastnei[ncand][ci];
			if (i < mylim)
				break;
			if (data.used[i])
				continue;
			//////// save state
			if((System.nanoTime() - sTime) > Gtries.broadcastStepTime.value()) 
		      {
				  String nodes = String.valueOf(i);//fastnei[ncand][ci]);
				  ci--;
				  int ctr = 0;
				  for(; ci >= 0 ; ci--)
					{
						nodes += "-" + fastnei[ncand][ci];
						ctr++;
					}
					  state = createWorkFromArray(state, nodes, this, mylim,  data.mymap); //data.glk,
					  return;
				  
			  }
			///////// Finish saving state
		
		//System.out.println("GLK:  "+ data.glk +" mymap length" + data.mymap.length);
		//System.out.println("111Worker:  "+ data.wid + "   GLK: " + data.glk + "    Depth: " + this.depth + "   GNode: " + this.gNId );
			data.glk = this.depth -1;///// TEST
			data.mymap[data.glk] = i;
	    
			
			boolean b = adjM[i][0];
			boolean test;
			for (j = 0; j < data.glk; j++)
			{
				test = adjM[i][data.mymap[j]];
				if (out[j] != test)
				  break;
			}
			if (j < data.glk)
				continue;
			
			if (is_graph)
			{
				data.frequencies[this.gNId]++;
				
				//this.frequency++;
				/*
				String toWrit="";
				for (int k = 0; k <= data.glk; k++)
				{
				  for (int l = 0; l <= data.glk; l++)
				  {
					  toWrit+=(adjM[data.mymap[k]][data.mymap[l]]?'1':'0');
				  }
				}
				
				/////Worker Result
				if(data.workerResult.containsKey(toWrit))
				{
					data.workerResult.put(toWrit, data.workerResult.get(toWrit)+ 1);
				}
				else
				{
					data.workerResult.put(toWrit, new Double(1));
				}
			*/
			}
	    
			data.used[i] = true;
			data.glk++;
			
			
			
			for (GTrieNode gTrieNodes : child) {
				//System.out.println(data.wid + "   Current iD:  " +gTrieNodes.gNId);
				gTrieNodes.goCondUndir(data, sTime, state); 
			}
			
			data.glk--;
			data.used[i] = false;
		  }
		
		
		
		
		
	}
	
	
	public WorkerData createWorkFromArray(WorkerData data, String nodes_to_do, GTrieNode gtrie_location, int mylim, int [] mapz){ //int g ,
		WorkerData my_data;
		my_data = data;
		String map = "";
		for(int i = 0; i< mapz.length ; i++)
		{
			if(map != "") map += "-";
			map += String.valueOf(mapz[i]);
		}
		String state = nodes_to_do + "&" + mylim + "&" + map ;
		my_data.work.put(gtrie_location.gNId, state);
		//my_data.limits.put(gtrie_location.gNId, mylim);
		return my_data;

}
	
	public void goBackToWork(WorkerData data, long sTime, int[] nodes, int mylim, WorkerData state){
		
		
		int ctrNodes = 0;
		int i, j;
		for(ctrNodes = 0; ctrNodes < nodes.length ; ctrNodes++)//int ci = nodes.length -1; ci >= 0 ; ci--)
		  {
			i= nodes[ctrNodes];
			if (i < mylim)
				break;
			if (data.used[i])
				continue;
			
			//////// save state
			if((System.nanoTime() - sTime) > Gtries.broadcastStepTime.value()) 
		      {
				  //System.out.println("saving state in goback: "+ nodes[ctrNodes]);
				  String nodes1 = String.valueOf(nodes[ctrNodes]);
				  ctrNodes++;
				  for(; ctrNodes < nodes.length ; ctrNodes++)
					{
						nodes1 += "-" + nodes[ctrNodes];
					}
					  //createWorkFromArray(data, ci, fastnei[ncand][0], this, mylim);
					  state = createWorkFromArray(state, nodes1, this, mylim,  data.mymap); //data.glk,
					  return;
				  
			  }
			/////////
			
			data.mymap[data.glk] = i;
	    boolean test;
			if(isdir)
			{
			for (j = 0; j < data.glk; j++)
			{
				
				test = adjM[data.mymap[j]][i];
				if (in[j] != test)
					break;
			}	
			
			if (j < data.glk)
				continue;
			}				
			
			boolean b = adjM[i][0];
			//boolean test;
			for (j = 0; j < data.glk; j++)
			{
				
				test =  adjM[i][data.mymap[j]]; 
			  if (out[j] != test)
				  break;
			}
			
			if (j < data.glk)
				continue;
			
			/*
			boolean b = adjM[i][0];
			boolean test;
			for (j = 0; j < data.glk; j++)
			{
				test = adjM[i][data.mymap[j]];
				if (out[j] != test)
				  break;
			}
			
			if (j < data.glk)
				continue;
			*/
			//System.out.println("Worker:  "+ data.wid + "   GLK: " + data.glk + "    Depth: " + this.depth + "   GNode: " + this.gNId );
			if (is_graph)
			{
				data.frequencies[this.gNId]++;
				
				//this.frequency++;
				/*
				//System.out.println("Worker:  "+ data.wid );//+ "   COUNTER: "+ counterT);
				String toWrit="";
				for (int k = 0; k <= data.glk; k++)
				{
				  for (int l = 0; l <= data.glk; l++)
				  {
					  toWrit+=(adjM[data.mymap[k]][data.mymap[l]]?'1':'0');
					
				  }
				}
				
				
				/////Worker Result
				if(data.workerResult.containsKey(toWrit))
				{
					data.workerResult.put(toWrit, data.workerResult.get(toWrit)+ 1);
				}
				else
				{
					data.workerResult.put(toWrit, new Double(1));
				}
				///////
				*/
			}
	    
			data.used[i] = true;
			data.glk++;
			
			
			if (isdir)
			{
				for (GTrieNode gTrieNodes : child) {
					gTrieNodes.goCondDir(data, sTime, state); 
				}
			}
			else
			{
				for (GTrieNode gTrieNodes : child) {
					gTrieNodes.goCondUndir(data, sTime, state); 
				}
			}
			
			
			data.glk--;
			data.used[i] = false;
		  }
		
	}
	public int maxDepth() {
		int aux = 0;//3;
		
		
		for (GTrieNode gTrieNodes : child) {
			aux = Math.max(aux, 1 + gTrieNodes.maxDepth()); ////[0] ???
		}
		
		return aux;
	}
	/*
	public GraphTree populateGraphTree(GraphTree tree, char[] s, int size) {
		
		for(GTrieNode gN1 : child)
			for(GTrieNode gN : child)
			{
				int i, pos = gN.depth - 1;

				for (i = 0; i< gN.depth; i++) {
					s[pos*size + i] = gN.out[i] ? '1' : '0';
					s[i*size + pos] = gN.in[i] ? '1' : '0';
				}
				
				String ss = String.valueOf(s);
				if (gN.is_graph)
					tree.setString(ss, gN.frequency);
				
				
				//gN.populateGraphTree(tree, s, size);
			}
				
		return tree;
		
	}
*/


public TreeMap< String, Double> populateGraphTree(TreeMap< String, Double> tree, char[] s, int size) {
		
		int i, pos = depth - 1;

		for (i = 0; i<depth; i++) {
			s[pos*size + i] = out[i] ? '1' : '0';
			s[i*size + pos] = in[i] ? '1' : '0';
		}
		
		String ss = String.valueOf(s);
		
		if (is_graph)
			tree.put(ss.trim(), Gtries.freqs[gNId]);//frequency);
		for(GTrieNode gN1 : child)
		for(GTrieNode gN : child)
		{
			gN.populateGraphTree(tree, s, size);
		}
		
		return tree;
		
	}
	
	
	
	
public TreeMap<String, Double> populateGtrie(GTrieNode roro, TreeMap< String, Double> tree, char[] s, int size) {
	
	int i, pos = roro.depth - 1;

	for (i = 0; i<roro.depth; i++) {
		s[pos*size + i] = roro.out[i] ? '1' : '0';
		s[i*size + pos] = roro.in[i] ? '1' : '0';
	}
	
	String ss = String.valueOf(s);
	
	
	if(roro.is_graph)
	{	
		tree.put(ss.trim(), Gtries.freqs[gNId]);//frequency);
		//return t;
	}
	
	for(GTrieNode ror: roro.child)
	{
		tree = ror.populateGtrie(ror, tree, s , size);
	}
	return tree;
	
}

	
}
