package eu.choreos.wp2.sia.analysis;

import java.util.List;

import edu.uci.ics.jung.graph.DirectedGraph;
import eu.choreos.wp2.sia.analysis.converters.ChoreographyModelToGraphConverter;
import eu.choreos.wp2.sia.analysis.converters.CoordinationDelegatesToGraphConverter;
import eu.choreos.wp2.sia.analysis.entity.report.AntiPatternReport;
import eu.choreos.wp2.sia.graph.algorithms.StabilityCalculator;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.Vertex;
import eu.choreos.wp3.middleware.entity.ChoreographyModel;
import eu.choreos.wp3.middleware.entity.CoordinationDelegate;

public class JungAnalyzer implements SIA{
	
	private ChoreographyModelToGraphConverter chorToGraphConverter;
	private CoordinationDelegatesToGraphConverter coordelToGraphConverter;
	
	public JungAnalyzer(
			ChoreographyModelToGraphConverter chorToGraphConverter, 
			CoordinationDelegatesToGraphConverter coordelToGraphConverter){
		
		this.chorToGraphConverter = chorToGraphConverter;
		this.coordelToGraphConverter = coordelToGraphConverter;
	}
	
	@Override
	public Double calculateOverallStability(
			ChoreographyModel choreographyModel) {
		
		DirectedGraph<Vertex, Edge> graph = 
				chorToGraphConverter.convert(choreographyModel);
		
		StabilityCalculator stabilityCalculator = 
				new StabilityCalculator();
		
		Double stability = stabilityCalculator.calculateOverallStability(graph);		
		return stability;
	}

	@Override
	public Double calculateOverallStability(
			List<CoordinationDelegate> coordinationDelegates) {
		
		DirectedGraph<Vertex,Edge> graph = 
				coordelToGraphConverter.convert(coordinationDelegates);
				
		StabilityCalculator stabilityCalculator = new StabilityCalculator();
			
		Double stability = stabilityCalculator.calculateOverallStability(graph);
		return stability;
	}
	
	public AntiPatternReport findAntiPatterns(
			List<CoordinationDelegate> coordinationDelegates){
		
		DirectedGraph<Vertex,Edge> graph = 
				coordelToGraphConverter.convert(coordinationDelegates);		
		
		AntiPatternReport antiPatternsReport = 
				new AntiPatternReport(graph);
		
		return antiPatternsReport;
	}
	
}