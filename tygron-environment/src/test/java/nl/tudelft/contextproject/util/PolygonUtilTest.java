package nl.tudelft.contextproject.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.esri.core.geometry.Polygon;

import nl.tudelft.contextproject.util.PolygonUtil;

import org.junit.Test;



public class PolygonUtilTest {
  
  /**
   * Tests the wkt reader.
   */
  @Test
  public void readerTest() {
    try {
      final Polygon polygon1 = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON (((1 1, 2 2"
          + ", 3 3, 1 1)))");
      Polygon polygon2 = new Polygon();
      polygon2.startPath(1, 1);
      polygon2.lineTo(2, 2);
      polygon2.lineTo(3, 3);
      polygon2.lineTo(1, 1);
      PolygonUtil.polygonEquals(polygon1,polygon2);
    } catch (Exception e) {
      fail();
      e.printStackTrace();
    }
  }
  
  /**
   * Tests the equals function.
   */
  @Test
  public void equalsTrueTest() {
    try {
      Polygon polygon1 = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON (((1 1, 2 2, 3 3, 1 1)))");
      Polygon polygon2 = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON (((1 1, 2 2, 3 3, 1 1)))");
      assertTrue(PolygonUtil.polygonEquals(polygon1,polygon2));
    } catch (Exception e) {
      fail();
      e.printStackTrace();
    }
  }
  
  /**
   * Tests the equals function.
   */
  @Test
  public void equalsFalseTest() {
    try {
      Polygon polygon1 = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON (((1 1, 2 2, 3 3, 1 1)))");
      Polygon polygon2 = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON (((4 4, 2 2, 3 3, 4 4)))");
      assertFalse(PolygonUtil.polygonEquals(polygon1,polygon2));
    } catch (Exception e) {
      fail();
      e.printStackTrace();
    }
  }
  
  /**
   * Tests the contains function.
   */
  @Test
  public void containsTrueTest() {
    try {
      Polygon polygon1 = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON (((0 0, 0 16, 16 16"
          + ", 16 0, 0 0)))");
      Polygon polygon2 = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON (((4 4, 4 8, 8 8, 8 4"
          + ", 4 4)))");
      assertTrue(PolygonUtil.polygonContains(polygon1, polygon2));
    } catch (Exception e) {
      fail();
      e.printStackTrace();
    }
  }
  
  /**
   * Tests the contains function.
   */
  @Test
  public void containsFalseTest() {
    try {
      Polygon polygon1 = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON (((0 0, 0 16, 16 16"
          + ", 16 0, 0 0)))");
      Polygon polygon2 = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON (((4 4, 4 8, 8 8, 8 4"
          + ", 4 4)))");
      assertFalse(PolygonUtil.polygonContains(polygon2, polygon1));
    } catch (Exception e) {
      fail();
      e.printStackTrace();
    }
  }
  
  /**
   * Tests the difference function.
   */
  @Test
  public void differenceTest() {
    try {
      Polygon polygon1 = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON (((0 0, 0 16"
          + ", 16 16, 16 0, 0 0)))");
      Polygon polygon2 = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON (((1 1, 1 15"
          + ", 15 15, 15 1, 1 1)))");
      Polygon difference = PolygonUtil.polygonDifference(polygon1, polygon2);
      Polygon comparePolygon = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON (((0 0, 16 0"
          + ", 16 16, 0 16, 0 0), (1 1, 1 15, 15 15, 15 1, 1 1)))");
      assertTrue(PolygonUtil.polygonEquals(difference, comparePolygon));
    } catch (Exception e) {
      fail();
      e.printStackTrace();
    }
  }
}
