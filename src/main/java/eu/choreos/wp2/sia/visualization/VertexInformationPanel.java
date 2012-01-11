package eu.choreos.wp2.sia.visualization;

import java.awt.LayoutManager;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.algorithms.scoring.ClosenessCentrality;
import eu.choreos.wp2.sia.analysis.entity.report.AntiPattern;
import eu.choreos.wp2.sia.analysis.entity.report.AntiPatternReport;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.Vertex;

public class VertexInformationPanel extends JPanel {
	
	public VertexInformationPanel(AntiPatternReport antiPattern, Vertex v) {
		super();
		addInfo(antiPattern, v);
	}

	public VertexInformationPanel(AntiPatternReport antiPattern, Vertex v, boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		addInfo(antiPattern, v);
	}

	public VertexInformationPanel(AntiPatternReport antiPattern, Vertex v, LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		addInfo(antiPattern,v );
	}

	public VertexInformationPanel(AntiPatternReport antiPattern, Vertex v, LayoutManager layout) {
		super(layout);
		addInfo(antiPattern, v);
	}
	
	void addInfo(AntiPatternReport antiPattern, Vertex v){
		this.setBorder(BorderFactory.createTitledBorder("Vertex Info"));
		Box box = Box.createVerticalBox();
		
		if (v != null){
			JLabel vertex = new JLabel("Vertex: " + v);
			box.add(vertex);
			box.add(new JSeparator());
			
			box.add(new JLabel("Degree Centrality:"));
			box.add(new JLabel("   In Degree:  " + antiPattern.getDegreeCentrality(v).getInDegree()));
			box.add(new JLabel("   Out Degree: " + antiPattern.getDegreeCentrality(v).getOutDegree()));
			
			String betweenness = String.format("%.4g", 
					new BetweennessCentrality<Vertex, Edge>(antiPattern.getDependencyGraph()).getVertexScore(v));
			box.add(new JLabel("Betweenness Cent.: " + betweenness));
			
			String closeness = String.format("%.4g", 
					new ClosenessCentrality<Vertex, Edge>(antiPattern.getDependencyGraph()).getVertexScore(v)); 
			box.add(new JLabel("Closeness Cent.: " + closeness));
			
			box.add(new JSeparator());
			JLabel patterns = new JLabel("Patterns:");
			box.add(patterns);
			
			Collection<AntiPattern> vertexPatterns = antiPattern.getAntiPatterns(v);
			for(AntiPattern p: vertexPatterns){
				box.add(new JLabel("   " + p));
			}	

		} else {
			JLabel vertex = new JLabel("Vertex: none selected");
			box.add(vertex);
		}
		
		
		this.add(box);
	}

}
