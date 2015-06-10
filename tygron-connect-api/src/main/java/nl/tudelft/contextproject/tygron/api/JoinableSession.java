package nl.tudelft.contextproject.tygron.api;

import nl.tudelft.contextproject.tygron.handlers.JsonObjectResultHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JoinableSession {
  private static final Logger logger = LoggerFactory.getLogger(JoinableSession.class);

  private String name;
  private String type;
  private int id;

  public JoinableSession() {

  }

  /**
   * Tygron Session Object with data.
   * @param data the json data
   */
  public JoinableSession(JSONObject data) {
    name = data.getString("name");
    type = data.getString("sessionType");
    id = data.getInt("id");
  }

  /**
   * Join a session in a set slot id.
   * @return Joined Session.
   */
  public Session join() {
    logger.info("Joining session in slot " + this.getId());
    JoinSessionRequest joinSessionRequest = new JoinSessionRequest(this.getId(), "VIEWER", "Tygron-API-Agent");

    JSONObject data = HttpConnection.getInstance().execute("services/event/IOServicesEventType/JOIN_SESSION/",
            CallType.POST, new JsonObjectResultHandler(), joinSessionRequest);
    Session session = new Session(this, data);

    // Set server token and session id in connection
    HttpConnection.getInstance().setClientToken(session.getClientToken());
    HttpConnection.getInstance().setServerToken(session.getServerToken());

    session.getEnvironment().start();
    return session;
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

  /**
   * Set a new session name.
   *
   * @param newName The new session name.
   */
  public void setName(String newName) {
    this.name = newName;
  }

  /**
   * Get the session name.
   *
   * @return The session name.
   */
  public String getName() {
    return this.name;
  }

  public String getType() {
    return type;
  }

  /**
   * Set a new session type.
   *
   * @param newType The new session type.
   */
  public void setType(String newType) {
    this.type = newType;
  }

  /**
   * Set a new session id.
   *
   * @param newId id The new session id.
   */
  public void setId(int newId) {
    this.id = newId;
  }

  /**
   * Get the session id.
   *
   * @return The session id.
   */
  public int getId() {
    return this.id;
  }
}
