package eu.choreos.wp2.sia.analysis.converters;

import java.util.List;

import edu.uci.ics.jung.graph.DirectedGraph;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.Vertex;
import eu.choreos.wp3.middleware.entity.CoordinationDelegate;

public interface CoordinationDelegatesToGraphConverter {

	public DirectedGraph<Vertex, Edge> convert(List<CoordinationDelegate> 
			coordinationDelegates);
	
}
