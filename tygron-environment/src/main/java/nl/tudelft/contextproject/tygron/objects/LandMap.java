package nl.tudelft.contextproject.tygron.objects;

import org.json.JSONArray;

import java.util.HashMap;

public class LandMap extends HashMap<Integer, Land> {
  
  /**
   * Constructs a map for all pieces of land.
   * @param array The array to read from.
   */
  public LandMap(JSONArray array) {
    for (int i = 0; i < array.length(); i++) {
      Land land = new Land(array.getJSONObject(i));
      this.put(land.getId(), land);
    }
  }
}
