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
  private String name;
  private String type;
  private String language;
  private String clientToken;
  private String serverToken;
  private int id;

  /**
   * Tygron Session Object.
   */
  public Session(Connection localApiConnection) {
    apiConnection = localApiConnection;
    setName(""); 
    clientToken = "";
    serverToken = "";
  }

  /**
   * Set a new session name.
   * 
   * @param newName
   *          The new session name.
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
   * @param newType
   *          The new session type.
   */
  public void setType(String newType) {
    this.type = newType;
  }

  /**
   * Set a new session language.
   * 
   * @param newLanguage
   *          The new session language.
   */
  public void setLanguage(String newLanguage) {
    this.language = newLanguage;
  }

  /**
   * Set a new session id.
   * 
   * @param session id
   *          The new session id.
   */
  public void setId(int newId) {
    this.id = newId;
    apiConnection.setSessionId(newId);
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
   * @param serverToken
   * 		The server token.
   */
  public void setServerToken(String serverToken){
    this.serverToken = serverToken;
    apiConnection.setServerToken(serverToken);
  }
  
  /**
   * Get the server token.
   *
   * @return The server token.
   */
  public String getServerToken(){
    return this.serverToken;
  }
  
  /**
   * Set a client token.
   * 
   * @param clientToken
   * 		The client token.
   */
  public void setClientToken(String clientToken){
    this.clientToken = clientToken;
  }
  
  /**
   * Get the client token.
   *
   * @return The client token.
   */
  public String getClientToken(){
    return this.clientToken;
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
}
