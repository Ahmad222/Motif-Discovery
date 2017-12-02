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




import java.util.*;
public class GraphMatrix {
	
	  private GraphType _type;

	  private int _num_nodes;
	  private int _num_edges;

	  private int[] _in;
	  private int[] _out;
	  private int[] _num_neighbours;

	  private boolean[][] _adjM;
	  private int[][] _array_neighbours;
	  private ArrayList<Integer>[] _adjOut;
	  private ArrayList<Integer>[] _adjIn;
	  private ArrayList<Integer>[] _neighbours;

	  // ------------------------------
	  // Graph Creation
	  // ------------------------------

	  private void _init()
	  {
		_num_nodes = _num_edges = 0;

		_adjM = null;
		_adjOut = null;
		_adjIn = null;
		_neighbours = null;
		_in = null;
		_out = null;
		_num_neighbours = null;
		_array_neighbours = null;
	  }
	  
	  private void _delete()
	  {
		int i;

		if (_adjM != null)
		{
		  for (i = 0; i < _num_nodes; i++)
		  {
			if (_adjM[i] != null)
			{
				_adjM[i] = null;
			}
		  }
		  _adjM = null;
		}
		if (_adjIn != null)
		{
			_adjIn = null;
		}
		if (_adjOut != null)
		{
			_adjOut = null;
		}
		if (_neighbours != null)
		{
			_neighbours = null;
		}

		if (_in != null)
		{
			_in = null;
		}
		if (_out != null)
		{
			_out = null;
		}
		if (_out != null)
		{
			_num_neighbours = null;
		}

		if (_array_neighbours != null)
		{
		  for (i = 0; i < _num_nodes; i++)
		  {
			if (_array_neighbours[i] != null)
			{
				_array_neighbours[i] = null;
			}
		  }
		  _array_neighbours = null;
		}
	  }
	  
	  private void _removeVector(ArrayList<Integer> v, int b)
	  {
		int i;
		int s = v.size();
		for (i = 0; i < s; i++)
		{
		  if (v.get(i) == b)
			  break;
		}
		if (i < s)
		{
			v.remove(i);
		}
	  }

	  
	  public GraphMatrix()
	  {
		_init();
	  }
	  public void dispose()
	  {
		_delete();
	  }
	  //@Override
	  public final boolean[][] adjacencyMatrix()
	  {
		  return _adjM;
	  }
	  
	 // @Override
	  public final void createGraph(int n, GraphType t)
	  {
		int i;

		_num_nodes = n;
		_type = t;

		_delete();

		_adjM = new boolean[n][];
		for (i = 0; i < n; i++)
		{
			_adjM[i] = new boolean[n];
		}
		_adjIn = (ArrayList<Integer>[])new ArrayList[n];
		_adjOut = (ArrayList<Integer>[])new ArrayList[n];
		_neighbours = (ArrayList<Integer>[])new ArrayList[n];
		_in = new int[n];
		_out = new int[n];
		_num_neighbours = new int[n];

		zero();
	  }
	  
	  //@Override
	  public final GraphType type()
	  {
		  return _type;
	  }

	  //@Override
	  public final void zero()
	  {
		int i;
		int j;
		_num_edges = 0;

		for (i = 0; i < _num_nodes;i++)
		{
		  _in[i] = 0;
		  _out[i] = 0;
		  _num_neighbours[i] = 0;
		  //_adjIn.get(i) = new ArrayList<Integer>();
		  //_adjIn.get(i).clear();
		  _adjIn[i] = new ArrayList<Integer>();
		  _adjOut[i] = new ArrayList<Integer>();
		  _neighbours[i] = new ArrayList<Integer>();
		  
		  _adjIn[i].clear();
		  _neighbours[i] = new ArrayList<Integer>();
		  
		  _adjOut[i].clear();
		  _neighbours[i].clear();
		  
		  for (j = 0; j < _num_nodes;j++)
		  {
			_adjM[i][j] = false;
		  }
		}
	  }

	  //@Override
	  public final int numNodes()
	  {
		  return _num_nodes;
	  }
	  //@Override
	  public final int numEdges()
	  {
		  return _num_edges;
	  }

	  //@Override
	  public final void addEdge(int a, int b)
	  {

		if (_adjM[a][b])
			return;

		_adjM[a][b] = true;

		_adjOut[a].add(b);
		_out[a]++;

		_adjIn[b].add(a);
		_in[b]++;

		_num_edges++;

		if (!_adjM[b][a])
		{
		  _neighbours[a].add(b);
		  _num_neighbours[a]++;
		  _neighbours[b].add(a);
		  _num_neighbours[b]++;
		  
		  if (_type == GraphType.UNDIRECTED)
		  {
			addEdge(b, a);
		  }
		  
		}
	  }
	  //@Override
	  public final void rmEdge(int a, int b)
	  {

		if (!_adjM[a][b])
			return;

		_adjM[a][b] = false;

		_removeVector(_adjOut[a], b);
		_out[a]--;

		_removeVector(_adjIn[b], a);
		_in[b]--;

		_num_edges--;

		if (!_adjM[b][a])
		{
		  _removeVector(_neighbours[a], b);
		  _num_neighbours[a]--;
		  _removeVector(_neighbours[b], a);
		  _num_neighbours[b]--;
		}
	  }

	  //@Override
	  public final boolean hasEdge(int a, int b)
	  {
		  return _adjM[a][b];
	  }
	  //@Override
	  public final boolean isConnected(int a, int b)
	  {
		  return _adjM[a][b] || _adjM[b][a];
	  }

	  //@Override
	  public final int nodeOutEdges(int a)
	  {
		  return _out[a];
	  }
	  //@Override
	  public final int nodeInEdges(int a)
	  {
		  return _in[a];
	  }
	  //@Override
	  public final int numNeighbours(int a)
	  {
		  return _num_neighbours[a];
	  }
	  //@Override
	  public final void preProcess()
	  {
		  return;
	  }
	  //@Override
	  public final void sortNeighbours()
	  {
		int i;
		for (i = 0; i < _num_nodes; i++)
		{
			Collections.sort(_neighbours[i]);
		}
	  }
	 // @Override
	  public final void sortNeighboursArray()
	  {
		int i;
		for (i = 0; i < _num_nodes; i++)
		{
			
		   Arrays.sort(_array_neighbours[i]);
		}
	  }
	  //@Override
	  public final void makeArrayNeighbours()
	  {
		int i;
		int j;
		//ArrayList<Integer>.iterator ii = new ArrayList<Integer>.iterator();
		_array_neighbours = new int[_num_nodes][];
		for (i = 0; i < _num_nodes; i++)
		{
		  _array_neighbours[i] = new int[_neighbours[i].size()];
		   j=0;
		  for (Integer ii : _neighbours[i]) 
		  {
			_array_neighbours[i][j] = ii;
			j++;
		  }
		  _neighbours[i].clear();
		}
	  }
	 // @Override
	  public final void makeVectorNeighbours()
	  {
		int i;
		int j;
		
		for (i = 0; i < _num_nodes; i++)
		{
		  for (j = 0; j < _num_neighbours[i]; j++)
		  {
			_neighbours[i].add(_array_neighbours[i][j]);
		  }
		}

		if (_array_neighbours != null)
		{
		  for (i = 0; i < _num_nodes; i++)
		  {
			if (_array_neighbours[i] != null)
			{
				_array_neighbours[i] = null;
			}
		  }
		  _array_neighbours = null;
		}
	  }

	  //@Override
	  public final ArrayList<Integer> neighbours(int a)
	  {
		  return _neighbours[a];
	  }
	  
	  //@Override
	  public final int[][] matrixNeighbours()
	  {
		  return _array_neighbours;
	  }
	  
	  
	  //@Override
	  public final int[] arrayNeighbours(int a)
	  {
		  return _array_neighbours[a];
	  }
	  
	  //@Override
	  public final int[] arrayNumNeighbours()
	  {
		  return _num_neighbours;
	  }
	  
	  //@Override
	  public final ArrayList<Integer> outEdges(int a)
	  {
		  return _adjOut[a];
	  }
	  //@Override
	  public final ArrayList<Integer> inEdges(int a)
	  {
		  
		  return _adjIn[a];
	  }
	  
	  
}



/*
import java.util.*;
public class GraphMatrix {
	//public enum GraphType {
	//	UNDIRECTED, DIRECTED
	//}
	
	private GraphType _type;

	  private int _num_nodes;
	  private int _num_edges;

	  private int[] _in;
	  private int[] _out;
	  private int[] _num_neighbours;

	  private boolean[][] _adjM;
	  private int[][] _array_neighbours;
	  private ArrayList<Integer>[] _adjOut;
	  //private ArrayList<Integer>[] _adjIn;
	  //ArrayList<ArrayList<Integer>> _adjIn;
	  private ArrayList<Integer>[] _adjIn;
	  private ArrayList<Integer>[] _neighbours;

	  // ------------------------------
	  // Graph Creation
	  // ------------------------------

	  private void _init()
	  {
		_num_nodes = _num_edges = 0;

		_adjM = null;
		_adjOut = null;
		_adjIn = null;
		_neighbours = null;
		_in = null;
		_out = null;
		_num_neighbours = null;
		_array_neighbours = null;
	  }
	  
	  
	  
	  
	  private void _delete()
	  {
		int i;

		if (_adjM != null)
		{
		  for (i = 0; i < _num_nodes; i++)
		  {
			if (_adjM[i] != null)
			{
				_adjM[i] = null;
			}
		  }
		  _adjM = null;
		}
		if (_adjIn != null)
		{
			_adjIn = null;
		}
		if (_adjOut != null)
		{
			_adjOut = null;
		}
		if (_neighbours != null)
		{
			_neighbours = null;
		}

		if (_in != null)
		{
			_in = null;
		}
		if (_out != null)
		{
			_out = null;
		}
		if (_out != null)
		{
			_num_neighbours = null;
		}

		if (_array_neighbours != null)
		{
		  for (i = 0; i < _num_nodes; i++)
		  {
			if (_array_neighbours[i] != null)
			{
				_array_neighbours[i] = null;
			}
		  }
		  _array_neighbours = null;
		}
	  }
	  
	  
	  
	  private void _removeVector(ArrayList<Integer> v, int b)
	  {
		int i;
		int s = v.size();
		for (i = 0; i < s; i++)
		{
		  if (v.get(i) == b)
			  break;
		}
		if (i < s)
		{
			//v.erase(v.iterator() + i);
			v.remove(i);
		}
	  }

	  
	  
	  
	  public GraphMatrix()
	  {
		_init();
	  }
	  public void dispose()
	  {
		_delete();
		  //super.dispose();
	  }

	  
	  //@Override
	  public final boolean[][] adjacencyMatrix()
	  {
		  return _adjM;
	  }
	  
	 // @Override
	  public final void createGraph(int n, GraphType t)
	  {
		int i;

		_num_nodes = n;
		_type = t;

		_delete();

		_adjM = new boolean[n][];
		for (i = 0; i < n; i++)
		{
			_adjM[i] = new boolean[n];
		}
		//_adjIn = new ArrayList<ArrayList<Integer>>(n);// [n];
		_adjIn = (ArrayList<Integer>[])new ArrayList[n];
		_adjOut = (ArrayList<Integer>[])new ArrayList[n];
		_neighbours = (ArrayList<Integer>[])new ArrayList[n];
		//_adjIn = //tangible.Arrays.initializeWithDefaultClassicVectorInstances(n);
		//_adjOut = tangible.Arrays.initializeWithDefaultClassicVectorInstances(n);
		//_neighbours = tangible.Arrays.initializeWithDefaultClassicVectorInstances(n);

		_in = new int[n];
		_out = new int[n];
		_num_neighbours = new int[n];

		zero();
	  }
	  
	  

	  
	  //@Override
	  public final GraphType type()
	  {
		  return _type;
	  }

	  //@Override
	  public final void zero()
	  {
		int i;
		int j;
		_num_edges = 0;

		for (i = 0; i < _num_nodes;i++)
		{
		  _in[i] = 0;
		  _out[i] = 0;
		  _num_neighbours[i] = 0;
		  //_adjIn.get(i) = new ArrayList<Integer>();
		  //_adjIn.get(i).clear();
		  _adjIn[i] = new ArrayList<Integer>();
		  _adjOut[i] = new ArrayList<Integer>();
		  _neighbours[i] = new ArrayList<Integer>();
		  
		  _adjIn[i].clear();
		  _neighbours[i] = new ArrayList<Integer>();
		  
		  _adjOut[i].clear();
		  _neighbours[i].clear();
		  
		  for (j = 0; j < _num_nodes;j++)
		  {
			_adjM[i][j] = false;
		  }
		}
	  }

	  //@Override
	  public final int numNodes()
	  {
		  return _num_nodes;
	  }
	  //@Override
	  public final int numEdges()
	  {
		  return _num_edges;
	  }

	  //@Override
	  public final void addEdge(int a, int b)
	  {

		if (_adjM[a][b])
			return;

		_adjM[a][b] = true;

		_adjOut[a].add(b);
		_out[a]++;

		_adjIn[b].add(a);
		_in[b]++;

		_num_edges++;

		if (!_adjM[b][a])
		{
		  _neighbours[a].add(b);
		  _num_neighbours[a]++;
		  _neighbours[b].add(a);
		  _num_neighbours[b]++;

		  if (_type == GraphType.UNDIRECTED)
		  {
			addEdge(b, a);
		  }
		}
	  }
	  //@Override
	  public final void rmEdge(int a, int b)
	  {

		if (!_adjM[a][b])
			return;

		_adjM[a][b] = false;

		_removeVector(_adjOut[a], b);
		_out[a]--;

		_removeVector(_adjIn[b], a);
		_in[b]--;

		_num_edges--;

		if (!_adjM[b][a])
		{
		  _removeVector(_neighbours[a], b);
		  _num_neighbours[a]--;
		  _removeVector(_neighbours[b], a);
		  _num_neighbours[b]--;
		}
	  }

	  //@Override
	  public final boolean hasEdge(int a, int b)
	  {
		  return _adjM[a][b];
	  }
	  //@Override
	  public final boolean isConnected(int a, int b)
	  {
		  return _adjM[a][b] || _adjM[b][a];
	  }

	  //@Override
	  public final int nodeOutEdges(int a)
	  {
		  return _out[a];
	  }
	  //@Override
	  public final int nodeInEdges(int a)
	  {
		  return _in[a];
	  }
	  //@Override
	  public final int numNeighbours(int a)
	  {
		  return _num_neighbours[a];
	  }
	  //@Override
	  public final void preProcess()
	  {
		  return;
	  }
	  //@Override
	  public final void sortNeighbours()
	  {
		int i;
		for (i = 0; i < _num_nodes; i++)
		{
			Collections.sort(_neighbours[i]);
			//sort(_neighbours[i].get(0),_neighbours[i].get(_neighbours[i].size()-1));
		 // sort(_neighbours[i].iterator(), _neighbours[i].iterator() + _neighbours[i].size());
		}
	  }
	 // @Override
	  public final void sortNeighboursArray()
	  {
		int i;
		for (i = 0; i < _num_nodes; i++)
		{
			
		   Arrays.sort(_array_neighbours[i]);
		  //qsort(_array_neighbours[i], _num_neighbours[i], Integer.SIZE, GraphUtils.int_compare(123,234));
		}
	  }
	  //@Override
	  public final void makeArrayNeighbours()
	  {
		int i;
		int j;
		//ArrayList<Integer>.iterator ii = new ArrayList<Integer>.iterator();
		_array_neighbours = new int[_num_nodes][];
		for (i = 0; i < _num_nodes; i++)
		{
		  _array_neighbours[i] = new int[_neighbours[i].size()];
		  //for (ii = _neighbours[i].iterator(), j = 0; ii != _neighbours[i].end(); ++ii, j++)
		  j=0;
		  for (Integer ii : _neighbours[i]) 
		  {
			_array_neighbours[i][j] = ii;
			j++;
		  }
		  _neighbours[i].clear();
		}
	  }
	 // @Override
	  public final void makeVectorNeighbours()
	  {
		int i;
		int j;
		//ArrayList<Integer>.iterator ii = new ArrayList<Integer>.iterator();

		for (i = 0; i < _num_nodes; i++)
		{
		  for (j = 0; j < _num_neighbours[i]; j++)
		  {
			_neighbours[i].add(_array_neighbours[i][j]);
		  }
		}

		if (_array_neighbours != null)
		{
		  for (i = 0; i < _num_nodes; i++)
		  {
			if (_array_neighbours[i] != null)
			{
				_array_neighbours[i] = null;
			}
		  }
		  _array_neighbours = null;
		}
	  }

	  //@Override
	  public final ArrayList<Integer> neighbours(int a)
	  {
		  return _neighbours[a];
	  }
	  
	  //@Override
	  public final int[][] matrixNeighbours()
	  {
		  return _array_neighbours;
	  }
	  
	  
	  //@Override
	  public final int[] arrayNeighbours(int a)
	  {
		  return _array_neighbours[a];
	  }
	  
	  //@Override
	  public final int[] arrayNumNeighbours()
	  {
		  return _num_neighbours;
	  }
	  
	  //@Override
	  public final ArrayList<Integer> outEdges(int a)
	  {
		  return _adjOut[a];
	  }
	  //@Override
	  public final ArrayList<Integer> inEdges(int a)
	  {
		  //return _adjIn.get(a);
		  return _adjIn[a];
	  }
	  
	  
}
*/