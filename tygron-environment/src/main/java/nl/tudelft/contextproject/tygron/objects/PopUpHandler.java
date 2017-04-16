package nl.tudelft.contextproject.tygron.objects;

import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.api.Session;
import nl.tudelft.contextproject.tygron.handlers.JsonObjectResultHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PopUpHandler {
  
  private HttpConnection apiConnection;
  private Session session;
  
  private int version;
  private int stakeholderId;
  private List<PopUp> list;
  
  /**
   * A list containing all new, active popups.
   * @param localApiConnection The connection
   * @param session The session
   * @param stakeholderId The id of the player's stakeholder
   */
  public PopUpHandler(HttpConnection localApiConnection, Session session, int stakeholderId) {
    apiConnection = localApiConnection;
    this.session = session;
    this.stakeholderId = stakeholderId;
    this.version = 0;
  }
  
  /**
   * Gets new popups from the API update.
   */
  public void newPopUps() {
    JSONObject dataObject = apiConnection.getUpdate(new JsonObjectResultHandler(), session, 
        getRequestObject());
    if (dataObject != null) {
      updateList(dataObject.getJSONObject("items"));
    } else {
      list = new ArrayList<PopUp>();
    }
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
