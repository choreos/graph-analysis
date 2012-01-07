package eu.choreos.wp2.sia.analysis.entity.report;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import edu.uci.ics.jung.graph.DirectedGraph;
import eu.choreos.wp2.sia.analysis.detectors.HubDetector;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.Vertex;

public class HubsReport {

	private Set<Vertex> localHubs;
	private Set<Vertex> globalHubs;
	
	public HubsReport(DirectedGraph<Vertex,Edge> graph){
		
		HubDetector hubDetector = new HubDetector(graph);
		this.localHubs = hubDetector.detectLocalHubs();
		this.globalHubs = hubDetector.detectGlobalHubs();
	}

	public Set<Vertex> getLocalHubs(){
		return localHubs;
	}
	
	public Set<Vertex> getGlobalHubs(){
		return globalHubs;
	}
	
	public boolean isLocalHub(Vertex v){
		return localHubs.contains(v);
	}
	
	public boolean isGlobalHub(Vertex v){
		return globalHubs.contains(v);
	}
	
	public boolean isHub(Vertex v){
		return isLocalHub(v) || isGlobalHub(v);
	}

	public Collection<AntiPattern> getAntiPatterns(Vertex v) {
		Collection<AntiPattern> antiPatternsFound = new HashSet<AntiPattern>();
		if (isLocalHub(v)) antiPatternsFound.add(AntiPattern.LOCAL_HUB);
		if (isGlobalHub(v)) antiPatternsFound.add(AntiPattern.GLOBAL_HUB);
		return antiPatternsFound;
	}
	
}
