package eu.choreos.wp2.sia.graph.entity;

import java.util.Map;

import edu.uci.ics.jung.graph.DirectedGraph;

/**
 * You can understand these centralities here: http://en.wikipedia.org/wiki/Centrality 
 * 
 * @author leofl
 *
 */
public interface CentralityAnalysis {
	
	/**
	 * Returns the graph used to calculate the metrics
	 * @return the analyzed graph
	 */
	public DirectedGraph<Vertex, Edge> getAnalyzedGraph();
	
	/**
	 * Returns the degree centrality of the whole graph
	 * @return the graph degree centrality
	 */
	public DegreeCentrality getGraphDegreeCentrality();

	/**
	 * Returns the degree centrality of each vertex of the graph
	 * @return a map with the relation between each vertex and its degree centrality
	 */
	public Map<Vertex, DegreeCentrality> getVerticesDegreeCentrality();

	/**
	 * Returns the betweeness centrality of each vertex of the graph
	 * @return a map with the relation between each vertex and its betweeness centrality
	 */
	public Map<Vertex, Double> getVerticesBetweennessCentrality();

	/**
	 * Returns the closeness centrality of each vertex of the graph
	 * If a vertex reaches no one else, its closeness centrality is <code>Double.NaN</code>
	 * @return a map with the relation between each vertex and its closeness centrality
	 */
	public Map<Vertex, Double> getVerticesClosenessCentrality();
	
	/**
	 * Returns the page rank of each vertex of the graph.
	 * The page rank is a variant of the Eigenvector centrality for directed graphs
	 * @return a map with the relation between each vertex and its page rank
	 */	
	public Map<Vertex, Double> getVerticesPageRank();

}
