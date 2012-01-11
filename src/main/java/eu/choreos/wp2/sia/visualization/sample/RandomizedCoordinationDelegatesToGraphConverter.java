package eu.choreos.wp2.sia.visualization.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import eu.choreos.wp2.sia.analysis.converters.CoordinationDelegatesToGraphConverter;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.SimpleEdge;
import eu.choreos.wp2.sia.graph.entity.SimpleVertex;
import eu.choreos.wp2.sia.graph.entity.Vertex;
import eu.choreos.wp3.middleware.entity.CoordinationDelegate;

/**
 * Ignores coordination delegates and builds a random directed graph
 * @author Guilherme Nogueira
 *
 */
public class RandomizedCoordinationDelegatesToGraphConverter implements 
CoordinationDelegatesToGraphConverter{
	
	int vertexes;
	int edges;
	
	DirectedGraph<Vertex, Edge> graph = new DirectedSparseGraph<Vertex, Edge>();

	@Override
	public DirectedGraph<Vertex, Edge> convert(List<CoordinationDelegate> coordinationDelegates) {
		return graph;
	}
	
	public void setSize(int numVertexes, int numEdges){
		this.vertexes = numVertexes;
		this.edges = numEdges;
	}
	
	public void generateGraph()	{
		List<Vertex> Vertexes = new ArrayList<Vertex>();
		
		// creates and adds vertexes
		for(int i=0; i<vertexes; i++){
			Vertexes.add(new SimpleVertex(i));
			graph.addVertex(Vertexes.get(0));
		}
		
		Random rand = new Random();
		
		//Adds the edges
		for(int i=0; i<edges; i++){
			Vertex from = Vertexes.get(rand.nextInt(vertexes));
			Vertex to = Vertexes.get(rand.nextInt(vertexes));
			while (to.equals(from)) {
				to = Vertexes.get(rand.nextInt(vertexes));
			}
			graph.addEdge(new SimpleEdge(i), from, to);
		}	
	}
}
