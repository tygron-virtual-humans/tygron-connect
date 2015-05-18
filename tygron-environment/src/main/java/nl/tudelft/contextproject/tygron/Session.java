package nl.tudelft.contextproject.tygron;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * TygronSession. General session handling to Tygron. A brief overview: First
 * you start a new session (START_NEW_SESSION) You'll get back an ID, but you
 * can also get the joinable session with GET_JOINABLE_SESSIONS You can now join
 * a session with JOIN_SESSION. You can either close your own session with
 * CLOSE_SESSION or kill the session with KILL_SESSION.
 *
 */
public class Session {

  // Session oriented
  private static Connection apiConnection;
  private String name;
  private String platform;
  private String state;
  private String type;
  private String language;
  private String clientToken;
  private String serverToken;
  private ArrayList<String> compatibleOperations;
  private int id;

  // Session data oriented
  private StakeholderList stakeholderList;
  private IndicatorList indicatorList;
  private ZoneList zoneList;
  private EconomyList economyList;

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
   * Load details from JSON Object and load them locally.
   * 
   * @param object
   *          the JSON Object with data
   */
  public void loadFromJson(JSONObject object) {
    this.setClientToken(object.getJSONObject("client").getString("clientToken"));
    this.setServerToken(object.getString("serverToken"));
    this.name = object.getString("project");
    // this.type = object.getString("type");
    this.platform = object.getString("platform");
    this.state = object.getJSONObject("client").getString("connectionState");

    compatibleOperations = new ArrayList<String>();
    JSONArray jsonArray = object.getJSONArray("lists");
    if (jsonArray != null) {
      int len = jsonArray.length();
      for (int i = 0; i < len; i++) {
        compatibleOperations.add(jsonArray.get(i).toString());
      }
    }
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
   * @param session
   *          id The new session id.
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
   * @param serverToken
   *          The server token.
   */
  public void setServerToken(String serverToken) {
    this.serverToken = serverToken;
    apiConnection.setServerToken(serverToken);
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
   * @param clientToken
   *          The client token.
   */
  public void setClientToken(String clientToken) {
    this.clientToken = clientToken;
  }

  /**
   * Return a (string) array with all the possible operations/data that can be
   * loaded from the API.
   * 
   * @return
   */
  public ArrayList<String> getCompatibleOperations() {
    return this.compatibleOperations;
  }

  /**
   * Load the stake holders into this session.
   * 
   * @return
   */
  public StakeholderList loadStakeHolders() {
    JSONArray data = apiConnection.callGetEventArray("slots/" + this.id
        + "/lists/stakeholders/");

    this.stakeholderList = new StakeholderList(data);

    return this.stakeholderList;
  }

  /**
   * Return the stake holder list
   * 
   * @return
   */
  public StakeholderList getStakeHolders() {
    return this.stakeholderList;
  }
  
  /**
   * Load the indicatorso into this session.
   * 
   * @return
   */
  public IndicatorList loadIndicators() {
    JSONArray data = apiConnection.callGetEventArray("slots/" + this.id
        + "/lists/indicators/");

    this.indicatorList = new IndicatorList(data);

    return this.indicatorList;
  }

  /**
   * Return the indicator list
   * 
   * @return
   */
  public IndicatorList getIndicators() {
    return this.indicatorList;
  }  

   /**
   * Load the zones into this session.
   * 
   * @return
   */
  public ZoneList loadZones() {
    JSONArray data = apiConnection.callGetEventArray("slots/" + this.id
        + "/lists/zones/");

    this.zoneList = new ZoneList(data);

    return this.zoneList;
  }

  /**
   * Return the zone list
   * 
   * @return
   */
  public ZoneList getZones() {
    return this.zoneList;
  }
  
  /**
  * Load the zones into this session.
  * 
  * @return
  */
 public EconomyList loadEconomies() {
   JSONArray data = apiConnection.callGetEventArray("slots/" + this.id
       + "/lists/economies/");

   this.economyList = new EconomyList(data);

   return this.economyList;
 }

 /**
  * Return the zone list
  * 
  * @return
  */
 public EconomyList getEconomies() {
   return this.economyList;
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
