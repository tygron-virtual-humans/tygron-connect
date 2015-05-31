package nl.tudelft.contextproject.tygron.objects;

import org.json.JSONObject;

public class Function {
  
  private int id;
  private int version;
  private int maxFloors;
  private int minFloors;
  private String name;
  
  /**
   * Constructs a Function from a JSONObject.
   * @param function The input object
   */
  public Function(JSONObject function) {
    id = function.getInt("id");
    version = function.getInt("version");
    name = function.getString("name");
    
    JSONObject functionValues = function.getJSONObject("functionValues");
    maxFloors = functionValues.getInt("MAX_FLOORS");
    minFloors = functionValues.getInt("MIN_FLOORS");
  }

  public int getId() {
    return id;
  }

  public int getVersion() {
    return version;
  }

  public int getMax_floors() {
    return maxFloors;
  }

  public int getMin_floors() {
    return minFloors;
  }

  public String getName() {
    return name;
  }
}
