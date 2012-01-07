package eu.choreos.wp2.sia.analysis.util;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

public class QuartileAnalysis {

	private double q1;
	private double q2;
	private double q3;
	private double iqr;
	
	public QuartileAnalysis(DescriptiveStatistics stats){
		//Calculates the quartiles
		this.q1 = stats.getPercentile(25);
		this.q2 = stats.getPercentile(50);
		this.q3 = stats.getPercentile(75);
		this.iqr = q3 - q1;
	}	
	
	public double getQ1() {
		return q1;
	}

	public double getQ2() {
		return q2;
	}

	public double getQ3() {
		return q3;
	}

	public double getIqr() {
		return iqr;
	}

	public double getLowerWhisker(){
		return q1 - (1.5 * iqr);
	}
	
	public double getUpperWhisker(){
		return q3 + (1.5 * iqr);
		
	}
	
	public double getExtremeLowerWhisker(){
		return q1 - (3 * iqr);
	}

	public double getExtremeUpperWhisker(){
		return q3 + (3 * iqr);
	}
	
}
