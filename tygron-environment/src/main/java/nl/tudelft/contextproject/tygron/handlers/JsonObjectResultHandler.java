package nl.tudelft.contextproject.tygron.handlers;

import org.json.JSONObject;

public class JsonObjectResultHandler extends ResultHandler<JSONObject> {
  @Override
  public JSONObject handleResult(String input) {
    if(input != null) {
      return new JSONObject(input);
    }
    return null;
  }
}
