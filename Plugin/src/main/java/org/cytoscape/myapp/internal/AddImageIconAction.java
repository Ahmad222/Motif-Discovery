package org.cytoscape.myapp.internal;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;

public class AddImageIconAction extends AbstractCyAction {
	            
	        public AddImageIconAction( final CyApplicationManager applicationManager){
	        	super(DEFAULT, applicationManager, null, null);
	           ImageIcon icon = new ImageIcon("\\images\\logo.jpg");//(getClass().getResource("/images/pic1.jpg"));
	    
	            putValue(LARGE_ICON_KEY, icon);
	          
	       }
	   
	      public boolean isInToolBar() {
	         return true;
	       }

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
}
