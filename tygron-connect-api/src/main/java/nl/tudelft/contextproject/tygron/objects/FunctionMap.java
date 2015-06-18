package nl.tudelft.contextproject.tygron.objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A FunctionMap is a map with function ids as keys and functions as values.
 */
public class FunctionMap extends HashMap<Integer, Function> {
  
  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Creates a map with function ids as keys and functions as values.
   * @param input The array containing the functions.
   */
  public FunctionMap(JSONArray input) {
    for (int i = 0; i < input.length(); i++) {
      JSONObject functionWrapper = input.getJSONObject(i);
      JSONObject functionObj = functionWrapper.getJSONObject("BaseFunction");
      Function function = new Function(functionObj);
      this.put(function.getId(), function);
    }
  }
}
