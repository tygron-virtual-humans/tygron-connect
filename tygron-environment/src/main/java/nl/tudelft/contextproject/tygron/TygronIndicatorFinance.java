package nl.tudelft.contextproject.tygron;

import org.json.JSONObject;

/**
 * TygronIndicatorFinance indicates the budget.
 *
 */
public class TygronIndicatorFinance extends TygronIndicator {
  private double target;
  private double currentBudget;
  
  /**
   * Constructs a TygronIndicatorFinance from a JSONObject.
   * @param input the input object
   */
  public TygronIndicatorFinance(JSONObject input) {
    super(input);
    target = input.getJSONObject("targets").getJSONArray("0").getDouble(0);
    currentBudget = input.getJSONObject("exactActorValues").getDouble("CURRENT");
  }

  public double getTarget() {
    return target;
  }

  public double getCurrentBudget() {
    return currentBudget;
  }
}
