package nl.tudelft.contextproject.democode;

import nl.tudelft.contextproject.tygron.Settings;
import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.handlers.JsonObjectResultHandler;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoHttp {
  private static final Logger logger = LoggerFactory.getLogger(DemoHttp.class);

  private DemoHttp() {
    // Static class
  }
  
  /**
   * Demo program for GET/POST requests to API.
   * 
   * @param args
   *          Main arguments
   */
  public static void main(String[] args) {
    // General setup for http
    HttpConnection.setSettings(new Settings());

    // Example GET request
    JSONObject getDemoResponse = HttpConnection.getInstance().execute("services/myuser/",
            CallType.GET, new JsonObjectResultHandler());
    logger.info("User is active? " + getDemoResponse.getBoolean("active"));

    // Example POST request
    JSONObject postDemoResponse = HttpConnection.getInstance().execute("services/event/UserServicesEventType/GET_MY_USER",
            CallType.POST, new JsonObjectResultHandler());
    logger.info("User #" + postDemoResponse.getInt("id") + " " + postDemoResponse.getString("userName") + " "
        + postDemoResponse.getString("firstName") + " " + postDemoResponse.getString("lastName"));
  }

}
