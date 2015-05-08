package nl.tudelft.contextproject.eis.democode;

import nl.tudelft.contextproject.tygron.TygronConnection;
import nl.tudelft.contextproject.tygron.TygronHttpConnection;
import nl.tudelft.contextproject.tygron.TygronSettings;

import org.json.JSONObject;

public class DemoHttp {

  /**
   * Demo program for GET/POST requests to API.
   * @param args Main arguments
   */
  public static void main(String[] args) {
    // General setup for http
    TygronSettings settings = new TygronSettings();
    TygronConnection http = new TygronHttpConnection(settings);

    // Example GET request
    JSONObject getDemoResponse = http.callGetEvent("services/myuser/");
    System.out.println(getDemoResponse.get("active"));
    
    // Example POST request
    JSONObject postDemoResponse = http.callPostEvent("services/myuser/", null);
    System.out.println(postDemoResponse);
  }

}
