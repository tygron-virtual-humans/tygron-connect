package nl.tudelft.contextproject.tygron.objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Stakeholder is a Tygron object which symbolizes a player.
 */
public class Stakeholder {
  private int id;
  private String name;
  private String shortName;
  private String description;
  private String type;
  
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
    type = input.getString("type");
    
    indicatorWeights = new HashMap<>();
    JSONArray currentIndicatorWeights = input.getJSONArray("currentIndicatorWeights");
    for (int i = 0; i < currentIndicatorWeights.length(); i++) {
      JSONObject indicator = currentIndicatorWeights.getJSONObject(i);
      indicatorWeights.put(indicator.getInt("indicatorID"), indicator.getDouble("weight"));
    }
    
    ownedLands = new ArrayList<>();
    JSONArray lands = input.getJSONArray("ownedLands");
    for (int i = 0; i < lands.length(); i++) {
      ownedLands.add(lands.getInt(i));
    }
    
    allowedFunctions = new ArrayList<>();
  }
  
  /**
   * Add functions from array to list.
   * @param array The array containing the functions.
   */
  public void addAllowedFunctions(List<Integer> array) {
    for (Integer functionId : array) {
      allowedFunctions.add(functionId);
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
  
  public String getType() {
    return type;
  }

  public List<Integer> getOwnedLands() {
    return ownedLands;
  }

  public Map<Integer, Double> getIndicatorWeights() {
    return indicatorWeights;
  }

  public List<Integer> getAllowedFunctions() {
    return allowedFunctions;
  }
}
