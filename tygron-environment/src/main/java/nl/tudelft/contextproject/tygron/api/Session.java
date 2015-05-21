package nl.tudelft.contextproject.tygron.api;

import nl.tudelft.contextproject.tygron.handlers.BooleanResultHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * TygronSession. General session handling to Tygron. A brief overview: First
 * you start a new session (START_NEW_SESSION) You'll get back an ID, but you
 * can also get the joinable session with GET_JOINABLE_SESSIONS You can now join
 * a session with JOIN_SESSION. You can either close your own session with
 * CLOSE_SESSION or kill the session with KILL_SESSION.
 */
public class Session {
  private static final Logger logger = LoggerFactory.getLogger(Session.class);

  // Session oriented
  private static HttpConnection apiConnection;
  private Environment environment;
  private Thread envThread;
  private String name;
  private String platform;
  private String state;
  private String type;
  private String language;
  private String clientToken;
  private String serverToken;
  private List<String> compatibleOperations;
  private int id;

  /**
   * Tygron Session Object.
   */
  public Session(HttpConnection localApiConnection) {
    apiConnection = localApiConnection;
    name = "";
    clientToken = "";
    serverToken = "";

    environment = new Environment(apiConnection, this);
  }

  /**
   * Load details from JSON Object and load them locally.
   *
   * @param object the JSON Object with data
   */
  public void loadFromJson(JSONObject object) {
    this.setClientToken(object.getJSONObject("client").getString("clientToken"));
    this.setServerToken(object.getString("serverToken"));
    this.name = object.getString("project");
    // this.type = object.getString("type");
    this.platform = object.getString("platform");
    this.state = object.getJSONObject("client").getString("connectionState");

    compatibleOperations = new ArrayList<>();
    JSONArray jsonArray = object.getJSONArray("lists");
    int len = jsonArray.length();
    for (int i = 0; i < len; i++) {
      compatibleOperations.add(jsonArray.get(i).toString());
    }
  }

  public void start() {
    envThread = new Thread(environment);
    envThread.start();
  }

  /**
   * Close a session (instead of killing it).
   *
   * @param keepAlive In keepAlive is true and you are the last one in the session,
   *                  should it be killed? False indicated it should. True indicated it
   *                  should be kept alive.
   * @return Whether the operations succeeded.
   */
  public boolean closeSession(boolean keepAlive) {
    logger.info("Closing session #" + this.id + " with clientToken " + this.clientToken + " (keepalive: " + keepAlive
        + ")");
    CloseSessionRequest closeSessionRequest = new CloseSessionRequest(this, keepAlive);
    boolean apiReturnValue = apiConnection.execute("services/event/IOServicesEventType/CLOSE_SESSION/", CallType.POST,
        new BooleanResultHandler(), closeSessionRequest);

    logger.info("Closing session result: " + apiReturnValue);

    return apiReturnValue;
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

  /**
   * Set a new session type.
   *
   * @param newType The new session type.
   */
  public void setType(String newType) {
    this.type = newType;
  }

  /**
   * Set a new session language.
   *
   * @param newLanguage The new session language.
   */
  public void setLanguage(String newLanguage) {
    this.language = newLanguage;
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

  /**
   * Set a new server token.
   *
   * @param serverToken The server token.
   */
  public void setServerToken(String serverToken) {
    logger.debug("Session serverToken=" + serverToken);
    this.serverToken = serverToken;
  }

  /**
   * Get the server token.
   *
   * @return The server token.
   */
  public String getServerToken() {
    return this.serverToken;
  }

  /**
   * Set a client token.
   *
   * @param clientToken The client token.
   */
  public void setClientToken(String clientToken) {
    logger.debug("Session clientToken=" + clientToken);
    this.clientToken = clientToken;
  }

  /**
   * Get the client token.
   *
   * @return The client token.
   */
  public String getClientToken() {
    return this.clientToken;
  }

  /**
   * Get the environment.
   *
   * @return The environment.
   */
  public Environment getEnvironment() {
    return this.environment;
  }

  /**
   * Return a (string) array with all the possible operations/data that can be
   * loaded from the API.
   *
   * @return The compatible operations for this session.
   */
  public List<String> getCompatibleOperations() {
    return this.compatibleOperations;
  }

  /**
   * Return a string interpretation of this object.
   */
  public String toString() {
    JSONObject str = new JSONObject();
    str.put("name", this.name);
    str.put("sessionType", this.type);
    str.put("language", this.language);
    str.put("id", this.id);
    return str.toString();
  }

  class CloseSessionRequest extends JSONArray {
    public CloseSessionRequest(Session session, boolean keepAlive) {
      put(session.getId());
      put(session.getClientToken());
      put(keepAlive);
    }
  }
}
