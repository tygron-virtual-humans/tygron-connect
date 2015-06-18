package nl.tudelft.contextproject.tygron.api;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

/**
 * A CallType is an abstraction over Tygron's HTTP call types.
 */
public enum CallType {
  GET, POST;
  /**
   * Creates a HttpRequestBase from this type.
   * 
   * @param parameters
   *          parameters to attach to the request, may be null
   * @return a HttpRequestBase that can be executed
   */
  public HttpRequestBase asRequest(Object parameters) {
    if (parameters != null) {
      return asRequest(parameters.toString());
    }
    return asRequest("");
  }

  /**
   * Creates a HttpRequestBase from this type.
   * 
   * @param entity
   *          entity to attach to the request, may be null
   * @return a HttpRequestBase that can be executed
   */
  public HttpRequestBase asRequest(String entity) {
    switch (this) {
      case GET:
        return new HttpGet();
      case POST:
        return postRequest(entity);
      default:
        //Can't be called.
        throw new RuntimeException("Invalid Type");
    }
  }

  /**
   * Creates an HttpPost with a given entity.
   * @param entity the content of the request
   * @return an HttpPost that can be executed.
   */
  private HttpRequestBase postRequest(String entity) {
    try {
      HttpPost ret = new HttpPost();
      if (entity != null && !entity.isEmpty()) {
        ret.setEntity(new StringEntity(entity));
      }
      return ret;
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }
}
