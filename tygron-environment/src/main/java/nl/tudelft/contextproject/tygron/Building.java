package nl.tudelft.contextproject.tygron;

import com.esri.core.geometry.Polygon;
import nl.tudelft.contextproject.util.PolygonUtil;

import org.codehaus.jackson.JsonParseException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Building {
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
    try {
      polygon = polyUtil.createPolygonFromWkt(input.getString("polygons"));
    } catch (JsonParseException e) {
      System.out.println("There was a problem parsing JSON");
      e.printStackTrace();
    } catch (JSONException e) {
      System.out.println("There was a JSON Exception");
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("A IO Exception occurred");
      e.printStackTrace();
    }
    floors = input.getInt("floors");
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
}
