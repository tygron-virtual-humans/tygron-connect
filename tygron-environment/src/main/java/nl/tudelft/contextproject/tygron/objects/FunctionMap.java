package nl.tudelft.contextproject.tygron.objects;

import org.json.JSONArray;

import java.util.HashMap;

public class FunctionMap extends HashMap<Integer, Function> {
  
  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Creates a map with function ids as keys and functions as values.
   * @param array The array containing the functions.
   */
  public FunctionMap(JSONArray array) {
    for (int i = 0; i < array.length(); i++) {
      Function function = new Function(array.getJSONObject(i));
      this.put(function.getId(), function);
    }
  }
}
