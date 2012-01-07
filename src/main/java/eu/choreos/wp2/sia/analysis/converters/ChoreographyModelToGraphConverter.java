package eu.choreos.wp2.sia.analysis.converters;

import edu.uci.ics.jung.graph.DirectedGraph;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.Vertex;
import eu.choreos.wp3.middleware.entity.ChoreographyModel;

public interface ChoreographyModelToGraphConverter {

	public DirectedGraph<Vertex, Edge> convert(ChoreographyModel chorModel);
	
}
