package nl.tudelft.contextproject.tygron;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * TygronSession. General session handling to Tygron. A brief overview: First
 * you start a new session (START_NEW_SESSION) You'll get back an ID, but you
 * can also get the joinable session with GET_JOINABLE_SESSIONS You can now join
 * a session with JOIN_SESSION. You can either close your own session with
 * CLOSE_SESSION or kill the session with KILL_SESSION.
 *
 */
public class Session {

  final Logger logger = LoggerFactory.getLogger(Session.class);
  
  // Session oriented
  private static HttpConnection apiConnection;
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
  private BuildingList buildingList;

  /**
   * Tygron Session Object.
   */
  public Session(HttpConnection localApiConnection) {
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
   * Close a session (instead of killing it).
   * 
   * @param keepAlive
   *          In keepAlive is true and you are the last one in the session,
   *          should it be killed? False indicated it should. True indicated it
   *          should be kept alive.
   * @return Whether the operations succeeded.
   */
  public boolean closeSession(boolean keepAlive) {

    logger.info("Closing session #" + this.id + " with clientToken " + this.clientToken + " (keepalive: " + keepAlive + ")");
    
    JSONArray dataArray = new JSONArray();
    dataArray.put(this.id); // Server slot ID
    dataArray.put(this.clientToken); // Client session token
    dataArray.put(keepAlive); // Keep alive?

    boolean apiReturnValue = apiConnection.callPostEventBoolean(
        "services/event/IOServicesEventType/CLOSE_SESSION/", dataArray); 
    
    logger.info("Closing session result: " + apiReturnValue);
    
    return apiReturnValue;
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
   *          The server token.
   */
  public void setServerToken(String serverToken) {
    logger.debug("Session serverToken=" + serverToken);
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
   * Return a (string) array with all the possible operations/data that can be
   * loaded from the API.
   * 
   * @return The compatible operations for this session.
   */
  public ArrayList<String> getCompatibleOperations() {
    return this.compatibleOperations;
  }

  /**
   * Load the stake holders into this session.
   * 
   * @return the list of stakeholders.
   */
  public StakeholderList loadStakeHolders() {
    
    logger.debug("Loading stakeholders"); 
    
    JSONArray data = apiConnection.callGetEventArray("slots/" + this.id
        + "/lists/stakeholders/");

    this.stakeholderList = new StakeholderList(data);

    return this.stakeholderList;
  }

  /**
   * Return the stake holder list
   * 
   * @return the list of stakeholders.
   */
  public StakeholderList getStakeHolders() {
    return this.stakeholderList;
  }

  /**
   * Load the indicators into this session.
   * 
   * @return the list of indicators.
   */
  public IndicatorList loadIndicators() {
    
    logger.debug("Loading indicators"); 
    
    JSONArray data = apiConnection.callGetEventArray("slots/" + this.id
        + "/lists/indicators/");

    this.indicatorList = new IndicatorList(data);

    return this.indicatorList;
  }

  /**
   * Return the indicator list
   * 
   * @return the list of indicators.
   */
  public IndicatorList getIndicators() {
    return this.indicatorList;
  }

  /**
   * Load the zones into this session.
   * 
   * @return the list of zones.
   */
  public ZoneList loadZones() {
    
    logger.debug("Loading zones"); 
    
    JSONArray data = apiConnection.callGetEventArray("slots/" + this.id
        + "/lists/zones/");

    this.zoneList = new ZoneList(data);

    return this.zoneList;
  }

  /**
   * Return the zone list
   * 
   * @return the list of zones.
   */
  public ZoneList getZones() {
    return this.zoneList;
  }

  /**
   * Load the economies into this session.
   * 
   * @return the list of economics.
   */
  public EconomyList loadEconomies() {
    
    logger.debug("Loading economies");  
    
    JSONArray data = apiConnection.callGetEventArray("slots/" + this.id
        + "/lists/economies/");

    this.economyList = new EconomyList(data);

    return this.economyList;
  }

  /**
   * Return the economies list
   * 
   * @return the list of economics.
   */
  public EconomyList getEconomies() {
    return this.economyList;
  }

  /**
   * Load the buildings into this session.
   * 
   * @return the list of buildings.
   */
  public BuildingList loadBuildings() {
    
    logger.debug("Loading buildings"); 
    
    JSONArray data = apiConnection.callGetEventArray("slots/" + this.id
        + "/lists/buildings/");

    this.buildingList = new BuildingList(data);

    return this.buildingList;
  }

  /**
   * Return the buildings list
   * 
   * @return the list of buildings.
   */
  public BuildingList getBuildings() {
    return this.buildingList;
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
