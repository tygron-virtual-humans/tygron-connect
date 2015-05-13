package nl.tudelft.contextproject.tygron;

import org.json.JSONObject;

/**
 * State of economies.
 *
 */
public class Economies {
  private String category;
  private String state;
  
  /**
   * Constructs a Economies from a JSONObject.
   * @param input the input object
   */
  public Economies(JSONObject input) {
    category = input.getString("category");
    state = input.getString("state");
  }
  
  public String getCategory() {
    return category;
  }
  
  public String getState() {
    return state;
  }
}
