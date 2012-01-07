package tests.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import eu.choreos.wp2.sia.graph.entity.CentralityAnalysis;
import eu.choreos.wp2.sia.graph.entity.CentralityResults;
import eu.choreos.wp2.sia.graph.entity.DegreeCentrality;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.SimpleEdge;
import eu.choreos.wp2.sia.graph.entity.SimpleVertex;
import eu.choreos.wp2.sia.graph.entity.StabilityAnalysis;
import eu.choreos.wp2.sia.graph.entity.StabilityResults;
import eu.choreos.wp2.sia.graph.entity.Vertex;

/**
 * Page Rank calculations performed through http://www.webworkshop.net/pagerank_calculator.php?pgs=5
 * This page uses a damping factor of 0.15 and PR_0(v) = 0.15 \forall v \in V
 * 
 * @author leonardo
 *
 */
public class GraphFactory {

	public enum TestGraph {SIMPLE, COMPLEX, LOOP};
	
	private Map<TestGraph, DirectedGraph<Vertex,Edge>> graphs = 
			new HashMap<TestGraph, DirectedGraph<Vertex, Edge>>();
	
	private Map<TestGraph, CentralityAnalysis> centralities = 
			new HashMap<TestGraph, CentralityAnalysis>();
	
	private Map<TestGraph, StabilityAnalysis> stabilities = 
			new HashMap<TestGraph, StabilityAnalysis>();
	
	public Map<TestGraph, DirectedGraph<Vertex,Edge>> getAllGraphs() {
		
		if (graphs.isEmpty()) 
			createAllGraphs();
		return Collections.unmodifiableMap(graphs);
	}
	
	public Map<TestGraph, CentralityAnalysis> getAllCentralities() {
		
		if (graphs.isEmpty()) 
			createAllGraphs();
		return Collections.unmodifiableMap(centralities);
	}	
	
	public DirectedGraph<Vertex, Edge> getGraph(TestGraph graph){
			
		if (graphs.get(graph) == null) 
			createGraph(graph);
		return graphs.get(graph);
	}
	

	public CentralityAnalysis getCentralityAnalysis(TestGraph graph) {
	
		if (centralities.get(graph) == null) 
			createGraph(graph);
		return centralities.get(graph);
	}
	
	public StabilityAnalysis getStabilityAnalysis(TestGraph graph) {
		
		if (stabilities.get(graph) == null) 
			createGraph(graph);
		return stabilities.get(graph);
	}

	private void createAllGraphs() {
		
		createSimpleGraph();
		createComplexGraph();
		createLoopGraph();
	}
	
	private void createGraph(TestGraph graph) {
		
		switch (graph) {
			case SIMPLE: 
				createSimpleGraph();
				break;
			case COMPLEX: 
				createComplexGraph();
				break;
			case LOOP:
				createLoopGraph();
				break;
		}
	}
	
	/**
	 *   a <---> b
	 *   |
	 *   |----> c
	 */
	private void createSimpleGraph() {

		// create graph
		
		DirectedSparseGraph<Vertex, Edge> graph = 
				new DirectedSparseGraph<Vertex, Edge>();
		
		//Creates the vertexes
		Vertex a = new SimpleVertex("a");
		Vertex b = new SimpleVertex("b");
		Vertex c = new SimpleVertex("c");
		
		//Adds the vertexes
		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		
		//Adds the edges
		graph.addEdge(new SimpleEdge("1"), a, b);
		graph.addEdge(new SimpleEdge("2"), b, a);
		graph.addEdge(new SimpleEdge("3"), a, c);
		
		// create centrality analysis
		
		Map<Vertex, DegreeCentrality> vDegCent = new HashMap<Vertex, DegreeCentrality>();
		vDegCent.put(a, new DegreeCentrality(0.5, 1));
		vDegCent.put(b, new DegreeCentrality(0.5, 0.5));
		vDegCent.put(c, new DegreeCentrality(0.5, 0));

		Map<Vertex, Double> vClosCent = new HashMap<Vertex, Double>();
		vClosCent.put(a, 1d);
		vClosCent.put(b, 2/3d);
		vClosCent.put(c, Double.NaN);

		Map<Vertex, Double> vBetwCent = new HashMap<Vertex, Double>();
		vBetwCent.put(a, 1d);
		vBetwCent.put(b, 0d);
		vBetwCent.put(c, 0d);

		Map<Vertex, Double> pageRank = new HashMap<Vertex, Double>();
		pageRank.put(a, 0.4344423);
		pageRank.put(b, 0.334638);
		pageRank.put(c, 0.334638);

		CentralityResults centralityAnalysis = new CentralityResults(graph);
		centralityAnalysis.setVerticesDegreeCentrality(vDegCent);
		centralityAnalysis.setGraphDegreeCentrality(new DegreeCentrality(0, 0.75));
		centralityAnalysis.setVerticesClosenessCentrality(vClosCent);
		centralityAnalysis.setVerticesBetweenessCentrality(vBetwCent);
		centralityAnalysis.setVerticesPageRank(pageRank);
		
		StabilityAnalysis stabilityAnalysis = new StabilityResults(0.5555);
		
		graphs.put(TestGraph.SIMPLE, graph);
		centralities.put(TestGraph.SIMPLE, centralityAnalysis);
		stabilities.put(TestGraph.SIMPLE, stabilityAnalysis);
	}
	
	/**
	 * f <--- c       |------> b
	 *        ^       |
	 *        |------ a -----> e <---> g
	 *                ^        ^
	 *                |        |--- h
	 *                d
	 */
	private void createComplexGraph() {

		// create graph
		
		DirectedSparseGraph<Vertex, Edge> graph = new DirectedSparseGraph<Vertex, Edge>();
		
		//Creates the vertexes
		Vertex a = new SimpleVertex("a");
		Vertex b = new SimpleVertex("b");
		Vertex c = new SimpleVertex("c");
		Vertex d = new SimpleVertex("d");
		Vertex e = new SimpleVertex("e");
		Vertex f = new SimpleVertex("f");
		Vertex g = new SimpleVertex("g");
		Vertex h = new SimpleVertex("h");
		
		//Adds the vertexes
		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(c);
		graph.addVertex(d);
		graph.addVertex(e);
		graph.addVertex(f);
		graph.addVertex(g);
		graph.addVertex(h);
		
		//Adds the edges
		graph.addEdge(new SimpleEdge("1"), a, b);
		graph.addEdge(new SimpleEdge("2"), a, c);
		graph.addEdge(new SimpleEdge("3"), a, e);
		graph.addEdge(new SimpleEdge("4"), c, f);
		graph.addEdge(new SimpleEdge("5"), d, a);
		graph.addEdge(new SimpleEdge("6"), e, g);
		graph.addEdge(new SimpleEdge("7"), h, e);
		
		// create centrality analysis
		
		Map<Vertex, DegreeCentrality> vDegCent = new HashMap<Vertex, DegreeCentrality>();
		vDegCent.put(a, new DegreeCentrality(1/7d, 3/7d));
		vDegCent.put(b, new DegreeCentrality(1/7d, 0));
		vDegCent.put(c, new DegreeCentrality(1/7d, 1/7d));
		vDegCent.put(d, new DegreeCentrality(0, 1/7d));
		vDegCent.put(e, new DegreeCentrality(2/7d, 1/7d));
		vDegCent.put(f, new DegreeCentrality(1/7d, 0));
		vDegCent.put(g, new DegreeCentrality(1/7d, 0));
		vDegCent.put(h, new DegreeCentrality(0, 1/7d));

		Map<Vertex, Double> vClosCent = new HashMap<Vertex, Double>();
		vClosCent.put(a, 5/7d); 
		vClosCent.put(b, Double.NaN);
		vClosCent.put(c, 1d);
		vClosCent.put(d, 6/13d);
		vClosCent.put(e, 1d);
		vClosCent.put(f, Double.NaN);
		vClosCent.put(g, Double.NaN);
		vClosCent.put(h, 2/3d);

		Map<Vertex, Double> vBetwCent = new HashMap<Vertex, Double>();
		vBetwCent.put(a, 5d);
		vBetwCent.put(b, 0d);
		vBetwCent.put(c, 2d);
		vBetwCent.put(d, 0d);
		vBetwCent.put(e, 3d);
		vBetwCent.put(f, 0d);
		vBetwCent.put(g, 0d);
		vBetwCent.put(h, 0d);

		Map<Vertex, Double> pageRank = new HashMap<Vertex, Double>();
		pageRank.put(a, 0d);
		pageRank.put(b, 0d);
		pageRank.put(c, 0d);
		pageRank.put(d, 0d);
		pageRank.put(e, 0d);
		pageRank.put(f, 0d);
		pageRank.put(g, 0d);
		pageRank.put(h, 0d);
		
		CentralityResults centralityAnalysis = new CentralityResults(graph);
		centralityAnalysis.setVerticesDegreeCentrality(vDegCent);
		centralityAnalysis.setGraphDegreeCentrality(new DegreeCentrality(0.0306, 0.0578)); 
		centralityAnalysis.setVerticesClosenessCentrality(vClosCent); 
		centralityAnalysis.setVerticesBetweenessCentrality(vBetwCent); 
		centralityAnalysis.setVerticesPageRank(pageRank);
		
		StabilityAnalysis stabilityAnalysis = new StabilityResults(0.7656); 
		
		graphs.put(TestGraph.COMPLEX, graph);
		centralities.put(TestGraph.COMPLEX, centralityAnalysis);
		stabilities.put(TestGraph.COMPLEX, stabilityAnalysis);
	}

	/**
	 *   |-------------- b <-------|
	 *   |               ^         |
	 *   |               |         |
	 *   |---> c ------> a <------ d
	 */
	private void createLoopGraph() {

		// create graph
		
		DirectedSparseGraph<Vertex, Edge> graph = new DirectedSparseGraph<Vertex, Edge>();
		
		//Creates the vertexes
		Vertex a = new SimpleVertex("a");
		Vertex b = new SimpleVertex("b");
		Vertex c = new SimpleVertex("c");
		Vertex d = new SimpleVertex("d");
		
		//Adds the vertexes
		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(d);
		
		//Adds the edges
		graph.addEdge(new SimpleEdge("1"), a, b);
		graph.addEdge(new SimpleEdge("2"), c, a);
		graph.addEdge(new SimpleEdge("3"), b, c);
		graph.addEdge(new SimpleEdge("4"), d, a);
		graph.addEdge(new SimpleEdge("5"), d, b);
		
		// create centrality analysis
		
		Map<Vertex, DegreeCentrality> vDegCent = new HashMap<Vertex, DegreeCentrality>();
		vDegCent.put(a, new DegreeCentrality(2/3d, 1/3d));
		vDegCent.put(b, new DegreeCentrality(2/3d, 1/3d));
		vDegCent.put(c, new DegreeCentrality(1/3d, 1/3d));
		vDegCent.put(d, new DegreeCentrality(0d, 2/3d));

		Map<Vertex, Double> vClosCent = new HashMap<Vertex, Double>();
		vClosCent.put(a, 4/9d);
		vClosCent.put(b, 4/9d);
		vClosCent.put(c, 4/9d);
		vClosCent.put(d, 3/4d);

		Map<Vertex, Double> vBetwCent = new HashMap<Vertex, Double>();
		vBetwCent.put(a, 1/6d); 
		vBetwCent.put(b, 1/3d); 
		vBetwCent.put(c, 1/6d); 
		vBetwCent.put(d, 0d); 
		
		Map<Vertex, Double> pageRank = new HashMap<Vertex, Double>();
		pageRank.put(a, 0d); 
		pageRank.put(b, 0d); 
		pageRank.put(c, 0d); 
		pageRank.put(d, 0d); 

		CentralityResults centralityAnalysis = new CentralityResults(graph);
		centralityAnalysis.setVerticesDegreeCentrality(vDegCent);
		centralityAnalysis.setGraphDegreeCentrality(new DegreeCentrality(1/6d, 1/6d));
		centralityAnalysis.setVerticesClosenessCentrality(vClosCent);
		centralityAnalysis.setVerticesBetweenessCentrality(vBetwCent);
		centralityAnalysis.setVerticesPageRank(pageRank);
		
		StabilityAnalysis stabilityAnalysis = new StabilityResults(7/16d);
		
		graphs.put(TestGraph.LOOP, graph);
		centralities.put(TestGraph.LOOP, centralityAnalysis);
		stabilities.put(TestGraph.LOOP, stabilityAnalysis);
	}

}
