package nl.tudelft.contextproject.democode;

import nl.tudelft.contextproject.tygron.HttpConnection;

import org.json.JSONObject;

public class DemoHttp {

  /**
   * Demo program for GET/POST requests to API.
   * @param args Main arguments
   */
  public static void main(String[] args) {
    // General setup for http
    HttpConnection http = new HttpConnection();

    // Example GET request
    JSONObject getDemoResponse = http.callGetEventObject("services/myuser/");
    System.out.println(getDemoResponse.get("active"));
    
    // Example POST request
    JSONObject postDemoResponse = http.callPostEventObject(
        "services/event/UserServicesEventType/GET_MY_USER/", null);
    System.out.println(postDemoResponse);
  }

}