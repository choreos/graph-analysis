package eu.choreos.wp2.sia.visualization;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.Vertex;

public interface VertexMenuListener {
	
    void setVertexAndView(DirectedGraph<Vertex, Edge> graph, 
    		Vertex v, VisualizationViewer<Vertex, Edge> visView);    
}