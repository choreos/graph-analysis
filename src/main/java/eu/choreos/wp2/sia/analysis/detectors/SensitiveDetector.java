package eu.choreos.wp2.sia.analysis.detectors;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import edu.uci.ics.jung.graph.DirectedGraph;
import eu.choreos.wp2.sia.graph.entity.DegreeCentrality;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.Vertex;

public class SensitiveDetector extends AbstractNodeDetector{
	
	private DirectedGraph<Vertex, Edge> graph;
	private int localThreshold;
	private double globalThreshold;
	
	static final ClassLoader loader = ButterflyDetector.class.getClassLoader();
	
	public SensitiveDetector(DirectedGraph<Vertex, Edge> graph){
		this.graph = graph;
		setThresholds();
	}

	public Set<Vertex> detectLocalSensitives(){
		return detectLocalNodes(graph);
	}
	
	public Set<Vertex> detectGlobalSensitives(){
		return detectGlobalNodes(graph);
	}
	
	@Override
	protected boolean isLocal(DegreeCentrality degreeCentrality) {
		return degreeCentrality.getOutDegree() >= localThreshold;
	}

	@Override
	protected boolean isGlobal(DegreeCentrality degreeCentrality) {
		int totalNodes = graph.getVertexCount();
		double percentTotal = degreeCentrality.getOutDegree() / totalNodes; 
		return  percentTotal >= globalThreshold;
	}

	private void setThresholds(){
		try{
			Properties properties = new Properties();
			properties.load(loader.getResourceAsStream("thresholds.properties"));
			
			this.localThreshold = 
					Integer.parseInt(
							properties.getProperty("local_sensitive"));
			
			this.globalThreshold = 
					Double.parseDouble(
							properties.getProperty("global_sensitive"));
		}catch(IOException e){
			System.out.println("Could not read properties file");
			e.printStackTrace();
		}
	}
}
