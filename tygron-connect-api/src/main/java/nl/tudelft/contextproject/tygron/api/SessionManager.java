package nl.tudelft.contextproject.tygron.api;

import nl.tudelft.contextproject.tygron.handlers.BooleanResultHandler;
import nl.tudelft.contextproject.tygron.handlers.IntegerResultHandler;
import nl.tudelft.contextproject.tygron.handlers.JsonArrayResultHandler;
import nl.tudelft.contextproject.tygron.handlers.JsonObjectResultHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {
  private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);

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
    JSONArray data = apiConnection.execute("services/event/IOServicesEventType/GET_JOINABLE_SESSIONS/", CallType.POST,
        new JsonArrayResultHandler());
    for (int i = 0; i < data.length(); i++) {
      JSONObject row = data.getJSONObject(i);
      // Add to datalist
      returnList.add(new Session(apiConnection, row));
    }
    logger.info(data.length() + " sessions available.");
    return returnList;
  }

  /**
   * Joins a Tygron session.
   */
  public void joinSession(Session session) {
    logger.info("Joining session in slot " + session.getId());
    JoinSessionRequest joinSessionRequest = new JoinSessionRequest(session.getId(), "VIEWER", "Tygron-API-Agent");

    JSONObject data = apiConnection.execute("services/event/IOServicesEventType/JOIN_SESSION/", CallType.POST,
        new JsonObjectResultHandler(), joinSessionRequest);

    session.loadFromJson(data);
    // Set server token and session id in connection
    apiConnection.setServerToken(session.getServerToken());

    session.getEnvironment().start();
  }

  class JoinSessionRequest extends JSONArray {
    public JoinSessionRequest(int slotId, String type, String address, String computerName, String rejoinToken) {
      this.put(slotId);
      this.put(type);
      this.put(address);
      this.put(computerName);
      this.put(rejoinToken);
    }

    public JoinSessionRequest(int slotId, String type, String computerName) {
      this(slotId, type, "", computerName, "");
    }
  }


  public Session createOrFindSessionAndJoin(String mapName) {
    return createOrFindSessionAndJoin(mapName,-1);
  }
 
  /**
   * Return a joinable loaded session. If it does not exist yet, start a session
   * and return it.
   *
   * @param mapName The mapname you are trying to join.
   */
  public Session createOrFindSessionAndJoin(String mapName, int preferedSlot) {
    logger.info("Create or find a session with name: " + mapName);

    Session session = null;

    // Try to find a session with the name if it already exists
    List<Session> availableList = getJoinableSessions();
    
    // Try to find the specified slot
    for (int i = 0; i < availableList.size(); i++) {
      if (preferedSlot == availableList.get(i).getId()) {
        session = availableList.get(i);
        break;
      }
    }
    
    // The slot cannot be found, let's try on to find a session on the mapname.
    if (session == null) {
      for (int i = 0; i < availableList.size(); i++) {
        if (mapName.equals(availableList.get(i).getName())) {
          session = availableList.get(i);
          break;
        }
      }
    }

    // else create a new session
    if (session == null) {
      int slot = startSession(mapName);
      session = new Session(apiConnection);
      session.setId(slot);
    }
    
    // if a session could not be joined/created let's print this
    if(session.getId() == -1){
    	logger.info("Could not create or join session. Do you have access to the selected map?");
    	throw new RuntimeException();
    }

    // Join / startup the session
    joinSession(session);
    return session;
  }

  /**
   * Create a new session with the given mapname.
   *
   * @param mapName the mapname.
   * @return The return value (slot id) of the new session.
   */
  public int startSession(String mapName) {
    logger.info("Creating session with name: " + mapName);
    StartSessionRequest startSessionRequest = new StartSessionRequest("MULTI_PLAYER", mapName);
    int slotNumber = apiConnection.execute("services/event/IOServicesEventType/START_NEW_SESSION/", CallType.POST,
        new IntegerResultHandler(), startSessionRequest);

    logger.info("Session created in slot: " + slotNumber);

    return slotNumber;
  }

  class StartSessionRequest extends JSONArray {
    public StartSessionRequest(String type, String mapName) {
      this.put(type);
      this.put(mapName);
    }
  }

  /**
   * Hard kill a Tygron session and reply whether it was a success or not.
   *
   * @return whether the kill was successful or not.
   */
  public boolean killSession(int slotId) {
    KillSessionRequest killSessionRequest = new KillSessionRequest(slotId);
    boolean apiCallResult = apiConnection.execute("services/event/IOServicesEventType/KILL_SESSION/", CallType.POST,
        new BooleanResultHandler(), killSessionRequest);
    logger.info("Killing session #" + slotId + " result: " + apiCallResult);

    return apiCallResult;
  }

  class KillSessionRequest extends JSONArray {
    public KillSessionRequest(int slotId) {
      this.put(slotId);
    }
  }

}
