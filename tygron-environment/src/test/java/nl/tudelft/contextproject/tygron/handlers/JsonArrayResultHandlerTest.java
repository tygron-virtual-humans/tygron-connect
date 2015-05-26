package nl.tudelft.contextproject.tygron.handlers;

import static org.junit.Assert.assertTrue;

import org.json.JSONArray;
import org.junit.Test;

public class JsonArrayResultHandlerTest {
  @Test
  public void handleResultTest() {
    ResultHandler<JSONArray> jah = new JsonArrayResultHandler();
    JSONArray jsonar = new JSONArray("[{}]");
    assertTrue(jsonar.toString().equals(jah.handleResult("[{}]").toString()));
  }
}
