package eu.choreos.wp2.sia.analysis.entity.report;

public enum AntiPattern {

	LOCAL_BUTTERFLY("Local Butterfly"),
	GLOBAL_BUTTERFLY("Global Butterfly"),
	LOCAL_SENSITIVE("Local Sensitive"),
	GLOBAL_SENSITIVE("Global Sensitive"),
	LOCAL_HUB("Local Hub"),
	GLOBAL_HUB("Global Hub");
	
	private String s;
	
	private AntiPattern(String s){
		this.s = s;
	}
	
	public String toString(){
		return this.s;
	}
}

