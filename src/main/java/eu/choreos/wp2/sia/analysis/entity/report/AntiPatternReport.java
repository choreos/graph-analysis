package eu.choreos.wp2.sia.analysis.entity.report;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import edu.uci.ics.jung.graph.DirectedGraph;
import eu.choreos.wp2.sia.graph.algorithms.DegreeCentralityCalculator;
import eu.choreos.wp2.sia.graph.entity.DegreeCentrality;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.Vertex;

public class AntiPatternReport {

	private DirectedGraph<Vertex, Edge> dependencyGraph;
	
	private ButterfliesReport butterfliesReport; 
	private SensitiveReport sensitivesReport;
	private HubsReport hubsReport;

	private Map<Vertex, DegreeCentrality> degreeCentrality;
	
	public AntiPatternReport(DirectedGraph<Vertex, Edge> dependencyGraph){
		this.dependencyGraph = dependencyGraph;
		
		this.degreeCentrality = new DegreeCentralityCalculator().calculateVerticesDegreeCentrality(dependencyGraph); 
		
		this.butterfliesReport = new ButterfliesReport(dependencyGraph);
		this.sensitivesReport = new SensitiveReport(dependencyGraph);
		this.hubsReport = new HubsReport(dependencyGraph);
	}

	public DirectedGraph<Vertex, Edge> getDependencyGraph(){
		return dependencyGraph;
	}
	
	public Map<Vertex, DegreeCentrality> getDegreeCentrality(){
		return degreeCentrality;
	}
	
	public DegreeCentrality getDegreeCentrality(Vertex v){
		return degreeCentrality.get(v);
	}
	
	public ButterfliesReport getButterfliesReport() {
		return butterfliesReport;
	}

	public SensitiveReport getSensitivesReport() {
		return sensitivesReport;
	}

	public HubsReport getHubsReport() {
		return hubsReport;
	}	
	
	public boolean isAntiPattern(Vertex v){
		return butterfliesReport.isButterfly(v) ||
				sensitivesReport.isSensitive(v) ||
				hubsReport.isHub(v);
	}
	
	public Collection<AntiPattern> getAntiPatterns(Vertex v){
		Collection<AntiPattern> antiPatternsFound = new HashSet<AntiPattern>();
		antiPatternsFound.addAll(butterfliesReport.getAntiPatterns(v));
		antiPatternsFound.addAll(sensitivesReport.getAntiPatterns(v));
		antiPatternsFound.addAll(hubsReport.getAntiPatterns(v));
		return antiPatternsFound;
	}
}
