package eu.choreos.wp2.sia.visualization;

import java.awt.Color;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import eu.choreos.wp2.sia.analysis.entity.report.AntiPatternReport;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.Vertex;

public class ColoringHelper implements ActionListener{
	
	private VisualizationViewer<Vertex, Edge> vv;
	private AntiPatternReport antiPatternReport;
	private ChoreographyVisualizer choreographVisualizer;

	public ColoringHelper(ChoreographyVisualizer chorVisualizer, 
			VisualizationViewer<Vertex, Edge> vv, AntiPatternReport antiPatterReport){
		this.choreographVisualizer = chorVisualizer;
		this.vv = vv;
		this.antiPatternReport = antiPatterReport;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final String selected = (String)((JComboBox)e.getSource()).getSelectedItem();
    	
		//Paints the vertices
		Transformer<Vertex,Paint> paintTransformer = new Transformer<Vertex,Paint>() {
			public Paint transform(Vertex v) {
				choreographVisualizer.updateVertexInfoPanel(null);
				
				if (selected.equals("Hub") && antiPatternReport.getHubsReport().isHub(v)){
					return Color.cyan;
				}
				else if (selected.equals("Butterfly") && antiPatternReport.getButterfliesReport().isButterfly(v)){
					return Color.cyan;
				}
				else if (selected.equals("Sensitive") && antiPatternReport.getSensitivesReport().isSensitive(v)){
					return Color.cyan;
				}
				return Color.white;
			}
		};
		                		
 		vv.getRenderContext().setVertexFillPaintTransformer(paintTransformer);
		vv.updateUI();
	}
	
	

}
