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

  private Settings settings;
  private String serverToken;

  private static final String API_URL_BASE = "https://server2.tygron.com:3022/api/";
  private static final String API_JSON_SUFFIX = "?f=JSON";
  private static final String API_DELIMITER = "/";
  private static final String API_SLOTS = "slots/";

  /**
   * Creates a Tygron connection using some settings.
   * @param settings the settings
   */
  public HttpConnection(Settings settings) {
    this.settings = settings;
    this.client = HttpClients.custom().build();
    this.handler = new BasicResponseHandler();
  }

  public void setServerToken(String serverToken) {
    this.serverToken = serverToken;
  }

  public <T> T execute(String eventName, CallType type, ResultHandler<T> resultHandler) {
    return execute(eventName, type, resultHandler, null, null);
  }

  public <T> T execute(String eventName, CallType type, ResultHandler<T> resultHandler, JSONArray parameters) {
    return execute(eventName, type, resultHandler, null, parameters);
  }

  public <T> T execute(String eventName, CallType type, ResultHandler<T> resultHandler, Session session) {
    return execute(eventName, type, resultHandler, session, null);
  }
  
  /**
   * Calls a method on Tygron's servers.
   * @param <T> A type
   * @param eventName The event name, a part of the URL
   * @param type GET or POST event
   * @param resultHandler The handler used to parse Tygron's result.
   * @param session The session this call should use, can be null
   * @param parameters The parameters this request should use, can be null
   * @return a result handled by this request
   */
  public <T> T execute(String eventName, CallType type, ResultHandler<T> resultHandler, Session session, JSONArray parameters) {
    try {
      HttpRequestBase requester = type.asRequest(parameters);
      String url = getApiUrl(eventName, session);
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
   * @param session The session this call should use
   * @param parameters The parameters this request should use
   * @return new updates of the data types requested
   */
  public JSONObject getUpdate(ResultHandler<JSONObject> resultHandler, Session session, JSONObject parameters) {
    try {
      HttpRequestBase requester = CallType.POST.asRequest(parameters);
      String url = getApiUrl("update/", session);
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
   * @param session a session, may be null
   * @return Tygron's response
   */
  protected String getApiUrl(String eventName, Session session) {
    if (session == null) {
      return API_URL_BASE + eventName + API_JSON_SUFFIX;
    } else {
      return API_URL_BASE + API_SLOTS + session.getId() + API_DELIMITER + eventName + API_JSON_SUFFIX;
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
  protected void addDefaultHeaders(HttpUriRequest request) {
    request.setHeader("Accept", "application/json");
    request.setHeader("Content-Type", "application/json");
    request.setHeader("Authorization", "Basic " + getAuthString());

    if (serverToken != null) {
      request.setHeader("serverToken", serverToken);
    }
  }
}
