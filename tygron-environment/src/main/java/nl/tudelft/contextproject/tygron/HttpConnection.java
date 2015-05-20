package nl.tudelft.contextproject.tygron;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

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

public class HttpConnection {
  final private static Logger logger = LoggerFactory.getLogger(HttpConnection.class);
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

  public <T> T execute(String eventName, Type type, ResultHandler<T> result) {
    return execute(eventName, type, result, null, null);
  }

  public <T> T execute(String eventName, Type type, ResultHandler<T> result, JSONArray parameters) {
    return execute(eventName, type, result, null, parameters);
  }

  public <T> T execute(String eventName, Type type, ResultHandler<T> result, Session session) {
    return execute(eventName, type, result, session, null);
  }

  public <T> T execute(String eventName, Type type, ResultHandler<T> result, Session session, JSONArray parameters) {
    try {
      HttpRequestBase requester = type.asRequest(parameters);
      String url = getApiUrl(eventName, session);
      requester.setURI(new URI(url));
      String resultString = execute(requester);
      return result.handleResult(resultString);
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
      }
      throw new RuntimeException("Invalid Type");
    }
  }

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

  public void addDefaultHeaders(HttpUriRequest request) {
    request.setHeader("Accept", "application/json");
    request.setHeader("Content-Type", "application/json");
    request.setHeader("Authorization", "Basic " + getAuthString(settings.getUserName(), settings.getPassword()));

    if (serverToken != null) {
      request.setHeader("serverToken", serverToken);
    }
  }
}
