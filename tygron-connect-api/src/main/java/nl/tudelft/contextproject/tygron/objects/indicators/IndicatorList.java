package nl.tudelft.contextproject.tygron.objects.indicators;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class IndicatorList extends ArrayList<Indicator> {

  /**
   * Serial version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructs a TygronIndicatorList from a server response.
   * @param input The server response
   */
  public IndicatorList(JSONArray input) {
    for (int i = 0; i < input.length(); i++) {
      JSONObject indicatorWrapper = input.getJSONObject(i);
      JSONObject indicatorObj = null;
      
      if (indicatorWrapper.has("PersonalIndicator"))  {
        indicatorObj = indicatorWrapper.getJSONObject("PersonalIndicator");
      } else if (indicatorWrapper.has("GlobalIndicator"))  {
        indicatorObj = indicatorWrapper.getJSONObject("GlobalIndicator");
      }
      
      String type = indicatorObj.getString("type");
      switch (type) {
        case "PARKING":
        case "GREEN":
          this.add(new IndicatorParking(indicatorObj));
          break;
        case "HOUSING":
          IndicatorHousing houseIndicator = new IndicatorHousing(indicatorObj);
          this.addAll(houseIndicator.extractIndicators());
          break;
        case "FINANCE":
          this.add(new IndicatorFinance(indicatorObj));
          break;
        default:
          throw new RuntimeException("Unknown type " + type);
      }
    }
  }
}
