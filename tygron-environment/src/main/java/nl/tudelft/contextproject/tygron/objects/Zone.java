package nl.tudelft.contextproject.tygron.objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Zone {

  private int allowedFloors;
  private List<Object> allowedFunctions;
  private List<Double> categoryFloorM2s;
  private List<Double> categoryFloorM2sMaquette;
  private List<Double> categoryM2s;
  private List<Double> categoryM2sMaquette;
  private int categoryVersion;
  private int categoryVersionMaquette;
  private int color;
  private String description;
  private List<Integer> detailVersions;
  private List<List<Double>> details;
  private int id;
  private String name;
  private JSONObject playable;
  // private ? polygons;
  private boolean showlabel;
  private int sortIndex;
  private int version;

  /**
   * Extract zone settings from zone.
   */
  public Zone(JSONObject object) {
    JSONObject zone = object.getJSONObject("Zone");
    allowedFloors = zone.getInt("allowedFloors");

    allowedFunctions = new ArrayList<Object>();
    JSONArray array = zone.getJSONArray("allowedFunctions");
    for (int i = 0; i < array.length(); i++) {
      allowedFunctions.add(array.get(i));
    }
    categoryFloorM2s = new ArrayList<Double>();
    array = zone.getJSONArray("categoryFloorM2s");
    for (int i = 0; i < array.length(); i++) {
      categoryFloorM2s.add(array.getDouble(i));
    }
    categoryFloorM2sMaquette = new ArrayList<Double>();
    array = zone.getJSONArray("categoryFloorM2sMaquette");
    for (int i = 0; i < array.length(); i++) {
      categoryFloorM2sMaquette.add(array.getDouble(i));
    }
    categoryM2s = new ArrayList<Double>();
    array = zone.getJSONArray("categoryM2s");
    for (int i = 0; i < array.length(); i++) {
      categoryM2s.add(array.getDouble(i));
    }
    categoryM2sMaquette = new ArrayList<Double>();
    array = zone.getJSONArray("categoryM2sMaquette");
    for (int i = 0; i < array.length(); i++) {
      categoryM2sMaquette.add(array.getDouble(i));
    }

    categoryVersion = zone.getInt("categoryVersion");
    categoryVersionMaquette = zone.getInt("categoryVersionMaquette");
    color = zone.getJSONObject("color").getInt("rgba");
    description = zone.getString("description");

    detailVersions = new ArrayList<Integer>();
    array = zone.getJSONArray("detailVersions");
    for (int i = 0; i < array.length(); i++) {
      detailVersions.add(array.getInt(i));
    }
    ;
    details = new ArrayList<List<Double>>();
    array = zone.getJSONArray("details");
    for (int i = 0; i < array.length(); i++) {
      List<Double> innerList = new ArrayList<Double>();
      JSONArray array2 = array.getJSONArray(i);
      for (int j = 0; j < array2.length(); j++) {
        innerList.add(array2.getDouble(j));
      }
      details.add(innerList);
    }
    ;

    id = zone.getInt("id");
    name = zone.getString("name");

    playable = zone.getJSONObject("playable");
    // polygons;

    showlabel = zone.getBoolean("showLabel");
    sortIndex = zone.getInt("sortIndex");
    version = zone.getInt("version");
  }

  public int getAllowedFloors() {
    return allowedFloors;
  }

  public List<Object> getAllowedFunctions() {
    return allowedFunctions;
  }

  public List<Double> getCategoryFloorM2s() {
    return categoryFloorM2s;
  }

  public List<Double> getCategoryFloorM2sMaquette() {
    return categoryFloorM2sMaquette;
  }

  public List<Double> getCategoryM2s() {
    return categoryM2s;
  }

  public List<Double> getCategoryM2sMaquette() {
    return categoryM2sMaquette;
  }

  public int getCategoryVersion() {
    return categoryVersion;
  }

  public int getCategoryVersionMaquette() {
    return categoryVersionMaquette;
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

}
