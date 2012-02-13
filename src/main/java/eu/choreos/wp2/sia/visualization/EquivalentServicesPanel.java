package eu.choreos.wp2.sia.visualization;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.uci.ics.jung.graph.DirectedGraph;
import eu.choreos.wp2.registry.ServiceDescription;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.SimpleEdge;
import eu.choreos.wp2.sia.graph.entity.SimpleVertex;
import eu.choreos.wp2.sia.graph.entity.Vertex;
import eu.choreos.wp3.middleware.service.ChoreographyAdapter;

public class EquivalentServicesPanel extends JPanel{

	private Vertex v;
	private ChoreographyVisualizer choreographyVisualizer;
	
	public EquivalentServicesPanel(final ChoreographyVisualizer choreographyVisualizer, 
			final Vertex v, List<ServiceDescription> equivalentServices){
		
		this.v = v;
		this.choreographyVisualizer = choreographyVisualizer;
		
		this.setBorder(BorderFactory.createTitledBorder("Equivalent Services"));
		Box box = Box.createVerticalBox();
				
		if (v != null){
			final JComboBox jComboBox = new JComboBox();
		
			for(ServiceDescription equivalentService : equivalentServices){
				jComboBox.addItem(equivalentService);	
			}
		
			box.add(jComboBox);
			
			JPanel buttonsPanel = new JPanel(new FlowLayout());
						
			JButton tryButton = new JButton("View change");
			tryButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ServiceDescription serviceDescription = 
							(ServiceDescription) jComboBox.getSelectedItem();
					
					DirectedGraph<Vertex, Edge> dependencyGraph = 
							choreographyVisualizer.getAntiPatterReport().getDependencyGraph();
					
					Collection<Vertex> vertices = dependencyGraph.getVertices();
					for(Vertex vertex : vertices){
						if(vertex.equals(v)){
							vertex.setLabel(serviceDescription.getLabel());
							SimpleVertex simpleVertex = (SimpleVertex) vertex;
							simpleVertex.setData(serviceDescription.getURI());
							break;
						}
					}
					
					choreographyVisualizer.refreshGraph();
				}
			});
			
			JButton applyChangeButton = new JButton("Apply change");
			applyChangeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ServiceDescription serviceDescription = 
							(ServiceDescription) jComboBox.getSelectedItem();
					
					ChoreographyAdapter adaptChoreography = new ChoreographyAdapter();
					adaptChoreography.adaptChoreography(serviceDescription.getURI());
				}
			});
			
			if (equivalentServices.isEmpty()){
				jComboBox.setEnabled(false);
				tryButton.setEnabled(false);
				applyChangeButton.setEnabled(false);
			}
			
			buttonsPanel.add(tryButton);
			buttonsPanel.add(applyChangeButton);
			
			box.add(buttonsPanel);
			
		} else {
			JLabel vertex = new JLabel("Vertex: none selected");
			box.add(vertex);
		}
		
		this.add(box);
	}
	
}
