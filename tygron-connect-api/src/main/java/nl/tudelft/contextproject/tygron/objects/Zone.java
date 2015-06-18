package nl.tudelft.contextproject.tygron.objects;

import com.esri.core.geometry.Polygon;

import nl.tudelft.contextproject.util.PolygonUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Zone {

  private int allowedFloors;
  private List<Object> allowedFunctions;
  private int categoryVersion;
  private int color;
  private String description;
  private List<Integer> detailVersions;
  private List<List<Double>> details;
  private int id;
  private String name;
  private JSONObject playable;
  private boolean showlabel;
  private int sortIndex;
  private int version;
  private Polygon polygon;

  /**
   * Extract zone settings from zone.
   * @param zone the json object with data
   */
  public Zone(JSONObject zone) {
    allowedFloors = zone.getInt("allowedFloors");

    allowedFunctions = new ArrayList<>();
    JSONArray array = zone.getJSONArray("allowedFunctions");
    for (int i = 0; i < array.length(); i++) {
      allowedFunctions.add(array.get(i));
    }

    categoryVersion = zone.getInt("categoryVersion");
    color = zone.getJSONObject("color").getInt("rgba");
    description = zone.getString("description");

    detailVersions = new ArrayList<>();
    array = zone.getJSONArray("detailVersions");
    for (int i = 0; i < array.length(); i++) {
      detailVersions.add(array.getInt(i));
    }

    details = new ArrayList<>();
    array = zone.getJSONArray("details");
    for (int i = 0; i < array.length(); i++) {
      List<Double> innerList = new ArrayList<>();
      JSONArray array2 = array.getJSONArray(i);
      for (int j = 0; j < array2.length(); j++) {
        innerList.add(array2.getDouble(j));
      }
      details.add(innerList);
    }

    id = zone.getInt("id");
    name = zone.getString("name");

    playable = zone.getJSONObject("playable");

    showlabel = zone.getBoolean("showLabel");
    sortIndex = zone.getInt("sortIndex");
    version = zone.getInt("version");
    polygon = PolygonUtil.createPolygonFromWkt(zone.getString("polygons"));
  }

  public int getAllowedFloors() {
    return allowedFloors;
  }

  public List<Object> getAllowedFunctions() {
    return allowedFunctions;
  }

  public int getCategoryVersion() {
    return categoryVersion;
  }

  public int getColor() {
    return color;
  }

  public String getDescription() {
    return description;
  }

  public List<Integer> getDetailVersions() {
    return detailVersions;
  }

  public List<List<Double>> getDetails() {
    return details;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public JSONObject getPlayable() {
    return playable;
  }

  public boolean isShowlabel() {
    return showlabel;
  }

  public int getSortIndex() {
    return sortIndex;
  }

  public int getVersion() {
    return version;
  }
  
  public Polygon getPolygon() {
    return polygon;
  }

}
