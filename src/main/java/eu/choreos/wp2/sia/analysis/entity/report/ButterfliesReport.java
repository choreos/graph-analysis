package eu.choreos.wp2.sia.analysis.entity.report;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import edu.uci.ics.jung.graph.DirectedGraph;
import eu.choreos.wp2.sia.analysis.detectors.ButterflyDetector;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.Vertex;

public class ButterfliesReport {
	
	private Set<Vertex> localButterflies;
	private Set<Vertex> globalButterflies;
	
	public ButterfliesReport(DirectedGraph<Vertex, Edge> graph){
		ButterflyDetector butterflyDetector = new ButterflyDetector(graph);
		this.localButterflies = butterflyDetector.detectLocalButterflies();
		this.globalButterflies = butterflyDetector.detectGlobalButterflies();
	}

	public Set<Vertex> getLocalButterflies(){
		return localButterflies;
	}
	
	public Set<Vertex> getGlobalButterflies(){
		return globalButterflies;
	}
	
	public boolean isLocalButterfly(Vertex v){
		return localButterflies.contains(v);
	}
	
	public boolean isGlobalButterfly(Vertex v){
		return globalButterflies.contains(v);
	}
	
	public boolean isButterfly(Vertex v){
		return isLocalButterfly(v) || isGlobalButterfly(v);
	}

	public Collection<AntiPattern> getAntiPatterns(Vertex v) {
		Collection<AntiPattern> antiPatternsFound = new HashSet<AntiPattern>();
		if (isLocalButterfly(v)) antiPatternsFound.add(AntiPattern.LOCAL_BUTTERFLY);
		if (isGlobalButterfly(v)) antiPatternsFound.add(AntiPattern.GLOBAL_BUTTERFLY);
		return antiPatternsFound;
	}
}