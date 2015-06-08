package nl.tudelft.contextproject.tygron.api.actions;

import com.esri.core.geometry.Polygon;

import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.Environment;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.api.Session;
import nl.tudelft.contextproject.tygron.handlers.StringResultHandler;
import nl.tudelft.contextproject.tygron.objects.Function;
import nl.tudelft.contextproject.tygron.objects.Stakeholder;
import nl.tudelft.contextproject.util.PolygonUtil;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BuildAction {
  
  private static final Logger logger = LoggerFactory.getLogger(BuildAction.class);
  
  private Session session;
  private Environment environment;
  
  public BuildAction(Session session) {
    this.session = session;
    this.environment = session.getEnvironment();
  }
  
  /**
   * Builds a project on a piece of land.
   * @param surface The desired surface of the building.
   * @param type The type of the building project. 0 for housing, 1 for park, 2 for parking lots.
   * @return Whether the build request was sent or not.
   */
  public boolean build(double surface, int type) {
    Stakeholder stakeholder = environment.getStakeholders().get(environment.getStakeholderId());
    logger.debug("Building project started");
    Polygon availableLand = environment.getAvailableLand(stakeholder);
    double availableSurface = availableLand.calculateArea2D();
    
    int minFloors = getMinFloors(availableLand, surface);
    int maxFloors = getMaxFloors(stakeholder, type);
    boolean enoughFloors = minFloors <= maxFloors;
    if (maxFloors == 0) {
      logger.info("Stakeholder " + stakeholder.getId() + " can't build type " + type);
      return false;
    } else if (!enoughFloors) {
      logger.info("Not enough land for building");
      return false;
    }
    
    Function function = getFunction(stakeholder, minFloors, type);
    
    // Select a random amount of floors
    int neededFloors = minFloors;
    if (minFloors != function.getMax_floors()) {
      Random random = new Random();
      neededFloors = random.nextInt(function.getMax_floors() - minFloors) + minFloors;
    }
    double neededSurface = surface / neededFloors;
    
    Polygon selectedLand;
    if (environment.withinMargin(availableLand, neededSurface)) {
      // If the available land is already the required size, select all of it
      selectedLand = availableLand;
    } else if (availableSurface < 5) {
      // The available land is small, so fill it entirely
      selectedLand = availableLand;
      neededFloors = (int) Math.ceil(surface / availableSurface);
    } else {
      // Otherwise, take a random piece of land
      selectedLand = environment.getSuitableLand(availableLand, neededSurface);
    }
    
    if (selectedLand != null) {
      BuildRequest buildRequest = new BuildRequest(stakeholder, 
          function, neededFloors, selectedLand);
      HttpConnection.getInstance().execute("event/PlayerEventType/BUILDING_PLAN_CONSTRUCTION/",
              CallType.POST, new StringResultHandler(), session, buildRequest);
      environment.loadBuildings();
      return true;
    } else {
      logger.info("Not enough land for building");
      return false;
    }
  }
  
  class BuildRequest extends JSONArray {
    public BuildRequest(Stakeholder stakeholder, Function function, int floors, Polygon polygon) {
      this.put(stakeholder.getId());
      this.put(function.getId());
      this.put(floors);
      this.put(PolygonUtil.toString(polygon));
    }
  }
  
  /**
   * Gets the minimum amount of floors necessary to get the desires surface.
   * @return The minimum amount of floors.
   */
  private int getMinFloors(Polygon selectedLand, double surface) {
    return (int) Math.ceil(surface / selectedLand.calculateArea2D());
  }
  
  /**
   * Gets the maximum amount of floors possible in the stakeholder's functions.
   * @param stakeholder The stakeholder initiating the building project.
   * @param type The type of building project.
   * @return The minimum amount of floors.
   */
  private int getMaxFloors(Stakeholder stakeholder, int type) {
    List<Integer> functions = stakeholder.getAllowedFunctions();
    int result = 0;
    for (int functionId : functions) {
      Function function = environment.getFunctions().get(functionId);
      if (function.isRightType(type)) {
        int maxFloors = function.getMax_floors();
        result = max(maxFloors, result);
      }
    }
    return result;
  }
  
  /**
   * Gets a function that fits the requested amount of floors.
   * @param stakeholder The stakeholder.
   * @param type The type of building project.
   * @param floors The requested amount of floors.
   * @return The function that fits the criteria.
   */
  private Function getFunction(Stakeholder stakeholder, int floors, int type) {
    List<Integer> functions = stakeholder.getAllowedFunctions();
    List<Function> functionList = new ArrayList<>();
    for (Integer functionId : functions) {
      Function function = environment.getFunctions().get(functionId);
      if (function.hasEnoughFloors(floors) && function.isRightType(type)) {
        functionList.add(function);
      }
    }
    
    // Pick a random function
    Random random = new Random();
    return functionList.get(random.nextInt(functionList.size()));
  }
  
  private int max(int i1, int i2) {
    if (i1 > i2) {
      return i1;
    }
    return i2;
  }
}
