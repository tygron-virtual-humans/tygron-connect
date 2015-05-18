package nl.tudelft.contextproject.eis;

import static org.junit.Assert.assertFalse;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.esri.core.geometry.Polygon;

import nl.tudelft.contextproject.eis.democode.DemoGeometryLoader;

import org.junit.Test;

public class DemoGeometryLoaderTest {
  @Test
  public void notcontain_test() {
    String container = "MULTIPOLYGON (((665.733 -329.601, -244.292 -23.896, "
        + "77.336 933.526, 987.36 627.82, 665.733 -329.601)))";
    String containee = "MULTIPOLYGON (((1000 1000 , 1000 -1000, -1000 -1000, "
        + "-1000 1000, 1000 1000)))";
    try {
      Polygon poly1 = DemoGeometryLoader.createPolygonFromWkt(container);
      Polygon poly2 = DemoGeometryLoader.createPolygonFromWkt(containee);
      assertFalse(DemoGeometryLoader.contains(poly1, poly2));
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void contain_test() {
    String container = "MULTIPOLYGON (((665.733 -329.601, -244.292 -23.896, "
        + "77.336 933.526, 987.36 627.82, 665.733 -329.601)))";
    String containee = "MULTIPOLYGON (((665.733 -329.601, -244.292 -23.896, "
        + "77.336 933.526, 987.36 627.82, 665.733 -329.601)))";
    try {
      Polygon poly1 = DemoGeometryLoader.createPolygonFromWkt(container);
      Polygon poly2 = DemoGeometryLoader.createPolygonFromWkt(containee);
      assertTrue(DemoGeometryLoader.contains(poly1, poly2));
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }
}
