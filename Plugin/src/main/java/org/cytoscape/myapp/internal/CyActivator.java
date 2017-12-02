package org.cytoscape.myapp.internal;

import java.util.Properties;

import javax.swing.JOptionPane;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanel;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.application.swing.CytoPanelState;
import org.cytoscape.event.CyEventHelper;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.osgi.framework.BundleContext;

public class CyActivator extends AbstractCyActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		
		CyApplicationManager cyApplicationManager = getService(context, CyApplicationManager.class);
		
		
		VisualMappingFunctionFactory vmfFactoryC =
				getService(context,VisualMappingFunctionFactory.class,
				"(mapping.type=continuous)");
		/*VisualMappingFunctionFactory vmfFactoryD =
				getService(context,VisualMappingFunctionFactory.class,
				"(mapping.type=discrete)");
		VisualMappingFunctionFactory vmfFactoryP =
				getService(context,VisualMappingFunctionFactory.class,
				"(mapping.type=passthrough)");
		*/
		
		CyNetworkFactory networkFactory = getService(context, CyNetworkFactory.class);
		CyNetworkManager networkManager = getService(context, CyNetworkManager.class);
		CyNetworkViewManager viewManager = getService(context, CyNetworkViewManager.class);
		CyNetworkViewFactory viewFactory = getService(context, CyNetworkViewFactory.class);
		VisualMappingManager vmm = getService(context, VisualMappingManager.class);
		VisualStyleFactory vFactory = getService(context, VisualStyleFactory.class);

		// To get references to services in CyActivator class
		      //VisualMappingManager vmmServiceRef = getService(context,VisualMappingManager.class);
		                    
		     // VisualStyleFactory visualStyleFactoryServiceRef = getService(context,VisualStyleFactory.class);
		                    
	//	      VisualMappingFunctionFactory vmfFactoryC = getService(context,VisualMappingFunctionFactory.class, "(mapping.type=continuous)");
		//      VisualMappingFunctionFactory vmfFactoryD = getService(context,VisualMappingFunctionFactory.class, "(mapping.type=discrete)");
	//	CyEventHelper eventHelper = getService(context, CyEventHelper.class);

	//	VisualMappingFunctionFactory continuousMappingFactory = 
	 //           getService(context, VisualMappingFunctionFactory.class, "(mapping.type=continuous)");
		
		//VisualStyleFactory vFactory = getService(context, VisualStyleFactory.class);
		/*
		MenuAction action = new MenuAction(cyApplicationManager, "MY Panel", continuousMappingFactory, vFactory);
		*/
		MenuAction action = new MenuAction(cyApplicationManager, "Motif_Discovery", vmfFactoryC , viewManager, viewFactory,vmm, vFactory);
		Properties properties = new Properties();
		
		registerAllServices(context, action, properties);
		
		MyCytoPanel myPanel = new MyCytoPanel();
		 
		
	
		
		//VisualMappingFunctionFactory vmfFactoryC = getService(context,VisualMappingFunctionFactory.class, "(mapping.type=continuous)");
		//int index = myPanel.indexOfComponent(myComponent);
		//myPanel.setSelectedIndex(index);
		//myPanel.setState(CytoPanelState.DOCK);
		//myPanel.enable();
		
				
		//   Register it as a service:
		registerService(context ,myPanel,CytoPanelComponent.class, new Properties());
		
	//   Create an instance:
		   AddImageIconAction addImageIconAction = new AddImageIconAction( cyApplicationManager );
		   //   Register it as a service:
		  registerService(context,addImageIconAction,CyAction.class, new Properties());
	}

}
