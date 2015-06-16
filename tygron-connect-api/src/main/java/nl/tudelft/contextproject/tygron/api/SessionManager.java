package nl.tudelft.contextproject.tygron.api;

import nl.tudelft.contextproject.tygron.handlers.IntegerResultHandler;
import nl.tudelft.contextproject.tygron.handlers.JsonArrayResultHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {
  private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);

  /**
   * Return a list of joinable tygron sessions.
   *
   * @return A list of joinable tygron sessions.
   */
  public List<JoinableSession> getJoinableSessions() {
    logger.info("Fetching joinable sessions.");
    List<JoinableSession> returnList = new ArrayList<>();
    JSONArray data = HttpConnection.getInstance().execute("services/event/IOServicesEventType/GET_JOINABLE_SESSIONS/",
            CallType.POST, new JsonArrayResultHandler());
    for (int i = 0; i < data.length(); i++) {
      JSONObject row = data.getJSONObject(i);
      // Add to datalist
      returnList.add(new JoinableSession(row));
    }
    logger.info(data.length() + " sessions available.");
    return returnList;
  }

  public Session createOrFindSessionAndJoin(String mapName) {
    return createOrFindSessionAndJoin(mapName,-1);
  }
 
  /**
   * Return a joinable loaded session. If it does not exist yet, start a session
   * and return it.
   *
   * @param mapName The mapname you are trying to join.
   * @param preferedSlot the preferred slot
   * @return a session
   */
  public Session createOrFindSessionAndJoin(String mapName, int preferedSlot) {
    logger.info("Create or find a session with name: " + mapName);

    JoinableSession session = null;

    // Try to find a session with the name if it already exists
    List<JoinableSession> availableList = getJoinableSessions();
    
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
      int slot = startSessionSlot(mapName);
      session = new JoinableSession();
      session.setId(slot);
    }
    
    // if a session could not be joined/created let's print this
    if (session.getId() == -1) {
      logger.info("Could not create or join session. Do you have access to the selected map?");
      throw new RuntimeException();
    }

    // Join / startup the session
    return session.join();
  }

  /**
   * Create a new session with the given mapname.
   *
   * @param mapName the mapname.
   * @return The return value (slot id) of the new session.
   */
  public int startSessionSlot(String mapName) {
    logger.info("Creating session with name: " + mapName);
    StartSessionRequest startSessionRequest = new StartSessionRequest("MULTI_PLAYER", mapName);
    int slotNumber = HttpConnection.getInstance().execute("services/event/IOServicesEventType/START_NEW_SESSION/",
            CallType.POST, new IntegerResultHandler(), startSessionRequest);

    logger.info("Session created in slot: " + slotNumber);

    return slotNumber;
  }

  class StartSessionRequest extends JSONArray {
    public StartSessionRequest(String type, String mapName) {
      this.put(type);
      this.put(mapName);
    }
  }
}
