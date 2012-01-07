package eu.choreos.wp2.sia.graph.algorithms;

import java.util.HashMap;
import java.util.Map;

import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.graph.DirectedGraph;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.Vertex;

public class BetweennessCentralityCalculator {
	
	/**
	 * TODO: write closeness centrality equation
	 * @return
	 */
	public Map<Vertex, Double> calculateVerticesBetweennessCentrality(
			DirectedGraph<Vertex, Edge> graph){

		Map<Vertex, Double> centralities = new HashMap<Vertex, Double>();
		
		BetweennessCentrality<Vertex, Edge> ranker = 
				new BetweennessCentrality<Vertex, Edge>(graph);
		
		for(Vertex v : graph.getVertices()){
			centralities.put(v, ranker.getVertexScore(v));
		}

		return centralities;
	}
}
