package org.apache.spark.examples.gtrie;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.Accumulator;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import org.apache.spark.TaskContext;


public class Gtries {
	public static GraphMatrix g;
	public static Gtrie gt_original;
	
	static Broadcast<Boolean> broadcastVarDir;
	static Broadcast<Integer> broadcastMotifSize;
	static Broadcast<Integer> broadcastGraphSize;
	static Broadcast<Integer> broadAuxNotProcessed;
    public static ArrayList<String> restNotProcessedArr  =  new ArrayList<String>();
	static Broadcast<Double> broadcastStepTime;
	static Broadcast<Integer> broadcastTotalNumMappers;
    static Accumulator<Double> accDiffTime;
	static Accumulator<Integer> accNumMappers;
	static Accumulator<Double> accTimeMappers;
	
	static Accumulator<Double> accTimeByAllMappers;
	static Accumulator<Double> accTimeByAllMappersSeq;
	static Accumulator<Double> accTimeByAllMappersPar;
	
	static Accumulator<Double> accTimeReducers;
	static Accumulator<Double> accTimeIterativeReds;
	static Accumulator<Double> accFill;
	
	
	
	public static GraphMatrix loadGraph(String graph_file , boolean dir) throws IOException
	{
		GraphMatrix g = new GraphMatrix();
		BufferedReader br = new BufferedReader(new FileReader(graph_file));
		int size;
		int max;
		int i;
		ArrayList<Integer> va = new ArrayList<Integer>();
		ArrayList<Integer> vb = new ArrayList<Integer>();

		try {
		    
		    String line = br.readLine();
		    if (line == null)
			  {
				  System.exit(1);
			  }
		   
		   

			  int a;
			  int b;
			  int c;
			  
			  size = max = 0;
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

	  va.clear();
	  vb.clear();

	  g.sortNeighbours();
	  g.makeArrayNeighbours();

	  return g;
	}
	
	
	
    

    public static TreeMap<String, Double> fun3(String s, GraphMatrix g1, Gtrie gt) throws FileNotFoundException, UnsupportedEncodingException
    {
	
		TaskContext tc = TaskContext.get();
		String [] nodes = s.split("@");
		//System.out.println("Data will be processed by Mapper " +tc.taskAttemptId()+" is:  " + s);
		int j;
		WorkerData data = new WorkerData();
		data.wid = tc.taskAttemptId();
		data.mymap = new int[broadcastMotifSize.value()];
		data.used = new boolean[broadcastGraphSize.value()];
		//data.root = gt._root;
		data.glk = 1;
		data.workerResult = new TreeMap<String, Double> ();
		
		WorkerData state = new WorkerData();
		state.wid = tc.taskAttemptId();
		state.mymap = new int[broadcastMotifSize.value()];
		state.used = new boolean[broadcastGraphSize.value()];
		//state.root = gt._root;
		state.glk = 1;
		state.workerResult = new TreeMap<String, Double> ();
		state.work = new TreeMap<Integer, String>();
   
		//state.limits = new TreeMap<Integer, Integer>();
		//state.root.zeroFrequency();
		//////////////
		//data.root.zeroFrequency();
		
		LinkedList<GTrieNode> c =  gt._root.child;//data.root.child;
		LinkedList<GTrieNode> c2 =  c.get(0).child;
		int i = 0;
		for (i = 0; i< broadcastGraphSize.value(); i++)
			data.used[i] = false;
		
		 
		 boolean flagTimeOut = false;
		 String [] states = new String[broadcastTotalNumMappers.value()];
		 for(int cc = 0; cc< states.length; cc++)
		{
			states[cc] = "";
		}
		
		String [] nodess;
		String myMaps;
		String myLims;
		String glks;
		String [] auxArr;
					
		long sTime = System.nanoTime();
		long finishExecTime = 0;
		for (i = 0; i < nodes.length; i++) {
			if(nodes[i].split("&").length > 1)
			{
				String [] stateData = nodes[i].split("&");
				//System.out.println("State:  Node ID: " + stateData[0]);
				GTrieNode ro = getCorrespondingNode(Integer.parseInt(stateData[0]), c.get(0));
				//System.out.print("          ID2: " + ro.gNId + "\n");
				String [] nodez =  stateData[1].split("-");
				int [] nodezz = new int[nodez.length];
				int cc = 0;
				for( cc = 0 ; cc < nodez.length ; cc++)
					nodezz[cc] = Integer.parseInt(nodez[cc]);
				
				int lim =  Integer.parseInt(stateData[2]);
				
				String [] map =  stateData[3].split("-");
				int [] mapz = new int[map.length];
				for( cc = 0 ; cc < map.length ; cc++)
					mapz[cc] = Integer.parseInt(map[cc]);
				
				int glkz = ro.depth - 1;//Integer.parseInt(stateData[4]); //ro.depth - 1;
				
				data.glk = glkz; //ro.depth - 1;
				data.mymap = mapz;
				//System.out.print("GLK: "+ data.glk);
				int ii=0;
				for(ii=0; ii < glkz; ii++)
					data.used[mapz[ii]] = true;
			
				ro.goBackToWork(data, sTime, nodezz, lim, state);
				
				//If the threshold reached and the state saved
				if(state.work.size() > 0)
				{			
					finishExecTime = System.nanoTime();
					//System.out.println("last iD..:  " +ro.gNId);
					for(Map.Entry<Integer,String> entry : state.work.entrySet()) {
									int key = entry.getKey();
									String value = entry.getValue();
									auxArr = value.split("&");
									nodess = auxArr[0].split("-");
									myLims = auxArr[1];
									myMaps = auxArr[2];
									//System.out.print(key + "  -----   " + value + " \n");
									int cc1 = 0;
									if(nodess.length < broadcastTotalNumMappers.value())
									{
										for(cc1 = 0; cc1 < nodess.length; cc1++)
										{
											if(states[cc1] != "")  states[cc1] += "@";
											states[cc1] += key + "&" + nodess[cc1] + "&" + myLims + "&" + myMaps;// + "&" + glks;
										}
									}
									else
									{
										for(cc1 = 0; cc1 < broadcastTotalNumMappers.value(); cc1++)
										{
											if(states[cc1] != "")  states[cc1] += "@";
											states[cc1] += key + "&" + nodess[cc1];
										}
										for(cc1 = broadcastTotalNumMappers.value(); cc1 < nodess.length; cc1++)
										{
											states[cc1 % broadcastTotalNumMappers.value()] += "-" + nodess[cc1];
										}
										for(cc1 = 0; cc1 < broadcastTotalNumMappers.value(); cc1++)
										{
											
											states[cc1] += "&" + myLims + "&" + myMaps ;//+ "&" + glks;
										}
									}
								}
								i++;
								flagTimeOut = true;
								break;
				}
				
				
				for(ii=0; ii < glkz; ii++)
					data.used[mapz[ii]] = false;
				
				
			}
			else
			{
				
			 try {
				   j = Integer.parseInt(nodes[i]);
					data.mymap[0] = j;
					data.used[j] = true;
					//System.out.println("\n\n\n\nStart Counting for: " + j+ "\n\n");
					if (g.type() == GraphType.DIRECTED)
						for (GTrieNode  gTrieNode : c2) {
							gTrieNode.goCondDir(data, sTime, state);
							
							if(state.work.size() > 0)
							{
								finishExecTime = System.nanoTime();
								for(Map.Entry<Integer,String> entry : state.work.entrySet()) {
									int key = entry.getKey();
									String value = entry.getValue();
									auxArr = value.split("&");
									nodess = auxArr[0].split("-");
									myLims = auxArr[1];
									myMaps = auxArr[2];
									if(nodess.length < broadcastTotalNumMappers.value())
									{
										for(int cc = 0; cc< nodess.length; cc++)
										{
											if(states[cc] != "")  states[cc] += "@";
											states[cc] += key + "&" + nodess[cc] + "&" + myLims + "&" + myMaps ;//+ "&" + glks;
										}
									}
									else
									{
										for(int cc = 0; cc< broadcastTotalNumMappers.value(); cc++)
										{
											if(states[cc] != "")  states[cc] += "@";
											states[cc] += key + "&" + nodess[cc];
										}
										for(int cc = broadcastTotalNumMappers.value(); cc< nodess.length; cc++)
										{
											states[cc % broadcastTotalNumMappers.value()] += "-" + nodess[cc];
										}
										for(int cc = 0; cc< broadcastTotalNumMappers.value(); cc++)
										{
											
											states[cc] += "&" + myLims + "&" + myMaps;// + "&" + glks;
										}
									}
								}
								i++;
								flagTimeOut = true;
								break;
							}
							
						}
					else
						for (GTrieNode  gTrieNode : c2) {
							gTrieNode.goCondUndir(data, sTime, state);
							
							if(state.work.size() > 0)
							{
								finishExecTime = System.nanoTime();
								for(Map.Entry<Integer,String> entry : state.work.entrySet()) {
									int key = entry.getKey();
									String value = entry.getValue();
									auxArr = value.split("&");
									nodess = auxArr[0].split("-");
									myLims = auxArr[1];
									myMaps = auxArr[2];
									if(nodess.length < broadcastTotalNumMappers.value())
									{
										for(int cc = 0; cc< nodess.length; cc++)
										{
											if(states[cc] != "")  states[cc] += "@";
											states[cc] += key + "&" + nodess[cc] + "&" + myLims + "&" + myMaps ;//+ "&" + glks;
										}
									}
									else
									{
										for(int cc = 0; cc< broadcastTotalNumMappers.value(); cc++)
										{
											if(states[cc] != "")  states[cc] += "@";
											states[cc] += key + "&" + nodess[cc];
										}
										for(int cc = broadcastTotalNumMappers.value(); cc< nodess.length; cc++)
										{
											states[cc % broadcastTotalNumMappers.value()] += "-" + nodess[cc];
										}
										for(int cc = 0; cc< broadcastTotalNumMappers.value(); cc++)
										{
											
											states[cc] += "&" + myLims + "&" + myMaps;// + "&" + glks;
										}
									}
								}
								i++;
								flagTimeOut = true;
								break;
							}
								
						}
							
					data.used[j] = false;
				  } 
			  catch (NumberFormatException e) {
				   System.out.println("This is not a number");
				   //System.out.println(e.getMessage());
				}
			
		}
			if(flagTimeOut)
				break;
			
		}
		
		if(finishExecTime == 0) finishExecTime = System.nanoTime();
		int ccc = 0;
		if(i <  nodes.length)
		{
			
        	for(int jj = i ; jj < nodes.length ; jj++){
				if(states[ccc % broadcastTotalNumMappers.value()] != "")  states[ccc % broadcastTotalNumMappers.value()] += "@";
			
				states[ccc % broadcastTotalNumMappers.value()] += nodes[jj];
				ccc++;
        	}
		}
		int size = broadcastMotifSize.value();
		char sg [] = new char[size*size + 1];
		sg[size*size] = 0;
		sg[0] = '0';
		
		//data.workerResult = c2.get(0).populateGraphTree(data.workerResult, sg, size);
		for(int wu = 0; wu < states.length ; wu ++)
		  {
			  if(states[wu] != "")
			  {
				data.workerResult.put(states[wu], new Double(broadAuxNotProcessed.value()));
			  }
		  }
	double takenTime = 	(System.nanoTime() - sTime);
	double seq = (System.nanoTime() - finishExecTime)/1000000;
	double par = (finishExecTime - sTime)/1000000;
	accTimeByAllMappers.add(takenTime / 1000000);
	accTimeByAllMappersSeq.add(seq);
	accTimeByAllMappersPar.add(par);
	double difTime = broadcastStepTime.value() - takenTime;
	//System.out.println("Time taken by Mapper " +tc.taskAttemptId()+" is:  " + Long.toString((System.nanoTime() - sTime)/1000000) +" MS , DATA: " + s + "********** Not Processed: " + ret );
	//System.out.println("Time taken by Mapper " +tc.taskAttemptId()+" is:  " + Long.toString((System.nanoTime() - sTime)/1000000) +" MS , Parallel: " + Long.toString((finishExecTime - sTime)/1000000) + "    Sequential: " + Long.toString((System.nanoTime() - finishExecTime)/1000000) );
	if(difTime > 0)
	{
		accDiffTime.add(difTime);
		accNumMappers.add(1);
	}
	return data.workerResult;//m;
    }

   
	public static long memoryUsage(ArrayList<String> s)
	{
		long mUsage = s.size() *  8 + 20;
		for(int s3 = 0 ; s3 < s.size() ; s3++)
			mUsage += (s.get(s3).length() * 2) +8+4+4;
		return mUsage;
		
		
	}
	public static GTrieNode getCorrespondingNode(int id, GTrieNode r)
	{
		LinkedList<GTrieNode> ch =  r.child;
		GTrieNode corresponding = null;
		for (GTrieNode  gTrieNode : ch) {
			if(gTrieNode.gNId == id)
			{
				corresponding = gTrieNode;
				break;
			}
			else
			{
				corresponding = getCorrespondingNode(id, gTrieNode);
				if(corresponding != null)
					break;
			}
			
		}
		return corresponding;
		
	}
	
	public static void main(String[] args) throws IOException {
		
	    SparkConf conf = new SparkConf().setAppName("ESU Application");
	    //conf.set("spark.cores.max","64");
	    //conf.set("spark.task.cpus","1");
	    //conf.set("spark.executor.cores","1");   
	    
	    //conf.set("spark.executor.memory","2g");
	    //conf.set("spark.driver.maxResultSize", "7g");
	    //conf.set("spark.storage.memoryFraction","0");
	    JavaSparkContext sc = new JavaSparkContext(conf);
	   
		double diffTime = 0;
		double stepTime = 0;
	    boolean dir = false;
		int motif_size;
		int graph_size;
		String graph_file = new String(new char[100]);
	
	 broadAuxNotProcessed = sc.broadcast(-3);
	
	    String [] data = args;
	    stepTime =  Long.parseLong(data[4], 10); 
		
	    
	    System.out.print("Creating Graph...\n");
	    
		
		
		
	if (data.length != 6)
	  {
		System.out.print("Usage: ./esu <dir/undir> <size> <graph> <numPartitions> <stepTime> <auxFile>\n");
		System.exit(1);
	  }

	  String arg_dir = data[0];
	  if (arg_dir.charAt(0) == 'd')
	  {
		  dir = true;
	  }
	  motif_size = Integer.parseInt(data[1]);
	  graph_file = data[2];
		
		
		g = loadGraph(graph_file , dir);
	  	System.out.print("Counting Occurrences... \n");
		//System.out.println("\nDynamic Iteration time starting in this example with: " + (stepTime/1000000) + "  MS\n");
		int numNodes = g.numNodes();
		int numPartitions = Integer.parseInt(data[3]);
		String auxFile = data[5];
		List<String> dataN = new ArrayList<String>();
		String [] dataPartitions = new String [numPartitions];
		for (int index = 0; index < numPartitions; index++)
		    {
				 dataPartitions[index] = "";
		    }
		for (int index = 0; index < numNodes; index++)
		    {
				if( dataPartitions[index % numPartitions] != "") dataPartitions[index % numPartitions] += "@";
				
				dataPartitions[index % numPartitions] += String.valueOf(index);
				
		    }
			//System.out.println("\n************> Initial working units (round robin)<************");
		for (int index = 0; index < numPartitions; index++)
		    {
				dataN.add(dataPartitions[index]);
				//System.out.println("************>" + dataPartitions[index]);
		    }
		JavaRDD<String> distData = sc.parallelize(dataN, numPartitions);

		  
		accTimeMappers = sc.accumulator(0L);
		accTimeByAllMappers = sc.accumulator(0L);
		
		accTimeByAllMappersSeq = sc.accumulator(0L);
		accTimeByAllMappersPar = sc.accumulator(0L);
		
		accTimeReducers = sc.accumulator(0L);
		accTimeIterativeReds = sc.accumulator(0L);
		accFill = sc.accumulator(0L);
		broadcastMotifSize = sc.broadcast(motif_size);
		broadcastGraphSize = sc.broadcast(g.numNodes());
		broadcastVarDir = sc.broadcast(dir);
		broadcastTotalNumMappers = sc.broadcast(numPartitions);
		double x = Math.pow(motif_size, 3);
		double y = Math.pow(numNodes, 2);
		double suggestedStepTime = 25*x*y;//Math.pow(10, 11);//    (numNodes * numNodes) * (motif_size * motif_size * motif_size);
		System.out.println("\nDynamic Iteration time starting in this example with: " + Math.round(suggestedStepTime/1000000) + "  MS.  nano:" + suggestedStepTime + "\n");
		broadcastStepTime = sc.broadcast(suggestedStepTime);//Long.parseLong(data[4], 10));
		stepTime = broadcastStepTime.value();
		int countTest = 0;
		  
		  //Building the Trie
		gt_original = new Gtrie();
		gt_original.readFromFile(auxFile);//"./undir3.gt");
		System.out.println("Finish building the Trie");
		  
		TreeMap<String, Double> counts11 = new TreeMap<String, Double>();
		TreeMap<String, Double> resultMap = new TreeMap<String, Double>();
		
		//Setting the Static variables of GTrieNode which are common between all workers 
		GTrieNode.numNodes = numNodes;
		GTrieNode.fastnei = g.matrixNeighbours();
		GTrieNode.adjM = g.adjacencyMatrix();
		GTrieNode.numnei = g.arrayNumNeighbours();
		if (g.type() == GraphType.DIRECTED) GTrieNode.isdir = true;
		else                       			GTrieNode.isdir = false;
		
		  
		  
		long startTime = System.nanoTime();
		while(true)
		    {
				long iterationStartTime = System.nanoTime();
				accDiffTime = sc.accumulator(0L);
				accNumMappers = sc.accumulator(0);
				countTest ++;				
				restNotProcessedArr  =  new ArrayList<String>();
				restNotProcessedArr.clear();
				System.out.println("\n--------------------------------------------");
				System.out.println("Iteration: " + countTest + "    iteration time: " + Math.round(broadcastStepTime.value()/1000000) + "  MS");
		
	
				JavaRDD<TreeMap<String, Double>> ones11 = distData.map(
						   new Function<String, TreeMap<String, Double>>() {
                               @Override
                                public TreeMap<String, Double> call(String s) throws FileNotFoundException, UnsupportedEncodingException{
                                    return  fun3(s,g, gt_original);
						   }
						
						}
				 );

				 counts11 = ones11.reduce(
							 new Function2<TreeMap<String, Double>, TreeMap<String, Double>, TreeMap<String, Double>>() {
								  public TreeMap<String, Double> call(TreeMap<String, Double> i1, TreeMap<String, Double> i2) {
									  long reducerStartTime = System.nanoTime();
									  TreeMap< String, Double> mapRes = new TreeMap< String, Double>();
									   for(Map.Entry<String,Double> entry : i1.entrySet()) {
											String key = entry.getKey();
											Double value = entry.getValue();
											if(value > 0)
											{
												if(mapRes.containsKey(key))
												{
													mapRes.put(key, mapRes.get(key) + value);
												}
												else
												{
													mapRes.put(key, value);
												}
											}
											else
											{
												   if(key.length() > 0 )
												   {
														restNotProcessedArr.add(key);
												   }
											}
										}
									   
									for(Map.Entry<String,Double> entry1 : i2.entrySet()) {
			    
											String key1 = entry1.getKey();
											Double value1 = entry1.getValue();
										if(value1 > 0)
										{

											if(mapRes.containsKey(key1))
											{
												mapRes.put(key1, mapRes.get(key1) + value1);
											}
											else
											{
												mapRes.put(key1, value1);
											}
										}
										else
										{
											if(key1.length() > 0 )
											{
												restNotProcessedArr.add(key1);
											}
										}
									}
									   
										long reducerStopTime = System.nanoTime();
										double tt = (reducerStopTime - reducerStartTime)/1000000;
										accTimeReducers.add(tt);
										return mapRes;//m2;
										                          
								  }
							      });

								 
						//long auxVar = -3;
						String mappersTimes = "\n";
						long seqStartTime = System.nanoTime();
						//System.out.println("---------------asd-------------- ");
						for(Map.Entry<String,Double> entry : counts11.entrySet()) {
							String key = entry.getKey();
							Double value = entry.getValue();
							if(value > 0)//== auxVar)
							{
								if(resultMap.containsKey(key))
								{
									resultMap.put(key, resultMap.get(key) + value);
								}
								else
								{
									resultMap.put(key, value);
								}
							}
						}
						
					 
						long seqStopTime = System.nanoTime();
						double calTime = (seqStopTime - iterationStartTime)/1000000;
						accTimeMappers.add(calTime);
						int notProcessedNum = restNotProcessedArr.size();
						//System.out.printf("Sequential Time: " + ((seqStopTime - seqStartTime)/1000000));
					 
					 ////Break Condition when there is no more data to process
					 if(notProcessedNum == 0 ) break;
					 
					 dataN.clear();
					 int newNumPart = numPartitions;
					 if(notProcessedNum > numPartitions)
					 {
						for (int index = 0; index < numPartitions; index++)
						{
							dataPartitions[index] = restNotProcessedArr.get(index);
						}
						for (int index = numPartitions; index < notProcessedNum; index++)
						{
							dataPartitions[index % numPartitions] += "@" + restNotProcessedArr.get(index);//String.valueOf(index);
						}
					}
					else
					{
						newNumPart = 0;
						for (int index = 0; index < notProcessedNum; index++)
						{
							dataPartitions[index] = restNotProcessedArr.get(index);
							newNumPart ++;
						}
					}
					long finishFillingDataTime = System.nanoTime();
					double fd = (finishFillingDataTime - seqStopTime)/1000000;
					accFill.add(fd);
					for (int index = 0; index < newNumPart; index++)
					{
						dataN.add(dataPartitions[index]);
					}
					 
					double dTime = accDiffTime.value();
					diffTime = dTime/accNumMappers.value();
					stepTime = broadcastStepTime.value();
					
					int numMappers = dataN.size();
					if(accNumMappers.value() == 0) 
					{
						stepTime += 0.2 * stepTime;
						//stepTime += 0.25 * (stepTime/numMappers);
					}
					else
					{
						stepTime -= ((stepTime * accNumMappers.value())/numMappers);
						stepTime -= 0.1 * stepTime;
						if(stepTime <= 0)
							stepTime = 5000000;
					}
					 
					stepTime += 5000000;
					broadcastStepTime = sc.broadcast(stepTime);
					 
					System.out.println("\nNumber of working units: " + restNotProcessedArr.size());
					System.out.println("Memory used by these working units: " + memoryUsage(restNotProcessedArr)/1000 + "  KB");//dataN.amountOfMemory);
					 //System.out.println(mappersTimes);
					System.out.println("TotalNumMappers:   "  + dataN.size() + "   NumMappersWhoWaited: " + accNumMappers + "\n    Difference in time :" + diffTime/1000000 + "MS   new step time : " + stepTime/1000000 + "MS");
					 
					System.out.println("------Calculation Time Until now: " + accTimeMappers.value()/1000);
					 if(dataN.size() == 1) // in this case we need anpther mapper to make the reducer work
					 {
						 dataN.add("");
					 }
					 restNotProcessedArr.clear();
					 //distData = sc.parallelize(dataN, numPartitions);
					 distData = sc.parallelize(dataN, dataN.size());
					 //break;
					long preparationStopTime = System.nanoTime();
					double pt = (preparationStopTime - seqStopTime)/1000000;
					accTimeIterativeReds.add(pt);
		      }

		
		  long stopTime = System.nanoTime();
		
		  String ret = "";
		  double tot = 0;
		 
		  for(Map.Entry<String,Double> entry : resultMap.entrySet()) {
		          
		      String key = entry.getKey();
		      Double value = entry.getValue();
		      ret += key + "  ->   " + value + "\n";
		      tot += value;
		  }
		    
		  System.out.printf("\n============================== \nNumber of Occurrences " + tot + "\n--OCCURRENCES--\n"  + ret  +"Time\nMiliseconds:"+(stopTime - startTime)/1000000+ "    Seconds: "+ (stopTime - startTime)/1000000000 +"\nNum Rounds: " + countTest + "\n==============================\n ++++++++++++++++++++\nCalculation Time: " + accTimeMappers.value()/1000 + "\n Reducing Time:" + accTimeReducers.value()/1000 + "\nPreparatin Time: " + accTimeIterativeReds.value()/1000 + "\n  Filling: " + accFill.value()/1000 + "All mappers took" + accTimeByAllMappers.value()/1000);
		  System.out.printf("\n ----------Time in MAppers \nTotal: "+ accTimeByAllMappers.value()/1000 +"\nSequential: "+ accTimeByAllMappersSeq.value()/1000 +"\nParallel: " + accTimeByAllMappersPar.value()/1000 );
		  
		  char sg [] = new char[motif_size*motif_size + 1];
			sg[motif_size*motif_size] = 0;
			sg[0] = '0';
		  resultMap = gt_original._root.child.get(0).child.get(0).populateGraphTree(resultMap, sg, motif_size);
		  ret = "";
		  tot = 0;
		  for(Map.Entry<String,Double> entry : resultMap.entrySet()) {
		          
		      String key = entry.getKey();
		      Double value = entry.getValue();
		      ret += key + "  ->   " + value + "\n";
		      tot += value;
		  }
		  System.out.printf("\n****************************** \nNumber of Occurrences " + tot + "\n--OCCURRENCES--\n"  + ret);
		  sc.stop();
		  
	}

}

