package eu.choreos.wp2.sia.graph.algorithms;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tests.util.GraphFactory;
import tests.util.GraphFactory.TestGraph;
import edu.uci.ics.jung.graph.DirectedGraph;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.JungCalculator;
import eu.choreos.wp2.sia.graph.entity.StabilityAnalysis;
import eu.choreos.wp2.sia.graph.entity.Vertex;

public class StabilityTest {

	private final double EPSILON = 0.0001;
	
	//@Test
	public void shouldCalculateSimpleOverallStability() {
		
		GraphFactory factory = new GraphFactory();
		DirectedGraph<Vertex, Edge> graph = factory.getGraph(TestGraph.SIMPLE);
		
		StabilityAnalysis expectedStability = factory.getStabilityAnalysis(TestGraph.SIMPLE);
		
		JungCalculator depAnalyzer = new JungCalculator(graph);
		StabilityAnalysis actualStability = depAnalyzer.calculateStabilityAnalysis();
		
		assertEquals(expectedStability.getOverallStability(),
				actualStability.getOverallStability(), EPSILON);
	}

	//@Test
	public void shouldCalculateComplexOverallStability() {
			
		GraphFactory factory = new GraphFactory();
		DirectedGraph<Vertex, Edge> graph = factory.getGraph(TestGraph.COMPLEX);
		StabilityAnalysis expectedStability = factory.getStabilityAnalysis(TestGraph.COMPLEX);
		
		JungCalculator depAnalyzer = new JungCalculator(graph);
		StabilityAnalysis actualStability = depAnalyzer.calculateStabilityAnalysis();
		
		assertEquals(expectedStability.getOverallStability(),
				actualStability.getOverallStability(), EPSILON);
	}

	//@Test
	public void shouldCalculateLoopOverallStability() {
			
		GraphFactory factory = new GraphFactory();
		DirectedGraph<Vertex, Edge> graph = factory.getGraph(TestGraph.LOOP);
		StabilityAnalysis expectedStability = factory.getStabilityAnalysis(TestGraph.LOOP);
		
		JungCalculator depAnalyzer = new JungCalculator(graph);
		StabilityAnalysis actualStability = depAnalyzer.calculateStabilityAnalysis();
		
		assertEquals(expectedStability.getOverallStability(),
				actualStability.getOverallStability(), EPSILON);
	}
	
}
