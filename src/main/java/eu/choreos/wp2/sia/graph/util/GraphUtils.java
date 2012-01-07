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
			//_computeTransitiveClosure(graph, transitiveClosure, v, v);
			traverse(graph, transitiveClosure, v);
		}
		
		return transitiveClosure;
	}

	/*DFS recursivo
	private static void _computeTransitiveClosure(DirectedGraph<Vertex, Edge> graph, 
			DirectedGraph<Vertex, Edge> transitiveClosure, Vertex v, Vertex w) {
		
		if (!v.equals(w)){
			SimpleEdge simpleEdge = new SimpleEdge(
					v.toString() + "," + w.toString());
			
			transitiveClosure.addEdge(simpleEdge, v, w);	
		}
		
		for (Vertex suc: graph.getSuccessors(w)){			
			if(transitiveClosure.findEdge(v, suc) == null){
				_computeTransitiveClosure(graph, transitiveClosure, v, suc);
			}
		}
	}	
	*/
	
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
