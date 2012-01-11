package eu.choreos.wp2.sia.visualization;

import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import eu.choreos.wp2.sia.analysis.JungAnalyzer;
import eu.choreos.wp2.sia.analysis.entity.report.AntiPatternReport;
import eu.choreos.wp2.sia.graph.algorithms.StabilityCalculator;

public class GraphInformationPanel extends JPanel {

	public GraphInformationPanel(AntiPatternReport antiPattern) {
		super();
		addInfo(antiPattern);
	}

	public GraphInformationPanel(AntiPatternReport antiPattern, boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		addInfo(antiPattern);
	}

	public GraphInformationPanel(AntiPatternReport antiPattern, LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		addInfo(antiPattern);
	}

	public GraphInformationPanel(AntiPatternReport antiPattern, LayoutManager layout) {
		super(layout);
		addInfo(antiPattern);
	}
	
	void addInfo(AntiPatternReport antiPattern){
		this.setBorder(BorderFactory.createTitledBorder("Graph Info"));
		Box box = Box.createVerticalBox();
		
		JLabel vertex = new JLabel("Vertexes: " + antiPattern.getDependencyGraph().getVertexCount());
		JLabel edges = new JLabel("Edges: " + antiPattern.getDependencyGraph().getEdgeCount());
		
		String globalStability = String.format("%.4g", new StabilityCalculator().calculateOverallStability(antiPattern.getDependencyGraph()));
		JLabel stability = new JLabel("Stability: " + globalStability);
		
		JLabel lButterfly = new JLabel("Local Butterflies: " + antiPattern.getButterfliesReport().getLocalButterflies().size());
		JLabel gButterfly = new JLabel("Global Butterflies: " + antiPattern.getButterfliesReport().getGlobalButterflies().size());
		
		JLabel lSensitive = new JLabel("Local Sensitives: " + antiPattern.getSensitivesReport().getLocalSensitives().size());
		JLabel gSensitive = new JLabel("Global Sensitives: " + antiPattern.getSensitivesReport().getGlobalSensitives().size());
		
		JLabel lHub = new JLabel("Local Hubs: " + antiPattern.getHubsReport().getLocalHubs().size());
		JLabel gHub = new JLabel("Global Hubs: " + antiPattern.getHubsReport().getGlobalHubs().size());
		
		box.add(vertex);
		box.add(edges);
		
		box.add(stability);
		
		box.add(lButterfly);
		box.add(gButterfly);
		
		box.add(lSensitive);
		box.add(gSensitive);
		
		box.add(lHub);
		box.add(gHub);	
		
		this.add(box);
	}

}
