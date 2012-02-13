package eu.choreos.wp2.sia.graph.entity;

public interface Vertex {

	public String getLabel();
	
	public void setLabel(String label);
	
	public boolean equals(Object otherVertex);
}
