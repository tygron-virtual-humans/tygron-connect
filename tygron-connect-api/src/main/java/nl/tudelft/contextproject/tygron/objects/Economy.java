package nl.tudelft.contextproject.tygron.objects;

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
    id = input.getInt("id");
    category = input.getString("category");
    state = input.getString("state"); 
  }
  
  public int getId() {
    return id;
  }
  
  public String getCategory() {
    return category;
  }
  
  public String getState() {
    return state;
  }
}
