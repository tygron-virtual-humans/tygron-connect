package nl.tudelft.contextproject.util;

import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.OperatorContains;
import com.esri.core.geometry.OperatorDifference;
import com.esri.core.geometry.OperatorEquals;
import com.esri.core.geometry.OperatorExportToWkt;
import com.esri.core.geometry.OperatorImportFromWkt;
import com.esri.core.geometry.OperatorIntersection;
import com.esri.core.geometry.OperatorIntersects;
import com.esri.core.geometry.OperatorOverlaps;
import com.esri.core.geometry.OperatorUnion;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.geometry.WktImportFlags;

import org.codehaus.jackson.JsonParseException;

import java.io.IOException;

public class PolygonUtil {

  public PolygonUtil() {
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

    Geometry geom = OperatorImportFromWkt.local().execute(
        WktImportFlags.wktImportDefaults, Geometry.Type.Polygon, wktString,
        null);

    return (Polygon) geom;
  }
  
  /**
   * Returns true if polygon1 contains polygon2.
   * 
   * @param polygon1
   *          The container.
   * @param polygon2
   *          The containee.
   * @return Boolean that shows whether polygon1 contains polygon2.
   */
  public static boolean polygonContains(Polygon polygon1, Polygon polygon2) {
    SpatialReference sr = SpatialReference.create(1);
    return OperatorContains.local().execute(polygon1, polygon2, sr, null);
  }
  
  /**
   * Returns the difference of polygon1 and polygon2.
   * @param polygon1 Input polygon.
   * @param polygon2 Subtractor polygon.
   * @return Difference of polygon1 and polygon2.
   */
  public static Polygon polygonDifference(Polygon polygon1, Polygon polygon2) {
    SpatialReference sr = SpatialReference.create(1);
    return (Polygon) OperatorDifference.local().execute(polygon1, polygon2, sr, null);
  }
  
  /**
   * Returns the intersection of polygon1 and polygon2.
   * @param polygon1 Input polygon.
   * @param polygon2 Input polygon.
   * @return Intersection of polygon1 and polygon2.
   */
  public static Polygon polygonIntersection(Polygon polygon1, Polygon polygon2) {
    SpatialReference sr = SpatialReference.create(1);
    return (Polygon) OperatorIntersection.local().execute(polygon1, polygon2, sr, null);
  }
  
  /**
   * Returns the union of polygon1 and polygon2.
   * @param polygon1 Input polygon.
   * @param polygon2 Input polygon.
   * @return Union of polygon1 and polygon2.
   */
  public static Polygon polygonUnion(Polygon polygon1, Polygon polygon2) {
    SpatialReference sr = SpatialReference.create(1);
    return (Polygon) OperatorUnion.local().execute(polygon1, polygon2, sr, null);
  }
  
  /**
   * Returns if the polygons are equal.
   * @param polygon1 Comparing polygon1.
   * @param polygon2 Comparing polygon2.
   * @return Boolean that shows whether polygon1 equals polygon2.
   */
  public static boolean polygonEquals(Polygon polygon1, Polygon polygon2) {
    SpatialReference sr = SpatialReference.create(1);
    return OperatorEquals.local().execute(polygon1, polygon2, sr, null);
  }
  
  public static String toString(Polygon polygon) {
    return OperatorExportToWkt.local().execute(0, polygon, null);
  }
}
