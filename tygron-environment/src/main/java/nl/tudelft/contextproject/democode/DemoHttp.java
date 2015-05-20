package nl.tudelft.contextproject.democode;

import nl.tudelft.contextproject.tygron.HttpConnection;
import nl.tudelft.contextproject.tygron.HttpConnection.Type;
import nl.tudelft.contextproject.tygron.Settings;
import nl.tudelft.contextproject.tygron.results.JsonObjectResultHandler;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoHttp {
  private static final Logger logger = LoggerFactory.getLogger(DemoHttp.class);

  /**
   * Demo program for GET/POST requests to API.
   * 
   * @param args
   *          Main arguments
   */
  public static void main(String[] args) {
    // General setup for http
    HttpConnection http = new HttpConnection(new Settings());

    // Example GET request
    JSONObject getDemoResponse = http.execute("services/myuser/", Type.GET, new JsonObjectResultHandler());
    logger.info("User is active? " + getDemoResponse.getBoolean("active"));

    // Example POST request
    JSONObject postDemoResponse = http.execute("services/event/UserServicesEventType/GET_MY_USER", Type.POST,
        new JsonObjectResultHandler());
    logger.info("User #" + postDemoResponse.getInt("id") + " " + postDemoResponse.getString("userName") + " "
        + postDemoResponse.getString("firstName") + " " + postDemoResponse.getString("lastName"));
  }

}
