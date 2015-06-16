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
import java.util.Random;

public class SellLandAction {
  
  private static final Logger logger = LoggerFactory.getLogger(SellLandAction.class);
  
  private Environment environment;
  
  public SellLandAction(Environment environment) {
    this.environment = environment;
  }
  
  /**
   * Sell a piece of land.
   * @param surface The desired surface of the land.
   * @param price The amount of money per unit of land.
   * @return Whether the request to sell was sent or not.
   */
  public boolean sellLand(double surface, double price) {
    logger.debug("Selling land");
    Stakeholder seller = environment.get(StakeholderList.class).get(environment.getStakeholderId());
    
    Polygon availableLand = environment.getAvailableLand(seller);
    
    if (availableLand.isEmpty()) {
      logger.info("No land available for selling");
      return false;
    } else if (availableLand.calculateArea2D() < surface) {
      logger.info("Not enough land available for selling");
      return false;
    }
    
    Polygon suitableLand = environment.getSuitableLand(availableLand, surface);
    
    List<Stakeholder> list = new ArrayList<>(environment.get(StakeholderList.class));
    list.remove(environment.getStakeholderId());
    Random random = new Random();
    Stakeholder buyer = list.get(random.nextInt(list.size()));
    
    SellLandRequest sellLandRequest = new SellLandRequest(seller, buyer, suitableLand, price);
    HttpConnection.getInstance().execute("event/PlayerEventType/MAP_SELL_LAND/",
            CallType.POST, new StringResultHandler(), true, sellLandRequest);
    environment.reload(LandMap.class);
    return true;
  }
  
  class SellLandRequest extends JSONArray {
    public SellLandRequest(Stakeholder buyer, Stakeholder seller, Polygon polygon, double price) {
      this.put(buyer.getId());
      this.put(seller.getId());
      this.put(PolygonUtil.toString(polygon));
      this.put(price);
    }
  }
}
