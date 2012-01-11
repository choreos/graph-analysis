package eu.choreos.wp2.sia.visualization;

/*
 * PopupVertexEdgeMenuMousePlugin.java
 *
 * Created on March 21, 2007, 12:56 PM; Updated May 29, 2007
 *
 * Copyright March 21, 2007 Grotto Networking
 *
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.JPopupMenu;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.decorators.PickableVertexPaintTransformer;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.Vertex;

/**
 * A GraphMousePlugin that brings up distinct popup menus when an edge or vertex is
 * appropriately clicked in a graph.  If these menus contain components that implement
 * either the EdgeMenuListener or VertexMenuListener then the corresponding interface
 * methods will be called prior to the display of the menus (so that they can display
 * context sensitive information for the edge or vertex).
 * @author Dr. Greg M. Bernstein
 */
public class PopupVertexEdgeMenuMousePlugin extends AbstractPopupGraphMousePlugin {
    
	private JPopupMenu edgePopup, vertexPopup;
    private DirectedGraph<Vertex, Edge> graph;
    private ChoreographyVisualizer choreographyVisualizer;
    
    /** Creates a new instance of PopupVertexEdgeMenuMousePlugin */
    public PopupVertexEdgeMenuMousePlugin(DirectedGraph<Vertex,Edge> graph, ChoreographyVisualizer chorVisualizer) {
    	this(MouseEvent.BUTTON3_MASK);
    	this.graph = graph;
    	this.choreographyVisualizer = chorVisualizer;
    }
    
    /**
     * Creates a new instance of PopupVertexEdgeMenuMousePlugin
     * @param modifiers mouse event modifiers see the jung visualization Event class.
     */
    public PopupVertexEdgeMenuMousePlugin(int modifiers) {
        super(modifiers);
    }
    
    /**
     * Implementation of the AbstractPopupGraphMousePlugin method. This is where the 
     * work gets done. You shouldn't have to modify unless you really want to...
     * @param e 
     */
    protected void handlePopup(MouseEvent e) {
        
    	final VisualizationViewer<Vertex, Edge> vv = (VisualizationViewer<Vertex, Edge>)e.getSource();
        
    	Point2D p = e.getPoint();
        
        GraphElementAccessor<Vertex, Edge> pickSupport = vv.getPickSupport();
        if(pickSupport != null) {
        	final Vertex v = pickSupport.getVertex(vv.getGraphLayout(), p.getX(), p.getY());
            if(v != null) {
                System.out.println("Vertex " + v + " was right clicked");
                vertexPopup = new MyMouseMenus.VertexMenu();
                updateVertexMenu(graph, v, vv, p, choreographyVisualizer);
                vertexPopup.show(vv, e.getX(), e.getY());
            } else {
                final Edge edge = pickSupport.getEdge(vv.getGraphLayout(), p.getX(), p.getY());
                if(edge != null) {
                    System.out.println("Edge " + edge + " was right clicked");
                    updateEdgeMenu(edge, vv, p);
                    edgePopup.show(vv, e.getX(), e.getY());
                }
            }
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    	super.mouseClicked(e);

    	System.out.println("Mouse clicked");

    	Point2D p = e.getPoint();

    	final VisualizationViewer<Vertex, Edge> vv = (VisualizationViewer<Vertex, Edge>)e.getSource();

    	GraphElementAccessor<Vertex, Edge> pickSupport = vv.getPickSupport();
    	if(pickSupport != null) {
    		final Vertex v = pickSupport.getVertex(vv.getGraphLayout(), p.getX(), p.getY());
    		if(v != null) {
    			System.out.println("Vertex " + v + " was clicked");    	
    			choreographyVisualizer.resetColoringBox();
    			choreographyVisualizer.updateVertexInfoPanel(v);

    			vv.getRenderContext().setVertexFillPaintTransformer(
    					new PickableVertexPaintTransformer<Vertex>(
    							vv.getPickedVertexState(), Color.white, Color.yellow));
    		}
    	}

    }
    
    /**
     * Getter for the edge popup.
     * @return 
     */
    public JPopupMenu getEdgePopup() {
        return edgePopup;
    }
    
    /**
     * Setter for the Edge popup.
     * @param edgePopup 
     */
    public void setEdgePopup(JPopupMenu edgePopup) {
        this.edgePopup = edgePopup;
    }
    
    /**
     * Getter for the vertex popup.
     * @return 
     */
    public JPopupMenu getVertexPopup() {
        return vertexPopup;
    }
    
    /**
     * Setter for the vertex popup.
     * @param vertexPopup 
     */
    public void setVertexPopup(JPopupMenu vertexPopup) {
        this.vertexPopup = vertexPopup;
    }
    
    //Ugly code
    private void updateVertexMenu(DirectedGraph<Vertex, Edge> graph, 
    		Vertex v, VisualizationViewer<Vertex, Edge> vv, Point2D point, ChoreographyVisualizer cv) {
        
    	if (vertexPopup == null) return;
        Component[] menuComps = vertexPopup.getComponents();
        for (Component comp: menuComps) {
            if (comp instanceof VertexMenuListener) {
                ((VertexMenuListener)comp).setGraph(graph, v, vv, cv);
            }
        }
    }
    
    private void updateEdgeMenu(Edge edge, VisualizationViewer<Vertex, Edge> vv, Point2D point) {
        if (edgePopup == null) return;
        Component[] menuComps = edgePopup.getComponents();
        for (Component comp: menuComps) {
            if (comp instanceof EdgeMenuListener) {
                ((EdgeMenuListener)comp).setEdgeAndView(edge, vv);
            }
        }
    }
    
}