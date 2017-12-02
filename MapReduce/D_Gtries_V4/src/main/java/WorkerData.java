package org.apache.spark.examples.gtrie;
import java.util.*;
public class WorkerData
{
   long wid;

   int [] mymap;
   boolean [] used;
   int glk;

   //GTrieNode root;
   // map of GtrieNode Id , and the nodes still need to be processed in that level 
   TreeMap<Integer, String> work;// = new TreeMap<long, long []>();
   // map of GtrieNode Id , and the limit in that level
   //TreeMap<Integer, Integer> limits;// = new TreeMap<long, long []>();
   
   TreeMap<String, Integer> workerResult;
   double [] frequencies;
   //long [] frequencies;                             // frequencies for each gtrie node
}