package nl.tudelft.contextproject.tygron;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {

  final Logger logger = LoggerFactory.getLogger(SessionManager.class);
  
  private static HttpConnection apiConnection;
  
  public SessionManager(HttpConnection localApiConnection) {
    apiConnection = localApiConnection;
  }
  
  /**
   * Return a list of joinable tygron sessions.
   * 
   * @return A list of joinable tygron sessions.
   */
  public List<Session> getJoinableSessions() {
    
    logger.info("Fetching joinable sessions."); 
    
    List<Session> returnList = new ArrayList<Session>();

    JSONArray data = apiConnection.callPostEventArray(
        "services/event/IOServicesEventType/GET_JOINABLE_SESSIONS/", null);

    for (int i = 0; i < data.length(); i++) {
      JSONObject row = data.getJSONObject(i);

      // Create new session
      Session localSession = new Session(apiConnection);

      // Set data here
      localSession.setName(row.getString("name"));
      localSession.setType(row.getString("sessionType"));
      localSession.setLanguage(row.getString("sessionType"));
      localSession.setId(row.getInt("id"));

      // Add to datalist
      returnList.add(localSession);
    }
    
    logger.info(data.length() + " sessions available.");

    return returnList;
  }
  
  /**
   * Join a Tygron session and reply whether it was a success or not. 
   * Data is loaded into memory.
   * @return whether the join was successful or not.
   */
  public boolean joinSession(Session session) {

    logger.info("Joining session in slot " + session.getId());
    
    JSONArray dataArray = new JSONArray();
    dataArray.put(session.getId());      // Server slot ID
    dataArray.put("VIEWER");    // My application type: EDITOR, VIEWER, ADMIN, BEAM 
    dataArray.put("");          // My client address (optional)
    dataArray.put("Tygron-API-Agent"); //  My client computer name (optional)
    dataArray.put("");          // My client token for rejoining (optional)
    
    JSONObject data = apiConnection.callPostEventObject(
        "services/event/IOServicesEventType/JOIN_SESSION/", dataArray);
        
    session.loadFromJson(data);
    
    session.start();
    
    return true;
  }
  
  /**
   * Return a joinable loaded session.
   * If it does not exist yet, start a session and return it.
   * 
   * @param mapName The mapname you are trying to join.
   */
  public Session createOrFindSessionAndJoin(String mapName) {
    
    logger.info("Create or find a session with name: " + mapName);
    
    // Set default slot
    int slot = -1;
    Session sess = null;
    
    // Try to find a session with the name if it already exists
    List<Session> availableList = getJoinableSessions();
    System.out.println(availableList);
    for (int i = 0;i < availableList.size();i++) {
      if (mapName.equals(availableList.get(i).getName())) {
        slot = availableList.get(i).getId();
        sess = availableList.get(i);
        break;
      }
    }
    
    // else create a new session
    if (slot == -1) {
      slot = createSession(mapName);
      sess = new Session(apiConnection);
      sess.setId(slot);
    }
    
    // Join / startup the session
    joinSession(sess);
    
    // Set server token and session id in connection
    apiConnection.setServerToken(sess.getServerToken());
    apiConnection.setSessionId(slot);
    
    return sess;
  }
  
  /**
   * Create a new session with the given mapname.
   * @param mapName the mapname.
   * @return The return value (slot id) of the new session.
   */
  public int createSession(String mapName) {
    
    logger.info("Creating session with name: " + mapName);
    JSONArray dataArray = new JSONArray();
    dataArray.put("MULTI_PLAYER");  // SessionType: SINGLE_PLAYER, MULTI_PLAYER, EDITOR
    dataArray.put(mapName);         // Project file name
    
    int slotNumber = apiConnection.callPostEventInt(
        "services/event/IOServicesEventType/START_NEW_SESSION/", dataArray); 
    
    logger.info("Session created in slot: " + slotNumber);

    return slotNumber;
  }
  
  /**
   * Hard kill a Tygron session and reply whether it was a success or not. 
   * @return whether the kill was successful or not.
   */
  public boolean killSession(int slotId) {
    
    JSONArray dataArray = new JSONArray();
    dataArray.put(slotId);  // Server slot ID
    
    boolean apiCallResult = apiConnection.callPostEventBoolean(
        "services/event/IOServicesEventType/KILL_SESSION/", dataArray);
    
    logger.info("Killing session #" + slotId + " result: " + apiCallResult);
    
    return apiCallResult;
  }

}
