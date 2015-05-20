package nl.tudelft.contextproject.tygron;

import nl.tudelft.contextproject.tygron.results.ResultHandler;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

public class HttpConnection {
  private static final Logger logger = LoggerFactory.getLogger(HttpConnection.class);
  private static HttpClient client = HttpClients.custom().build();

  private Settings settings;
  private String serverToken;

  private static final String API_URL_BASE = "https://server2.tygron.com:3022/api/";
  private static final String API_JSON_SUFFIX = "?f=JSON";
  private static final String API_DELIMITER = "/";
  private static final String API_SLOTS = "slots/";

  /**
   * Creates a Tygron connection using some settings.
   */
  public HttpConnection(Settings settings) {
    this.settings = settings;

  }

  public void setServerToken(String serverToken) {
    this.serverToken = serverToken;
  }

  public <T> T execute(String eventName, Type type, ResultHandler<T> resultHandler) {
    return execute(eventName, type, resultHandler, null, null);
  }

  public <T> T execute(String eventName, Type type, ResultHandler<T> resultHandler, JSONArray parameters) {
    return execute(eventName, type, resultHandler, null, parameters);
  }

  public <T> T execute(String eventName, Type type, ResultHandler<T> resultHandler, Session session) {
    return execute(eventName, type, resultHandler, session, null);
  }
  
  /**
   * Calls a method on Tygron's servers.
   * @param eventName The event name, a part of the URL
   * @param type GET or POST event
   * @param resultHandler The handler used to parse Tygron's result.
   * @param session The session this call should use, can be null
   * @param parameters The parameters this request should use, can be null
   * @return a result handled by this request
   */
  public <T> T execute(String eventName, Type type, ResultHandler<T> resultHandler, Session session, JSONArray parameters) {
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

  private String execute(HttpUriRequest request) {
    try {
      addDefaultHeaders(request);
      HttpResponse httpResponse = client.execute(request);
      logger.debug("Request " + request.toString());
      String response = new BasicResponseHandler().handleResponse(httpResponse);
      logger.debug("Response " + response);
      return response;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public enum Type {
    GET, POST;
    /**
     * Creates a HttpRequestBase from this type.
     * @param parameters parameters to attach to the request, may be null
     * @return a HttpRequestBase that can be executed
     */
    public HttpRequestBase asRequest(JSONArray parameters) {
      switch (this) {
        case GET:
          return new HttpGet();
        case POST:
          try {
            HttpPost ret = new HttpPost();
            if (parameters != null) {
              ret.setEntity(new StringEntity(parameters.toString()));
            }
            return ret;
          } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
          }
        default: 
          throw new RuntimeException("Invalid Type");
      }
      
    }
  }

  /**
   * Returns Tygron's API url endpoint for a given event name and session.
   * @param eventName the event that should be called
   * @param session a session, may be null
   * @return Tygron's response
   */
  public String getApiUrl(String eventName, Session session) {
    if (session == null) {
      return API_URL_BASE + eventName + API_JSON_SUFFIX;
    } else {
      return API_URL_BASE + API_SLOTS + session.getId() + API_DELIMITER + eventName + API_JSON_SUFFIX;
    }
  }

  public String getAuthString(String username, String password) {
    String headerValue = settings.getUserName() + ":" + settings.getPassword();
    return Base64.encodeBase64String(headerValue.getBytes());
  }
  
  /**
   * Adds the required headers (authentication) for Tygron communication.
   * @param request the request to attach the headers to
   */
  public void addDefaultHeaders(HttpUriRequest request) {
    request.setHeader("Accept", "application/json");
    request.setHeader("Content-Type", "application/json");
    request.setHeader("Authorization", "Basic " + getAuthString(settings.getUserName(), settings.getPassword()));

    if (serverToken != null) {
      request.setHeader("serverToken", serverToken);
    }
  }
}
