package nl.tudelft.contextproject.tygron.objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Contains Actions.
 */
public class ActionList extends ArrayList<Action> {
  
  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructs a action menu list from a tygron response.
   * @param input The array to read from.
   */
  public ActionList(JSONArray input) {
    for (int i = 0; i < input.length(); i++) {
      JSONObject actionWrapper = input.getJSONObject(i);
      JSONObject actionObj = actionWrapper.getJSONObject("ActionMenu");
      Action action = new Action(actionObj);
      this.add(action);
    }
  }
}
