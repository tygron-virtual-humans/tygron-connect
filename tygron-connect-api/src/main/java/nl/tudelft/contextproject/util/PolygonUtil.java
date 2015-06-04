package nl.tudelft.contextproject.util;

import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.OperatorContains;
import com.esri.core.geometry.OperatorDifference;
import com.esri.core.geometry.OperatorEquals;
import com.esri.core.geometry.OperatorExportToWkt;
import com.esri.core.geometry.OperatorImportFromWkt;
import com.esri.core.geometry.OperatorIntersection;
import com.esri.core.geometry.OperatorUnion;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.geometry.WktImportFlags;

import java.util.List;

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
   * Returns the union of all polygons in the list.
   * @param polygonList The list of polygons.
   * @return Union of all polygons.
   */
  public static Polygon polygonUnion(List<Polygon> polygonList) {
    Polygon result = new Polygon();
    for (Polygon polygon : polygonList) {
      result = polygonUnion(result, polygon);
    }
    return result;
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
  
  /**
   * Creates a rectangle shaped polygon from the given coordinates 
   *     which represent the two opposite corners.
   *     
   * @param x1 X coordinate of the first corner.
   * @param y1 Y coordinate of the first corner.
   * @param x2 X coordinate of the second corner.
   * @param y2 Y coordinate of the second corner.
   * @return Rectangle shaped polygon.
   */
  public static Polygon makeRectangle(double x1, double y1, double x2, double y2) {
    StringBuilder builder = new StringBuilder();
    builder.append("MULTIPOLYGON (((");
    builder.append(x1);
    builder.append(" ");
    builder.append(y1);
    builder.append(", ");
    builder.append(x1);
    builder.append(" ");
    builder.append(y2);
    builder.append(", ");
    builder.append(x2);
    builder.append(" ");
    builder.append(y2);
    builder.append(", ");
    builder.append(x2);
    builder.append(" ");
    builder.append(y1);
    builder.append(", ");
    builder.append(x1);
    builder.append(" ");
    builder.append(y1);
    builder.append(")))");
    return createPolygonFromWkt(builder.toString());
  }
  
  public static String toString(Polygon polygon) {
    return OperatorExportToWkt.local().execute(0, polygon, null);
  }
}
