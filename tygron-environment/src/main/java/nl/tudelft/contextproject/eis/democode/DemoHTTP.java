package nl.tudelft.contextproject.eis.democode;

import java.io.IOException;

import nl.tudelft.contextproject.tygron.TygronHTTP;
import nl.tudelft.contextproject.tygron.TygronSettings;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.BasicResponseHandler;

public class DemoHTTP {

	public static void main(String[] args) {
		
		// General setup for http
		TygronSettings settings = new TygronSettings();
		TygronHTTP http = new TygronHTTP(settings);
		String responseString = null;
		HttpResponse response; // Response OBJ
		
		// Example GET request
		response = http.requestGet("services/myuser/");
		try {
			responseString = new BasicResponseHandler().handleResponse(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(responseString);
		System.out.println(response);
		
		// Example POST request
		response = http.requestPost("services/event/UserServicesEventType/GET_MY_USER/",null);
		
		try {
			responseString = new BasicResponseHandler().handleResponse(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(responseString);
		System.out.println(response);
	}

}
