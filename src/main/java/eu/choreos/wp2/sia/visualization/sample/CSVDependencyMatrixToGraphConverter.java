package eu.choreos.wp2.sia.visualization.sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import eu.choreos.wp2.sia.analysis.converters.CoordinationDelegatesToGraphConverter;
import eu.choreos.wp2.sia.graph.entity.Edge;
import eu.choreos.wp2.sia.graph.entity.SimpleEdge;
import eu.choreos.wp2.sia.graph.entity.SimpleVertex;
import eu.choreos.wp2.sia.graph.entity.Vertex;
import eu.choreos.wp3.middleware.entity.CoordinationDelegate;

/**
 * Builds a directed graph based on an exported CSV file 
 * from MagicDraw Dependency Matrix tool
 * @author Guilherme Nogueira
 *
 */
public class CSVDependencyMatrixToGraphConverter implements 
CoordinationDelegatesToGraphConverter{

	private JFrame frame;
	private Boolean selected;
	private Map<String, List<String>> dependencies = null;

	public CSVDependencyMatrixToGraphConverter(JFrame frame) {
		this.frame = frame;
		this.dependencies = readCSV();
		if (this.dependencies != null) {
			this.selected = true;
		} else {
			this.selected = false;
		}
		
	}

	private Map<String, List<String>> readCSV() {
		BufferedReader CSVFile;
		try {
			JFileChooser fc = new JFileChooser();
            fc.addChoosableFileFilter(new CSVFilter());
            fc.setAcceptAllFileFilterUsed(false);
          
            int returnVal = fc.showOpenDialog(this.frame);

            if (returnVal != JFileChooser.APPROVE_OPTION) {
        		// cancelou
                return null;
            } 

            File file = fc.getSelectedFile();
			CSVFile = new BufferedReader(new FileReader(file));
			String dataRow = CSVFile.readLine();
			
			// read header
			List<String> header = new ArrayList<String>();
			if (dataRow != null) {
				String[] dataArray = dataRow.split(",");
				header.add("");
				for (int i=1; i<dataArray.length; i++) {
					String clean = dataArray[i].substring(1, dataArray[i].indexOf('[')).trim(); 
					header.add(clean);
					System.out.println(clean);
				}
				dataRow = CSVFile.readLine();
			}

			// read dependencies
			Map<String, List<String>> dependencies = new HashMap<String, List<String>>();
			while (dataRow != null){
				String[] dataArray = dataRow.split(",");
				
				String current = dataArray[0].substring(1, dataArray[0].indexOf('[')).trim();
				
				for (int i=1; i<dataArray.length; i++) { 
					if (dataArray[i].contains("x")) {
						if (dependencies.get(current) == null ){
							dependencies.put(current, new ArrayList<String>());
						}
						dependencies.get(current).add(header.get(i));
					}
				}
				dataRow = CSVFile.readLine();
			}
			CSVFile.close();
			
			System.out.println();
			
			int x = 0;
			for (Map.Entry<String, List<String>> e : dependencies.entrySet()){
				System.out.println(e.getKey() + " -> " + e.getValue());
				x += e.getValue().size();
			}
			System.out.println("total: " + x);
			
			return dependencies;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public Boolean isSelected(){
		return this.selected;
	}

	@Override
	public DirectedGraph<Vertex, Edge> convert(
			List<CoordinationDelegate> coordinationDelegates) {
		
		DirectedGraph<Vertex, Edge> graph = 
				new DirectedSparseGraph<Vertex, Edge>();
		
		Map<String, Vertex> vertexes = new HashMap<String, Vertex>();
		
		int label = 1;
		for (Map.Entry<String, List<String>> e : dependencies.entrySet()){
			String from = e.getKey();
			
			Vertex vf = vertexes.get(from);
			if (vf == null){
				vf = new SimpleVertex(from);
				vertexes.put(from, vf);
				graph.addVertex(vf);
			}
			
			for (String to: e.getValue()){
				Vertex vt = vertexes.get(to);
				if (vt == null){
					vt = new SimpleVertex(to);
					vertexes.put(to, vt);
					graph.addVertex(vt);
				}
				graph.addEdge(new SimpleEdge(label++), vf, vt);
			}
		}
		
		return graph;
	}
	
	public class CSVFilter extends FileFilter {

	    public boolean accept(File f) {
	        if (f.isDirectory()) {
	            return false;
	        }

	        String extension = getExtension(f);
	        if (extension != null) {
	            if (extension.equals("csv")) {
	                    return true;
	            }
	        }

	        return false;
	    }
	    
	    public String getExtension(File f) {
	        String ext = null;
	        String s = f.getName();
	        int i = s.lastIndexOf('.');

	        if (i > 0 &&  i < s.length() - 1) {
	            ext = s.substring(i+1).toLowerCase();
	        }
	        return ext;
	    }

	    public String getDescription() {
	        return "CSV files";
	    }
	}
	
}
