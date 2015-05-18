package nl.tudelft.contextproject.tygron;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class EconomyList extends ArrayList<Economy> {
  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructs a economy list from a tygron response.
   * @param input
   * input response
   */
  public EconomyList(JSONArray input) {
    for (int i = 0; i < input.length(); i++) {
      JSONObject economyWrapper = input.getJSONObject(i);
      Economy economy = new Economy(economyWrapper);
      this.add(economy);
    }
  }
}
