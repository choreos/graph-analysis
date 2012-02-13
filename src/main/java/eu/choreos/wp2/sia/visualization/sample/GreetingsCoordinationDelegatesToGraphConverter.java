package eu.choreos.wp2.sia.visualization.sample;

import java.util.List;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import eu.choreos.wp2.sia.analysis.converters.CoordinationDelegatesToGraphConverter;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.SimpleEdge;
import eu.choreos.wp2.sia.graph.entity.SimpleVertex;
import eu.choreos.wp2.sia.graph.entity.Vertex;
import eu.choreos.wp3.middleware.entity.CoordinationDelegate;

/**
 * Ignores coordination delegates and builds a hardcoded directed graph
 * @author gustavo
 *
 */
public class GreetingsCoordinationDelegatesToGraphConverter implements 
	CoordinationDelegatesToGraphConverter{

	@Override
	public DirectedGraph<Vertex, Edge> convert(
			List<CoordinationDelegate> coordinationDelegates) {
		
		DirectedGraph<Vertex, Edge> graph = 
				new DirectedSparseGraph<Vertex, Edge>();
		
		//Creates the vertexes
		SimpleVertex a = new SimpleVertex("client");
		
		SimpleVertex b = new SimpleVertex("hello_greetings");
		b.setData("http://localhost:1234/hello");
		
		//Adds the vertexes
		graph.addVertex(a);
		graph.addVertex(b);
		
		//Adds the edges
		graph.addEdge(new SimpleEdge("1"), a, b);
		
		return graph;
	}
}
