package nl.tudelft.contextproject.tygron;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TygronSession {

  private static TygronConnection apiConnection;
  private String sessionName = "";

  /**
   * Tygron Session Object.
   */
  public TygronSession(TygronConnection localApiConnection) {
    apiConnection = localApiConnection;
  }
  
  /**
   * Set a new session name.
   * @param newName The new session name.
   */
  public void setName(String newName) {
    this.sessionName = newName;
  }

  /**
   * Return a list of joinable tygron sessions.
   * 
   * @return A list of joinable tygron sessions.
   */
  public List<TygronSession> getJoinableSessions() {
    List<TygronSession> returnList = new ArrayList<TygronSession>();

    JSONArray data = apiConnection.callPostEventArray(
        "services/event/IOServicesEventType/GET_JOINABLE_SESSIONS/", null);

    for (int i = 0; i < data.length(); i++) {
      JSONObject row = data.getJSONObject(i);
      
      // Create new session
      TygronSession localSession = new TygronSession(apiConnection);
      
      // Set data here
      localSession.setName(row.getString("name"));
      
      // Add to datalist
      returnList.add(localSession);
    }

    return returnList;
  }
  
  /**
   * Return a string interpretation of this object.
   */
  public String toString() {
    return "{\"name\":\"" + this.sessionName + "\"}";
  }
}
