package nl.tudelft.contextproject.tygron;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class HttpConnection {

  private Settings settings;
  private HttpClient client;
  private String authString;
  private String serverToken;
  private int sessionId;

  private static final String API_URL_BASE = "https://server2.tygron.com:3022/api/";
  private static final String API_JSON_SUFFIX = "?f=JSON";
  private static final String API_DELIMITER = "/";
  private static final String API_SLOTS = "slots/";

  /**
   * Creates a Tygron connection using some settings.
   * 
   * @param tygronSettings
   *          the settings that should be used.
   */
  public HttpConnection(Settings tygronSettings) {
    settings = tygronSettings;
    client = HttpClients.custom().build();
    String headerValue = settings.getUserName() + ":" + settings.getPassword();
    authString = Base64.encodeBase64String(headerValue.getBytes());
  }

  public void setServerToken(String serverToken) {
    this.serverToken = serverToken;
  }

  public void setSessionId(int sessionId) {
    this.sessionId = sessionId;
  }

  /**
   * Add default headers to a request for authentication/json responses.
   * 
   * @param request
   *          A HttpGet / HttpPost request
   */
  public void addDefaultHeaders(HttpRequestBase request) {
    request.setHeader("Accept", "application/json");
    request.setHeader("Content-Type", "application/json");
    request.setHeader("Authorization", "Basic " + authString);

    if (serverToken != null) {
      request.setHeader("serverToken", serverToken);
    }
  }

  private String getApiUrl(String eventName) {
    return API_URL_BASE + eventName + API_JSON_SUFFIX;
  }

  private String getApiSessionUrl(String eventName) {
    return API_URL_BASE + API_SLOTS + sessionId + API_DELIMITER + eventName
        + API_JSON_SUFFIX;
  }

  /**
   * Calls a get event without a session.
   * @param eventName the event as described in the API
   * @return the result from the server
   */
  public String callGetEvent(String eventName) {
    HttpGet request = new HttpGet(getApiUrl(eventName));

    return getEvent(request);
  }

  /**
   * Calls a post event without a session.
   * @param eventName the event as described in the API
   * @return the result from the server
   */
  public String callPostEvent(String eventName, JSONArray parameters) {
    HttpPost request = new HttpPost(getApiUrl(eventName));

    return postEvent(request, parameters);
  }
  
  public String callSessionGetEvent(String eventName) {
    HttpGet request = new HttpGet(getApiSessionUrl(eventName));
    return getEvent(request);
  }

  public String callSessionPostEvent(String eventName, JSONArray parameters) {
    HttpPost request = new HttpPost(getApiSessionUrl(eventName));
    return postEvent(request, parameters);
  }

  /**
   * Calls a get event.
   * @param request the event as described in the API
   * @return the result from the server
   */
  public String getEvent(HttpGet request) {
    addDefaultHeaders(request);
    try {
      HttpResponse response = client.execute(request);
      return new BasicResponseHandler().handleResponse(response);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Calls a post event.
   * @param request the event as described in the API
   * @param parameters the parameters to bind to the request
   * @return the result from the server
   */
  public String postEvent(HttpPost request, JSONArray parameters) {
    addDefaultHeaders(request);

    // adds parameters
    if (parameters != null) {
      try {
        request.setEntity(new StringEntity(parameters.toString()));
      } catch (UnsupportedEncodingException e) {
        throw new RuntimeException(e);
      }
    }

    try {
      HttpResponse response = client.execute(request);
      return new BasicResponseHandler().handleResponse(response);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  public JSONObject callGetEventObject(String eventName) {
    return new JSONObject(callGetEvent(eventName));
  }
  
  public JSONArray callGetEventArray(String eventName) {
    return new JSONArray(callGetEvent(eventName));
  }
 
  public String callPostEventRaw(String eventName, JSONArray parameters) {
    return callPostEvent(eventName, parameters);
  }
  
  public boolean callPostEventBoolean(String eventName, JSONArray parameters) {
    return Boolean.parseBoolean(callPostEvent(eventName, parameters));
  }
  
  public int callPostEventInt(String eventName, JSONArray parameters) {
    return Integer.parseInt(callPostEvent(eventName, parameters));
  }
  
  public JSONObject callPostEventObject(String eventName, JSONArray parameters) {
    return new JSONObject(callPostEvent(eventName, parameters));
  }

  public JSONArray callPostEventArray(String eventName, JSONArray parameters) {
    return new JSONArray(callPostEvent(eventName, parameters));
  }

  public JSONObject callSessionGetEventObject(String eventName) {
    return new JSONObject(callSessionGetEvent(eventName));
  }
  
  public JSONArray callSessionGetEventArray(String eventName) {
    return new JSONArray(callSessionGetEvent(eventName));
  }
  

  public JSONObject callSessionPostEventObject(String eventName, JSONArray parameters) {
    return new JSONObject(callSessionPostEvent(eventName, parameters));
  }
 
  public JSONArray callSessionPostEventArray(String eventName, JSONArray parameters) {
    return new JSONArray(callSessionPostEvent(eventName, parameters));
  }  
}

