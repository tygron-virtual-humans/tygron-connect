package nl.tudelft.contextproject.tygron;

import org.json.JSONObject;

/**
 * State of economies.
 *
 */
public class Economy {
  
  private int id;
  private String category;
  private String state;
  
  /**
   * Constructs a Economies from a JSONObject.
   * @param input the input object
   */
  public Economy(JSONObject input) {
    JSONObject object = input.getJSONObject("Economy");
    
    id = object.getInt("id");
    category = object.getString("category");
    state = object.getString("state"); 
  }
  
  public String getCategory() {
    return category;
  }
  
  public String getState() {
    return state;
  }
  
  @Override
  public String toString(){
    JSONObject str = new JSONObject();
    str.put("id", this.id);
    str.put("category", this.category);
    str.put("state", this.state);
    
    return str.toString();
  }  
}
