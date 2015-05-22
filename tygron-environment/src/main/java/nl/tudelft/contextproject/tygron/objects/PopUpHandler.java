package nl.tudelft.contextproject.tygron.objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PopUpHandler {
  
  private int version;
  private int stakeholderId;
  private List<PopUp> list;
  
  /**
   * A list containing all active popups.
   * @param array The id of the player's stakeholder id
   */
  public PopUpHandler(int stakeholderId) {
    this.stakeholderId = stakeholderId;
    this.version = 0;
  }
  
  /**
   * Creates a JSONObject which can be used as input for the Tygron API update.
   * @return The input for update
   */
  public JSONObject getRequestObject() {
    return new JSONObject("{\"POPUPS\":" + this.version + "}");
  }
  
  /**
   * Puts all new popups visible to the user in a list.
   * @param object The JSONObject containing new popups
   */
  public void updateList(JSONObject object) {
    list = new ArrayList<PopUp>();
    JSONArray popUpArray = object.getJSONObject("POPUPS")
        .getJSONArray("[Lnl.tytech.core.data.item.Item;");
    for (int i = 0; i < popUpArray.length(); i++) {
      PopUp popUp = new PopUp(popUpArray.getJSONObject(i));
      this.version = Integer.max(this.version, popUp.getVersion());
      if (popUp.getVisibleForActorIds().contains(this.stakeholderId)) {
        list.add(popUp);
      }
    }
  }
  
  public List<PopUp> getList() {
    return list;
  }
}
