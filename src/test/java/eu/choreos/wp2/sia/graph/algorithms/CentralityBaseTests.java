package eu.choreos.wp2.sia.graph.algorithms;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import eu.choreos.wp2.sia.graph.entity.CentralityAnalysis;
import eu.choreos.wp2.sia.graph.entity.DegreeCentrality;
import eu.choreos.wp2.sia.graph.entity.Vertex;


/**
 * Centralize the centrality tests, 
 * since they are the same for every type of graph (simple, complex or loop)
 * 
 * @author leofl
 *
 */
public class CentralityBaseTests {

	public static void shouldCalculateVerticeDegreeCentrality(
			CentralityAnalysis expectedCentrality, 
			CentralityAnalysis actualCentrality) {
		
		Map<Vertex, DegreeCentrality> expected = 
				expectedCentrality.getVerticesDegreeCentrality();
		
		Map<Vertex, DegreeCentrality> calculated = 
				actualCentrality.getVerticesDegreeCentrality();
		
		for (Vertex v: expected.keySet()) {
			assertEquals(expected.get(v), calculated.get(v));
		}
	}
	
	public static void shouldCalculateGraphDegreeCentrality(
			CentralityAnalysis expectedCentrality, 
			CentralityAnalysis actualCentrality) {
		
		DegreeCentrality expected = 
				expectedCentrality.getGraphDegreeCentrality();
		
		DegreeCentrality calculated = 
				actualCentrality.getGraphDegreeCentrality();
		
		assertEquals(expected, calculated);
	}
	
	public static void shouldCalculateVerticesClosenessCentrality(
			CentralityAnalysis expectedCentrality, 
			CentralityAnalysis actualCentrality) {
		
		Map<Vertex, Double> expected = 
				expectedCentrality.getVerticesClosenessCentrality();
		
		Map<Vertex, Double> calculated = 
				actualCentrality.getVerticesClosenessCentrality();
		
		for (Vertex v: expected.keySet()) {
			assertEquals("Vertex " + v, expected.get(v), calculated.get(v));
		}
	}

	public static void shouldCalculateVerticesBetweennessCentrality(
			CentralityAnalysis expectedCentrality, 
			CentralityAnalysis actualCentrality) {
		
		Map<Vertex, Double> expected = 
				expectedCentrality.getVerticesBetweennessCentrality();
		
		Map<Vertex, Double> calculated = 
				actualCentrality.getVerticesBetweennessCentrality();
		
		for (Vertex v: expected.keySet()) {
			assertEquals(expected.get(v), calculated.get(v));
		}		
	}
	
	public static void shouldCalculateVerticesPageRank(
			CentralityAnalysis expectedCentrality, 
			CentralityAnalysis actualCentrality) {
		
		Map<Vertex, Double> expected = expectedCentrality.getVerticesPageRank();
		Map<Vertex, Double> calculated = actualCentrality.getVerticesPageRank();
		
		for (Vertex v: expected.keySet()) {
			assertEquals(expected.get(v), calculated.get(v), 0.01);
		}		
	}	
}
