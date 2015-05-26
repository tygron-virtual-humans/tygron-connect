package nl.tudelft.contextproject.tygron.handlers;

import static org.junit.Assert.assertEquals;

import org.json.JSONObject;
import org.junit.Test;

public class JsonObjectResultHandlerTest {
  @Test
  public void handleResultNullTest() {
    ResultHandler<JSONObject> joh = new JsonObjectResultHandler();
    assertEquals(joh.handleResult(null),null);
  }
  
  @Test
  public void handleResultTest() {
    ResultHandler<JSONObject> joh = new JsonObjectResultHandler();
    JSONObject jobj = new JSONObject("{}");
    assertEquals(joh.handleResult("{}").toString(),jobj.toString());
  }
}
