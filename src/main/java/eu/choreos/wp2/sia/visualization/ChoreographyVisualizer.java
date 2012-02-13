package eu.choreos.wp2.sia.visualization;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.util.Relaxer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.PickableEdgePaintTransformer;
import edu.uci.ics.jung.visualization.decorators.PickableVertexPaintTransformer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import eu.choreos.wp2.registry.ServiceDescription;
import eu.choreos.wp2.registry.ServiceRegistry;
import eu.choreos.wp2.sia.analysis.JungAnalyzer;
import eu.choreos.wp2.sia.analysis.converters.CoordinationDelegatesToGraphConverter;
import eu.choreos.wp2.sia.analysis.entity.report.AntiPattern;
import eu.choreos.wp2.sia.analysis.entity.report.AntiPatternReport;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.SimpleVertex;
import eu.choreos.wp2.sia.graph.entity.Vertex;
import eu.choreos.wp2.sia.visualization.sample.DullCoordinationDelegatesToGraphConverter;
import eu.choreos.wp2.sia.visualization.sample.GreetingsCoordinationDelegatesToGraphConverter;
import eu.choreos.wp2.sia.visualization.sample.RandomizedCoordinationDelegatesToGraphConverter;

public class ChoreographyVisualizer {

	private VisualizationViewer<Vertex, Edge> graphPanel;
	private JRadioButton greetingsButton;
	private JRadioButton randomButton;
	private JRadioButton hardcodedButton;
	private JFrame frame = new JFrame("Choreography Analyzer");
	private JMenuBar jMenuBar;
	private JPanel infoPanel;
	private JPanel controlPanel;
	private AntiPatternReport antiPatterReport;
	private int currentGraphType = 1;
	private JComboBox coloringBox;
	private VertexInformationPanel vertexInformationPanel;
	private EquivalentServicesPanel equivalentServicesPanel; 

	public AntiPatternReport getAntiPatterReport() {
		return antiPatterReport;
	}

	public void setAntiPatterReport(AntiPatternReport antiPatterReport) {
		this.antiPatterReport = antiPatterReport;
	}

	public ChoreographyVisualizer(final AntiPatternReport antiPatternReport){
		this.antiPatterReport = antiPatternReport;
		JFrame jFrame = buildJFrame(antiPatternReport);
		jFrame.setVisible(true);
	}

	private JFrame buildJFrame(AntiPatternReport antiPatternReport) {
		jMenuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});

		fileMenu.add(exitItem);

		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);

		JMenuItem aboutItem = new JMenuItem("About");
		helpMenu.add(aboutItem);

		jMenuBar.add(fileMenu);
		jMenuBar.add(helpMenu);

		frame.setJMenuBar(jMenuBar);

		graphPanel = buildVisualizationViewer(antiPatternReport, null);

		frame.getContentPane().add(graphPanel, BorderLayout.CENTER);

		infoPanel = buildInfoPanel();

		frame.getContentPane().add(infoPanel, BorderLayout.WEST);


		controlPanel = buildControlsPanel();

		frame.getContentPane().add(controlPanel, BorderLayout.SOUTH);

		//Other stuff
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.pack();
		return frame;
	}

	public void rebuildGraph(final AntiPatternReport antiPatternReport){
		if (antiPatternReport != null) this.antiPatterReport = antiPatternReport;

		frame.getContentPane().removeAll();
		frame.setJMenuBar(jMenuBar);

		Dimension currentSize = new Dimension(graphPanel.getWidth(), graphPanel.getHeight());
		graphPanel = buildVisualizationViewer(this.antiPatterReport, currentSize);
		frame.getContentPane().add(graphPanel, BorderLayout.CENTER);
		
		controlPanel = buildControlsPanel();
		frame.getContentPane().add(controlPanel, BorderLayout.SOUTH);
		
		infoPanel = buildInfoPanel();
		frame.getContentPane().add(infoPanel, BorderLayout.WEST);

		//Other stuff		
		frame.validate();
	}
	
	private VisualizationViewer<Vertex, Edge> buildVisualizationViewer(
			final AntiPatternReport antiPatternReport, Dimension size) {

		if (size == null){
			size = new Dimension(1024,768);
		}

		// Creates the visualization viewer
		VisualizationViewer<Vertex, Edge> vv = 
				new VisualizationViewer<Vertex, Edge>(
						new FRLayout<Vertex, Edge>(
								antiPatternReport.getDependencyGraph(), 
								size));

		vv.setPreferredSize(size); //Sets the viewing area size
		vv.setBackground(Color.WHITE);

		//Sets transformers
		Transformer<Vertex, Paint> vertexPaintTransformer = buildVertexPaintTransformer(vv);
		vv.getRenderContext().setVertexFillPaintTransformer(vertexPaintTransformer);

		Transformer<Edge, Paint> edgePaintTransformer = buildEdgePaintTransformer(vv);
		vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaintTransformer);

		Transformer<Vertex, String> tooltipTransformer = buildTooltipTransformer(antiPatternReport);
		vv.setVertexToolTipTransformer(tooltipTransformer);

		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<Vertex>());
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.E);		

		//Sets the graphMouse
		DefaultModalGraphMouse<Vertex, Edge> gm = buildGraphMouse(antiPatternReport);
		vv.setGraphMouse(gm);

		return vv;
	}

	private Transformer<Vertex, String> buildTooltipTransformer(
			final AntiPatternReport antiPatternReport) {
		//Creates the appropriate tooltip
		Transformer<Vertex,String> tooltipTransformer = new Transformer<Vertex,String>(){
			public String transform(Vertex v) {
				Collection<AntiPattern> antiPatterns = 
						antiPatternReport.getAntiPatterns(v);
				return antiPatterns.toString();
			}
		};
		return tooltipTransformer;
	}

	private Transformer<Vertex, Paint> buildVertexPaintTransformer(
			VisualizationViewer<Vertex, Edge> vv) {

		Transformer<Vertex, Paint> vertexPaintTransformer = 
				new PickableVertexPaintTransformer<Vertex>(
						vv.getPickedVertexState(), Color.white, Color.yellow);

		return vertexPaintTransformer;
	}

	private Transformer<Edge, Paint> buildEdgePaintTransformer(
			VisualizationViewer<Vertex, Edge> vv) {

		Transformer<Edge, Paint> edgePaintTransformer = 
				new PickableEdgePaintTransformer<Edge>(
						vv.getPickedEdgeState(), Color.black, Color.red);

		return edgePaintTransformer;
	}

	private JPanel buildControlsPanel() {

		//Scale Panel
		final ScalingControl scaler = new CrossoverScalingControl();
		JButton plus = new JButton("+");
		plus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scaler.scale(graphPanel, 1.1f, graphPanel.getCenter());
			}
		});
		JButton minus = new JButton("-");
		minus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scaler.scale(graphPanel, 1 / 1.1f, graphPanel.getCenter());
			}
		});

		JPanel scalePanel = new JPanel();
		scalePanel.setBorder(BorderFactory.createTitledBorder("Zoom"));
		scalePanel.add(plus);
		scalePanel.add(minus);

		//Mouse mode panel        
		final DefaultModalGraphMouse<Vertex, Edge> graphMouse = (DefaultModalGraphMouse<Vertex, Edge>)graphPanel.getGraphMouse();
		graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
		JComboBox modeBox = graphMouse.getModeComboBox();
		modeBox.setSelectedIndex(1);


		JPanel mouseModePanel = new JPanel();
		mouseModePanel.setBorder(BorderFactory.createTitledBorder("Mouse Mode"));
		mouseModePanel.add(modeBox);

		//Reset panel
		JButton reset = new JButton("REFRESH");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				rebuildGraph(null);

				//Reset the layout
				Layout<Vertex, Edge> layout = graphPanel.getGraphLayout();
				layout.initialize();
				Relaxer relaxer = graphPanel.getModel().getRelaxer();
				if (relaxer != null) {
					relaxer.stop();
					relaxer.prerelax();
					relaxer.relax();
				}
				graphPanel.validate();
			}
		});

		JPanel resetPanel = new JPanel();
		resetPanel.setBorder(BorderFactory.createTitledBorder("Refresh"));
		resetPanel.add(reset);

		JPanel controls = new JPanel(); 
		controls.add(scalePanel);
		controls.add(mouseModePanel);
		controls.add(resetPanel);
		return controls;
	}

	private DefaultModalGraphMouse<Vertex, Edge> buildGraphMouse(
			final AntiPatternReport antiPatternReport) {

		PopupVertexEdgeMenuMousePlugin popupMousePlugin = 
				new PopupVertexEdgeMenuMousePlugin(
						antiPatternReport.getDependencyGraph(), this);
		
		popupMousePlugin.setVertexPopup(new MyMouseMenus.VertexMenu());
		popupMousePlugin.setEdgePopup(new MyMouseMenus.EdgeMenu());

		DefaultModalGraphMouse<Vertex, Edge> gm = new DefaultModalGraphMouse<Vertex, Edge>();

		gm.setMode(ModalGraphMouse.Mode.PICKING);
		gm.add(popupMousePlugin);
		return gm;
	}

	private JPanel buildInfoPanel() {
		JPanel infoPanel = new JPanel();
		Box box = Box.createVerticalBox();

		greetingsButton = new JRadioButton("Greetings Orchestration");
		greetingsButton.setActionCommand("greetings");
		if (currentGraphType == 1) greetingsButton.setSelected(true);
		
		hardcodedButton = new JRadioButton("Hardcoded Example");
		hardcodedButton.setActionCommand("hardcoded");
		if (currentGraphType == 2) hardcodedButton.setSelected(true);
		
		randomButton = new JRadioButton("Random Graph");
		randomButton.setActionCommand("random");
		if (currentGraphType == 3) randomButton.setSelected(true);

		//Group the radio buttons.
		ButtonGroup graphButtonGroup = new ButtonGroup();
		
		graphButtonGroup.add(greetingsButton);
		graphButtonGroup.add(hardcodedButton);
		graphButtonGroup.add(randomButton);

		JButton graphOKButton = new JButton("OK");
		graphOKButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		graphOKButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JungAnalyzer analyzer = null;
				
				if (greetingsButton.isSelected()){
					currentGraphType = 1;
					CoordinationDelegatesToGraphConverter greetingsConverter =
							new GreetingsCoordinationDelegatesToGraphConverter();

					analyzer = new JungAnalyzer(null, greetingsConverter);
				}
				
				if (hardcodedButton.isSelected()){
					currentGraphType = 2;
					CoordinationDelegatesToGraphConverter dullConverter =
							new DullCoordinationDelegatesToGraphConverter();

					analyzer = new JungAnalyzer(null, dullConverter);
				}
				
				if (randomButton.isSelected()){
					currentGraphType = 3;
					RandomizedCoordinationDelegatesToGraphConverter randomized = 
							new RandomizedCoordinationDelegatesToGraphConverter();

					randomized.setSize(30, 25);
					randomized.generateGraph();

					analyzer = new JungAnalyzer(null, randomized);
				}
				

				AntiPatternReport antiPatternReport = analyzer.findAntiPatterns(null);
				rebuildGraph(antiPatternReport);
			}
		});

		//Put the radio buttons in a column in a panel.
		JPanel radioPanel = new JPanel(new GridLayout(0, 1));
		radioPanel.add(greetingsButton);
		radioPanel.add(hardcodedButton);
		radioPanel.add(randomButton);

		JPanel graphSelectorPanel = new JPanel();
		graphSelectorPanel.setBorder(BorderFactory.createTitledBorder("Graph"));
		graphSelectorPanel.setLayout(new BoxLayout(graphSelectorPanel, BoxLayout.Y_AXIS));

		graphSelectorPanel.add(radioPanel);
		graphSelectorPanel.add(graphOKButton);


		String[] coloringStrings = {"None", "Butterfly", "Hub", "Sensitive"};
		coloringBox = new JComboBox(coloringStrings);
		coloringBox.setSelectedIndex(0);
		coloringBox.addActionListener((ActionListener) new ColoringHelper(this, graphPanel, antiPatterReport));

		JPanel colorSelectorPanel = new JPanel();
		colorSelectorPanel.setBorder(BorderFactory.createTitledBorder("Vertex Coloring"));

		colorSelectorPanel.add(coloringBox);
		
		GraphInformationPanel graphInformationPanel = new GraphInformationPanel(antiPatterReport);
		
		vertexInformationPanel = new VertexInformationPanel(antiPatterReport, null);
		equivalentServicesPanel = new EquivalentServicesPanel(this, null, null);
		
		box.add(graphSelectorPanel);
		box.add(colorSelectorPanel);
		
		box.add(graphInformationPanel);
		box.add(vertexInformationPanel);
		box.add(equivalentServicesPanel);
		
		infoPanel.add(box);
		
		return infoPanel;
	}
	
	public void updateVertexInfoPanel(Vertex v){
		Box box = (Box) infoPanel.getComponent(0);
		box.remove(vertexInformationPanel);
		
		vertexInformationPanel = new VertexInformationPanel(antiPatterReport, v);
		box.add(vertexInformationPanel);
		
		box.repaint();
		infoPanel.repaint();
	}
	
	public void updateEquivalentServicesPanel(Vertex v){
		Box box = (Box) infoPanel.getComponent(0);
		box.remove(equivalentServicesPanel);
		
		//TODO:Obtain equivalent services
		SimpleVertex simpleVertex = (SimpleVertex) v;
		
		String URI = (String) simpleVertex.getData();
		
		ServiceRegistry serviceRegistry = new ServiceRegistry();		
		
		List<ServiceDescription> equivalentServices = 
				serviceRegistry.findEquivalentServices(URI);		
		
		equivalentServicesPanel = new EquivalentServicesPanel(this, v, 
				equivalentServices);
		
		box.add(equivalentServicesPanel);
		
		box.repaint();
		infoPanel.repaint();
	}
	
	public void resetColoringBox(){
		coloringBox.setSelectedIndex(0);
	}

	public static void main(String[] args) {
		/*
		RandomizedCoordinationDelegatesToGraphConverter randomized = 
				new RandomizedCoordinationDelegatesToGraphConverter();

		randomized.setSize(30, 25);
		randomized.generateGraph();

		JungAnalyzer analyzer = new JungAnalyzer(null, randomized);
		 */	
		CoordinationDelegatesToGraphConverter greetingsConverter =
				new GreetingsCoordinationDelegatesToGraphConverter();

		JungAnalyzer analyzer = new JungAnalyzer(null, greetingsConverter);
		AntiPatternReport antiPatternReport = analyzer.findAntiPatterns(null);

		new ChoreographyVisualizer(antiPatternReport);
	}

	public void refreshGraph() {
		this.graphPanel.repaint();
	}

}
