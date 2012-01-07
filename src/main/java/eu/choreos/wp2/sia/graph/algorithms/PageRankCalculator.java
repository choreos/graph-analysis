package eu.choreos.wp2.sia.graph.algorithms;

import java.util.HashMap;
import java.util.Map;

import edu.uci.ics.jung.algorithms.scoring.PageRank;
import edu.uci.ics.jung.graph.DirectedGraph;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.Vertex;

public class PageRankCalculator {

	public Map<Vertex, Double> calculatePageRank(DirectedGraph<Vertex, Edge> graph){

		Map<Vertex, Double> pageRank = new HashMap<Vertex, Double>();
		
		PageRank<Vertex,Edge> ranker = new PageRank<Vertex,Edge>(graph, 1);
		ranker.initialize();
		ranker.evaluate();
		
		for (Vertex v: graph.getVertices()) {
			pageRank.put(v, ranker.getVertexScore(v));
		}
		
		return pageRank;
	}
	
}
