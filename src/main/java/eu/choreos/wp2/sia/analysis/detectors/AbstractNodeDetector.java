package eu.choreos.wp2.sia.analysis.detectors;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.uci.ics.jung.graph.DirectedGraph;
import eu.choreos.wp2.sia.graph.algorithms.DegreeCentralityCalculator;
import eu.choreos.wp2.sia.graph.entity.DegreeCentrality;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.Vertex;
import eu.choreos.wp2.sia.graph.util.GraphUtils;

public abstract class AbstractNodeDetector {
	
	protected Set<Vertex> detectLocalNodes(DirectedGraph<Vertex, Edge> graph) {

		Set<Vertex> localNodes = new HashSet<Vertex>();
		
		DegreeCentralityCalculator degreeCalculator = 
				new DegreeCentralityCalculator();
		
		Map<Vertex,DegreeCentrality> degreeCentralityResults = 
				degreeCalculator.calculateVerticesDegreeCentrality(graph);
	
		for (Vertex v: degreeCentralityResults.keySet()) {
			DegreeCentrality degreeCentrality = degreeCentralityResults.get(v);
			if (isLocal(degreeCentrality)){
				localNodes.add(v);
			}
		}
		
		return localNodes;
	}
	
	protected Set<Vertex> detectGlobalNodes(DirectedGraph<Vertex, Edge> graph) {

		Set<Vertex> localNodes = new HashSet<Vertex>();
		
		//Computes the transitive closure
		DirectedGraph<Vertex,Edge> transitiveClosure = 
				GraphUtils.computeTransitiveClosure(graph);

		//Calculates degree centrality
		DegreeCentralityCalculator degreeCalculator = 
				new DegreeCentralityCalculator();
		
		Map<Vertex,DegreeCentrality> degreeCentralityResults = 
				degreeCalculator.calculateVerticesDegreeCentrality(transitiveClosure);
	
		for (Vertex v: degreeCentralityResults.keySet()) {
			DegreeCentrality degreeCentrality = degreeCentralityResults.get(v);
			if (isGlobal(degreeCentrality)){
				localNodes.add(v);
			}
		}
		
		return localNodes;
	}
	
	/**	
	public Set<V> detectRelativeLocal(DirectedGraph<V, E> graph) {
		
		Set<V> localNodes = new HashSet<V>();
	
		//Calculates degree centrality for all nodes		
		DegreeCentralityCalculator<V,E> degreeCalculator = 
				new DegreeCentralityCalculator<V,E>();
		
		Map<V,DegreeCentrality> degreeCentralityResults = 
				degreeCalculator.calculateVerticesDegreeCentrality(graph);
		
		//Adds all degree centrality values in stats
		DescriptiveStatistics stats = new DescriptiveStatistics();
		for (V v: degreeCentralityResults.keySet()) {
			DegreeCentrality degreeCentrality = degreeCentralityResults.get(v);
			stats.addValue(degreeCentrality.getInDegree());
		}
		
		//Do a quartile analysis
		QuartileAnalysis quartileAnalysis = 
				DescriptiveStatisticsUtils.doQuartileAnalysis(stats);
		stats.clear();
		
		//Detect butterfly nodes (outliers)
		for (V v: degreeCentralityResults.keySet()) {
			DegreeCentrality degreeCentrality = degreeCentralityResults.get(v);
			if (isRelativeLocal(
					degreeCentrality, 
					quartileAnalysis.getExtremeUpperWhisker())){
				
				localNodes.add(v);
			}	
		}
		
		return localNodes;
	}
	
	public Set<V> detectRelativeGlobal(DirectedGraph<V, E> graph) {
		
		Set<V> localNodes = new HashSet<V>();
	
		//Calculates degree centrality for all nodes		
		DegreeCentralityCalculator<V,E> degreeCalculator = 
				new DegreeCentralityCalculator<V,E>();
		
		Map<V,DegreeCentrality> degreeCentralityResults = 
				degreeCalculator.calculateVerticesDegreeCentrality(graph);
		
		//Adds all degree centrality values in stats
		DescriptiveStatistics stats = new DescriptiveStatistics();
		for (V v: degreeCentralityResults.keySet()) {
			DegreeCentrality degreeCentrality = degreeCentralityResults.get(v);
			stats.addValue(degreeCentrality.getInDegree());
		}
		
		//Do a quartile analysis
		QuartileAnalysis quartileAnalysis = 
				DescriptiveStatisticsUtils.doQuartileAnalysis(stats);
		stats.clear();
		
		//Detect butterfly nodes (outliers)
		for (V v: degreeCentralityResults.keySet()) {
			DegreeCentrality degreeCentrality = degreeCentralityResults.get(v);
			if (isRelativeGlobal(
					degreeCentrality, 
					quartileAnalysis.getExtremeUpperWhisker())){
				
				localNodes.add(v);
			}	
		}
		
		return localNodes;
	}
	
	private boolean isRelativeLocal(
			DegreeCentrality degreeCentrality, double extremeUpperWhisker) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean isRelativeGlobal(
			DegreeCentrality degreeCentrality, double extremeUpperWhisker) {
		// TODO Auto-generated method stub
		return false;
	}
	*/
	
	protected abstract boolean isLocal(DegreeCentrality degreeCentrality);
	
	protected abstract boolean isGlobal(DegreeCentrality degreeCentrality);
	
}
