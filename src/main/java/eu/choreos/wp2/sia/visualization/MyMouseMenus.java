package eu.choreos.wp2.sia.visualization;

/*
 * MyMouseMenus.java
 *
 * Created on March 21, 2007, 3:34 PM; Updated May 29, 2007
 *
 * Copyright March 21, 2007 Grotto Networking
 *
 */

import java.awt.Color;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.PickableVertexPaintTransformer;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.Vertex;
import eu.choreos.wp2.sia.graph.util.GraphUtils;

/**
 * A collection of classes used to assemble popup mouse menus for the custom
 * edges and vertices developed in this example.
 * @author Dr. Greg M. Bernstein
 */
public class MyMouseMenus {
    
    public static class EdgeMenu extends JPopupMenu {        
     
        public EdgeMenu() {
            super("Edge Menu");
        }    
    }
    
    public static class VertexMenu extends JPopupMenu{
            	
    	public VertexMenu() {
            super("Vertex Menu");
            this.add(new ChangeImpactAnalysis());
        }

        public static class ChangeImpactAnalysis extends JMenuItem implements 
        	VertexMenuListener{
        
        	private DirectedGraph<Vertex, Edge> graph;
        	private Vertex v;
        	private VisualizationViewer<Vertex, Edge> vv;
        	private ChoreographyVisualizer cv;
        	
        	public ChangeImpactAnalysis(){
        		super("Perform ripple effect analysis");
        	    this.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	System.out.println("Performing Change Impact Analysis...");
                    	
                    	cv.resetColoringBox();
                    	cv.updateVertexInfoPanel(null);
                    	
                    	DirectedGraph<Vertex, Edge> transitiveClosure = 
                    			GraphUtils.computeTransitiveClosure(graph);

                    	final Collection<Vertex> predecessors = 
                    			transitiveClosure.getPredecessors(v);

                    	//cleans previous paint vertices
                    	Transformer<Vertex,Paint> paintTransformer = new Transformer<Vertex,Paint>() {
                    		public Paint transform(Vertex otherVertex) {
                    			return Color.WHITE;
                    		}
                    	};

                    	vv.getRenderContext().setVertexFillPaintTransformer(paintTransformer);
                    	vv.updateUI();

                    	//Paints the vertices
                    	paintTransformer = new Transformer<Vertex,Paint>() {
                    		public Paint transform(Vertex otherVertex) {
                    			if (predecessors.contains(otherVertex)){
                    				return Color.YELLOW;
                    			}
                    			else if(otherVertex.equals(v)){
                    				return Color.GREEN;
                    			}
                    			else{                					
                    				return Color.WHITE;
                    			}
                    		}
                    	};

                 		vv.getRenderContext().setVertexFillPaintTransformer(paintTransformer);
                		vv.updateUI();
                    }
                    
                });
        	}
        	
			@Override
			public void setGraph(DirectedGraph<Vertex, Edge> graph, 
					Vertex v, VisualizationViewer<Vertex, Edge> vv, ChoreographyVisualizer cv) {
				this.graph = graph;
				this.v = v;
				this.vv = vv;
				this.cv = cv;
			}

        }
    }
    
}