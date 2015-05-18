package nl.tudelft.contextproject.util;

import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.OperatorContains;
import com.esri.core.geometry.OperatorEquals;
import com.esri.core.geometry.OperatorImportFromWkt;
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
   * @throws JsonParseException
   *           Exception thrown if JSON is not in right format.
   * @throws IOException
   *           Exception thrown if other IOException is encountered.
   */
  public Polygon createPolygonFromWkt(String wktString)
      throws JsonParseException, IOException {

    Geometry geom = OperatorImportFromWkt.local().execute(
        WktImportFlags.wktImportDefaults, Geometry.Type.Polygon, wktString,
        null);

    return (Polygon) geom;
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
  public boolean contains(Polygon polygon1, Polygon polygon2) {
    SpatialReference sr = SpatialReference.create(1);

    return OperatorContains.local().execute(polygon1, polygon2, sr, null);
  }
  
  /**
   * Returns if the polygons are equal.
   * @param polygon1 Comparing polygon1.
   * @param polygon2 Comparing polygon2.
   * @return Boolean that shows whether polygon1 equals polygon2.
   */
  public boolean equals(Polygon polygon1, Polygon polygon2) {
    SpatialReference sr = SpatialReference.create(1);

    return OperatorEquals.local().execute(polygon1, polygon2, sr, null);
  }
}
