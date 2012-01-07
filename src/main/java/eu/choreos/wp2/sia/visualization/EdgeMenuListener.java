package eu.choreos.wp2.sia.visualization;

/*
 * EdgeMenuListener.java
 *
 * Created on March 21, 2007, 2:41 PM; Updated May 29, 2007
 * Copyright March 21, 2007 Grotto Networking
 */

import edu.uci.ics.jung.visualization.VisualizationViewer;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.Vertex;

public interface EdgeMenuListener {
	
    /**
     * Used to set the edge and visulization component.
     * @param e 
     * @param visComp 
     */
     void setEdgeAndView(Edge e, VisualizationViewer<Vertex, Edge> visView); 
    
}