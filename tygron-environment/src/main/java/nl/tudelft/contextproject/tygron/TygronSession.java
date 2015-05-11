package nl.tudelft.contextproject.tygron;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * TygronSession.
 * General session handling to Tygron. A brief overview:
 *  First you start a new session (START_NEW_SESSION)
 *  You'll get back an ID, but you can also get the joinable session with GET_JOINABLE_SESSIONS
 *  You can now join a session with JOIN_SESSION.
 *  You can either close your own session with CLOSE_SESSION or 
 *  kill the session with KILL_SESSION.
 * @author Paul
 *
 */
public class TygronSession {

  private static TygronConnection apiConnection;
  private String sessionName = "";
  private String sessionType;
  private String sessionLanguage;
  private String sessionClientToken = "";
  private String sessionServerToken = "";
  private int sessionId;

  /**
   * Tygron Session Object.
   */
  public TygronSession(TygronConnection localApiConnection) {
    apiConnection = localApiConnection;
  }

  /**
   * Set a new session name.
   * 
   * @param newName
   *          The new session name.
   */
  public void setName(String newName) {
    this.sessionName = newName;
  }

  /**
   * Set a new session type.
   * 
   * @param newType
   *          The new session type.
   */
  public void setType(String newType) {
    this.sessionType = newType;
  }

  /**
   * Set a new session language.
   * 
   * @param newLanguage
   *          The new session language.
   */
  public void setLanguage(String newLanguage) {
    this.sessionLanguage = newLanguage;
  }

  /**
   * Set a new session id.
   * 
   * @param session id
   *          The new session id.
   */
  public void setSessionId(int newId) {
    this.sessionId = newId;
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
      localSession.setType(row.getString("sessionType"));
      localSession.setLanguage(row.getString("sessionType"));
      localSession.setSessionId(row.getInt("id"));

      // Add to datalist
      returnList.add(localSession);
    }

    return returnList;
  }
  
  /**
   * Join a Tygron session and reply whether it was a success or not. 
   * Data is loaded into memory.
   * @return whether the join was succesful or not.
   */
  public boolean joinSession(int slotId) {
    HashMap<String,String> dataMap = new HashMap<String,String>();
    dataMap.put("0", slotId + "");          // Param: Integer: Server slot ID
    dataMap.put("1", "VIEWER"); // Param: AppType: My application type: EDITOR, VIEWER, ADMIN, BEAM 
    dataMap.put("2", "");                   // Param: String: My client address (optional)
    dataMap.put("3", "Tygron-API-Agent");   // Param: String: My client computer name (optional)
    dataMap.put("4", "");                   // Param: UUID: My client token for rejoining (optional)
    JSONObject data = apiConnection.callPostEventObject(
        "services/event/IOServicesEventType/JOIN_SESSION/", dataMap);
    
    this.sessionClientToken = data.getString("sessionClientToken");
    this.sessionServerToken = data.getString("serverToken");
    
    return true;
  }

  /**
   * Return a string interpretation of this object.
   */
  public String toString() {
    JSONObject str = new JSONObject();
    str.put("name", this.sessionName);
    str.put("sessionType", this.sessionType);
    str.put("language", this.sessionLanguage);
    str.put("id", this.sessionId);
    return str.toString();
  }
}
