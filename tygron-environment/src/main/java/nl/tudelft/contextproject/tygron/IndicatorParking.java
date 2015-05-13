package nl.tudelft.contextproject.tygron;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * TygronIndicatorParking indicates what the ratio between parking and living space is.
 *
 */
public class IndicatorParking extends Indicator {
  double targetParking;
  double currentParking;

  /**
   * Constructs a TygronIndicatorParking from a JSONObject.
   * @param input the input object
   */
  public IndicatorParking(JSONObject input) {
    super(input);

    JSONArray maquetteDeltaScores = input.getJSONArray("maquetteDeltaScores");
    assert maquetteDeltaScores.length() % 2 == 0;
    int targetParkingIndex = maquetteDeltaScores.length() / 2 - 1;
    int currentParkingIndex = maquetteDeltaScores.length() - 1;
    
    targetParking = maquetteDeltaScores.getDouble(targetParkingIndex);
    currentParking = maquetteDeltaScores.getDouble(currentParkingIndex);
  }
}
