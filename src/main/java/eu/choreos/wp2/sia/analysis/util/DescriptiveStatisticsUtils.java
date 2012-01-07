package eu.choreos.wp2.sia.analysis.util;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

public abstract class DescriptiveStatisticsUtils {

	public static QuartileAnalysis doQuartileAnalysis(DescriptiveStatistics stats){
		QuartileAnalysis quartileAnalysis = new QuartileAnalysis(stats);
		return quartileAnalysis;		
	}
	
}
