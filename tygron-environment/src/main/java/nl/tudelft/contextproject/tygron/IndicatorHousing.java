package nl.tudelft.contextproject.tygron;

import org.json.JSONArray;
import org.json.JSONObject;

public class IndicatorHousing extends Indicator {
  double[] start;
  double[] current;
  double[] desired;

  /**
   * Constructs a TygronIndicatorHousing from a JSONObject.
   * @param input the input object
   */
  public IndicatorHousing(JSONObject input) {
    super(input);

    JSONObject targetsObj = input.getJSONObject("targets");
    assert targetsObj.keySet().size() == 1;
    JSONArray target = targetsObj.getJSONArray("0");

    JSONArray progress = input.getJSONArray("progress");
    assert progress.length() % 2 == 0;
    assert target.length() == progress.length() / 2;
    
    start = new double[target.length()];
    current = new double[target.length()];
    desired = new double[target.length()];
    for (int i = 0; i < target.length(); i++) {
      // we're not sure what this is but we suspect it's some sort of starting
      // value
      start[i] = progress.getDouble(i);
      current[i] = progress.getDouble(i * 2);
      desired[i] = target.getDouble(i);
    }
  }

}
