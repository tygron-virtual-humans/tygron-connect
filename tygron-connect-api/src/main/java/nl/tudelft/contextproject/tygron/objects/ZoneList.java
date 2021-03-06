package nl.tudelft.contextproject.tygron.objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * List with Zones.
 *
 */
public class ZoneList extends ArrayList<Zone> {
  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructs a ZoneList from a tygron response.
   * @param input input response
   */
  public ZoneList(JSONArray input) {
    for (int i = 0; i < input.length(); i++) {
      JSONObject zoneWrapper = input.getJSONObject(i);
      JSONObject zoneObject = zoneWrapper.getJSONObject("Zone");
      Zone zone = new Zone(zoneObject);
      this.add(zone);
    }
  }
}
