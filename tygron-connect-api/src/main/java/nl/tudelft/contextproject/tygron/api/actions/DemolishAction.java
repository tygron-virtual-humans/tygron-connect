package nl.tudelft.contextproject.tygron.api.actions;

import com.esri.core.geometry.Polygon;

import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.Environment;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.handlers.StringResultHandler;
import nl.tudelft.contextproject.tygron.objects.Building;
import nl.tudelft.contextproject.tygron.objects.BuildingList;
import nl.tudelft.contextproject.tygron.objects.LandMap;
import nl.tudelft.contextproject.tygron.objects.Stakeholder;
import nl.tudelft.contextproject.tygron.objects.StakeholderList;
import nl.tudelft.contextproject.util.PolygonUtil;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemolishAction {
  
  private static final Logger logger = LoggerFactory.getLogger(DemolishAction.class);
  
  private Environment environment;
  
  public DemolishAction(Environment environment) {
    this.environment = environment;
  }
  
  /**
   * Demolishes a piece of land.
   * @param surface The desired surface of the land to demolish.
   * @return Whether the desired surface was demolished.
   */
  public boolean demolish(double surface) {
    logger.debug("Demolishing");
    
    Stakeholder stakeholder = environment.get(StakeholderList.class).get(environment.getStakeholderId());
    Polygon occupiedLand = getOccupiedLand(stakeholder);
    
    if (occupiedLand.isEmpty()) {
      logger.info("Nothing to demolish");
      return false;
    } else if (occupiedLand.calculateArea2D() < surface) {
      logger.info("Not enough available for demolishing");
      return false;
    }
    
    Polygon suitableLand = environment.getSuitableLand(occupiedLand, surface);
    
    DemolishRequest demolishRequest = new DemolishRequest(stakeholder, suitableLand);
    HttpConnection.getInstance().execute("event/PlayerEventType/BUILDING_PLAN_DEMOLISH_COORDINATES/",
            CallType.POST, new StringResultHandler(), true, demolishRequest);
    environment.reload(BuildingList.class);
    return true;
  }
  
  static class DemolishRequest extends JSONArray {
    public DemolishRequest(Stakeholder buyer, Polygon polygon) {
      this.put(buyer.getId());
      this.put(PolygonUtil.toString(polygon));
      this.put("SURFACE");
    }
  }
  
  /**
   * Get all of the stakeholder's land that contains buildings.
   * @param stakeholder The stakeholder.
   * @return The stakeholder's occupied land.
   */
  private Polygon getOccupiedLand(Stakeholder stakeholder) {
    Polygon owned = new Polygon();
    for (Integer landId : stakeholder.getOwnedLands()) {
      owned = PolygonUtil.polygonUnion(owned, environment.get(LandMap.class).get(landId).getPolygon());
    }

    logger.debug("Owned land: {}", PolygonUtil.toString(owned));
    
    Polygon occupied = new Polygon();
    for (Building building : environment.get(BuildingList.class)) {
      if (!building.demolished()) {
        occupied = PolygonUtil.polygonUnion(occupied, building.getPolygon());
      }
    }

    logger.debug("Occupied land: {}", PolygonUtil.toString(occupied));

    Polygon intersection = PolygonUtil.polygonIntersection(owned, occupied);
    logger.debug("Intersection land: {}", PolygonUtil.toString(intersection));
    return intersection;
  }
}
