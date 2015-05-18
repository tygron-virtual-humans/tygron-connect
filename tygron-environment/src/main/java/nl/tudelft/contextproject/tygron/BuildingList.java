package nl.tudelft.contextproject.tygron;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BuildingList extends ArrayList<Building> {
  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructs a building list from a tygron response.
   * @param input
   * input response
   */
  public BuildingList(JSONArray input) {
    for (int i = 0; i < input.length(); i++) {
      JSONObject buildingWrapper = input.getJSONObject(i);
      JSONObject buildingObj = buildingWrapper.getJSONObject("Building");
      Building building = new Building(buildingObj);
      this.add(building);
    }
  }
}
