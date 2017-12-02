package org.cytoscape.myapp.internal;
/*
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
*/


import java.awt.Color;
import java.awt.Component;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.xml.stream.util.StreamReaderDelegate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanel;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.application.swing.CytoPanelState;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.EdgeBendVisualProperty;
import org.cytoscape.view.presentation.property.NodeShapeVisualProperty;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualPropertyDependency;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.view.vizmap.mappings.BoundaryRangeValues;
import org.cytoscape.view.vizmap.mappings.ContinuousMapping;

public class MenuAction extends AbstractCyAction {
	protected static CyApplicationManager applicationManager;
	
	protected static VisualMappingFunctionFactory continuousMappingFactoryServiceRef;
	
	public static int numColoring = 0;
	public static GraphMatrix graphM = new GraphMatrix();
	
	protected static	VisualMappingManager vmm;
	protected static	VisualStyleFactory vFactory;
	protected static CyNetworkViewManager viewManager;
	protected static CyNetworkViewFactory viewFactory;
	
	
	private final static Log logger = LogFactory.getLog(MenuAction.class);
	static String res;
	static String res2;
	
	 static HashMap<Integer, Integer> CytoGtrie = new HashMap<Integer, Integer>();
		// HashMap<Integer, Integer> GtrietoCyto = new HashMap<Integer, Integer>();
	 static int[] Gt_to_Cy ;
	 
	 
	 static HashMap<Integer, Integer> nodesToColor = new HashMap<Integer, Integer>();
	 final static int [] values = new int[10];
	 
	public MenuAction(final CyApplicationManager applicationManager, final String menuTitle, final VisualMappingFunctionFactory v, final CyNetworkViewManager viewManager1, final CyNetworkViewFactory viewFactory1,final VisualMappingManager vmm1 ,final VisualStyleFactory vFactory1)//, VisualMappingFunctionFactory continuousMappingFactoryServiceRef1, VisualStyleFactory vFactory) 
	{
		super(menuTitle, applicationManager, null, null);
		this.applicationManager = applicationManager;
		this.continuousMappingFactoryServiceRef = v;
		this.vmm = vmm1;
		this.vFactory = vFactory1;
		this.viewFactory = viewFactory1;
		this.viewManager = viewManager1;
		//this.vs = (VisualStyle) vFactory;
		setPreferredMenu("Apps");
	}
	public void actionPerformed(ActionEvent e) {
	
			final CyNetworkView currentNetworkView = applicationManager.getCurrentNetworkView();
		//	 JOptionPane.showMessageDialog(null, "Start222222222");
			final CyNetwork network = currentNetworkView.getModel();
		    
		    JOptionPane.showMessageDialog(null, "Enjoy counting motifs :)");
		    String out = "";
		    int numnodes = network.getNodeCount() ;
		   // out += "NumNodes = " +numnodes + "\n";
		    try{
		    //out+= MenuAction.generateGraph(numnodes, 2) + "\n";
		    	
		    }
		    catch(Exception e1)
			{
				
				JOptionPane.showMessageDialog(null, "Error" + e1.toString());
				
			}
		    /*
		    int from,to;
		    //JOptionPane.showMessageDialog(null, "NUM "+out);
		    for(CyEdge edge :	network.getEdgeList())
		    {
		    	
		    	from = edge.getSource().hashCode();
		    	to = edge.getTarget().hashCode();
		    	//out += MenuAction.addEdge(from, to) + "\n" ;
		    //	out += from + "    ,    "+ to + "\n";
		    }
		    */
		    //MenuAction.finalize(1);
		    //JOptionPane.showMessageDialog(null, "SUM "+ out);
		 
		
	}
	public static void initGraph(int dir)
	{
		 //JOptionPane.showMessageDialog(null, "Start11111");
		
		 
		 CyNetworkView currentNetworkView = null;
		 
		 try
		 {
			 currentNetworkView = applicationManager.getCurrentNetworkView();
		 }
		 catch(Exception eapp)
		 {
			 JOptionPane.showMessageDialog(null, eapp);
		 }
		 //JOptionPane.showMessageDialog(null, "Start222222222");
		 
		final CyNetwork network = currentNetworkView.getModel();
		
	    //JOptionPane.showMessageDialog(null, "Start");
	    String out = "";
	    String out2 = "";
	    int numnodes = network.getNodeCount() ;
	    out2 += numnodes;
	    out2+= "\n";
	    out2 += network.getEdgeCount();
	    
	    Gt_to_Cy = new int [numnodes];
	    
	  
	  	    if(numnodes >= 3){
	  	    	
	    try{
	    	
	    	if(dir == 1)
	    		graphM.createGraph(numnodes, GraphType.DIRECTED);
	    	else
	    		graphM.createGraph(numnodes, GraphType.UNDIRECTED);
	    
	    }
	    catch(Exception e1)
		{
			
			JOptionPane.showMessageDialog(null, "Error" + e1.toString());
			
		}
	    

	    int Counter = 0;
	    
	    for(CyNode nod :	network.getNodeList())
	    {
	    	CytoGtrie.put(nod.hashCode(), Counter);
	    	Gt_to_Cy[Counter] = nod.hashCode();
	    	Counter ++;	
	    }
	    
	    
	    for(CyEdge edge :	network.getEdgeList())
	    {
	    	graphM.addEdge(CytoGtrie.get(edge.getSource().hashCode()), CytoGtrie.get(edge.getTarget().hashCode()));
	    }
	    
	    graphM.sortNeighbours();
	    graphM.makeArrayNeighbours();
	    
	    String f =  "--";// + MenuAction.finalize(1)+ "--";
	    out += f;//"--" 
	    out2 += f;
	    out2+= "\n N=   ";
	    out2+= CytoGtrie.size() +   "\n";
	  	}
	}
	
	
	public static void colorSpecificGraph(String g )
	{
		 
		Map<Integer, Integer> colorDictionary = new HashMap();

		 CyNetworkView currentNetworkView = null;
		 
		 try
		 {
			 currentNetworkView = applicationManager.getCurrentNetworkView();
		 }
		 catch(Exception eapp)
		 {
			 JOptionPane.showMessageDialog(null, eapp);
		 }
		 
		final CyNetwork network = currentNetworkView.getModel();
	    
		//Graph model
		boolean directed = false;
	    CyEdge edge =	network.getEdgeList().get(0);
	    if(edge.isDirected())
	    	directed = true;
	    
	    String out = "";
	    int numnodes = network.getNodeCount() ;
	    int [] nodes = null;
	    try{
	    	
	    		nodes = GTrieNode.myMap2.get(g.trim());
	    		
	    	
	    }
	    catch(Exception e1)
		{
			
			JOptionPane.showMessageDialog(null, "Error" + e1.toString());
			
		}
	    if(nodes.length == 0)
	    {	JOptionPane.showMessageDialog(null, "Error Happened" );
	    
	    }
	    else
	    {
   	 	int minn=nodes[0], maxn=0;
   	 
	   	 for(int n=0 ; n< nodes.length;n++)
	   	 {
	   		int node = Gt_to_Cy[n];
	   		 
	   		 nodesToColor.put(node, nodes[n]);
	   		colorDictionary.put(nodes[n], 1);

	   		 if(nodes[n] < minn)
	   			 minn = nodes[n];
	   		 if(nodes[n] > maxn)
	   			 maxn = nodes[n];
	   		
	   	 }
	   	
	   	 
	   	
	   	 String colName = "#Occurrences";
	   	 if(numColoring == 0)
	   	{
	   	 try {
	   		 //if(!network.getDefaultNodeTable().getColumns().contains(colName))
	   			 network.getDefaultNodeTable().createColumn(colName, Integer.class, true);
	   		 
	   		numColoring++;
	   		
	   		
		} catch (Exception e) {
			System.out.println("Error in creating new column:  " + numColoring);
		}
	   		
	   	
	   	}
	   	
	   	 
	   	
	   	for(CyNode nod :	network.getNodeList())
	    {
	   		
	    	network.getDefaultNodeTable().getRow(nod.getSUID()).set(colName, nodesToColor.get(nod.hashCode()));
	    	
	    }
	   	
	 // Create our view
	 		CyNetworkView view = viewFactory.createNetworkView(network);
	 		//viewManager.addNetworkView(view);
	   	VisualStyle style = vmm.getVisualStyle(view);
		
		ContinuousMapping mapping = (ContinuousMapping) 
				continuousMappingFactoryServiceRef.createVisualMappingFunction(colName, 
			                                             Integer.class,
			                                             BasicVisualLexicon.NODE_FILL_COLOR);  
	 // Define the points
	   	int val1 =  minn;//2d;
	   	Color g1 = new Color(126,236,126);//(8, 199, 154);
	   	Color g2 = new Color(94,209,94);//(255, 227, 130);
	   	Color g3 = new Color(64,183,64);//(96,109,128);
	   	Color g4 = new Color(37,149,37);//(86,126,187);
	   	Color g5 = new Color(19, 121, 19);//(43,76,126);
	   	Color g6 = new Color(243,182,184);//(31,31,32);
	   	Color g7 = new Color(222,117,121);//(189,42,78);
	   	Color g8 = new Color(199,92,95);//(157,100,110);
	   	Color g9 = new Color(185,49,54);//(190,80,37);
	   	Color g10 = new Color(161,18,23);//(190,37,37);
	   	
		BoundaryRangeValues<Paint> brv1 =
			   	new BoundaryRangeValues<Paint>(g1, g1, g2);
	   	int val2 =  maxn;//12d;
	   	BoundaryRangeValues<Paint> brv10 =
	   	new BoundaryRangeValues<Paint>(g9, g10 , g10);
	   	
	   	BoundaryRangeValues<Paint> brv2 =
	   		   	new BoundaryRangeValues<Paint>(g1, g2 , g3);
	   	BoundaryRangeValues<Paint> brv3 =
	   		   	new BoundaryRangeValues<Paint>(g2, g3 , g4);
	   	BoundaryRangeValues<Paint> brv4 =
	   		   	new BoundaryRangeValues<Paint>(g3, g4 , g5);
	   	BoundaryRangeValues<Paint> brv5 =
	   		   	new BoundaryRangeValues<Paint>(g4, g5 , g6);
	   	BoundaryRangeValues<Paint> brv6 =
	   		   	new BoundaryRangeValues<Paint>(g5, g6 , g7);
	   	BoundaryRangeValues<Paint> brv7 =
	   		   	new BoundaryRangeValues<Paint>(g6, g7 , g8);
	   	BoundaryRangeValues<Paint> brv8 =
	   		   	new BoundaryRangeValues<Paint>(g7, g8 , g9);
	   	BoundaryRangeValues<Paint> brv9 =
	   		   	new BoundaryRangeValues<Paint>(g8, g9 , g10);
	   	

	   	// Set the points
	   	int all = colorDictionary.size();//maxn - minn; //Number of different occurrences
	   	int ratio = all / 10;
	   	
	   	
	   
	   	//	int [] values = new int[10]; 
	   	 
	   	SortedSet<Integer> difColors = new TreeSet<Integer>(colorDictionary.keySet());
	   	Iterator tailSetIt = difColors.iterator();
	   	int val , ctr = 0;
	   	if(ratio > 0)
	   	{
	   	        while (tailSetIt.hasNext()) {
	   	        	int sum = 0;
	   	        	int cc;
	   		   		for(cc= 0;cc< ratio ; cc++)
	   		   		{
	   		   			if(tailSetIt.hasNext())
	   		   			{
	   		   				val =  (int) tailSetIt.next();
	   		   				sum+= val;
	   		   			}
	   		   			else
	   		   				break;
	   		   			 
	   		   		}
	   		   		if(ctr < 10)
	   		   		{
	   		   			values[ctr] = sum/ratio;
	   		   			ctr++;
	   		   		}
	   		   		else
	   		   		{
	   		   			values[9] = sum/cc;
	   		   		}
	   		   		

	   	        }
	   
	   	        
	   	}
	   	else // when the number of different occerences less than 10
	   	{
	   		boolean flagFilled = true;
	   		for(int cc= 0; cc< 10 ;cc++)
	   		{
	   			flagFilled = true;
	   			//while (tailSetIt.hasNext()) {
	   			if(tailSetIt.hasNext()){
	   				values[cc] = (int) tailSetIt.next();
	   				flagFilled = false;
	   			}
	   			if(flagFilled) 
	   			{
	   				values[cc] = -1;//values[cc-1] + 5;
	   			}
	   		}
	   	}
	   	 	mapping.addPoint(values[0], brv1);
		   	mapping.addPoint(values[1], brv2);
		   	mapping.addPoint(values[2] , brv3);
		   	mapping.addPoint(values[3] , brv4);
		   	mapping.addPoint(values[4] , brv5);
		   	mapping.addPoint(values[5] , brv6);
		   	mapping.addPoint(values[6] , brv7);
		   	mapping.addPoint(values[7] , brv8);
		   	mapping.addPoint(values[8] , brv9);
		   	mapping.addPoint(values[9] , brv10);
	   	        
	 
	 // unlock node size
        Set<VisualPropertyDependency<?>> deps = style.getAllVisualPropertyDependencies();
        for(VisualPropertyDependency<?> dep: deps) {
            dep.setDependency(false);
        }
        
	   	Color NODE_LABEL_COLOR = Color.BLACK;//new Color(50, 150, 50);
	   	Color NODE_BORDER_COLOR = Color.GRAY;
	    // set node related default
	   	style.setDefaultValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.ELLIPSE);
        //vs.setDefaultValue(BasicVisualLexicon.NODE_FILL_COLOR, NODE_COLOR);
	   	style.setDefaultValue(BasicVisualLexicon.NODE_LABEL_COLOR, NODE_LABEL_COLOR);
	   	style.setDefaultValue(BasicVisualLexicon.NODE_BORDER_PAINT, NODE_BORDER_COLOR);
	   	style.setDefaultValue(BasicVisualLexicon.NODE_TRANSPARENCY, 220);
	   	style.setDefaultValue(BasicVisualLexicon.NODE_LABEL_FONT_SIZE, 14);
	   	
	 // add the mapping to visual style
	   	style.addVisualMappingFunction(mapping); 
	 		vmm.addVisualStyle(style);
	 		vmm.setVisualStyle(style, view);
	 		
	    }
	  
	}

	
	public static String selectNodes(int id)
	{
		int from , to;
		if(values[id - 1] > 0)
		{
			if(id > 1){
				from = values[id - 2];
				to = values[id - 1];
			}
			else
			{
					from = 0;
					to = values[id - 1];
				
			}
			
			
			return "The nodes colored with this color occurred between: " + from + "   and: " + to + "  times.";
			
		}
		else
		{
			return "This color does not exsit, number of ranges is less than 10.";
		}
		
		/*
		CyNetworkView currentNetworkView = null;
		 
		 try
		 {
			 currentNetworkView = applicationManager.getCurrentNetworkView();
		 }
		 catch(Exception eapp)
		 {
			 JOptionPane.showMessageDialog(null, eapp);
		 }
		 
		final CyNetwork network = currentNetworkView.getModel();
	    
		for(CyNode nod :	network .getNodeList())
	    {
	   		
	    	//network.getDefaultNodeTable().getRow(nod.getSUID()).set(colName, nodesToColor.get(nod.hashCode()));
	    	
	    }*/
		//JOptionPane.showMessageDialog(null, "ID:   " + id);
	}
}
