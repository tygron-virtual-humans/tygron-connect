package nl.tudelft.contextproject.tygron;

import com.esri.core.geometry.Polygon;

import nl.tudelft.contextproject.util.PolygonUtil;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Building {
  
  final Logger logger = LoggerFactory.getLogger(Building.class);
  
  private int id;
  private String name;
  private Polygon polygon;
  private int floors;
  
  /**
   * Constructs Buildings from a JSONObject.
   * @param input A JSONObject containing building information.
   */
  public Building(JSONObject input) {
    PolygonUtil polyUtil = new PolygonUtil();
    name = input.getString("name");
    id = input.getInt("id");
    
    try {
      polygon = polyUtil.createPolygonFromWkt(input.getString("polygons"));
    } catch (Exception e) {
      throw new RuntimeException("Error parsing Building with string " + input.toString());
    }
    floors = input.getInt("floors");
  }

  /**
   * Get the buildings id.
   * @return Building id.
   */
  public int getId() {
    return id;
  } 
  
  /**
   * Get the buildings name.
   * @return Building name.
   */
  public String getName() {
    return name;
  }
  
  /**
   * Get the polygon defining the building.
   * @return Building's polygon.
   */
  public Polygon getPolygon() {
    return polygon;
  }
  
  /**
   * Get the number of floors of the building.
   * @return Number of floors.
   */
  public int getFloors() {
    return floors;
  }
  
  @Override
  public String toString() {
    JSONObject str = new JSONObject();
    str.put("id", this.id);
    str.put("name", this.name);
    return str.toString();
  }
}
