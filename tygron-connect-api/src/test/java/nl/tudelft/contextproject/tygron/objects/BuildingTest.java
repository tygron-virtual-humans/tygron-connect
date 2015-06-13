package nl.tudelft.contextproject.tygron.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.esri.core.geometry.Polygon;

import nl.tudelft.contextproject.tygron.CachedFileReader;
import nl.tudelft.contextproject.tygron.api.Session;
import nl.tudelft.contextproject.tygron.objects.Building;
import nl.tudelft.contextproject.tygron.objects.BuildingList;
import nl.tudelft.contextproject.util.PolygonUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(value = MockitoJUnitRunner.class)
public class BuildingTest {
  BuildingList building;

  @Mock
  private Session sessionMock;

  /**
   * Creates a building from a cached file.
   */
  @Before
  public void setupBuilding() {
    String file = "/serverResponses/testmap/lists/building.json";
    String buildingContents = CachedFileReader.getFileContents(file);
    JSONArray buildingResult = new JSONArray(buildingContents);
    building = new BuildingList(buildingResult);
  }

  @Test
  public void nameTest() {
    assertEquals("Delfgauwseweg", building.get(0).getName());
  }

  @Test
  public void idTest() {
    assertEquals(0, building.get(0).getId());
  }

  
  @Test
  public void floorTest() {
    assertEquals(1, building.get(0).getFloors());
  }

  @Test
  public void polygonTest() {
    Polygon polygon1 = building.get(0).getPolygon();
    try {
      Polygon polygon2 = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON (((32.063 122.522"
          + ", 111.728 92.311, 166.819 69.234, 229.024 45.124, 226.367 38.006, 182 55.553"
          + ", 0 124.472, 0 134.572, 32.063 122.522)))");
      assertTrue(PolygonUtil.polygonEquals(polygon1, polygon2));
    } catch (Exception e) {
      fail();
      e.printStackTrace();
    }
  }
  
  @Test
  public void functionIdTest() {
    assertEquals(436, building.get(0).getFunctionId());
  }
  
  @Test
  public void stateTest() {
    assertEquals("READY", building.get(0).getState());
  }
  
  @Test
  public void demolishedTrueTest() {
    assertTrue(building.get(1).demolished());
  }
  
  @Test
  public void demolishedFalseTest() {
    assertFalse(building.get(0).demolished());
  }
  
  @Test(expected = Exception.class)
  public void polyfailTest() {
    JSONObject jobject = new JSONObject("{\"polygons\":99,\"name\":\"i\",\"id\":0,"
        + "\"state\":\"DEMOLISHED\",\"floors\":3,\"functionID\":21}");
    @SuppressWarnings("unused")
    Building building = new Building(jobject);
  }
  
  @Test
  public void getIndexTest() {
    assertNotNull(building.getId(0));
  }
  
  @Test
  public void getIndexFailTest() {
    assertNull(building.getId(1));
  }
}
