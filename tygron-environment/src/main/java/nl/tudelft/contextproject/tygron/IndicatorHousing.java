package nl.tudelft.contextproject.tygron;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IndicatorHousing {
  double[] start;
  double[] current;
  double[] desired;
  JSONObject jsonInput;

  /**
   * Constructs a IndicatorHousing from a JSONObject.
   * @param input the input object
   */
  public IndicatorHousing(JSONObject input) {
    jsonInput = input;
    
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

  /**
   * Extracts the individual indicators from housing.
   * @return a list of indicators
   */
  public Collection<? extends Indicator> extractIndicators() {
    List<Indicator> indicators = new ArrayList<Indicator>();
    for (int i = 0; i < start.length; i++) {
      if (desired[i] > 0) {
        indicators.add(new SubIndicatorHousing(jsonInput, 
            desired[i], current[i]));
      }
    }
    return indicators;
  }

}
