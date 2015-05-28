package nl.tudelft.contextproject.tygron.api;

import nl.tudelft.contextproject.tygron.handlers.BooleanResultHandler;
import nl.tudelft.contextproject.tygron.handlers.JsonArrayResultHandler;
import nl.tudelft.contextproject.tygron.objects.BuildingList;
import nl.tudelft.contextproject.tygron.objects.EconomyList;
import nl.tudelft.contextproject.tygron.objects.StakeholderList;
import nl.tudelft.contextproject.tygron.objects.ZoneList;
import nl.tudelft.contextproject.tygron.objects.indicators.IndicatorList;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains all data relative to the session.
 * 
 */
public class Environment implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(Session.class);

  // Environment oriented
  private HttpConnection apiConnection;
  private Session session;

  // Session data oriented
  private StakeholderList stakeholderList;
  private IndicatorList indicatorList;
  private ZoneList zoneList;
  private EconomyList economyList;
  private BuildingList buildingList;

  private Thread environmentThread;

  public Environment(HttpConnection localApiConnection, Session session) {
    apiConnection = localApiConnection;
    this.session = session;
  }

  /**
   * Starts the update loop for this environment.
   */
  public void start() {
    if (environmentThread == null) {
      environmentThread = new Thread(this);
    }
    environmentThread.start();
  }

  /**
   * Main update run for the environment.
   */
  @Override
  public void run() {
    logger.debug("Running Environment update loop...");
    while (true) {
      reload();
      try {
        Thread.sleep(2500);
      } catch (InterruptedException e) {
        logger.error("Environment crashed!");
        throw new RuntimeException(e);
      }
    }
  }
  
  /**
   * Reloads all data.
   */
  public void reload() {
    loadStakeHolders();
    loadIndicators();
    loadZones();
    loadEconomies();
    loadBuildings();
  }
  /**
   * Load the stake holders into this session.
   * 
   * @return the list of stakeholders.
   */
  public StakeholderList loadStakeHolders() {
    logger.debug("Loading stakeholders");
    JSONArray data = apiConnection.execute("lists/"
        + "stakeholders/", CallType.GET, new JsonArrayResultHandler(), session);
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
    JSONArray data = apiConnection.execute("lists/"
        + "indicators", CallType.GET, new JsonArrayResultHandler(), session);
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
    JSONArray data = apiConnection.execute("lists/"
        + "zones", CallType.GET, new JsonArrayResultHandler(), session);
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
    JSONArray data = apiConnection.execute("lists/"
        + "economies", CallType.GET, new JsonArrayResultHandler(), session);
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
    JSONArray data = apiConnection.execute("lists/"
        + "buildings", CallType.GET, new JsonArrayResultHandler(), session);
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
   * Select a stakeholder to play, can only be done once.
   * @param stakeholderId the stakeholder id to select.
   */
  public boolean setStakeholder(int stakeholderId) {
    boolean retValue = apiConnection.execute("event/PlayerEventType/STAKEHOLDER_SELECT", CallType.POST, 
        new BooleanResultHandler(), session, new StakeHolderSelectRequest(stakeholderId,session.getClientToken()));
    logger.info("Setting stakeholder to #" + stakeholderId + ". Operation " + ((retValue) ? "succes!" : "failed!" ));
    return retValue;
  }
  
  class StakeHolderSelectRequest extends JSONArray {
    public StakeHolderSelectRequest(int stakeholderId, String sessionToken) {
      this.put(stakeholderId);
      this.put(sessionToken);
    }
  }
}

