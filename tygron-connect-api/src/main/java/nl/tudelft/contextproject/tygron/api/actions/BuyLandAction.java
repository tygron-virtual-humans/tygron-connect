package nl.tudelft.contextproject.tygron.api.actions;

import com.esri.core.geometry.Polygon;

import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.Environment;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.handlers.StringResultHandler;
import nl.tudelft.contextproject.tygron.objects.LandMap;
import nl.tudelft.contextproject.tygron.objects.Stakeholder;
import nl.tudelft.contextproject.tygron.objects.StakeholderList;
import nl.tudelft.contextproject.util.PolygonUtil;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * A BuyLandAction buys a piece of land.
 */
public class BuyLandAction {
  
  private static final Logger logger = LoggerFactory.getLogger(BuyLandAction.class);
  
  private Environment environment;
  
  public BuyLandAction(Environment environment) {
    this.environment = environment;
  }
  
  /**
   * Buys a piece of land.
   * @param surface The desired surface of the land.
   * @param cost The amount of money per unit of land.
   * @return Whether the action was a success.
   */
  public boolean buyLand(double surface, double cost) {
    logger.debug("Buying land");
    
    List<Polygon> availableLandList = getBuyableLand();
    Polygon availableLand = PolygonUtil.polygonUnion(availableLandList);
    
    if (availableLand.isEmpty() || availableLand.calculateArea2D() < 0.05) {
      logger.info("No land available for buying");
      return false;
    } else if (availableLand.calculateArea2D() < surface) {
      logger.info("Not enough land available for buying");
      return false;
    }
    
    Polygon suitableLand = environment.getSuitableLand(availableLand, surface);
    
    // Split the land per landowner.
    List<Polygon> splitLand = new ArrayList<>();
    for (Polygon polygon : availableLandList) {
      splitLand.add(PolygonUtil.polygonIntersection(polygon, suitableLand));
    }
    
    Stakeholder buyer = environment.get(StakeholderList.class).get(environment.getStakeholderId());
    for (Polygon landPiece : splitLand) {
      BuyLandRequest buyLandRequest = new BuyLandRequest(buyer, landPiece, cost);
      HttpConnection.getInstance().execute("event/PlayerEventType/MAP_BUY_LAND/",
              CallType.POST, new StringResultHandler(), true, buyLandRequest);
    }
    environment.get(LandMap.class);
    return true;
  }
  
  static class BuyLandRequest extends JSONArray {
    public BuyLandRequest(Stakeholder buyer, Polygon polygon, double cost) {
      this.put(buyer.getId());
      this.put(PolygonUtil.toString(polygon));
      this.put(cost);
    }
  }
  
  /**
   * Gets all available lands not owned by the selected stakeholder.
   * @return Land that can be bought.
   */
  private List<Polygon> getBuyableLand() {
    List<Polygon> result = new ArrayList<Polygon>();
    for (Stakeholder stakeholder : environment.get(StakeholderList.class)) {
      Polygon land = new Polygon();
      if (stakeholder.getId() != environment.getStakeholderId()) {
        Polygon stakeholderLand = environment.getAvailableLand(stakeholder);
        logger.debug(PolygonUtil.toString(stakeholderLand));
        land = PolygonUtil.polygonUnion(land, stakeholderLand);
      }
      result.add(land);
    }
    return result;
  }
}
