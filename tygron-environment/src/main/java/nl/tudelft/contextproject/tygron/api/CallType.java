package nl.tudelft.contextproject.tygron.api;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;

import java.io.UnsupportedEncodingException;

public enum CallType {
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

