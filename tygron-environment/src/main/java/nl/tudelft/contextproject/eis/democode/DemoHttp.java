package nl.tudelft.contextproject.eis.democode;

import nl.tudelft.contextproject.tygron.TygronHttp;
import nl.tudelft.contextproject.tygron.TygronSettings;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONObject;

import java.io.IOException;

public class DemoHttp {

  /**
   * Demo program for GET/POST requests to API.
   * @param args Main arguments
   */
  public static void main(String[] args) {

    // General setup for http
    TygronSettings settings = new TygronSettings();
    TygronHttp http = new TygronHttp(settings);
    String responseString = null;
    HttpResponse response; // Response OBJ

    // Example GET request
    response = http.requestGet("services/myuser/");
    JSONObject jsResponse = http.getJsonFromResponse(response);
    System.out.println(jsResponse.get("active"));
    
    // Example POST request
    response = http.requestPost(
        "services/event/UserServicesEventType/GET_MY_USER/", null);

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
