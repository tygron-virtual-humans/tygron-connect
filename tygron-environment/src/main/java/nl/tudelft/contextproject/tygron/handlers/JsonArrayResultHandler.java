package nl.tudelft.contextproject.tygron.handlers;

import org.json.JSONArray;

public class JsonArrayResultHandler extends ResultHandler<JSONArray> {
  @Override
  public JSONArray handleResult(String input) {
    return new JSONArray(input);
  }
}
