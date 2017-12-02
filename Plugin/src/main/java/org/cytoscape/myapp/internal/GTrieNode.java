package org.cytoscape.myapp.internal;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;


public class GTrieNode {
	private static final int MAX_BUF = 1024;
	private static final char BASE_FIRST = ' ';
	private static final int BASE_FORMAT = 95;
	private static final int BASE_BITS = 6;
	public static int[] mymap;
	public static boolean[] used;
	public static int numNodes;
	public static int[][] fastnei;
	public static boolean[][] adjM;
	public static int[] numnei;
	public static boolean isdir;
	public static int glk;
	public static int counterT = 0;
	public static TreeMap< String, int []> myMap2 = new TreeMap<String, int []>();
	public static double prob = 0;
	//public static TreeMap< String, Long> myOccs = new TreeMap<String, Long>(); 
	int depth;
	Long frequency ;
	//public static LinkedList<GTrieNode []> child = null;
	public LinkedList<GTrieNode> child;
	
	boolean show_occ = true;
	//public static String occ_file = "D:\\res.txt";
	//public static PrintWriter writer;
	Pair<Character, Character> p;
	Pair<Integer,Integer> p2;
	
	//static LinkedList<LinkedList<Pair>> cond  = new LinkedList<LinkedList<Pair>>();
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
		frequency = (long) 0;
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

		//child = new LinkedList<GTrieNode []>();
		child = new LinkedList<GTrieNode>();
		child.clear();
		
		//cond= new LinkedList<LinkedList<Pair>>();
		//cond.clear();
		this_node_cond = new LinkedList<LinkedList<Integer>>();
		this_node_cond.clear();

		cond_ok = false;
		cond_this_ok = false;
	}
	public final void readFromFile(FileInputStream inputStream ) throws IOException
	//public final void readFromFile(BufferedReader buffReader) throws IOException
	{
		int nchilds;
		
	  int i;
	  int j;
	  int pos;
	  int bits;
	  
	  //Pair p = new Pair(p, p);
	  char aux;
	  char [] buff =  new char[MAX_BUF];
	  String buf = new String(new char[MAX_BUF]);
	  byte[] buffer = new byte[MAX_BUF];
	  byte[] buffer2 = new byte[MAX_BUF];
	  
	  //GTrieNode[] c;
	  GTrieNode c;
	  //BufferedReader reader = new BufferedReader(new FileReader(f2));
	 // FileInputStream inputStream = new FileInputStream(inputStream);
	  
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
      
   	  
      //while((nRead = inputStream.read(buffer, 0, MAX_BUF)) != -1) {
      //while((nRead = inputStream.read(buffer, 0, numChars)) != -1) {
      //String line = buffReader.readLine();
      if(line  != null){
    	//  buffer = line.getBytes(StandardCharsets.UTF_8);;
    	  
    	for (int totot = 0; totot < MAX_BUF; totot++)
  		{
    		 // System.out.println("---> buf[t]: "+  buffer[totot]+ " , t: "+ totot);
  			if (buffer[totot] == 0)
  				break;
  		}
   		  int totot = (int)buffer[0];
    	  aux =  (char) ((int)buffer[0] - (int)BASE_FIRST);//(char) buff[0];
    	  if(BIT_VALUE(aux, 0))//(aux == BASE_FIRST)
    	  { is_graph = true;
    	  	//System.out.println("GRAPH");
    	  }
    	  else
    		  is_graph = false;
    	  
    	  int nbytes = aux >> 1;

  		pos = 1;
  		//System.out.println("Starting11: POS: "+ pos + " nbytes: " + nbytes + "   AUX:" +(int)aux );
  		
  		for (i = 0, j = 1, nchilds = 0; i < nbytes; i++, j *= BASE_FORMAT)
  		{
  			//if(buff[pos++] == BASE_FIRST)
  			//{
  				
  			//}
  		  aux = (char) (((int)buffer[pos++]) - ((int)BASE_FIRST));
  		  nchilds += (int)aux * j;
  		}
  		
  		//System.out.println("Starting: POS: "+ pos + " NChilds: " + nchilds);
  		
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
	  			  //Pair<Character, Character> p = new Pair<Character, Character>(aux, aux2);
	  			//System.out.println("Values being added to P,  f:"+ p2.getFirst() + "   S:"+p2.getSecond());
	  			  //newcond.addLast(p);
	  			//newcond.addLast(p2);
	  			newcond.add(p2);
	  			}
  			
	  			//cond.addLast(newcond);
	  			cond.add(newcond);
	  			//System.out.println("COND: "+ cond.size());
  			  }
  			}

  			//if(pos < line.length())
  			if (buffer[pos] != '\n')
  			{
  				System.out.println("ERROR");
  			  /*fprintf(stderr, "ERROR: [%s] !%d!%c!\n", buf, pos, buf.charAt(pos));
  			  fprintf(stderr,"[%d](%s) |", depth, is_graph?"X":" ");
  			  for (i = 0, bits = 0; i < depth; i++, bits++)
  			  {
  			fprintf(stderr,"%s", out[i]?"1":"0");
  			  }
  			  fprintf(stderr,"|\n");
  			  */
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
  			  //c[i] = new GTrieNode(depth + 1);
  			 c.readFromFile(inputStream);
  			 
  			 //c.readFromFile(buffReader);
  			  this.child.add(c);
  			  //this.child.addLast(c);
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
		// TODO Auto-generated method stub
		frequency = (long) 0;
		if(this.child.size() > 0)
		for (GTrieNode gTrieNodes : this.child) {
			gTrieNodes.zeroFrequency();
			//gTrieNodes.frequency = 0;
		}
		/*
		for (GTrieNode gTrieNodes : child) {
			gTrieNodes.zeroFrequency(); ////[0] ???
		}
		
		*/
	}
	public void goCondDir() {
		
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
					int tat = mymap[fft];
					int tat2 = mymap[sst];
					if ((sst < glk) && (mymap[fft] > mymap[sst]))
						  break;
					else if (sst == glk && mymap[fft] > glaux)
					{		
							glaux = mymap[fft];
					}	}
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
			glaux = numnei[mymap[conn[i]]];
			if (glaux < j)
			{
			  ci = mymap[conn[i]];
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
			if (used[i])
				continue;
			mymap[glk] = i;
	    
			boolean test;
			for (j = 0; j < glk; j++)
			{
				
				test = adjM[mymap[j]][i];
				if (in[j] != test)
					break;
			}	
			
			if (j < glk)
				continue;
			
			boolean b = adjM[i][0];
			//boolean test;
			for (j = 0; j < glk; j++)
			{
				
				test =  adjM[i][mymap[j]]; //?????????
			  if (out[j] != test)
				  break;
			}
			
			if (j < glk)
				continue;
			
			if (is_graph)
			{
			  frequency++;
			  counterT ++;
			  //System.out.println("PLUS: " + counterT);
			  
			  int counter = 0;
				int len = (mymap.length * mymap.length)+1;
				char [] s = new char[len];
				for (int k = 0; k <= glk; k++)
					for (int l = 0; l <= glk; l++)
						s[counter++] = (adjM[mymap[k]][mymap[l]] ? '1' : '0');
						
				
				s[counter] = '\0';
				//strncpy(sg2, s, motifSize*motifSize);
				//sg = s;
				//for coloring
				String subG = String.valueOf(s).trim();
				
				
				if (!myMap2.containsKey(subG))//count(s) == 0)
				{
					int [] dd = new int[numNodes]; //NEW
					//d.nodez = new int[numNodes]; //NEW
					for (int ie = 0; ie < numNodes; ie++)
					{
						dd[ie] = 0;
						//d.nodez[ie] = 0;
					}

					for (int ie = 0; ie <= glk; ie++)
					{
						dd[mymap[ie]]++;
						//d.nodez[mymap[i]] ++;
						
					}
					
					myMap2.put(subG, dd);//insert(pair<string, nodes>(s, d));
					//printf("Inserting %s \n",s);

				}
				else
				{
					int [] dd = myMap2.get(subG); 
					
					//nodes r = myMap2[s];
					for (int ie = 0; ie <= glk; ie++)
					{
						//r.nodez[mymap[i]] ++;
						dd[mymap[ie]] ++;
						
					}
					
					myMap2.put(subG, dd);
					//(myMap2).insert(pair<string, nodes>(s, r));
					
				}
				
			}
	    
			used[i] = true;
			glk++;
			
			
			for (GTrieNode gTrieNodes : child) {
				gTrieNodes.goCondDir(); 
			}
			
			glk--;
			used[i] = false;
		  }
		
		
		
		
		
	}
	public void goCondUndir() {
		
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
			
			//counterT++;
			//System.out.println("FAAAAT  " + counterT);
			for(int jN=0; jN < cond.size()  ;  ++jN )
			{
				glaux = -1;
				
				LinkedList<Pair> jj2N = cond.get(jN);
				Pair kk3 = jj2N.get(jj2N.size()-1);
				int kN = 0;
				for(kN = 0 ; kN < jj2N.size() ; ++kN){
					Pair kk2N = jj2N.get(kN);
					//ft = (char)kk2N.getFirst();
					//st = (char)kk2N.getSecond();
					fft =  (int) kk2N.getFirst();//(int) ft;
					sst = (int) kk2N.getSecond();//(int) st;
					int tat = mymap[fft];
					int tat2 = mymap[sst];
					if ((sst < glk) && (mymap[fft] > mymap[sst]))
						  break;
					else if (sst == glk && mymap[fft] > glaux)
					{		//System.out.println("ENTER, sst = glk: "+ glk + "  mymap[fft]: "+ mymap[fft] + "  GLAUX:" + glaux);
							glaux = mymap[fft];
					}	}
				if(kN > 0)
				if (kk3 == jj2N.get(kN -1))
				  {
					i = 0;
					if (glaux < mylim)
					{
						mylim = glaux;
						//System.out.println("kk == kkend,  mylim = glaux : " + mylim);
						//System.out.println("mymap[0]: "+mymap[0]+",   mymap[1]: "+mymap[1]+" , mymap[2]:"+mymap[2]);
					}
				  }
			}
			
			if (i != 0)
				return;
		  }
		
		  //System.out.println("VALUES mylim: "+ mylim);
		
		  if (mylim == Integer.MAX_VALUE)
		  {
			  mylim = 0;
		  }
	    
		  ncand = 0;
		  j = ci = Integer.MAX_VALUE;
		  
		  for (i = 0; i < nconn; i++)
		  {
			glaux = numnei[mymap[conn[i]]];
			if (glaux < j)
			{
			  ci = mymap[conn[i]];
			  j = glaux;
			  //System.out.println("VALUES_INSIDE CI: "+ ci+ "  J:" + j);
			}
		  }
	    
		  glaux = j;
		  ncand = ci;
		  //System.out.println("VALUES CI: " + glaux + "  J:" + j);
		  
		  //for (p = fastnei[ncand][j - 1], ci = glaux - 1; ci >= 0; ci--, p--)
		  for(ci = glaux -1 ; ci >= 0 ; ci--)
		  {
			//i = p;
			i= fastnei[ncand][ci];
			if (i < mylim)
				break;
			if (used[i])
				continue;
			mymap[glk] = i;
	    
			
			boolean b = adjM[i][0];
			boolean test;
			for (j = 0; j < glk; j++)
			{
				//System.out.println("FOR2");
				test = adjM[i][mymap[j]];//b && ((mymap[j] == 0) ? false : true);
			  if (out[j] != test)//b)//(b + mymap[j])) ????????????????????????????????????????????????
				  break;
			}
			
			//counterT++;
			//System.out.println("GLK:  "+ glk+ "   COUNTER: "+ counterT);
			if (j < glk)
				continue;
			//System.out.println("FOR");
			if (is_graph)
			{
			  frequency++;
			  counterT ++;
			  //System.out.println("PLUS: " + counterT);
			  
			  int counter = 0;
				int len = (mymap.length * mymap.length)+1;
				char [] s = new char[len];
				for (int k = 0; k <= glk; k++)
					for (int l = 0; l <= glk; l++)
						s[counter++] = (adjM[mymap[k]][mymap[l]] ? '1' : '0');
						
				
				s[counter] = '\0';
				//strncpy(sg2, s, motifSize*motifSize);
				//sg = s;
				//for coloring
				String subG = String.valueOf(s).trim();
				
				
				if (!myMap2.containsKey(subG))//count(s) == 0)
				{
					int [] dd = new int[numNodes]; //NEW
					//d.nodez = new int[numNodes]; //NEW
					for (int ie = 0; ie < numNodes; ie++)
					{
						dd[ie] = 0;
						//d.nodez[ie] = 0;
					}

					for (int ie = 0; ie <= glk; ie++)
					{
						dd[mymap[ie]]++;
						//d.nodez[mymap[i]] ++;
						
					}
					
					myMap2.put(subG, dd);//insert(pair<string, nodes>(s, d));
					//printf("Inserting %s \n",s);

				}
				else
				{
					int [] dd = myMap2.get(subG); 
					
					//nodes r = myMap2[s];
					for (int ie = 0; ie <= glk; ie++)
					{
						//r.nodez[mymap[i]] ++;
						dd[mymap[ie]] ++;
						
					}
					
					myMap2.put(subG, dd);
					//(myMap2).insert(pair<string, nodes>(s, r));
					
				}
				/*
			  
			if (show_occ)
			  {
				String toWrit="";
			for (int k = 0; k <= glk; k++)
			{
			  for (int l = 0; l <= glk; l++)
			  {
				  toWrit+=(adjM[mymap[k]][mymap[l]]?'1':'0');
				//fputc(adjM[mymap[k]][mymap[l]]?'1':'0', occ_file);
			  }
			}
			//fputc(':', occ_file);
			toWrit+=':';
			for (int k = 0; k <= glk; k++)
			{
			  //fprintf(occ_file, " %d", mymap[k] + 1);
				toWrit+=  (mymap[k] + 1);
			}
			//fputc('\n', occ_file);
				toWrit+= '\n';
				//writer.println(toWrit);
				writer.append(toWrit);
	    		
				//
			  }*/
			}
	    
			used[i] = true;
			glk++;
			
			
			//for (GTrieNode[] gTrieNodes : child) {
			for (GTrieNode gTrieNodes : child) {
				gTrieNodes.goCondUndir(); ////[0] ???
			}
			
			glk--;
			used[i] = false;
		  }
		
		
		
		
		
	}
	public int maxDepth() {
		int aux = 0;//3;
		
		
		for (GTrieNode gTrieNodes : child) {
			aux = Math.max(aux, 1 + gTrieNodes.maxDepth()); ////[0] ???
		}
		
		return aux;
	}
	/*public TreeMap< String, Long> populateGraphTree(TreeMap< String, Long> tree, char[] s, int size) {
		
		int i, pos = depth - 1;

		for (i = 0; i<depth; i++) {
			s[pos*size + i] = out[i] ? '1' : '0';
			s[i*size + pos] = in[i] ? '1' : '0';
		}
		
		String ss = String.valueOf(s);
		
		if (is_graph)
			tree.put(ss.trim(), frequency);
		for(GTrieNode gN1 : child)
		for(GTrieNode gN : child)
		{
			gN.populateGraphTree(tree, s, size);
		}
	
		return tree;
		
	}

	
*/
public TreeMap<String, Long> populateGtrie(GTrieNode roro, TreeMap< String, Long> tree, char[] s, int size) {
	
	int i, pos = roro.depth - 1;

	for (i = 0; i<roro.depth; i++) {
		s[pos*size + i] = roro.out[i] ? '1' : '0';
		s[i*size + pos] = roro.in[i] ? '1' : '0';
	}
	
	String ss = String.valueOf(s);
	
	
	if(roro.is_graph)
	{	
		tree.put(ss.trim(), frequency);
		//return t;
	}
	
	for(GTrieNode ror: roro.child)
	{
		tree = ror.populateGtrie(ror, tree, s , size);
	}
	return tree;
	
}
	
	
	

public int [] findNodes(String sg1){
	
	int [] nodes  = myMap2.get(sg1);
	
	if (nodes.length == 0)
		System.out.printf("GTrie  NULLLL\n");
	
	
	return nodes;
}



public void goCondSample() {
	
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
		
		//counterT++;
		//System.out.println("FAAAAT  " + counterT);
		for(int jN=0; jN < cond.size()  ;  ++jN )
		{
			glaux = -1;
			
			LinkedList<Pair> jj2N = cond.get(jN);
			Pair kk3 = jj2N.get(jj2N.size()-1);
			int kN = 0;
			for(kN = 0 ; kN < jj2N.size() ; ++kN){
				Pair kk2N = jj2N.get(kN);
				//ft = (char)kk2N.getFirst();
				//st = (char)kk2N.getSecond();
				fft =  (int) kk2N.getFirst();//(int) ft;
				sst = (int) kk2N.getSecond();//(int) st;
				int tat = mymap[fft];
				int tat2 = mymap[sst];
				if ((sst < glk) && (mymap[fft] > mymap[sst]))
					  break;
				else if (sst == glk && mymap[fft] > glaux)
				{		//System.out.println("ENTER, sst = glk: "+ glk + "  mymap[fft]: "+ mymap[fft] + "  GLAUX:" + glaux);
						glaux = mymap[fft];
				}	}
			if(kN > 0)
			if (kk3 == jj2N.get(kN -1))
			  {
				i = 0;
				if (glaux < mylim)
				{
					mylim = glaux;
					//System.out.println("kk == kkend,  mylim = glaux : " + mylim);
					//System.out.println("mymap[0]: "+mymap[0]+",   mymap[1]: "+mymap[1]+" , mymap[2]:"+mymap[2]);
				}
			  }
		}
		
		if (i != 0)
			return;
	  }
	
	  //System.out.println("VALUES mylim: "+ mylim);
	
	  if (mylim == Integer.MAX_VALUE)
	  {
		  mylim = 0;
	  }
    
	  ncand = 0;
	  j = ci = Integer.MAX_VALUE;
	  if(nconn == 0)
	  {
		  ncand = numNodes;
	  }
	  else
	  {
		  for (i = 0; i < nconn; i++)
		  {
			glaux = numnei[mymap[conn[i]]];
			if (glaux < j)
			{
			  ci = mymap[conn[i]];
			  j = glaux;
			  //System.out.println("VALUES_INSIDE CI: "+ ci+ "  J:" + j);
			}
		  }
	    ncand = j;
	  }
	  
	  
	  int[] cand = new int[ncand];
	  ncand = 0;
	  
	  if (nconn == 0) { // We are at a node with no connections to ancestors
		    for (i=numNodes-1; i>=0; i--)
		      if (!used[i]) 
			cand[ncand++]=i;
		  } else {
		    for (i=0; i<j; i++) {
		      glaux = fastnei[ci][i];
		      if (!used[glaux])
			cand[ncand++]=glaux;
		    }
		  }

		  if (cond_this_ok) mylim = 0;
		  else {
		    //list< list<int> >::const_iterator jjj;
		    //list<int>::const_iterator kkk;
		    mylim = Integer.MAX_VALUE;
		    for (LinkedList<Integer> jjj : this_node_cond){
		    //(jjj=this_node_cond.begin(); jjj!=this_node_cond.end(); ++jjj) {
		      glaux = -1;
		      for (int kkk : jjj)//(kkk=(*jjj).begin(); kkk!=(*jjj).end(); ++kkk)
			if (mymap[kkk]>glaux) glaux = mymap[kkk];
		      if (glaux<mylim) mylim=glaux;
		    }
		  }

		  for (ci=ncand-1; ci>=0; ci--) {
		    i = cand[ci];
		    if (i<mylim) break;
		    mymap[glk] = i;

		    if (isdir) {  
		      for (j=0; j<glk; j++)
			if (in[j]  != adjM[mymap[j]][i] ||
			    out[j] != adjM[i][mymap[j]])
			  break;
		    } else {
		      for (j=0; j<glk; j++)
			if (in[j]  != adjM[mymap[j]][i])
			  break;
		    }
		    if (j<glk) continue;

	  
	  
	  
	  if (is_graph)
		{
		  frequency++;
		  counterT ++;
		  
			
		}
    
		used[i] = true;
		glk++;
		
		
		//for (GTrieNode[] gTrieNodes : child) {
		for (GTrieNode gTrieNodes : child) {
			if(Rand.random.nextDouble() <= prob)
			gTrieNodes.goCondSample(); 
		}
		
		glk--;
		used[i] = false;
	  }
	
	
	
	
	
}







	
}
