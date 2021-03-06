package nl.tudelft.contextproject.tygron.objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Holds multiple Economies. Is reponsible for parsing it's result frmo
 */
public class EconomyList extends ArrayList<Economy> {
  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 1L;

  public EconomyList() {}
  /**
   * Constructs a economy list from a tygron response.
   * @param input input response
   */
  public EconomyList(JSONArray input) {
    for (int i = 0; i < input.length(); i++) {
      JSONObject economyWrapper = input.getJSONObject(i);
      JSONObject economyObj = economyWrapper.getJSONObject("Economy");
      Economy economy = new Economy(economyObj);
      this.add(economy);
    }
  }
}
