
package org.cytoscape.myapp.internal;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.ProcessBuilder.Redirect;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.activation.MailcapCommandMap;
import javax.print.DocFlavor.URL;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.ScrollPaneConstants;
import javax.swing.SortOrder;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.plaf.OptionPaneUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.cytoscape.application.swing.CytoPanel;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.application.swing.CytoPanelState;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.omg.CORBA._PolicyStub;


public class MyCytoPanel extends JPanel implements CytoPanelComponent{
	 //AAA//	MenuAction obj = new MenuAction(null, "");
 //String gtrieFILE;
 int flag_Method_Applied = 1;
 
 //final JProgressBar progressBar1 = new JProgressBar();
 String res [] = null;
 long startTime = 0;
 long elapsedTime = 0L;
 boolean flagFinishExe = false , flagFinishProgress = false, flagProgressSet = false;
 long expectedNumOccurences;
 int randNetworkNum;
 int sampleRatio = 2;
 String currentPath = "";
 FileInputStream fiStream;
 static String lastMessae = "";
 static long timeLatsMessage = 0L;
 //long countingExpectedTime = -1;
public MyCytoPanel() {
		
	 final JPanel [] mainPanel = new JPanel[4];
	 setLayout(new GridLayout(4,1));
	 for(int n = 0; n < 4; n++) {
		 mainPanel[n] = new JPanel();
	      add(mainPanel[n]);
	   }
	 
	 JPanel frow = new JPanel(new GridLayout(1,2));
	 JPanel frow1 = new JPanel(new GridLayout(1,1));
	 Font font = new Font("Arial", Font.BOLD, 16);
	 final Font font1 = new Font("Arial", Font.BOLD, 10);
	 final Font font12 = new Font("Arial", Font.BOLD, 12);
	 final Color fontColor = new Color(52, 71, 117);
	 final Color buttonColor = new Color(220, 220, 220);//new Color(191, 185, 192);
	 final JTextField numRand = new JTextField(20);
	 numRand.setFont(font12);
	 numRand.setBackground(buttonColor);
	 numRand.setForeground(fontColor);
	 numRand.setText("0");
	 
	 
	
	 
	 GridBagConstraints c = new GridBagConstraints();
	 //if (shouldFill) {
         //natural height, maximum width
         c.fill = GridBagConstraints.HORIZONTAL;
//}
         
	JLabel msglabel = new JLabel("Welcome to This Frame");
	msglabel.setAlignmentX(CENTER_ALIGNMENT);
	msglabel.setAlignmentY(CENTER_ALIGNMENT);
	msglabel.setBackground(buttonColor);
	msglabel.setForeground(fontColor);
	msglabel.setFont(font);
	
	JLabel sgSize = new JLabel("     Motif Size");
	sgSize.setAlignmentX(CENTER_ALIGNMENT);
	sgSize.setAlignmentY(CENTER_ALIGNMENT);
	sgSize.setBackground(buttonColor);
	sgSize.setForeground(fontColor);
	sgSize.setFont(font12);
	
	JLabel sgType = new JLabel("     Motif Type");
	sgType.setAlignmentX(CENTER_ALIGNMENT);
	sgType.setAlignmentY(CENTER_ALIGNMENT);
	sgType.setBackground(buttonColor);
	sgType.setForeground(fontColor);
	sgType.setFont(font12);
	
	
	JLabel numRands = new JLabel(" # Random Networks");
	numRands.setAlignmentX(CENTER_ALIGNMENT);
	numRands.setAlignmentY(CENTER_ALIGNMENT);
	numRands.setBackground(buttonColor);
	numRands.setForeground(fontColor);
	numRands.setFont(font12);
	
	
	final JLabel LogoLabel = new JLabel("Welcome to Count Occurences plugin");
	LogoLabel.setAlignmentX(CENTER_ALIGNMENT);
	LogoLabel.setAlignmentY(CENTER_ALIGNMENT);
	LogoLabel.setForeground(fontColor);
	LogoLabel.setBackground(fontColor);
	LogoLabel.setFont(font);
	
	final JLabel DescriptionLabel = new JLabel("");
	DescriptionLabel.setAlignmentX(CENTER_ALIGNMENT);
	DescriptionLabel.setAlignmentY(CENTER_ALIGNMENT);
	DescriptionLabel.setForeground(fontColor);
	DescriptionLabel.setBackground(fontColor);
	DescriptionLabel.setFont(font1);
	
		
		final JComboBox NumNodes = new JComboBox();
		NumNodes.setBackground(buttonColor);
		NumNodes.setForeground(fontColor);
		NumNodes.setFont(font);
		
		NumNodes.addItem("3");
		NumNodes.addItem("4");
		NumNodes.addItem("5");
		NumNodes.addItem("6");
		NumNodes.addItem("7");
		//NumNodes.addItem("8");
		//NumNodes.addItem("9");
	
		 
	    final String[] columnNames1 = {"Subgraph",
	            "Frequency"  , "Z-Score"};

	    int numRows = 5;
		final String[][] data1 = new String [numRows][] ;
		for(int i= 0; i<numRows ; i++)
			data1[i] = new String[2];
		
		
		final JFrame frameDraw = new JFrame();
		frameDraw.setSize(500, 500); 
		frameDraw.addWindowListener(new WindowAdapter() {
	       public void windowClosing(WindowEvent windowEvent){
	    	   frameDraw.dispose();
	       }        
	    }); 
		final JTable table1 = new JTable(data1, columnNames1);
		if(flag_Method_Applied == 1)
		{
			table1.setVisible(false);
		
		}
		JScrollPane scrollpane = new JScrollPane(table1);
		
       
	
	
		 final Label statusbar = new Label();
		 //statusbar.setBackground(buttonColor);
		 statusbar.setForeground(fontColor);
		 statusbar.setFont(font);
			
		///here crop
	    
		 
		 
		 
		 
		 final JComboBox comboDirected = new JComboBox();
		 comboDirected.setBackground(buttonColor);
		 comboDirected.setForeground(fontColor);
		 comboDirected.setFont(font);
			
		 comboDirected.addItem("Undirected");
		 comboDirected.addItem("Directed");
		 
	   
	    final JProgressBar progressBar1 = new JProgressBar();
	 	//int p = MenuAction.progress(1);
	    //progressBar1.setValue(p);
	 	
	    progressBar1.setStringPainted(true);
	    Border border1 = BorderFactory.createTitledBorder("Progress...");
	    progressBar1.setBorder(border1);
	    progressBar1.setVisible(false);
	    
		JButton CountOccurences = new JButton("Run");
		CountOccurences.setBackground(buttonColor);
		CountOccurences.setForeground(fontColor);
		CountOccurences.setFont(font);
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;//gd.getDisplayMode().getWidth();
		int height = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;//gd.getDisplayMode().getHeight();
		
        final int hight1 = (int) Math.ceil((height * 3)/17); // 120
        int hight2 = hight1/2; // 60
        final int hight3 = hight1/3;
        
        final int width1 = (int) Math.ceil(width/3.7);//(int) Math.ceil(width/3.5);
        final int width2 = width1/3;
        
		
		LogoLabel.setPreferredSize(new Dimension(width1, hight2));
		NumNodes.setPreferredSize(new Dimension(width2, hight2));
		numRand.setPreferredSize(new Dimension(width2, hight2));
		statusbar.setPreferredSize(new Dimension(width2, hight2));
		comboDirected.setPreferredSize(new Dimension(width2, hight2));
		CountOccurences.setPreferredSize(new Dimension(width2, hight2));
		
		
		frow = new JPanel(new GridLayout(3,1)); 
		frow1 = new JPanel(new GridLayout(1,3));
		frow1.setPreferredSize(new Dimension(width1, hight3));
		frow1.add(sgSize);
		frow1.add(sgType);
		frow1.add(numRands);
		frow.add(frow1);
		
		
		frow1 = new JPanel(new GridLayout(1,3));
		frow1.setPreferredSize(new Dimension(width1, hight3));
		frow1.add(NumNodes);
		frow1.add(comboDirected);
		frow1.add(numRand);
		frow.add(frow1);
		
	    frow1 = new JPanel(new GridLayout(1,1));
	    frow1.setPreferredSize(new Dimension(width1, hight3));
		frow1.add(CountOccurences);
		frow.add(frow1);
		numRand.setToolTipText("Number of Random Networks");
		mainPanel[0].add(frow);
		
		DescriptionLabel.setPreferredSize(new Dimension(width1, hight2));
		progressBar1.setPreferredSize(new Dimension(width1, hight2));
		
		frow = new JPanel(new GridLayout(2,1));
		 
	    frow1 = new JPanel(new GridLayout(1,1));
		frow1.add(DescriptionLabel);
		frow.add(frow1);
		 
		frow1 = new JPanel(new GridLayout(1,1));
		frow1.add(progressBar1);
		frow.add(frow1);
		 
		mainPanel[1].add(frow);
	    
		CountOccurences.addActionListener(new ActionListener() {
			
	    @SuppressWarnings("null")
		public void actionPerformed(ActionEvent e) {	   
	       if(comboDirected.getSelectedItem().toString().equals("Directed"))
		        {
	    		   MenuAction.initGraph(1);
	    		   
		        }
	    	   else
	    	   {
	    		   MenuAction.initGraph(2);
	    	   }
	    	   
	    	   
	    	   final int ms = Integer.parseInt((String)NumNodes.getSelectedItem());
	    	   
	    	   
	    	   final Thread runExpectation = new Thread(new Runnable() {
	    		     public void run() {
	    		    	 expectedNumOccurences = ESU.countSubgraphsSample(MenuAction.graphM, ms, 0.5) * 2;
	    		    	 
	    		     }
	    	   });
	    	   runExpectation.start();
	    	   //JOptionPane.showMessageDialog(null, "Preparing to count Occurences");
	    	   try {
				runExpectation.join();
			} catch (InterruptedException e3) {
				e3.printStackTrace();
			}
	    	   
	    	   //JOptionPane.showMessageDialog(null, "expectedNumOccurences: " + expectedNumOccurences);
	    	    res = null;
	    	   startTime = System.currentTimeMillis();
	    	   
	    		   //create the path of the aux file
	    	/*	   
			try {
					   currentPath="";
						String currentPath1 = MyCytoPanel.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
						JOptionPane.showMessageDialog(null, "current Path1:   " + currentPath1);
						String workingDir = System.getProperty("user.dir");
						JOptionPane.showMessageDialog(null, "current Path2:   " + workingDir);
						
						String [] auxArr = currentPath1.split("/");
						for (String  f : auxArr) {
							if((!f.endsWith(".jar"))&& f.length() !=0)
								currentPath+=f+"\\";
						}
						//JOptionPane.showMessageDialog(null, "Path :  " + currentPath);
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				   //JOptionPane.showMessageDialog(null, "current Path3:   " + currentPath);
				   //currentPath= "C:\\Users\\Ahmad\\CytoscapeConfiguration\\3\\apps\\installed\\";
	    		   */
	    			  
	    			try{
	    				   
	    			if(!((comboDirected.getSelectedItem().toString().equals("Directed"))&&(ms > 6)))
		    		{   
	    				   if(comboDirected.getSelectedItem().toString().equals("Directed"))
	   		   		        {
	   		   	    		   
	    					   //currentPath+= "GtrieAux\\Gtrie\\dir"+ms+".gt";
	    					   currentPath = "/dir"+ms+".gt";
	    					   //expectedNumOccurences = (2 * ms) * expectedNumOccurences;
	   		   		        }
	   		   	    	   else
	   		   	    	   	{
	   		   	    		   //currentPath+= "GtrieAux\\Gtrie\\undir"+ms+".gt";
	   		   	    		   currentPath = "/undir"+ms+".gt";
	   		   	    	   	}
	    				   
	    				   
	    				  
	    				  
	    				  // java.net.URL ss = MyCytoPanel.class.getResource("/undir3.gt");
	    				   //JOptionPane.showMessageDialog(null, "1:   " + currentPath);
	    				   InputStream iStream = MyCytoPanel.class.getResourceAsStream(currentPath);
	    				   if(iStream == null)
	    				   {
	    					   JOptionPane.showMessageDialog(null, "Error happened while loading auxiliary files");
	    					   return;
	    				   }
	    				   Path temp = Files.createTempFile("resource-", ".gt");
	    				   //JOptionPane.showMessageDialog(null, "2:   " + currentPath);
		    				Files.copy(iStream, temp, StandardCopyOption.REPLACE_EXISTING);
		    				//JOptionPane.showMessageDialog(null, "3:   " + currentPath);
		    				fiStream = new FileInputStream(temp.toFile());
		    				//JOptionPane.showMessageDialog(null, "4:   " + currentPath);
		    				iStream.close();
		    				//JOptionPane.showMessageDialog(null, "5:   " + currentPath);
		    				/*byte[] buffer2 = new byte[1024];
		    				int nRead1 = fiStream.read(buffer2 , 0, 10);
		    				JOptionPane.showMessageDialog(null, "current Path55:   " + new String(buffer2) + "\n\n" );
		    				 */  
	    				  
	    				   //JOptionPane.showMessageDialog(null, "current PathFF:   " + currentPath);
		 					
	    					   
	    					   final Thread runGTrie = new Thread(new Runnable() {
	    			    		     public void run() {
	    			    		    	// String res1 [] = null;
	    		    		   try{
	    		    			   
	    		    			   flagFinishExe = false;
	    		    			 
	    		    			   //res = MenuAction.CountOccurences(1,ms, gtrieFILE);
	    		    			   int numRandI = 0;
	    		    			   if (numRand.getText().matches("[0-9]+") && numRand.getText().length() >= 2) {
	    		    				   numRandI = Integer.parseInt(numRand.getText());
	    		    				   }
	    		    				   
	    		    			   	 // make test on numRandI 
	    		    			    //res = MenuAction.CountOccurences(1,ms, currentPath, numRandI);
	    		    			   //JOptionPane.showMessageDialog(null, "55:   " + currentPath);	
	    		    			   Gtrie gt_original = new Gtrie();
	    		    			   	//gt_original.readFromFile(currentPath);
	    		    			   	//JOptionPane.showMessageDialog(null, "6:   " + currentPath);
	    		    			   	gt_original.readFromFile2(fiStream);
	    		    			   //	JOptionPane.showMessageDialog(null, "7:   " + currentPath);
	    		    			   	fiStream.close();
	    		    			   	GTrieNode.myMap2 = new TreeMap<String, int []>();
	    		    			   	//JOptionPane.showMessageDialog(null, "Complete reading :   " + currentPath);
	    		    				gt_original.census(MenuAction.graphM);
	    		    				
	    		    				res = gt_original.populateGtrie(ms);
	    		    				    		    		        
	    		    				//System.out.println("before");
	    		    				if(numRandI > 0)
	    		    				{
	    		    					
	    		    					flagFinishExe = true;
	    		    					//System.out.println("Inside zz " + numRandI);
	    		    					TreeMap< String, Long> originalOccs = gt_original.populateGtrie2(ms);
	    		    					//System.out.println("Insidessss " + numRandI);
	    		    					//gt_original = new Gtrie(); 
	    		    					//System.out.println("Insidewwwwwww " + currentPath);
	    		    					//gt_original.readFromFile(currentPath);
	    		    					//System.out.println("Inside2  " + numRandI);
	    		    					String [] finRes = new String[originalOccs.size()+1];
	    		    					finRes[0] = "**"+ originalOccs.size() +"#"+GTrieNode.counterT;
	    		    					int rCtr = 1;
	    		    					//System.out.println("Inside3  " + numRandI);
	    		    					Tmap [] m_count =  new Tmap[numRandI];
	    		    					for (int i = 0; i< numRandI; i++) {
	    		    						randNetworkNum = i;
	    		    						//System.out.println("Ins" + i);
	    		    						// Create new random network from previous one
		    		    					MenuAction.graphM.makeVectorNeighbours();
		    		    					Rand.markovChainPerEdge(MenuAction.graphM, 3, 10);//random_exchanges, random_tries);
		    		    					MenuAction.graphM.sortNeighbours();
		    		    					MenuAction.graphM.makeArrayNeighbours();
		    		    					gt_original.census(MenuAction.graphM);
		    		    					m_count[i] = new Tmap(gt_original.populateGtrie2(ms));
		    		    					
	    		    					}
	    		    					
	    		    					// Compute significance
	    		    				for(Map.Entry<String,Long> entry : originalOccs.entrySet()) {	
	    		    					String key = entry.getKey();
	    		    					Long value = entry.getValue();
	    		    					// Average frequency
	    		    					double avg = 0;
	    		    					for(int i = 0; i< numRandI ; i++)
	    		    					{
	    		    						avg += m_count[i].tree.get(key);
	    		    					}
	    		    					// Standard deviation
	    		    					double dev = 0;
	    		    					for(int i = 0; i< numRandI ; i++)
	    		    					{
	    		    						dev += (m_count[i].tree.get(key) - avg)* (m_count[i].tree.get(key) - avg) / (numRandI - 1);
	    		    					}
	    		    					dev = Math.sqrt(dev);
	    		    					// zscore
	    		    					double zscore = (value - avg) / dev;
	    		    					finRes[rCtr++] = key+"#"+ value+"#" + zscore;
	    		    					//System.out.println("res: " + avg+"#" + zscore);
	    		    				}
	    		    				res = finRes;
	    		    				}
	    		    				
	    		    			    elapsedTime = System.currentTimeMillis() - startTime;
	    		    			    flagFinishExe = true;
	    		    	 		   }
	    		    		   catch(Exception e1)
	    		    		   {
	    		    			   
	    		    		   }
	    			    	}
	    		       });
	    		    		   
	    		    		   
	    			runGTrie.start();
	    					 
	    			mainPanel[2].removeAll(); 
	    		    mainPanel[2].repaint();
	    			mainPanel[3].removeAll(); 
	    			mainPanel[3].repaint();
	    			 /////////////////////////////
	    					   Thread t = new Thread(){
	    					        public void run(){
	    					        	 progressBar1.setVisible(true);
	    					        	 int p = 0;
	    					        	 int ctr = 0;
	    					        	 while(p<expectedNumOccurences)
	    			    	  		    	{
	    					                
	    					                p = GTrieNode.counterT + 1;  
	    					                final int percent = (int) ((p*100)/expectedNumOccurences);
	    					                progressBar1.setValue(0);
	    					                SwingUtilities.invokeLater(new Runnable() {
	    					                    public void run() {
	    					                        progressBar1.setValue(percent);
	    					                        //Expected Remaining Time
	    					                        
	    					                        long expectedRemainingTime = 0;
	    					                        if((percent < 100) && percent > 0)
	    					                        	expectedRemainingTime =  (((100*(100-percent))/percent) * (System.currentTimeMillis() - startTime))/100;
	    					                        //countingExpectedTime = expectedRemainingTime;
	    					                        if(expectedRemainingTime > 1000)
	    			    		 					{
	    			    		 						DescriptionLabel.setText(" Expected remaining time is: " + (expectedRemainingTime/1000) + " seconds  and " + (expectedRemainingTime %1000) + "  milliseconds");
	    			    		 					}
	    			    		 					else
	    			    		 					{
	    			    		 						DescriptionLabel.setText(" Expected remaining time in MS: " + expectedRemainingTime);
	    			    		 						
	    			    		 					}
	    			    		 					
	    					                    }
	    					                  });
	    					                if(flagFinishExe == true)
	    			 						{
	    					                	
	    			 							progressBar1.setValue(100);
	    			 							
	    			 							break;
	    			 						
	    			 						}
	    			 					
	    					                try {
	    					                    Thread.sleep(30);
	    					                } catch (InterruptedException e) {}
	    					            }
	    					        	 flagFinishProgress = true;
	    					        }
	    					    };
	    					   ////////////////////////////
	    					   
	    					   
	    					   
	    					   
	    					    t.start();
	    					    // progressss
	    					    /// the following message boxes because the cytoscape needs like a triger to update the progress bar
	    					    
 		    				  final JOptionPane optionPane2 = new JOptionPane("Processing... Please Wait", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);

		    				   final JDialog dialog2 = new JDialog();
		    				   dialog2.setTitle("Message");
		    				   dialog2.setModal(true);

		    				   dialog2.setContentPane(optionPane2);

		    				  // dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		    				   dialog2.pack();
		    				   int delay2 = 50;
		    				   ActionListener taskPerformer2 = new ActionListener() {
 		    				       public void actionPerformed(ActionEvent evt) {
 		    				           //...Perform a task...
 		    				    	   dialog2.dispose();
 		    				       }
 		    				   };
 		    				  
 		    				 while(!flagFinishProgress)
	    					    {
	 		    					if(!dialog2.isShowing())
	    					    	{
	    					    		
	 		    						new javax.swing.Timer(delay2, taskPerformer2).start();
	 		 		    				  dialog2.setVisible(true);
	    					    	}
	 		    					else
	 		    						Thread.sleep(20);
	 		    					 	
	    					    	
	    					    }
 		    			
 		    				  try {
 								t.join();
 								//new javax.swing.Timer(5, taskPerformer).start();
 								
 								
 							} catch (InterruptedException e2) {
 								// TODO Auto-generated catch block
 								e2.printStackTrace();
 							}
 		    				   
 		    				
	    	  		    	 try {
	    	  		    		 
	    	  		    		final JOptionPane optionPane1 = new JOptionPane("Please Wait ... Calculating Random Networks", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
	    						
 		    				   final JDialog dialog1 = new JDialog();
 		    				   dialog1.setTitle("Message");
 		    				   dialog1.setModal(true);

 		    				   dialog1.setContentPane(optionPane1);

 		    				  // dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
 		    				   dialog1.pack();
 		    				   
 		    				   int delay1 = 50; //milliseconds
 		    				   
 		    				   ActionListener taskPerformer1 = new ActionListener() {
 		    				       public void actionPerformed(ActionEvent evt) {
 		    				           //...Perform a task...
 		    				    	   dialog1.setVisible(false);
 		    				    	   dialog1.dispose();
 		    				       }};
 		    				       
 		    				    new javax.swing.Timer(delay1, taskPerformer1).start();
  						   		dialog1.setVisible(true);
	    	  		    		 //print that calculating random
	    	  		    		if (numRand.getText().matches("[0-9]+")) {
	    		    				   if( Integer.parseInt(numRand.getText())> 0)
	    		    				   {   
	    		    					   
	    		    					   
	    		    					   
			    		    				   
		    		    				       progressBar1.setMaximum(Integer.parseInt(numRand.getText()));
		    		    					   progressBar1.setMinimum(0);
		    		    					   //progressBar1.setIndeterminate(true);
		    		    					   progressBar1.setVisible(true);
		    		    					   //dialog1.setMaximumSize(new Dimension(12, 12));
	    		    					   while(runGTrie.isAlive())//!flagFinishProgress)//t.isAlive())
	   	    					    		{
			   	 		    					
		    		    					   DescriptionLabel.setText(" Calculating Random Networks... " + randNetworkNum + "  /  " + numRand.getText());
		    		    					   progressBar1.setValue(randNetworkNum);
		    		    					   DescriptionLabel.repaint();
		    		    					   
		    		    					   if(!dialog1.isShowing())
		   	    					    			{
		    		    						   		new javax.swing.Timer(delay1, taskPerformer1).start();
		    		    						   		dialog1.setVisible(true);
		    		    						   		Thread.sleep(1000);
		   	    					    			} 	   
		    		    					   //else
		    		    						 // Thread.sleep(20);   
			    		    				   
		    		    				   
			    		    				   
			    		    				   //Thread.sleep(20);
		    		    				   
		    		    				   }
	    		    				   }
	    	  		    		}
	    	  		    		runGTrie.join();
	    	  		    		//Thread.sleep(30);
	    	  		    		
	    						} catch (InterruptedException e1) {
	    							e1.printStackTrace();
	    						}
	    					   
		    			    }
	    				   else {
	    					   JOptionPane.showMessageDialog(null, "The maximum supported motif size in case of DIRECTED is 6" );
	   			    		
						}

		    		   }
		    		   catch(Exception e1)
		    		   {
		    			   
		    		   }
	    		   
	    		   
	    	   
	    		   String [] aux ;
    			   int row=0, col=0;
    			   
    			   int numRows = 0 ;
    			   if(res == null)
    				   return;
    			   for(String s : res)
	    		   {
    				   if(!s.startsWith("**"))
  					   {
    					   aux = s.split("#");
    		    			  if(Integer.parseInt( aux[1]) != 0)
    		    			  {
    			    			  numRows++;
    		    			  }   
  					   }
	    			  
	    		   }  
    			   final String[][] dataResult = new String [numRows][] ;
    			   
    				for(int i= 0; i<numRows ; i++)
    					dataResult[i] = new String[3];
    				
    				
    				
    				 for(int row1 = 0; row1<numRows; row1++)
 		    		   {
 		    			  
 		    			  for(col = 0; col<3; col++)
 		    				 dataResult[row1][col] = "";
 		    			
 		    		   }  
    				
    				 
    				//Fill the table
    				 String numGraphs ="" ,numOccurences="";  
  				   for(String s : res)
		    	   {
  					   if(s.startsWith("**"))
  					   {
  						 aux = s.split("#");
		    			  
  						 numGraphs = aux[0].replace("**", "");
  						 numOccurences = aux[1];
			    			
  					   }
  					   else{
  						 aux = s.split("#");
		    			  if(Integer.parseInt( aux[1]) != 0)
		    			  {
			    			  for(col = 0; col<3; col++)
			    				  dataResult[row][col] = aux[col];
			    			  
			    			  row++;
		    			  } 
  					   }
		    			  
		    	   }  
  				   
  				   //Sorting
  				   String tempSg , tempFreq , tempZ;
  				   for(int i1 = 0; i1< numRows ; i1++ )
  				   {
  					   for(int i2 = i1+1 ; i2 < numRows ;  i2++)
  					   {
  						   if((Integer.parseInt(dataResult[i1][1])) < (Integer.parseInt(dataResult[i2][1])))
  						   {
  							   tempFreq = dataResult[i1][1];
  							   tempSg = dataResult[i1][0];
  							   tempZ = dataResult[i1][2];
  								dataResult[i1][0] = dataResult[i2][0];
  								dataResult[i1][1] = dataResult[i2][1];
  								dataResult[i1][2] = dataResult[i2][2];
  								

  							    dataResult[i2][1] = tempFreq; 
  								dataResult[i2][0] = tempSg;
  								dataResult[i2][2] = tempZ;
  											
  						   }
  							   
  							   
  					   }
  				   }
  				   
  				   //Description
  				 if((elapsedTime/1000) > 1)
  				   {
  					 DescriptionLabel.setText("<html> <body>Different Types of Subgraphs Found [Original Network]: "+ numGraphs+"<br>Subgraph Occurrences Found [Original Network]: "+ numOccurences + "<br> Computing Time: "+ (elapsedTime / 1000) + " seconds  and " + (elapsedTime %1000) + "  milliseconds</body></html>");
  				   }
  				 else{
  					DescriptionLabel.setText("<html> <body>Different Types of Subgraphs Found [Original Network]: "+ numGraphs+"<br>Subgraph Occurrences Found [Original Network]: "+ numOccurences + "<br> Computing Time: "+ (elapsedTime / 1000) + " second  and " + (elapsedTime %1000) + "  milliseconds</body></html>");
  				 }
  				 String test = DescriptionLabel.getText();
  				 progressBar1.setValue(0);
  				 progressBar1.setVisible(false);
  				 DescriptionLabel.repaint();
  				 mainPanel[1].repaint();
  				
  				 ////////////Uprate the Description label
  				/*
  				final JOptionPane optionPane1 = new JOptionPane("Mission Complete:" , JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);

			   		final JDialog dialog1 = new JDialog();
			   		dialog1.setTitle("Message");
			   		dialog1.setModal(true);

			   		dialog1.setContentPane(optionPane1);

			   		// dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			   		dialog1.pack();
		   
		   
			   		int delay1 = 500; //milliseconds
			   		ActionListener taskPerformer1 = new ActionListener() {
			   			public void actionPerformed(ActionEvent evt) {
		           //...Perform a task...
		    	   dialog1.dispose();
			   			}
			   		};
			   		//new javax.swing.Timer(delay1, taskPerformer1).start();
		   
			   		dialog1.setVisible(true);
  				 */
  				 
  				 ////////////
  				 final JTable tableResults = new JTable(dataResult,columnNames1);
 				
  				 tableResults.repaint();
  				 tableResults.setEnabled(false);
  				 int w = (int) (0.96* width1);
  				 tableResults.setPreferredSize(new Dimension(w, 18*numRows));
  				 tableResults.setBackground(buttonColor);
  				 tableResults.setForeground(fontColor);
  				 tableResults.getTableHeader().setFont(font12);
  				 tableResults.getTableHeader().setBackground(buttonColor);
  				 tableResults.getTableHeader().setForeground(fontColor);
  				 
  				 tableResults.setFont(font1);
  				 //tableResults.setRowMargin(5);
  				TableColumn col1 = tableResults.getColumnModel().getColumn(0);
  		        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();  
  		        dtcr.setHorizontalAlignment(SwingConstants.CENTER);
  		        col1.setCellRenderer(dtcr);
  		        TableColumn col2 = tableResults.getColumnModel().getColumn(1);
		        col2.setCellRenderer(dtcr);
		        TableColumn col3 = tableResults.getColumnModel().getColumn(2);
		        col3.setCellRenderer(dtcr);
		        
		        col1.setPreferredWidth(w/2); //first column is bigger
		        col2.setPreferredWidth(w/4); //first column is bigger
		        col3.setPreferredWidth(w/4); //first column is bigger
		        
		        tableResults.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		        
		        
		        ////////header on click
		        JTableHeader Resheader = tableResults.getTableHeader();
		        Resheader.setUpdateTableInRealTime(true);
		        Resheader.addMouseListener(new MouseAdapter() {
		        	
		        	@Override
		            public void mouseClicked(MouseEvent e) {
		                int colClicked = tableResults.columnAtPoint(e.getPoint());
		                String nameC = tableResults.getColumnName(colClicked);
		                //System.out.println("Column index selected " + col + " " + name);
		               // JOptionPane.showMessageDialog(null, "clicked " + nameC);
		                int numRowz = dataResult.length;
		                String tempSg , tempFreq , tempZ;
		                
		                if(nameC == "Frequency")
		                {
		                	if((Integer.parseInt(dataResult[0][1])) < (Integer.parseInt(dataResult[numRowz-1][1])))
		                	{
		                		 for(int i1 = 0; i1< numRowz ; i1++ )
				  				   {
				  					   for(int i2 = i1+1 ; i2 < numRowz ;  i2++)
				  					   {
				  						   if((Integer.parseInt(dataResult[i1][1])) < (Integer.parseInt(dataResult[i2][1])))
				  						   {
				  							   tempFreq = dataResult[i1][1];
				  							   tempSg = dataResult[i1][0];
				  							   tempZ = dataResult[i1][2];
				  							   
				  								dataResult[i1][0] = dataResult[i2][0];
				  								dataResult[i1][1] = dataResult[i2][1];
				  								dataResult[i1][2] = dataResult[i2][2];
				  								

				  							    dataResult[i2][1] = tempFreq; 
				  								dataResult[i2][0] = tempSg;
				  								dataResult[i2][2] = tempZ;
				  											
				  						   }
				  							   
				  							   
				  					   }
				  				   }
		                	}
		                	else
		                	{
		                		 for(int i1 = 0; i1< numRowz ; i1++ )
				  				   {
				  					   for(int i2 = i1+1 ; i2 < numRowz ;  i2++)
				  					   {
				  						   if((Integer.parseInt(dataResult[i1][1])) > (Integer.parseInt(dataResult[i2][1])))
				  						   {
				  							   tempFreq = dataResult[i1][1];
				  							   tempSg = dataResult[i1][0];
				  							   tempZ = dataResult[i1][2];
				  							   
				  								dataResult[i1][0] = dataResult[i2][0];
				  								dataResult[i1][1] = dataResult[i2][1];
				  								dataResult[i1][2] = dataResult[i2][2];
				  								

				  							    dataResult[i2][1] = tempFreq; 
				  								dataResult[i2][0] = tempSg;
				  								dataResult[i2][2] = tempZ;
				  											
				  						   }
				  							   
				  							   
				  					   }
				  				   }
		                	}
		                	
		                }
		                else
		                {
		                	if(nameC =="Z-Score")
		                	{
		                		if((Double.parseDouble(dataResult[0][2])) < (Double.parseDouble(dataResult[numRowz-1][2])))
			                	{
		                			for(int i1 = 0; i1< numRowz ; i1++ )
					  				   {
					  					   for(int i2 = i1+1 ; i2 < numRowz ;  i2++)
					  					   {
					  						   if((Double.parseDouble(dataResult[i1][2])) < (Double.parseDouble(dataResult[i2][2])))
					  						   {
					  							   tempFreq = dataResult[i1][1];
					  							   tempSg = dataResult[i1][0];
					  							   tempZ = dataResult[i1][2];
					  							   
					  								dataResult[i1][0] = dataResult[i2][0];
					  								dataResult[i1][1] = dataResult[i2][1];
					  								dataResult[i1][2] = dataResult[i2][2];
					  								
					  								dataResult[i2][2] = tempZ;
					  							    dataResult[i2][1] = tempFreq; 
					  								dataResult[i2][0] = tempSg;
					  											
					  						   }
					  							   
					  							   
					  					   }
					  				   }
			                	}
		                		else
		                		{
		                			for(int i1 = 0; i1< numRowz ; i1++ )
					  				   {
					  					   for(int i2 = i1+1 ; i2 < numRowz ;  i2++)
					  					   {
					  						   if((Double.parseDouble(dataResult[i1][2])) > (Double.parseDouble(dataResult[i2][2])))
					  						   {
					  							   tempFreq = dataResult[i1][1];
					  							   tempSg = dataResult[i1][0];
					  							   tempZ = dataResult[i1][2];
					  							   
					  							   dataResult[i1][0] = dataResult[i2][0];
					  							   dataResult[i1][1] = dataResult[i2][1];
					  							   dataResult[i1][2] = dataResult[i2][2];
					  								
					  							   dataResult[i2][2] = tempZ;
					  							   dataResult[i2][1] = tempFreq; 
					  							   dataResult[i2][0] = tempSg;
					  											
					  						   }
					  							   
					  							   
					  					   }
					  				   }
		                		}
		                		
		                	}
		                }
		  				  
		                
		                
		            }
				});
		        
		        //Resheader.addMouseListener(tableModel.new ColumnListener(tableResults));
		        Resheader.setReorderingAllowed(true);
		        ///////
		        
		        
		        
		        
		        tableResults.setAutoscrolls(true);
		        //tableResults.setPreferredScrollableViewportSize(new Dimension(width1 + 100, hight1));
		        
		        tableResults.setFillsViewportHeight(true);
		        tableResults.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		        JScrollPane js=new JScrollPane(tableResults);
		        tableResults.repaint();
		        js.repaint();
		        js.setVisible(true);
		        //add(js);
		        //////////////refresh
		        //new javax.swing.Timer(delay1, taskPerformer1).start();
				   
		   		//dialog1.setVisible(true);
		        /////////////
		        tableResults.addMouseListener(new java.awt.event.MouseAdapter() {
  				    @Override
  				    public void mouseClicked(java.awt.event.MouseEvent evt) {
  				        int row = tableResults.rowAtPoint(evt.getPoint());
  				        int col = tableResults.columnAtPoint(evt.getPoint());
  				        if (row >= 0 && col >= 0) {
  				        //	 lbXYZ.setText(row  + "   " + col);  
  				        //	 lbXYZ.repaint();
  				        
  				        	 frameDraw.getContentPane().removeAll();
  				        	
  				        	 String str = dataResult[row][0];//"0111000000000010";
  				        	 /////str = data1[row][0];/////
  				        	 char[] charArray = str.toCharArray();
  				        	 int size = (int) Math.sqrt( charArray.length);
  				        	boolean directed = false;//true;
  				        	if(comboDirected.getSelectedItem().toString().equals("Directed"))
  				        	{
  				        		directed = true;
  				        	}
  				        	 
  				        	// char adj [] = {'0','1','1','1','0','0','0','0','0','0','0','0','0','0','0','0'};
  				        	 DrawGraph.setvals(size, directed,charArray, hight1);
  				        	 //frameDraw.add(new DrawGraph());
  				        	DrawColors.setvals(10 , 0 , 100, hight1);
  				        	 //frameDraw.setVisible(true);
  				        	JScrollPane frow2 = new JScrollPane();
  				        	
  				        	 JPanel frow1 = new JPanel(new GridLayout(1,2));
  				        	
  			  				frow1.add(new DrawGraph());
  			  				frow1.add(new DrawColors());
  			  				
  			  				frow1.setPreferredSize(new Dimension(width1, hight1));
  			  				
  				        	mainPanel[2].removeAll(); 
  			  				mainPanel[2].add(frow1);
  				        	 
  				        	 
  				        	 // coloring the graph.
  				        	 MenuAction.colorSpecificGraph(str);
  				        	//MenuAction.numColoring++;
  				        	 
  				        }
  				    }
  				});
		        
  				mainPanel[2].addMouseListener(new java.awt.event.MouseAdapter() {
  					
  					String occs = "";
  				   // @Override
  				    public void mouseClicked(java.awt.event.MouseEvent evt) {
  				    	//Color c =   evt.getPoint();
  				    	if((mainPanel[2].isShowing()) && (MenuAction.numColoring > 0))
  				    	{
  				    		Graphics2D g2d = (Graphics2D) new DrawColors().getGraphics() ;
  				    		//System.out.println("MOUSE ");
  				    		//JOptionPane.showMessageDialog(null, "Coords:   " + evt.getPoint().x + " :  " + evt.getPoint().y  +"  width: " + width1 + "   hight: " + hight1);
  				    		if(evt.getPoint().x > 3 * (width1/4)) //Second Column
  				    		{
  				    			if(evt.getPoint().y < (hight1 / 5))
  				    			{	
  				    				occs = MenuAction.selectNodes(6);
  				    				
  				    			}
  				    			else
  				    			{
  				    				if(evt.getPoint().y < 2*(hight1 / 5))
  				    				{
  				    					occs = MenuAction.selectNodes(7);
  				    					//JOptionPane.showMessageDialog(null, occs);
  				    					
  				    				}
  				    				
  				    				else{
  				    					if(evt.getPoint().y < 3*(hight1 / 5)){
  				    						occs = MenuAction.selectNodes(8);
  				    						//JOptionPane.showMessageDialog(null,  occs);
  				    						
  				    				}
  				    				else{
  				    					if(evt.getPoint().y < 4*(hight1 / 5)){
  				    						occs = MenuAction.selectNodes(9);
  				    						//JOptionPane.showMessageDialog(null,  occs);
  				    						
  				    					}
  				    					else
  				    					{
  				    						occs = MenuAction.selectNodes(10);
			    							//JOptionPane.showMessageDialog(null,  occs);
  				    						
  				    					}
  				    				}
	  				    					
	  				    					
  				    						
  				    					}
  				    			}
  				    				
  				    				
  				    		}
  				    		else
  				    		{
  				    			if(evt.getPoint().x > (width1/2)) // First Column
  				    			{
  				    				if(evt.getPoint().y < (hight1 / 5))
  	  				    			{	
  				    					occs = MenuAction.selectNodes(1);
  	  				    				//JOptionPane.showMessageDialog(null, occs);
  				    					
  	  				    			}
  	  				    			else
  	  				    			{
  	  				    				if(evt.getPoint().y < 2*(hight1 / 5))
  	  				    				{
  	  				    					occs = MenuAction.selectNodes(2);
  	  				    					//JOptionPane.showMessageDialog(null,  occs);
	  	  				    				
  	  				    				}
  	  				    				else{
  	  				    					if(evt.getPoint().y < 3*(hight1 / 5)){
  	  				    						occs = MenuAction.selectNodes(3);
	  	  	  				    					//JOptionPane.showMessageDialog(null, occs);
	  	  				    					
  	  				    				}
  	  				    				else{
  	  				    					if(evt.getPoint().y < 4*(hight1 / 5)){
  	  				    						occs = MenuAction.selectNodes(4);
  	  				    						//JOptionPane.showMessageDialog(null, occs);
	  	  				    					
  	  				    					}
  	  				    					else
  	  				    					{
  	  				    						occs = MenuAction.selectNodes(5);
			    								//JOptionPane.showMessageDialog(null, occs);
	  	  				    					
  	  				    					}
  	  				    				}
  		  				    					
  		  				    					
  	  				    						
  	  				    					}
  	  				    			}
  				    			}
  				    		}
  				    	
  				    		
  				    		if((!occs.equals(lastMessae)) || ((System.nanoTime() - timeLatsMessage)/1000000000)> 3)
  				    		{
  				    			lastMessae = occs;
  				    			timeLatsMessage = System.nanoTime();
  				    			JOptionPane.showMessageDialog(null, occs);
  				    		}
  				    		
  				    	}
  				    	
  				    }
  				    
  				}
  				);
		        /*
		        JScrollPane scrollPane = new JScrollPane(tableResults);
		        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		        */
		        
  				 JPanel frow1 = new JPanel(new GridLayout(1,1));
  				 frow1.add(js);
  				 frow1.setPreferredSize(new Dimension(width1 , hight1));
  				//frow1.setAlignmentX(JComponent.CENTER_ALIGNMENT);
  				
  				mainPanel[3].removeAll(); 
  				mainPanel[3].add(frow1);
	    	   
	    	   }
  				
    	//	   }
    		 
	    });
		
		
		
	      
	    
	      this.setVisible(true);
	      
	      
	     
	      	
	      
	      
	      
	}

	@Override
	public Component getComponent() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public CytoPanelName getCytoPanelName() {
		
		return CytoPanelName.WEST;
	}

	@Override
	public Icon getIcon() {
		
		
		
		
		return null;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Motif_Discovery";
	}
	
}

