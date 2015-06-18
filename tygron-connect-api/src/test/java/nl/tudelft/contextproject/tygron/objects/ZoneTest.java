package nl.tudelft.contextproject.tygron.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.esri.core.geometry.Polygon;

import nl.tudelft.contextproject.tygron.CachedFileReader;
import nl.tudelft.contextproject.tygron.objects.ZoneList;
import nl.tudelft.contextproject.util.PolygonUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ZoneTest {
  ZoneList zoneList;

  /**
   * Initialises the zone test.
   */
  @Before
  public void setupZoneTest() {
    String file = "/serverResponses/testmap/lists/zone.json";
    String contents = CachedFileReader.getFileContents(file);
    JSONArray json = new JSONArray(contents);
    zoneList = new ZoneList(json);
  }

  @Test
  public void allowedFloorsTest() {
    assertEquals(5, zoneList.get(0).getAllowedFloors());
  }

  @Test
  public void allowedFunctionsEmptyTest() {
    assertTrue(zoneList.get(0).getAllowedFunctions().isEmpty());
  }
  
  @Test
  public void allowedFunctionsTest() {
    assertEquals("LUXE", zoneList.get(1).getAllowedFunctions().get(0));
  }

  @Test
  public void categoryVersionTest() {
    assertEquals(4, zoneList.get(0).getCategoryVersion());
  }

  @Test
  public void colorTest() {
    assertEquals(-7909992, zoneList.get(0).getColor());
  }

  @Test
  public void descriptionTest() {
    assertEquals("", zoneList.get(0).getDescription());
  }

  @Test
  public void detailVersionsTest() {
    ArrayList<Integer> ar = new ArrayList<>();
    ar.add(4);
    ar.add(4);
    assertTrue(zoneList.get(0).getDetailVersions().equals(ar));
  }

  @Test
  public void idTest() {
    assertEquals(5, zoneList.get(0).getId());
  }

  @Test
  public void nameTest() {
    assertEquals("Emerald", zoneList.get(0).getName());
  }

  @Test
  public void showlabelTest() {
    assertTrue(zoneList.get(0).isShowlabel());
  }

  @Test
  public void getSortIndexTest() {
    assertEquals(-1, zoneList.get(0).getSortIndex());
  }

  @Test
  public void versionTest() {
    assertEquals(4, zoneList.get(0).getVersion());
  }

  @Test
  public void playableTest() {
    JSONObject jobject = new JSONObject("{}");
    assertTrue(jobject.toString().equals(zoneList.get(0).getPlayable().toString()));
  }

  @Test
  public void getDetailsTest() {
    String ar = "[[1142.8457545049998, 4069.5895371893384, 3.154208828218336E8"
        + ", 3.6613757822604495E8, 3.3658121291559994E8, 3.347581316114003E8"
        + ", 224772.74781999996, 0.0, 133637.83486284115, 14749.698708972417"
        + ", -30458.617506266684, 576.7549986769894], [1142.8457545049998"
        + ", 4069.5895371893384, 3.154208828218336E8, 3.6613757822604495E8"
        + ", 3.3658121291559994E8, 3.347581316114003E8, 224772.74781999996"
        + ", 0.0, 133637.83486284115, 14749.698708972417" 
        + ", -30458.617506266684, 576.7549986769894]]";
    assertTrue(ar.equals(zoneList.get(0).getDetails().toString()));
  }

  @Test
  public void getCategoryVersionTest() {
    assertEquals(4, zoneList.get(0).getCategoryVersion());
  }
  
  @Test
  public void polygonTest() {
    Polygon polygon1 = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON (((665.733 -329.601, "
        + "-244.292 -23.896, 77.336 933.526, 987.36 627.82, 665.733 -329.601)))");
    Polygon polygon2 = zoneList.get(0).getPolygon();
    assertTrue(PolygonUtil.polygonEquals(polygon1, polygon2));
  }
}
