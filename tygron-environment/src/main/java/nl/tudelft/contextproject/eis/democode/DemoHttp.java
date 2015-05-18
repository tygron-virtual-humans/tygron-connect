package nl.tudelft.contextproject.eis.democode;

import nl.tudelft.contextproject.tygron.Connection;
import nl.tudelft.contextproject.tygron.HttpConnection;
import nl.tudelft.contextproject.tygron.Settings;

import org.json.JSONObject;

public class DemoHttp {

  /**
   * Demo program for GET/POST requests to API.
   * @param args Main arguments
   */
  public static void main(String[] args) {
    // General setup for http
    Settings settings = new Settings();
    Connection http = new HttpConnection(settings);

    // Example GET request
    JSONObject getDemoResponse = http.callGetEventObject("services/myuser/");
    System.out.println(getDemoResponse.get("active"));
    
    // Example POST request
    JSONObject postDemoResponse = http.callPostEventObject(
        "services/event/UserServicesEventType/GET_MY_USER/", null);
    System.out.println(postDemoResponse);
  }

}
