package eu.choreos.wp2.sia.analysis.entity.report;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import edu.uci.ics.jung.graph.DirectedGraph;
import eu.choreos.wp2.sia.analysis.detectors.SensitiveDetector;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.Vertex;

public class SensitiveReport{
	
	private Set<Vertex> localSensitives;
	private Set<Vertex> globalSensitives;
	
	public SensitiveReport(DirectedGraph<Vertex,Edge> graph){
		SensitiveDetector sensitiveDetector = new SensitiveDetector(graph);
		this.localSensitives = sensitiveDetector.detectLocalSensitives();
		this.globalSensitives = sensitiveDetector.detectGlobalSensitives();
	}

	public Set<Vertex> getLocalSensitives(){
		return localSensitives;
	}
	
	public Set<Vertex> getGlobalSensitives(){
		return globalSensitives;
	}
	
	public boolean isLocalSensitive(Vertex v){
		return localSensitives.contains(v);
	}
	
	public boolean isGlobalSensitive(Vertex v){
		return globalSensitives.contains(v);
	}
	
	public boolean isSensitive(Vertex v){
		return isLocalSensitive(v) || isGlobalSensitive(v);
	}

	public Collection<AntiPattern> getAntiPatterns(Vertex v) {
		Collection<AntiPattern> antiPatternsFound = new HashSet<AntiPattern>();
		if (isLocalSensitive(v)) antiPatternsFound.add(AntiPattern.LOCAL_SENSITIVE);
		if (isGlobalSensitive(v)) antiPatternsFound.add(AntiPattern.GLOBAL_SENSITIVE);
		return antiPatternsFound;
	}

}
