package nl.tudelft.contextproject.tygron.api.actions;

import com.esri.core.geometry.Polygon;

import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.Environment;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.api.Session;
import nl.tudelft.contextproject.tygron.handlers.StringResultHandler;
import nl.tudelft.contextproject.tygron.objects.Stakeholder;
import nl.tudelft.contextproject.util.PolygonUtil;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BuyLandAction {
  
  private static final Logger logger = LoggerFactory.getLogger(BuyLandAction.class);
  
  private Session session;
  private Environment environment;
  
  public BuyLandAction(Session session) {
    this.session = session;
    this.environment = session.getEnvironment();
  }
  
  /**
   * Buys a piece of land.
   * @param surface The desired surface of the land.
   * @param cost The amount of money per unit of land.
   */
  public boolean buyLand(double surface, double cost) {
    logger.debug("Buying land");
    
    List<Polygon> availableLandList = getBuyableLand();
    Polygon availableLand = PolygonUtil.polygonUnion(availableLandList);
    
    if (availableLand.isEmpty()) {
      logger.info("No land available for buying");
      return false;
    } else if (availableLand.calculateArea2D() < surface) {
      logger.info("Not enough land available for buying");
      return false;
    }
    
    Polygon suitableLand = environment.getSuitableLand(availableLand, surface);
    
    // Split the land per landowner.
    List<Polygon> splitLand = new ArrayList<Polygon>();
    for (Polygon polygon : availableLandList) {
      splitLand.add(PolygonUtil.polygonIntersection(polygon, suitableLand));
    }
    
    Stakeholder buyer = environment.getStakeholders().get(environment.getStakeholderId());
    for (Polygon landPiece : splitLand) {
      BuyLandRequest buyLandRequest = new BuyLandRequest(buyer, landPiece, cost);
      HttpConnection.getInstance().execute("event/PlayerEventType/MAP_BUY_LAND/",
              CallType.POST, new StringResultHandler(), session, buyLandRequest);
    }
    return true;
  }
  
  class BuyLandRequest extends JSONArray {
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
    for (Stakeholder stakeholder : environment.getStakeholders()) {
      Polygon land = new Polygon();
      if (stakeholder.getId() != environment.getStakeholderId()) {
        land = PolygonUtil.polygonUnion(land, environment.getAvailableLand(stakeholder));
      }
      result.add(land);
    }
    return result;
  }
}
