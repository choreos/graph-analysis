package eu.choreos.wp2.sia.graph.entity;

public class SimpleVertex implements Vertex{

	private String label;
	
	public SimpleVertex(String label){
		this.label = label;
	}
	
	public SimpleVertex(int label){
		this.label = "" + label;
	}

	@Override
	public String getLabel() {
		return label;
	}
	
	@Override
	public void setLabel(String label){
		this.label = label;
	}

	private Object data;
	
	public Object getData(){
		return data;
	}
	
	public void setData(Object data){
		this.data = data;
	}
	
	public boolean equals(Object o){
		Vertex otherVertex = (Vertex)o;
		return label.equals(otherVertex.getLabel());
	}
	
	public String toString(){
		return label;
	}	
}