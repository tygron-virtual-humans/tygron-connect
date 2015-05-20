package nl.tudelft.contextproject.tygron;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains all data relative to the session.
 * @author Admin
 *
 */
public class Environment implements Runnable  {
  
  final Logger logger = LoggerFactory.getLogger(Session.class);
  
  // Environment oriented
  private static HttpConnection apiConnection;
  
  // Session data oriented
  private StakeholderList stakeholderList;
  private IndicatorList indicatorList;
  private ZoneList zoneList;
  private EconomyList economyList;
  private BuildingList buildingList;
  
  public Environment(HttpConnection localApiConnection) {
    apiConnection = localApiConnection;
  }
  
  /**
   * Main update loop for the environment.
   */
  public void run() {
    
    logger.debug("Running Environment update loop...");
    
    try {
      Thread.sleep(2500);
    } catch (InterruptedException e) {
      logger.error("Environment crashed!");
      throw new RuntimeException(e);
    }
  }
  
  /**
   * Load the stake holders into this session.
   * 
   * @return the list of stakeholders.
   */
  public StakeholderList loadStakeHolders() {
    
    logger.debug("Loading stakeholders"); 
    
    JSONArray data = apiConnection.callSessionGetEventArray("lists/stakeholders/");

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
    
    JSONArray data = apiConnection.callSessionGetEventArray("lists/indicators/");

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
    
    JSONArray data = apiConnection.callSessionGetEventArray("lists/zones/");

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
    
    JSONArray data = apiConnection.callSessionGetEventArray("lists/economies/");

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
    
    JSONArray data = apiConnection.callSessionGetEventArray("lists/buildings/");

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
  
}

