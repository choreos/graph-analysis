package eu.choreos.wp2.sia.graph.algorithms;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import tests.util.GraphFactory;
import tests.util.GraphFactory.TestGraph;
import edu.uci.ics.jung.graph.DirectedGraph;
import eu.choreos.wp2.sia.graph.entity.CentralityAnalysis;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.JungCalculator;
import eu.choreos.wp2.sia.graph.entity.Vertex;

public class VerticesDegreeCentralityTest {

	private Map<TestGraph, DirectedGraph<Vertex, Edge>> graphs;
	private Map<TestGraph, CentralityAnalysis> expectedCentralities;
	private Map<TestGraph, CentralityAnalysis> actualCentralities;
	
	@Before
	public void setUp() throws Exception {
		
		GraphFactory factory = new GraphFactory();
		graphs = factory.getAllGraphs();
		expectedCentralities = factory.getAllCentralities();
		
		actualCentralities = new HashMap<TestGraph, CentralityAnalysis>();
		JungCalculator analyzer = new JungCalculator(graphs.get(TestGraph.SIMPLE));
		actualCentralities.put(TestGraph.SIMPLE, analyzer.calculateCentralityAnalysis());
		analyzer = new JungCalculator(graphs.get(TestGraph.COMPLEX));
		actualCentralities.put(TestGraph.COMPLEX, analyzer.calculateCentralityAnalysis());
		analyzer = new JungCalculator(graphs.get(TestGraph.LOOP));
		actualCentralities.put(TestGraph.LOOP, analyzer.calculateCentralityAnalysis());
	}

	//@Test
	public void shouldCalculateSimpleVerticeDegreeCentrality() {

		CentralityBaseTests.shouldCalculateVerticeDegreeCentrality(
				expectedCentralities.get(TestGraph.SIMPLE), actualCentralities.get(TestGraph.SIMPLE));
	}
	
	//@Test
	public void shouldCalculateComplexVerticeDegreeCentrality() {

		CentralityBaseTests.shouldCalculateVerticeDegreeCentrality(
				expectedCentralities.get(TestGraph.COMPLEX), actualCentralities.get(TestGraph.COMPLEX));
	}

	//@Test
	public void shouldCalculateLoopVerticeDegreeCentrality() {

		CentralityBaseTests.shouldCalculateVerticeDegreeCentrality(
				expectedCentralities.get(TestGraph.LOOP), actualCentralities.get(TestGraph.LOOP));
	}

}
