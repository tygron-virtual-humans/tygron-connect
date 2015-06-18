package nl.tudelft.contextproject.tygron.objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Holds buildings.
 */
public class BuildingList extends ArrayList<Building> {
  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 1L;

  public BuildingList() {

  }

  /**
   * Constructs a building list from a tygron response.
   * @param input input response
   */
  public BuildingList(JSONArray input) {
    for (int i = 0; i < input.length(); i++) {
      JSONObject buildingWrapper = input.getJSONObject(i);
      JSONObject buildingObj = buildingWrapper.getJSONObject("Building");
      Building building = new Building(buildingObj);
      this.add(building);
    }
  }
  
  /**
   * Gets the building with the provided id.
   * @param id The id.
   * @return The building with the provided id.
   */
  public Building getId(int id) {
    for (Building building : this) {
      if (building.getId() == id) {
        return building;
      }
    }
    return null;
  }
}
