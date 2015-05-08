package nl.tudelft.contextproject.tygron;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TygronHttp {

  private TygronSettings settings;
  private HttpClient client;
  private String authString;
  private static final String API_URL_BASE = "https://server2.tygron.com:3022/api/";
  private static final String API_JSON_SUFFIX = "?f=JSON";

  public TygronHttp(TygronSettings localSettings) {
    settings = localSettings;
    setup();
  }

  /**
   * Setup the objects/information we'll need in all requests.
   */
  public void setup() {
    client = HttpClients.custom().build();
    String headerValue = settings.getUserName() + ":" + settings.getPassword();
    authString = Base64.encodeBase64String(headerValue.getBytes());
  }

  /**
   * Add default headers to a request for authentication/json responses.
   * 
   * @param request
   *          A HttpGet / HttpPost request
   */
  public void addDefaultHeaders(HttpRequestBase request) {
    request.setHeader("Accept", "application/json");
    request.setHeader("Authorization", "Basic " + authString);
  }

  /**
   * Executes a GET request without parameters.
   * 
   * @param apiEvent
   *          The event that needs to be called
   * @return a HttpResponse object, can be parsed by the other function/ tools.
   */
  public HttpResponse requestGet(String apiEvent) {
    HttpGet request = new HttpGet(API_URL_BASE + apiEvent + API_JSON_SUFFIX);
    addDefaultHeaders(request);
    try {
      return client.execute(request);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Executes a POST request with or without parameters.
   * 
   * @param apiEvent
   *          The event that needs to be called
   * @return a HttpResponse object, can be parsed by the other function/ tools.
   */
  public HttpResponse requestPost(String apiEvent, Map<String, String> kvPairs) {
    HttpPost request = new HttpPost(API_URL_BASE + apiEvent + API_JSON_SUFFIX);
    addDefaultHeaders(request);

    // If there are keys/values, add them.
    if (kvPairs != null && kvPairs.isEmpty() == false) {
      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
          kvPairs.size());
      String key;
      String value;
      Iterator<String> itKeys = kvPairs.keySet().iterator();

      while (itKeys.hasNext()) {
        key = itKeys.next();
        value = kvPairs.get(key);
        nameValuePairs.add(new BasicNameValuePair(key, value));
      }
      try {
        request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
      } catch (UnsupportedEncodingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    // Execute / try the request.
    try {
      return client.execute(request);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Returns a JSON Object extracted from a HttpReponse.
   * 
   * @param response
   *          HtppClient Transform
   * @return JSON Object response
   */
  public JSONObject getJsonFromResponse(HttpResponse response) {
    try {
      String responseString = new BasicResponseHandler()
          .handleResponse(response);
      return new JSONObject(responseString);
    } catch (IOException e) {
      System.out.println("Failed to get JSON Object from HttpResponse.");
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}
