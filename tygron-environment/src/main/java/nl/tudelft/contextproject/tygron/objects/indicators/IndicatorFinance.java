package nl.tudelft.contextproject.tygron.objects.indicators;

import org.json.JSONObject;

/**
 * TygronIndicatorFinance indicates the budget.
 *
 */
public class IndicatorFinance extends Indicator {
  private double target;
  private double currentBudget;
  
  /**
   * Constructs a TygronIndicatorFinance from a JSONObject.
   * @param input the input object
   */
  public IndicatorFinance(JSONObject input) {
    super(input);
    target = input.getJSONObject("targets").getJSONArray("0").getDouble(0);
    currentBudget = input.getJSONObject("exactActorValues").getDouble("CURRENT");
  }

  @Override
  public double getTarget() {
    return target;
  }

  @Override
  public double getProgress() {
    return currentBudget / target;
  }

  @Override
  public double getCurrent() {
    return currentBudget;
  }
}
