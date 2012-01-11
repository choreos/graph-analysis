package eu.choreos.wp2.sia.graph.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.SimpleEdge;
import eu.choreos.wp2.sia.graph.entity.Vertex;

public abstract class GraphUtils{

	public static DirectedSparseGraph<Vertex, Edge> computeTransitiveClosure(
			DirectedGraph<Vertex, Edge> graph) {
		
		DirectedSparseGraph<Vertex, Edge> transitiveClosure = 
				new DirectedSparseGraph<Vertex, Edge>();
		
		for(Vertex v: graph.getVertices()){
			traverse(graph, transitiveClosure, v);
		}
		
		return transitiveClosure;
	}
	
	public static void traverse(DirectedGraph<Vertex, Edge> graph, 
			DirectedGraph<Vertex, Edge> transitiveClosure, Vertex root){
		
		List<Vertex> visited = new LinkedList<Vertex>();
		
	    LinkedBlockingDeque<Vertex> queue = new LinkedBlockingDeque<Vertex>();
        queue.add(root);
        
        visited.add(root);

	    while(!queue.isEmpty()){
	        Vertex currentVertex = queue.pollFirst();   
	        Collection<Vertex> suc = graph.getSuccessors(currentVertex);
	        for (Vertex v: suc){
	        	if (!visited.contains(v)){
	        		queue.add(v);
	        	}
	        }
	        transitiveClosure.addEdge(new SimpleEdge(root + "," + currentVertex), root, currentVertex);
	        visited.add(currentVertex);
	    }
	}

	
}
