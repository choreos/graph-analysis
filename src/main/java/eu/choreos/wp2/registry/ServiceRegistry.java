package eu.choreos.wp2.registry;

import java.util.ArrayList;
import java.util.List;

public class ServiceRegistry {
	
	public List<ServiceDescription> findEquivalentServices(String URI){		
	
		List<ServiceDescription> equivalentServices = 
				new ArrayList<ServiceDescription>();
		
		if(URI != null){
			ServiceDescription helloService = new ServiceDescription();		
			helloService.setLabel("hello_greetings");
			helloService.setURI("http://localhost:1234/hello");
			equivalentServices.add(helloService);
			
			ServiceDescription oiService = new ServiceDescription();
			oiService.setLabel("oi_greetings");
			oiService.setURI("http://localhost:1235/oi");
			equivalentServices.add(oiService);
			
			ServiceDescription ciaoService = new ServiceDescription();		
			ciaoService.setLabel("ciao_greetings");
			ciaoService.setURI("http://localhost:1236/ciao");
			equivalentServices.add(ciaoService);

			
			ServiceDescription toRemove = new ServiceDescription();
			toRemove.setURI(URI);
			equivalentServices.remove(toRemove);
		}
				
		return equivalentServices;
	}
	
}