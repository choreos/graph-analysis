package eu.choreos.wp2.sia.graph.algorithms;

import java.util.HashMap;
import java.util.Map;

import edu.uci.ics.jung.algorithms.scoring.ClosenessCentrality;
import edu.uci.ics.jung.graph.DirectedGraph;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.Vertex;

public class ClosenessCentralityCalculator {

	/**
	 * TODO: write closeness centrality equation
	 * @return
	 */
	public Map<Vertex, Double> calculateVerticesClosenessCentrality(
			DirectedGraph<Vertex, Edge> graph){
	
		Map<Vertex, Double> centralities = new HashMap<Vertex, Double>();
		ClosenessCentrality<Vertex, Edge> ranker = 
				new ClosenessCentrality<Vertex, Edge>(graph);
		
		for(Vertex v : graph.getVertices()){
			centralities.put(v, ranker.getVertexScore(v));
		}

		return centralities;
	}
	
}
