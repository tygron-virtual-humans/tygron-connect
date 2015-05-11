package nl.tudelft.contextproject.tygron;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TygronStakeholderList extends ArrayList<TygronStakeholder> {
  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructs a TygronStakeholderList from a tygron response.
   * @param input
   * input response
   */
  public TygronStakeholderList(JSONArray input) {
    for (int i = 0; i < input.length(); i++) {
      JSONObject stakeholderWrapper = input.getJSONObject(i);
      JSONObject stakeholderObj = stakeholderWrapper.getJSONObject("Stakeholder");
      TygronStakeholder stakeholder = new TygronStakeholder(stakeholderObj);
      this.add(stakeholder);
    }
  }
}
