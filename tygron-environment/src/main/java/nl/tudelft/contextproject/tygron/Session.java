package nl.tudelft.contextproject.tygron;

import org.json.JSONObject;

/**
 * TygronSession.
 * General session handling to Tygron. A brief overview:
 *  First you start a new session (START_NEW_SESSION)
 *  You'll get back an ID, but you can also get the joinable session with GET_JOINABLE_SESSIONS
 *  You can now join a session with JOIN_SESSION.
 *  You can either close your own session with CLOSE_SESSION or 
 *  kill the session with KILL_SESSION.
 *
 */
public class Session {

  private static Connection apiConnection;
  private String sessionName;
  private String sessionType;
  private String sessionLanguage;
  private String sessionClientToken;
  private String sessionServerToken;
  private int sessionId;

  /**
   * Tygron Session Object.
   */
  public Session(Connection localApiConnection) {
    apiConnection = localApiConnection;
    setName(""); 
    sessionClientToken = "";
    sessionServerToken = "";
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
   * Get the session name.
   * 
   * @return The new session name.
   */
  public String getName() {
    return this.sessionName;
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
   * Set a new server token.
   * @param serverToken The server token.
   */
  public void setServerToken(String serverToken){
    this.sessionServerToken = serverToken;
  }
  
  /**
   * Set a client token.
   * @param clientToken The client token.
   */
  public void setClientToken(String clientToken){
    this.sessionClientToken = clientToken;
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
