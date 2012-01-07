package eu.choreos.wp2.sia.analysis;

import java.util.List;

import eu.choreos.wp3.middleware.entity.ChoreographyModel;
import eu.choreos.wp3.middleware.entity.CoordinationDelegate;

public interface SIA {

	public Double calculateOverallStability(ChoreographyModel 
			choreographyModel);
	
	public Double calculateOverallStability(
			List<CoordinationDelegate> coordinationDelegates);
	
}
