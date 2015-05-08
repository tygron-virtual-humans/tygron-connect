package nl.tudelft.contextproject.tygron;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class TygronHTTP {

	private TygronSettings settings;
	private HttpClient client;
	private String authString;
	private final String API_URL_BASE = "https://server2.tygron.com:3022/api/";
	private final String API_JSON_SUFFIX = "?f=JSON";
	
	public TygronHTTP(TygronSettings localSettings){
		settings = localSettings;
		setup();
	}
	
	/**
	 * Setup the objects/information we'll need in all requests.
	 */
	public void setup(){
		client = HttpClients.custom().build();
		String headerValue = settings.getUserName() + ":" + settings.getPassword();
		authString = Base64.encodeBase64String(headerValue.getBytes());
	}
	
	/**
	 * Add default headers to a request for authentication/json responses
	 * @param request A HttpGet / HttpPost request
	 */
	public void addDefaultHeaders(HttpRequestBase request){
		request.setHeader("Accept","application/json");
		request.setHeader("Authorization", "Basic " + authString);
	}
	
	/**
	 * Executes a GET request without parameters.
	 * @param API_EVENT The event that needs to be called
	 * @return a HttpResponse object, can be parsed by the other function/ tools.
	 */
	public HttpResponse requestGet(String API_EVENT){
		HttpGet request = new HttpGet(API_URL_BASE + API_EVENT + API_JSON_SUFFIX);
		addDefaultHeaders(request);
		try {
			return client.execute(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	}
	
	/**
	 * Executes a POST request with or without parameters.
	 * @param API_EVENT The event that needs to be called
	 * @return a HttpResponse object, can be parsed by the other function/ tools.
	 */
	public HttpResponse requestPost(String API_EVENT, Map<String, String> kvPairs){
		HttpPost request = new HttpPost(API_URL_BASE + API_EVENT + API_JSON_SUFFIX);
		addDefaultHeaders(request);
		
		// If there are keys/values, add them.
		if (kvPairs != null && kvPairs.isEmpty() == false) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
					kvPairs.size());
			String k, v;
			Iterator<String> itKeys = kvPairs.keySet().iterator();

			while (itKeys.hasNext()) {
				k = itKeys.next();
				v = kvPairs.get(k);
				nameValuePairs.add(new BasicNameValuePair(k, v));
			}
			try {
				request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Execute / try the request.
		try {
			return client.execute(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	}
}
