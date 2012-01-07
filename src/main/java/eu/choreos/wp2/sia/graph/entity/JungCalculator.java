package eu.choreos.wp2.sia.graph.entity;

import edu.uci.ics.jung.graph.DirectedGraph;
import eu.choreos.wp2.sia.graph.algorithms.BetweennessCentralityCalculator;
import eu.choreos.wp2.sia.graph.algorithms.ClosenessCentralityCalculator;
import eu.choreos.wp2.sia.graph.algorithms.DegreeCentralityCalculator;
import eu.choreos.wp2.sia.graph.algorithms.PageRankCalculator;
import eu.choreos.wp2.sia.graph.algorithms.StabilityCalculator;

public class JungCalculator {
	
	private CentralityAnalysis centralityAnalysis;
	private StabilityAnalysis stabilityAnalysis;
	
	private DirectedGraph<Vertex, Edge> graph;
	
	public JungCalculator() {
	}

	public JungCalculator(DirectedGraph<Vertex, Edge> graph) {
		this.graph = graph;
	}

	public DirectedGraph<Vertex, Edge> getGraph() {
		return graph;
	}

	public void setGraph(DirectedGraph<Vertex, Edge> graph) {
		this.graph = graph;
		this.centralityAnalysis = null;
		this.stabilityAnalysis = null;
	}

	
	public StabilityAnalysis calculateStabilityAnalysis(){
		
		if (this.stabilityAnalysis == null) {
			StabilityCalculator calculator = new StabilityCalculator();
			double overallStability = calculator.calculateOverallStability(this.graph);
			this.stabilityAnalysis = new StabilityResults(overallStability);
		}
		return this.stabilityAnalysis;
	}
	
	public CentralityAnalysis calculateCentralityAnalysis(){
		
		if (this.graph == null)
			throw new IllegalStateException("The graph should not be null");		
		
		if (this.centralityAnalysis != null)
			return this.centralityAnalysis;
			
		CentralityResults centralityResults = new CentralityResults(graph);
		
		DegreeCentralityCalculator degreeCalc = new DegreeCentralityCalculator();
		centralityResults.setVerticesDegreeCentrality(degreeCalc.calculateVerticesDegreeCentrality(this.graph));
		centralityResults.setGraphDegreeCentrality(degreeCalc.calculateGraphDegreeCentrality(this.graph));
		
		ClosenessCentralityCalculator closenessCalc = new ClosenessCentralityCalculator();		
		centralityResults.setVerticesClosenessCentrality(closenessCalc.calculateVerticesClosenessCentrality(this.graph));
		
		BetweennessCentralityCalculator betweenessCalc = new BetweennessCentralityCalculator();				
		centralityResults.setVerticesBetweenessCentrality(betweenessCalc.calculateVerticesBetweennessCentrality(this.graph));
		
		PageRankCalculator pageRankCalc = new PageRankCalculator();
		centralityResults.setVerticesPageRank(pageRankCalc.calculatePageRank(graph));
		
		this.centralityAnalysis = centralityResults;
		return centralityResults;
	}
	
}