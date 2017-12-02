package org.cytoscape.myapp.internal;

import java.security.SecureRandom;
import java.util.ArrayList;

public class Rand {

	
	private static final double RAND_MAX = 0x7fff;
	static SecureRandom random = new SecureRandom();
	  

	// Initialize pseudo-random generator with seed 's'
	void seed(int s) {
		try {
		    
		    random.setSeed(s);
		  } catch (Exception e) {
		    
		  }
	}

	// Pseudo-Random number between 'a' and 'b' (inclusive)
	static int getInteger(int a, int b) {
		//double aux = random() / (double)RAND_MAX;
		int ret = 1;
		while (true)
		{
			int aux = random.nextInt(b - a);//random.nextDouble()/ RAND_MAX;//rand() / (double)RAND_MAX;
			ret = a + aux;//(int)(a + aux*(b - a + 1));
			//System.out.println("Re:  " + ret + "    b:" + b);
			if ((ret >= a) && (ret <= b))
			{
				break;
			}
		}

		
		
		return ret;
	}

	// Pseudo-Random number between 0 and 1
	double getDouble() {
		//return random() / (double)RAND_MAX;
		return random.nextDouble();// / RAND_MAX;//rand() / (double)RAND_MAX;
	}

	// Randomize 'g' network with 'num' exchanges per edge and 'tries' attempts per edge
	static void markovChainPerEdge(GraphMatrix g, int num, int tries) {
		int i, j, k, n, edges, nodes = g.numNodes();
		int a, b, c, d, aux;
		ArrayList<Integer> v,u;
		//vector<int> *v, *u;
		//vector<int>::iterator ii;
		//printf("Innn: %d , %d  \n", num, tries);
		a = b = c = d = 0;
		//System.out.println("AWLLLL:  ");
		for (n = 0; n < num; n++)
		{
			//printf("NNN: %d   \n", n);
			//System.out.println("Looping:  " + n);
			for (i = 0; i<nodes; i++) {
				//printf("III: %d   \n", i);
				v = g.outEdges(i);
				edges = v.size();
				//System.out.println("EDGES:  " + edges + "    N" + nodes);
				a = i;
				for (j = 0; j<edges; j++) {
					b = v.get(j);//(*v)[j];
					for (k = 0; k<tries; k++) {
						//System.out.println("KK:  " + k);
						c = random.nextInt(nodes);//getInteger(0, nodes - 1);
						//printf("CCC: 0 <%d> %d \n", c , nodes -1);
						//System.out.println("CCCC:  " + c);
						if (a == c || b == c || g.hasEdge(c, b)) continue;
						aux = g.nodeOutEdges(c);
						if (aux == 0) continue;
						//System.out.println("CCCC:  BB");
						u = g.outEdges(c);
						//System.out.println("CCCC:  BB222");
						d = random.nextInt(aux) + 1;//getInteger(1, aux);
						//printf("DDD: 1 <%d> %d  \n", d, aux);
						//System.out.println("DDDD:  " + d);
						d = u.get(d-1);//(*u)[d - 1];
						if (a == d || b == d || g.hasEdge(a, d)) continue;
						break;
					}
					if (k<tries) { // Found an edge to swap!
						//fflush(stdout);
						//System.out.println("Found an edge to swap!");
						g.rmEdge(a, b);  g.rmEdge(c, d);
						g.addEdge(a, d); g.addEdge(c, b);
						if (g.type() == GraphType.UNDIRECTED) {
							g.rmEdge(b, a);  g.rmEdge(d, c);
							g.addEdge(d, a); g.addEdge(b, c);
						}
					}
				}
			}
			//System.out.println("Fin11:  ");
		}
		//System.out.println("Fin:  ");
		
	}


	
}
