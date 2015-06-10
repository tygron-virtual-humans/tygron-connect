package nl.tudelft.contextproject.tygron.api;

import com.esri.core.geometry.Polygon;

import nl.tudelft.contextproject.tygron.handlers.BooleanResultHandler;
import nl.tudelft.contextproject.tygron.handlers.JsonArrayResultHandler;
import nl.tudelft.contextproject.tygron.handlers.JsonObjectResultHandler;
import nl.tudelft.contextproject.tygron.objects.Action;
import nl.tudelft.contextproject.tygron.objects.ActionList;
import nl.tudelft.contextproject.tygron.objects.Building;
import nl.tudelft.contextproject.tygron.objects.BuildingList;
import nl.tudelft.contextproject.tygron.objects.EconomyList;
import nl.tudelft.contextproject.tygron.objects.FunctionMap;
import nl.tudelft.contextproject.tygron.objects.LandMap;
import nl.tudelft.contextproject.tygron.objects.PopUpHandler;
import nl.tudelft.contextproject.tygron.objects.Stakeholder;
import nl.tudelft.contextproject.tygron.objects.StakeholderList;
import nl.tudelft.contextproject.tygron.objects.ZoneList;
import nl.tudelft.contextproject.tygron.objects.indicators.Indicator;
import nl.tudelft.contextproject.tygron.objects.indicators.IndicatorFinance;
import nl.tudelft.contextproject.tygron.objects.indicators.IndicatorList;
import nl.tudelft.contextproject.util.PolygonUtil;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Contains all data relative to the session.
 * 
 */
public class Environment implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(Environment.class);
  
  //The error margin for the amount of land
  private final double errorMargin = 0.10;

  // Environment oriented
  private PopUpHandler popUpHandler;

  // Session data oriented
  private StakeholderList stakeholderList;
  private IndicatorList indicatorList;
  private ZoneList zoneList;
  private EconomyList economyList;
  private BuildingList buildingList;
  private FunctionMap functionMap;
  private LandMap landMap;
  private ActionList actionList;
  
  private int mapWidth;

  private Thread environmentThread;

  private int stakeholderId;

  
  public void setPopupHandler(PopUpHandler popUpHandler) {
    this.popUpHandler = popUpHandler;
  }
  
  /**
   * Creates an environment that communicates with the session API.
   * @param session The session.
   */
  public Environment() {
    stakeholderId = -1;
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
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        logger.error("Environment crashed!");
        throw new RuntimeException(e);
      }
    }
  }
  
  /**
   * Allows or disables game interaction.
   */
  public void allowGameInteraction(boolean set) {
    String allowInteraction = "event/LogicEventType/SETTINGS_ALLOW_GAME_INTERACTION/";
    JSONArray param = new JSONArray();
    param.put(set);
    HttpConnection.getInstance().execute(allowInteraction, CallType.POST,
            new JsonObjectResultHandler(), true, param);
  }
  
  /**
   * Reloads all data.
   */
  public void reload() {
    loadStakeholders();
    loadIndicators();
    loadZones();
    loadEconomies();
    loadBuildings();
    loadFunctions();
    loadLands();
    if (popUpHandler != null) {
      popUpHandler.loadPopUps();
    }
  }
  /**
   * Load the stake holders into this session.
   * 
   * @return the list of stakeholders.
   */
  public StakeholderList loadStakeholders() {
    logger.debug("Loading stakeholders");
    JSONArray data = HttpConnection.getInstance().execute("lists/"
            + "stakeholders/", CallType.GET, new JsonArrayResultHandler(), true);
    this.stakeholderList = new StakeholderList(data);
    
    setActions();
    
    return this.stakeholderList;
  }

  /**
   * Return the stake holder list
   * 
   * @return the list of stakeholders.
   */
  public StakeholderList getStakeholders() {
    return this.stakeholderList;
  }
  
  /**
   * Load actions and assign their functions to stakeholders.
   */
  private void setActions() {
    ActionList actionList = loadActions();
    for (Action action : actionList) {
      Map<Integer, Boolean> activeForStakeholder = action.getActiveForStakeholder();
      Set<Integer> stakeholders = activeForStakeholder.keySet();
      Map<Integer, List<Integer>> functionMap = new HashMap<Integer, List<Integer>>();
      for (Integer stakeholderId : stakeholders) {
        if (activeForStakeholder.get(stakeholderId)) {
          functionMap.put(stakeholderId, action.getFunctionTypes());
        }
      }
      
      setFunctions(functionMap);
    }
  }
  
  private void setFunctions(Map<Integer, List<Integer>> functionsMap) {
    for (Stakeholder stakeholder : stakeholderList) {
      List<Integer> functions = functionsMap.get(stakeholder.getId());
      if (functions != null) {
        stakeholder.addAllowedFunctions(functions);
      }
    }
  }

  /**
   * Load the indicators into this session.
   * 
   * @return the list of indicators.
   */
  public IndicatorList loadIndicators() {
    logger.debug("Loading indicators");
    JSONArray data = HttpConnection.getInstance().execute("lists/"
            + "indicators", CallType.GET, new JsonArrayResultHandler(), true);
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
    JSONArray data = HttpConnection.getInstance().execute("lists/"
            + "zones", CallType.GET, new JsonArrayResultHandler(), true);
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
    JSONArray data = HttpConnection.getInstance().execute("lists/"
            + "economies", CallType.GET, new JsonArrayResultHandler(), true);
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
    JSONArray data = HttpConnection.getInstance().execute("lists/"
            + "buildings", CallType.GET, new JsonArrayResultHandler(), true);
    this.buildingList = new BuildingList(data);
    return this.buildingList;
  }

  /**
   * Return the buildings list.
   * 
   * @return the list of buildings.
   */
  public BuildingList getBuildings() {
    return this.buildingList;
  }
  
  /**
   * Loads all functions into this session.
   * @return A map of the functions.
   */
  public FunctionMap loadFunctions() {
    logger.debug("Loading functions");
    JSONArray data = HttpConnection.getInstance().execute("lists/functions",
            CallType.GET, new JsonArrayResultHandler(), true);
    this.functionMap = new FunctionMap(data);
    return this.functionMap;
  }
  
  /**
   * Return the functions map.
   * 
   * @return the map of functions.
   */
  public FunctionMap getFunctions() {
    return functionMap;
  }
  
  /**
   * Loads all lands into this session.
   * @return A map of the lands.
   */
  public LandMap loadLands() {
    logger.debug("Loading lands");
    JSONArray data = HttpConnection.getInstance().execute("lists/lands",
            CallType.GET, new JsonArrayResultHandler(), true);
    this.landMap = new LandMap(data);
    return this.landMap;
  }
  
  /**
   * Return the lands map.
   * 
   * @return the map of lands.
   */
  public LandMap getLands() {
    return landMap;
  }
  
  /**
   * Loads all possible actions into this session.
   * @return A list of the actions.
   */
  public ActionList loadActions() {
    logger.debug("Loading actions");
    JSONArray data = HttpConnection.getInstance().execute("lists/actionmenus/",
        CallType.GET, new JsonArrayResultHandler(), true);
    this.actionList = new ActionList(data);
    return this.actionList;
  }
  
  public ActionList getActions() {
    return this.actionList;
  }
  
  /**
   * Get all of the stakeholder's land that is free from buildings.
   * @param stakeholder The stakeholder.
   * @return The stakeholder's free land.
   */
  public Polygon getAvailableLand(Stakeholder stakeholder) {
    Polygon land = new Polygon();
    for (Integer landId : stakeholder.getOwnedLands()) {
      land = PolygonUtil.polygonUnion(land, landMap.get(landId).getPolygon());
    }
      
    for (Building building : buildingList) {
      if (!building.demolished()) {
        land = PolygonUtil.polygonDifference(land, building.getPolygon());
      }
    }
    
    return land;
  }
  
  /**
   * Gets a piece of land of a certain surface from the available land.
   * @param availableLand The available land.
   * @param surface The desired surface of the land.
   * @return A piece of land with a certain surface.
   */
  public Polygon getSuitableLand(Polygon availableLand, double surface) {
    getMapWidth();
    Random random = new Random();
    Polygon selectedLand;
    Polygon intersection;
    
    double x1;
    double y1;
    double x2;
    double y2;
    // Select a random rectangle in the map and get the part of land that is available.
    do {
      x1 = random.nextDouble() * mapWidth;
      y1 = random.nextDouble() * mapWidth;
      x2 = random.nextDouble() * mapWidth;
      y2 = random.nextDouble() * mapWidth;
      selectedLand = PolygonUtil.makeRectangle(x1, y1, x2, y2);
      intersection = PolygonUtil.polygonIntersection(selectedLand, availableLand);
    } while (intersection.calculateArea2D() < surface);
    
    while (!withinMargin(intersection, surface) && intersection.calculateArea2D() != 0) {
      // Reduce the land to a square as much as possible
      if (Math.abs(x1 - x2) > Math.abs(y1 - y2)) {
        x1 = x2 > x1 ? x1 + 0.5 : x1 - 0.5;
      } else {
        y1 = y2 > y1 ? y1 + 0.5 : y1 - 0.5;
      }
      selectedLand = PolygonUtil.makeRectangle(x1, y1, x2, y2);
      intersection = PolygonUtil.polygonIntersection(selectedLand, availableLand);
    }

    // If the selected land is empty, try again
    return intersection.calculateArea2D() != 0 ? intersection :
      getSuitableLand(availableLand, surface);
  }
  
  private void getMapWidth() {
    if (mapWidth == 0) {
      mapWidth = HttpConnection.getInstance().execute("lists/settings/31/",
              CallType.GET, new JsonObjectResultHandler(), true).getInt("value");
    }
  }
  
  public boolean withinMargin(Polygon selectedLand, double surface) {
    return selectedLand.calculateArea2D() < surface * (1 + errorMargin) 
        && selectedLand.calculateArea2D() > surface * (1 - errorMargin);
  }
  
  /**
   * Returns true if the selected stakeholder can ask for money.
   * @return Can stakeholder ask for money.
   */
  public boolean canAskMoney() {
    for (Action action : actionList) {
      Map<Integer, Boolean> activeForStakeholder = action.getActiveForStakeholder();
      boolean active = activeForStakeholder.containsKey(stakeholderId) 
          ? activeForStakeholder.get(stakeholderId) : false;
      if (action.getSpecialOptions().contains("SHOW_SUBSIDY_PANEL") && active) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Returns true if the selected stakeholder can give money to other stakeholders.
   * @return Can stakeholder give money.
   */
  public boolean canGiveMoney() {
    for (Action action : actionList) {
      Map<Integer, Boolean> activeForStakeholder = action.getActiveForStakeholder();
      boolean active = activeForStakeholder.containsKey(stakeholderId) 
          ? activeForStakeholder.get(stakeholderId) : false;
      if (action.getSpecialOptions().contains("SHOW_MONEY_TRANSFER_PANEL") && active) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Gets the current budget of the given stakeholder.
   * @param stakeholderId The stakeholder's id.
   * @return The current budget.
   */
  public double getBudget(int stakeholderId) {
    for (Indicator indicator : indicatorList) {
      if (indicator.getType().equals("FINANCE")) {
        IndicatorFinance indicatorFinance = (IndicatorFinance) indicator;
        if (indicatorFinance.getActorId() == stakeholderId) {
          return indicatorFinance.getCurrent();
        }
      }
    }
    return -1;
  }
}

