package nl.tudelft.contextproject.tygron.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;
import org.junit.Test;

/**
 * Created by Dereck on 5/29/2015.
 */
public class CallTypeTest {
  @Test
  public void postAsRequestTestWithEntity() {
    JSONObject testRequestObject = new JSONObject();
    testRequestObject.put("key", "value");
    testRequestObject.put("int", "5");
    HttpRequestBase requestBase = CallType.POST.asRequest(testRequestObject);
    if (requestBase instanceof HttpPost) {
      HttpPost casted = (HttpPost) requestBase;
      if (casted.getEntity() instanceof StringEntity) {
        assertEquals(testRequestObject.toString().length(), casted.getEntity()
            .getContentLength());
      } else {
        fail();
      }
    } else {
      fail();
    }
  }

  @Test
  public void postAsRequestTestNoEntity() {
    JSONObject nullObject = null;
    HttpRequestBase requestBase = CallType.POST.asRequest(nullObject);
    if (requestBase instanceof HttpPost) {
      HttpPost casted = (HttpPost) requestBase;
      assertNull(casted.getEntity());
    } else {
      fail();
    }
  }

  @Test
  public void getAsRequest() {
    HttpRequestBase requestBase = CallType.GET.asRequest("");
    if (!(requestBase instanceof HttpGet)) {
      fail();
    }
  }

}
