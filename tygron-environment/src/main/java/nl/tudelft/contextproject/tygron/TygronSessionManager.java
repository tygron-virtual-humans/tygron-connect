package nl.tudelft.contextproject.tygron;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class TygronSessionManager {

  private static TygronConnection apiConnection;
  
  public TygronSessionManager(TygronConnection localApiConnection){
    apiConnection = localApiConnection;
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
   * @return whether the join was successful or not.
   */
  public boolean joinSession(TygronSession session, int slotId) {
    HashMap<String,String> dataMap = new HashMap<String,String>();
    dataMap.put("0", slotId + "");          // Param: Integer: Server slot ID
    dataMap.put("1", "VIEWER"); // Param: AppType: My application type: EDITOR, VIEWER, ADMIN, BEAM 
    dataMap.put("2", "");                   // Param: String: My client address (optional)
    dataMap.put("3", "Tygron-API-Agent");   // Param: String: My client computer name (optional)
    dataMap.put("4", "");                   // Param: UUID: My client token for rejoining (optional)
    JSONObject data = apiConnection.callPostEventObject(
        "services/event/IOServicesEventType/JOIN_SESSION/", dataMap);
    
    session.setClientToken(data.getString("sessionClientToken"));
    session.setServerToken(data.getString("serverToken"));
    
    return true;
  }
  
  /**
   * Return a joinable loaded session.
   * If it does not exist yet, start a session and return it.
   * 
   * @param mapName The mapname you are trying to join.
   */
  public TygronSession createOrJoinSession(String mapName){
    List<TygronSession> availableList = getJoinableSessions();
    for(int i = 0;i<availableList.size();i++){
      if(mapName.equals(availableList.get(i).getName())){
        return availableList.get(i);
      }
    }
    
    // TODO: Create a new session
    
    return new TygronSession(apiConnection);
  }
  
  public int createSession(String mapName){
    
    HashMap<String,String> dataMap = new HashMap<String,String>();
    dataMap.put("0", "MULTI_PLAYER");   // SessionType: SessionType: SINGLE_PLAYER, MULTI_PLAYER, EDITOR
    dataMap.put("1", mapName);          // String: Project file name
    dataMap.put("2", "EN");             // TLanguage: Language: NL, EN (optional)
    dataMap.put("3", "");               // empty
    dataMap.put("4", "");               // empty
    
    JSONObject data = apiConnection.callPostEventObject(
        "services/event/IOServicesEventType/START_NEW_SESSION/", dataMap); 
    
    System.out.println(data);
    
    return -1;
  }
  
  /**
   * Hard kill a Tygron session and reply whether it was a success or not. 
   * @return whether the kill was successful or not.
   */
  public boolean killSession(int slotId) {
    HashMap<String,String> dataMap = new HashMap<String,String>();
    dataMap.put("0", slotId + "");          // Param: Integer: Server slot ID
    
    JSONObject data = apiConnection.callPostEventObject(
        "services/event/IOServicesEventType/KILL_SESSION/", dataMap);
    
    System.out.println(data);
    
    return true;
  }

}
