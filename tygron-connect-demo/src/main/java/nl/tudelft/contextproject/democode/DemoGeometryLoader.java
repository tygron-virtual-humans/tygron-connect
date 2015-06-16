package nl.tudelft.contextproject.democode;

import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.OperatorContains;
import com.esri.core.geometry.OperatorImportFromWkt;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.geometry.WktImportFlags;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoGeometryLoader {

  private DemoGeometryLoader() {
    // Static class
  }

  /**
   * Demonstrates the capabilities of the esri geometry library.
   * 
   * @param args
   *          Not used.
   */
  public static void main(String[] args) {

    final Logger logger = LoggerFactory.getLogger(DemoGeometryLoader.class);

    String container = "MULTIPOLYGON (((665.733 -329.601, -244.292 -23.896, "
        + "77.336 933.526, 987.36 627.82, 665.733 -329.601)))";
    String containee = "MULTIPOLYGON (((1000 1000 , 1000 -1000, -1000 -1000, "
        + "-1000 1000, 1000 1000)))";
    try {
      Polygon poly1 = createPolygonFromWkt(container);
      Polygon poly2 = createPolygonFromWkt(containee);
      logger.info("" + contains(poly1, poly2));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Returns if polygon1 contains polygon2.
   * 
   * @param polygon1
   *          The container.
   * @param polygon2
   *          The containee.
   * @return Boolean that shows whether polygon1 contains polygon2.
   */
  public static boolean contains(Polygon polygon1, Polygon polygon2) {
    SpatialReference sr = SpatialReference.create(1);

    return OperatorContains.local().execute(polygon1, polygon2, sr, null);
  }

  /**
   * Returns a polygon from the Well-known text format.
   * 
   * @param wktString
   *          A string in the Well-known text format containing polygon
   *          information.
   * @return Polygon created from the wkt data.
   */
  public static Polygon createPolygonFromWkt(String wktString) {
    Geometry geom;
    geom = OperatorImportFromWkt.local().execute(
        WktImportFlags.wktImportDefaults, Geometry.Type.Polygon, wktString,
        null);
    return (Polygon) geom;
  }
}
