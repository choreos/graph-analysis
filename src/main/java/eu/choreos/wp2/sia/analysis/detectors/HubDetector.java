package eu.choreos.wp2.sia.analysis.detectors;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import edu.uci.ics.jung.graph.DirectedGraph;
import eu.choreos.wp2.sia.graph.entity.DegreeCentrality;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.Vertex;

public class HubDetector extends AbstractNodeDetector{
	
	private DirectedGraph<Vertex, Edge> graph;
	
	private int localInThreshold;
	private int localOutThreshold;
	
	private double globalInThreshold;
	private double globalOutThreshold;
	
	static final ClassLoader loader = ButterflyDetector.class.getClassLoader();
	
	public HubDetector(DirectedGraph<Vertex, Edge> graph){
		this.graph = graph;
		setThresholds();
	}

	public Set<Vertex> detectLocalHubs(){
		return detectLocalNodes(graph);
	}
	
	public Set<Vertex> detectGlobalHubs(){
		return detectGlobalNodes(graph);
	}
	
	@Override
	protected boolean isLocal(DegreeCentrality degreeCentrality) {
		return degreeCentrality.getInDegree() >= localInThreshold && 
				degreeCentrality.getOutDegree() >= localOutThreshold;
	}

	@Override
	protected boolean isGlobal(DegreeCentrality degreeCentrality) {
		int totalNodes = graph.getVertexCount();
		
		double percentTotalIn = degreeCentrality.getInDegree() / totalNodes;
		double percentTotalOut = degreeCentrality.getOutDegree() / totalNodes; 
		
		return  percentTotalIn >= globalInThreshold &&
				percentTotalOut >= globalOutThreshold;
	}
	
	private void setThresholds(){
		try{
			Properties properties = new Properties();
			properties.load(loader.getResourceAsStream("thresholds.properties"));
			
			this.localInThreshold = 
					Integer.parseInt(
							properties.getProperty("local_hub_in"));
			
			this.localOutThreshold = 
					Integer.parseInt(
							properties.getProperty("local_hub_out"));
			
			
			this.globalInThreshold = 
					Double.parseDouble(
							properties.getProperty("global_hub_in"));
			
			this.globalOutThreshold = 
					Double.parseDouble(
							properties.getProperty("global_hub_out"));
			
		}catch(IOException e){
			System.out.println("Could not read properties file");
			e.printStackTrace();
		}
	}
}
