package nl.tudelft.contextproject.tygron.objects;

import com.esri.core.geometry.Polygon;

import nl.tudelft.contextproject.util.PolygonUtil;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Land {
  
  private static final Logger logger = LoggerFactory.getLogger(Land.class);
  
  private int id;
  private int ownerId;
  private Polygon polygon;
  
  /**
   * Constructs a land object from a JSONObject.
   * @param land The JSONObject.
   */
  public Land(JSONObject land) {
    id = land.getInt("id");
    ownerId = land.getInt("ownerID");
    try {
      polygon = PolygonUtil.createPolygonFromWkt(land.getString("polygons"));
    } catch (Exception e) {
      logger.info("Error parsing Land with string " + land.toString());
      throw new RuntimeException(e);
    }
  }

  public int getId() {
    return id;
  }

  public int getOwnerId() {
    return ownerId;
  }

  public Polygon getPolygon() {
    return polygon;
  }
}
