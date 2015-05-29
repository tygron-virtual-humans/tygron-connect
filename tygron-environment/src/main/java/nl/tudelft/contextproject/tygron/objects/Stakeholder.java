package nl.tudelft.contextproject.tygron.objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stakeholder {
  private int id;
  private String name;
  private String shortName;
  private String description;
  
  private List<Integer> ownedLands;
  private List<Integer> allowedFunctions;
  
  private Map<Integer, Double> indicatorWeights;
  
  /**
   * Constructs a TygronStakeholder from an API response.
   * @param input
   *            A response retrieved from Tygron
   */
  public Stakeholder(JSONObject input) {
    id = input.getInt("id");
    name = input.getString("name");
    shortName = input.getString("shortName");
    description = input.getString("description");
    
    indicatorWeights = new HashMap<Integer, Double>();
    JSONArray currentIndicatorWeights = input.getJSONArray("currentIndicatorWeights");
    for (int i = 0; i < currentIndicatorWeights.length(); i++) {
      JSONObject indicator = currentIndicatorWeights.getJSONObject(i);
      indicatorWeights.put(indicator.getInt("indicatorID"), indicator.getDouble("weight"));
    }
    
    ownedLands = new ArrayList<Integer>();
    JSONArray lands = input.getJSONArray("ownedLands");
    for (int i = 0; i < lands.length(); i++) {
      ownedLands.add(lands.getInt(i));
    }
    
    allowedFunctions = new ArrayList<Integer>();
  }
  
  /**
   * Add functions from array to list.
   * @param array The array containing the functions.
   */
  public void addAllowedFunctions(JSONArray array) {
    for (int i = 0; i < array.length(); i++) {
      allowedFunctions.add(array.getInt(i));
    }
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getShortName() {
    return shortName;
  }

  public String getDescription() {
    return description;
  }

  public List<Integer> getOwnedLands() {
    return ownedLands;
  }

  public Map<Integer, Double> getIndicatorWeights() {
    return indicatorWeights;
  }
  
  @Override
  public String toString() {
    JSONObject str = new JSONObject();
    str.put("name", this.name);
    str.put("shortName", this.shortName);
    str.put("id", this.id);
    return str.toString();
  }
  
}
