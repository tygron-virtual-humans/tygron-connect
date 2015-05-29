package nl.tudelft.contextproject.tygron.objects.indicators;

import org.json.JSONObject;

public class SubIndicatorHousing extends Indicator {

  private double desired;
  private double current;
  
  /**
   * Constructs a SubIndicator of the housing.
   * @param input The JSON input
   * @param desired The target
   * @param current The current value
   */
  public SubIndicatorHousing(JSONObject input, double desired, double current) {
    super(input);
    
    this.desired = desired;
    this.current = current;
  }

  @Override
  public double getProgress() {
    return current / desired;
  }

  @Override
  public double getTarget() {
    return desired;
  }

  @Override
  public double getCurrent() {
    return current;
  }

}
