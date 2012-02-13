package eu.choreos.wp3.middleware.service;

import java.io.IOException;

import org.apache.xmlbeans.XmlException;

import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.WSDLException;

public class ChoreographyAdapter {

	private static String ADDRESSER_ENDPOINT = 
			"http://localhost:1238/addresser?wsdl";
	
	public void adaptChoreography(String greetingServiceURI){
	
		WSClient addresser = null;
		try {
			addresser = new WSClient(ADDRESSER_ENDPOINT);
		} catch (WSDLException e) {
			e.printStackTrace();
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FrameworkException e) {
			e.printStackTrace();
		}
	
		try {
			addresser.request("setEndpoint", greetingServiceURI);					
		} catch (InvalidOperationNameException e) {
			e.printStackTrace();
		} catch (FrameworkException e) {
			e.printStackTrace();
		}
	}
}