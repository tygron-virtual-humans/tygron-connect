package nl.tudelft.contextproject.tygron;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import nl.tudelft.contextproject.util.PolygonUtil;

import org.codehaus.jackson.JsonParseException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.esri.core.geometry.Polygon;

public class BuildingTest {
  DummyConnection connection;
  Building building;
  
  /**
   * Set up the mock connection and use building.json as mock json file.
   */
  @Before
  public void setupConnection() {
    connection = new DummyConnection();
    connection.setFile("/serverResponses/testmap/lists/building.json");
    JSONObject result = connection.callGetEventObject("");
    building = new Building(result);
  }
  
  @Test
  public void nameTest() {
    assertEquals("Delfgauwseweg",building.getName());
  }
  
  @Test
  public void floorTest() {
    assertEquals(1,building.getFloors());
  }
  
  @Test
  public void polygonTest() {
    PolygonUtil polyUtil = new PolygonUtil();
    Polygon polygon1 = building.getPolygon();
    try {
      Polygon polygon2 = polyUtil.createPolygonFromWkt("MULTIPOLYGON (((32.063 122.522"
          + ", 111.728 92.311, 166.819 69.234, 229.024 45.124, 226.367 38.006, 182 55.553"
          + ", 0 124.472, 0 134.572, 32.063 122.522)))");
      assertTrue(polyUtil.equals(polygon1, polygon2));
    } catch (JsonParseException e) {
      fail();
      e.printStackTrace();
    } catch (IOException e) {
      fail();
      e.printStackTrace();
    }
  }
}
