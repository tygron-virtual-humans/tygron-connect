package nl.tudelft.contextproject.tygron.objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A LandMap maps Land IDs to Lands.
 */
public class LandMap extends HashMap<Integer, Land> {
  
  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 1L;

  public LandMap() {

  }
  /**
   * Constructs a map for all pieces of land.
   * @param input The array to read from.
   */
  public LandMap(JSONArray input) {
    for (int i = 0; i < input.length(); i++) {
      JSONObject landWrapper = input.getJSONObject(i);
      JSONObject landObj = landWrapper.getJSONObject("Land");
      Land land = new Land(landObj);
      this.put(land.getId(), land);
    }
  }
}
