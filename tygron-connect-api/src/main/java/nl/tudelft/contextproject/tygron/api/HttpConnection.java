package nl.tudelft.contextproject.tygron.api;

import nl.tudelft.contextproject.tygron.Settings;
import nl.tudelft.contextproject.tygron.handlers.ResultHandler;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

public class HttpConnection {
  private static final Logger logger = LoggerFactory.getLogger(HttpConnection.class);
  protected HttpClient client;
  protected BasicResponseHandler handler;

 

  private static final String API_URL_BASE = "https://server2.tygron.com:3022/api/";
  private static final String API_JSON_SUFFIX = "?f=JSON";
  private static final String API_DELIMITER = "/";
  private static final String API_SLOTS = "slots/";

  /**
   * Creates a Tygron connection using some settings.
   * @param settings the settings
   */
  private HttpConnection() {
    this.client = HttpClients.custom().build();
    this.handler = new BasicResponseHandler();
  }

  private static HttpConnection instance;
  private static Settings settings;
  private static HttpConnectionData data;
  
  public static void setSettings(Settings settings) {
    HttpConnection.settings = settings;
  }
  
  public static void setData(HttpConnectionData newData) {
    data = newData;
  }  

  public static HttpConnectionData getData() {
    return data;
  }  
  
  /**
   * Return the HttpConnection instance.
   * @return the http connection instance
   */
  public static HttpConnection getInstance() {
    if (instance == null) {
      if (settings == null) {
        settings = new Settings();
      }
      instance = new HttpConnection();
    }
    return instance;
  }

  public <T> T execute(String eventName, CallType type, ResultHandler<T> resultHandler) {
    return execute(eventName, type, resultHandler, false, null);
  }

  public <T> T execute(String eventName, CallType type, ResultHandler<T> resultHandler, JSONArray parameters) {
    return execute(eventName, type, resultHandler, false, parameters);
  }

  public <T> T execute(String eventName, CallType type, ResultHandler<T> resultHandler, boolean isSession) {
    return execute(eventName, type, resultHandler, isSession, null);
  }
  
  /**
   * Calls a method on Tygron's servers.
   * @param <T> A type
   * @param eventName The event name, a part of the URL
   * @param type GET or POST event
   * @param resultHandler The handler used to parse Tygron's result.
   * @param isSession If this is a call to the session or regular API.
   * @param parameters The parameters this request should use, can be null
   * @return a result handled by this request
   */
  public <T> T execute(String eventName, CallType type,
      ResultHandler<T> resultHandler, boolean isSession, JSONArray parameters) {
    try {
      HttpRequestBase requester = type.asRequest(parameters);
      String url = getApiUrl(eventName, isSession);
      requester.setURI(new URI(url));
      String resultString = execute(requester);
      return resultHandler.handleResult(resultString);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
  
  protected String execute(HttpUriRequest request) {
    try {
      addDefaultHeaders(request);
      HttpResponse httpResponse = client.execute(request);
      logger.debug("Request " + request.toString());
      String response = handler.handleResponse(httpResponse);
      logger.debug("Response " + response);
      return response;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * Calls the update method on Tygron's servers.
   * @param resultHandler The handler used to parse Tygron's result.
   * @param isSession If this is a call to the session or regular API.
   * @param parameters The parameters this request should use
   * @return new updates of the data types requested
   */
  public JSONObject getUpdate(ResultHandler<JSONObject> resultHandler, boolean isSession, JSONObject parameters) {
    try {
      HttpRequestBase requester = CallType.POST.asRequest(parameters);
      String url = getApiUrl("update/", isSession);
      requester.setURI(new URI(url));
      String resultString = execute(requester);
      return resultHandler.handleResult(resultString);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Returns Tygron's API url endpoint for a given event name and session.
   * @param eventName the event that should be called
   * @param isSession Whether this is a call to the session part of the API.
   * @return Tygron's response
   */
  protected String getApiUrl(String eventName, boolean isSession) {
    if (!isSession) {
      return API_URL_BASE + eventName + API_JSON_SUFFIX;
    } else {
      return API_URL_BASE + API_SLOTS + data.getSessionId() + API_DELIMITER + eventName + API_JSON_SUFFIX;
    }
  }
  
  protected String getAuthString() {
    String headerValue = settings.getUserName() + ":" + settings.getPassword();
    return Base64.encodeBase64String(headerValue.getBytes());
  }
  
  /**
   * Adds the required headers (authentication) for Tygron communication.
   * @param request the request to attach the headers to
   */
  /**
   * Adds the required headers (authentication) for Tygron communication.
   * @param request the request to attach the headers to
   */
  protected void addDefaultHeaders(HttpUriRequest request) {
    request.setHeader("Accept", "application/json");
    request.setHeader("Content-Type", "application/json");
    request.setHeader("Authorization", "Basic " + getAuthString());

    if (data != null && data.getServerToken() != null) {
      request.setHeader("serverToken", data.getServerToken());
    }
    
    if (data != null && data.getClientToken() != null) {
      request.setHeader("clientToken", data.getClientToken());
    }
  }
}
