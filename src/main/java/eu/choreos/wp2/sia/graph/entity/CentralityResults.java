package eu.choreos.wp2.sia.graph.entity;

import java.util.Collections;
import java.util.Map;

import edu.uci.ics.jung.graph.DirectedGraph;

public class CentralityResults implements CentralityAnalysis{

	private final DirectedGraph<Vertex, Edge> graph;
	private DegreeCentrality graphDegreeCentrality;
	private Map<Vertex, DegreeCentrality> verticesDegreeCentrality;
	private Map<Vertex, Double> verticesBetweenessCentrality;
	private Map<Vertex, Double> verticesClosenessCentrality;	
	private Map<Vertex, Double> verticesPageRank;
	
	public CentralityResults(DirectedGraph<Vertex, Edge> graph) {
		this.graph = graph;
	}
	
	@Override
	public DirectedGraph<Vertex, Edge> getAnalyzedGraph() {
		return graph;
	}

	@Override
	public DegreeCentrality getGraphDegreeCentrality() {
		return graphDegreeCentrality;
	}

	@Override
	public Map<Vertex, DegreeCentrality> getVerticesDegreeCentrality() {
		return Collections.unmodifiableMap(verticesDegreeCentrality);
	}

	@Override
	public Map<Vertex, Double> getVerticesBetweennessCentrality() {
		return Collections.unmodifiableMap(verticesBetweenessCentrality);
	}

	@Override
	public Map<Vertex, Double> getVerticesClosenessCentrality() {
		return Collections.unmodifiableMap(verticesClosenessCentrality);
	}

	@Override
	public Map<Vertex, Double> getVerticesPageRank() {
		return Collections.unmodifiableMap(verticesPageRank);
	}

	public void setGraphDegreeCentrality(DegreeCentrality graphDegreeCentrality) {
		this.graphDegreeCentrality = graphDegreeCentrality;
	}

	public void setVerticesDegreeCentrality(
			Map<Vertex, DegreeCentrality> verticesDegreeCentrality) {
		this.verticesDegreeCentrality = verticesDegreeCentrality;
	}

	public void setVerticesBetweenessCentrality(
			Map<Vertex, Double> verticesBetweenessCentrality) {
		this.verticesBetweenessCentrality = verticesBetweenessCentrality;
	}

	public void setVerticesClosenessCentrality(
			Map<Vertex, Double> verticesClosenessCentrality) {
		this.verticesClosenessCentrality = verticesClosenessCentrality;
	}
	
	public void setVerticesPageRank(
			Map<Vertex, Double> verticesPageRank) {
		this.verticesPageRank = verticesPageRank;
	}	
}
