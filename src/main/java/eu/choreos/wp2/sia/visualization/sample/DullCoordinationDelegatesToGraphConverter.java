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
public class DullCoordinationDelegatesToGraphConverter implements 
	CoordinationDelegatesToGraphConverter{

	@Override
	public DirectedGraph<Vertex, Edge> convert(
			List<CoordinationDelegate> coordinationDelegates) {
		
		DirectedGraph<Vertex, Edge> graph = 
				new DirectedSparseGraph<Vertex, Edge>();
		
		//Creates the vertexes
		Vertex a = new SimpleVertex("a");
		Vertex b = new SimpleVertex("b");
		Vertex c = new SimpleVertex("c");
		Vertex d = new SimpleVertex("d");
		Vertex e = new SimpleVertex("e");
		Vertex f = new SimpleVertex("f");
		Vertex g = new SimpleVertex("g");
		Vertex h = new SimpleVertex("h");
		Vertex i = new SimpleVertex("i");
		
		//Adds the vertexes
		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(c);
		graph.addVertex(d);
		graph.addVertex(e);
		graph.addVertex(f);
		graph.addVertex(g);
		graph.addVertex(h);
		graph.addVertex(i);
		
		//Adds the edges
		graph.addEdge(new SimpleEdge("1"), a, b);
		graph.addEdge(new SimpleEdge("2"), a, c);
		graph.addEdge(new SimpleEdge("3"), a, e);
		graph.addEdge(new SimpleEdge("4"), c, f);
		graph.addEdge(new SimpleEdge("5"), d, a);
		graph.addEdge(new SimpleEdge("6"), e, g);
		graph.addEdge(new SimpleEdge("7"), h, e);
		graph.addEdge(new SimpleEdge("8"), i, a);

		return graph;
	}
}
