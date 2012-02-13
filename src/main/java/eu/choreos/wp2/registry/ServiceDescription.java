package eu.choreos.wp2.registry;


public class ServiceDescription {

	private String label;
	private String URI;
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	
	public String getURI() {
		return URI;
	}
	
	public void setURI(String uRI) {
		URI = uRI;
	}
	
	public boolean equals(Object o){
		ServiceDescription otherServiceDescription = (ServiceDescription)o;
		return URI.equals(otherServiceDescription.getURI());
	}
	
	public String toString(){
		return label;
	}
}
