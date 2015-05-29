package nl.tudelft.contextproject.tygron.objects;

import org.json.JSONObject;

public class Land {
  
  private int id;
  private int ownerId;
  private String polygons;
  
  /**
   * Constructs a land object from a JSONObject.
   * @param input The JSONObject.
   */
  public Land(JSONObject input) {
    JSONObject land = input.getJSONObject("Land");
    id = land.getInt("id");
    ownerId = land.getInt("ownerID");
    polygons = land.getString("polygons");
  }

  public int getId() {
    return id;
  }

  public int getOwnerId() {
    return ownerId;
  }

  public String getPolygons() {
    return polygons;
  }
  
  @Override
  public String toString() {
    JSONObject str = new JSONObject();
    str.put("ownerID", this.ownerId);
    str.put("polygons", this.polygons);
    str.put("id", this.id);
    return str.toString();
  }
}
