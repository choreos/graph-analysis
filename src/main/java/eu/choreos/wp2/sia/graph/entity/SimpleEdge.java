package eu.choreos.wp2.sia.graph.entity;

public class SimpleEdge implements Edge{

	private String label;
	
	public SimpleEdge(String label){
		this.label = label;
	}
	
	public SimpleEdge(int label){
		this.label = "" + label;
	}

	@Override
	public String getLabel() {
		return label;
	}
	
	public boolean equals(Object o){
		Edge otherEdge = (Edge)o;
		return label.equals(otherEdge.getLabel());
	}
	
	public String toString(){
		return label;
	}
	
}
