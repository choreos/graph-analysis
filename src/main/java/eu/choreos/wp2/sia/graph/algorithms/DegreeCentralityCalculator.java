package eu.choreos.wp2.sia.graph.algorithms;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import edu.uci.ics.jung.graph.DirectedGraph;
import eu.choreos.wp2.sia.graph.entity.DegreeCentrality;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.Vertex;

public class DegreeCentralityCalculator {

	private final Comparator<DegreeCentrality> inDegreeComparator = new Comparator<DegreeCentrality>() {
		public int compare(DegreeCentrality dc1, DegreeCentrality dc2){
			return dc1.getInDegree().compareTo(dc2.getInDegree());
		}
	};
	
	private final Comparator<DegreeCentrality> outDegreeComparator = new Comparator<DegreeCentrality>() {
		public int compare(DegreeCentrality dc1, DegreeCentrality dc2){
			return dc1.getOutDegree().compareTo(dc2.getOutDegree());
		}
	};
	
	public Map<Vertex, DegreeCentrality> calculateVerticesDegreeCentrality(DirectedGraph<Vertex, Edge> graph) {
		
		Map<Vertex, DegreeCentrality> vDegCen = 
				new HashMap<Vertex, DegreeCentrality>();
		
		for (Vertex v: graph.getVertices()) {
			double in = (double) graph.inDegree(v);
			double out = (double) graph.outDegree(v);
			vDegCen.put(v, new DegreeCentrality(in, out));
		}
		
		return vDegCen;
	}	
	
	public DegreeCentrality calculateGraphDegreeCentrality(DirectedGraph<Vertex, Edge> graph) {
		
		Map<Vertex, DegreeCentrality> centralities = calculateVerticesDegreeCentrality(graph);
		double highestIn = getHighestInDegreeCentrality(graph);
		double highestOut = getHighestOutDegreeCentrality(graph);

		double in = 0, out = 0;
		for (Vertex v: graph.getVertices()) {
			in += highestIn - centralities.get(v).getInDegree();  
			out += highestOut - centralities.get(v).getOutDegree();  
		}
		
		int den = (graph.getVertexCount() - 1) * (graph.getVertexCount() - 2);
		
		return new DegreeCentrality(in/den, out/den);
	}
	
	private double getHighestInDegreeCentrality(DirectedGraph<Vertex, Edge> graph) {
		
		Collection<DegreeCentrality> centralities = calculateVerticesDegreeCentrality(graph).values();
		DegreeCentrality dc = Collections.max(centralities, inDegreeComparator);
		return dc.getInDegree();
	}

	private double getHighestOutDegreeCentrality(DirectedGraph<Vertex, Edge> graph) {
		
		Collection<DegreeCentrality> centralities = calculateVerticesDegreeCentrality(graph).values();
		DegreeCentrality dc = Collections.max(centralities, outDegreeComparator);
		return dc.getOutDegree();
	}

}