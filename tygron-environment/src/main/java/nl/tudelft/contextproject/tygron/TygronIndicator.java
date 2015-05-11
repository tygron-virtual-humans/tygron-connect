package nl.tudelft.contextproject.tygron;

import org.json.JSONObject;

/**
 * TygronIndicator is a class that contains indicator data.
 *
 */
public abstract class TygronIndicator {
  private String name;
  private String shortName;
  private String type;
  private String descriptionHtml;
  private String explanationHtml;
  
  /**
   * Constructs a TygronIndicator.
   * @param input Server response
   */
  public TygronIndicator(JSONObject input) {
    name = input.getString("name");
    shortName = input.getString("shortName");
    type = input.getString("type");
    descriptionHtml = input.getString("description");
    explanationHtml = input.getString("explanation");
  }
  
  public String getName() {
    return name;
  }
  
  public String getShortName() {
    return shortName;
  }
  
  public String getType() {
    return type;
  }
  
  public String getDescriptionHtml() {
    return descriptionHtml;
  }
  
  public String getExplanationHtml() {
    return explanationHtml;
  }
}
