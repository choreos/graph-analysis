package eu.choreos.wp2.sia.graph.algorithms;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.Vertex;
import eu.choreos.wp2.sia.graph.util.GraphUtils;

public class StabilityCalculator {
	
	public double calculateOverallStability(DirectedGraph<Vertex, Edge> graph) {
		
		if (graph == null)
			throw new IllegalStateException("The graph should not be null");

		// This is the algorithm described in 
		// www.ime.usp.br/~yoshi/2007i/mac328/Slides/2007.04.20/lecture.pdf
			
		DirectedSparseGraph<Vertex, Edge> transitiveClosure = 
				GraphUtils.computeTransitiveClosure(graph); 
		
		int sumImpact = 0;
		for (Vertex v : transitiveClosure.getVertices()){
			int impacted = transitiveClosure.getPredecessorCount(v);
			sumImpact += impacted;
		}
		
		int n = graph.getVertexCount();
		double avgImpact = (double) sumImpact / (n * n);
		double overallStability = 1 - avgImpact;
		
		return overallStability;
	}
	

}