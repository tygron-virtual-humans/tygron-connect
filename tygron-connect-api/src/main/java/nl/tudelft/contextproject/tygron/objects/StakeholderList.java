package nl.tudelft.contextproject.tygron.objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * List with Tygron Stakeholders.
 *
 */
public class StakeholderList extends ArrayList<Stakeholder> {
  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 1L;

  public StakeholderList() {
  }

  /**
   * Constructs a TygronStakeholderList from a tygron response.
   * @param input input response
   */
  public StakeholderList(JSONArray input) {
    for (int i = 0; i < input.length(); i++) {
      JSONObject stakeholderWrapper = input.getJSONObject(i);
      JSONObject stakeholderObj = stakeholderWrapper.getJSONObject("Stakeholder");
      Stakeholder stakeholder = new Stakeholder(stakeholderObj);
      this.add(stakeholder);
    }
  }
}
