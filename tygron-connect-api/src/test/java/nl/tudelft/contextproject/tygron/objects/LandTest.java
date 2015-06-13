package nl.tudelft.contextproject.tygron.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.esri.core.geometry.Polygon;

import nl.tudelft.contextproject.tygron.CachedFileReader;
import nl.tudelft.contextproject.util.PolygonUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class LandTest {
  LandMap lands;

  /**
   * Creates a building from a cached file.
   */
  @Before
  public void setupLands() {
    String file = "/serverResponses/testmap/lists/land.json";
    String contents = CachedFileReader.getFileContents(file);
    JSONArray result = new JSONArray(contents);
    lands = new LandMap(result);
  }

  @Test
  public void idTest() {
    assertEquals(0, lands.get(0).getId());
  }
  
  @Test
  public void ownerIdTest() {
    assertEquals(2, lands.get(0).getOwnerId());
  }
  
  
  @Test
  public void polygonTest() {
    Polygon polygon1 = lands.get(0).getPolygon();
    try {
      Polygon polygon2 = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON (((19.462 86.494, 21.552 92.012,"
          + " 26.97 89.95, 24.879 84.432, 19.462 86.494)))");
      assertTrue(PolygonUtil.polygonEquals(polygon1, polygon2));
    } catch (Exception e) {
      fail();
      e.printStackTrace();
    }
  }
  
  @Test(expected = Exception.class)
  public void polyfailTest() {
    JSONObject jobject = new JSONObject("{\"id\":0,\"version\":1,\"name\":\"0\",\"ownerID\":2,\"polygons\":\"i\"}");
    @SuppressWarnings("unused")
    Land land = new Land(jobject);
  }
}
