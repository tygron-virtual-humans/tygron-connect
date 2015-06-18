package nl.tudelft.contextproject.tygron.api.actions;

import com.esri.core.geometry.Polygon;
import nl.tudelft.contextproject.tygron.api.Environment;
import nl.tudelft.contextproject.tygron.objects.*;
import nl.tudelft.contextproject.util.PolygonUtil;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LandTest {
  Polygon land1 = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON (((0 0, 0 16"
          + ", 16 16, 16 0, 0 0)))");
  Polygon land1part = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON (((0 0, 0 8"
          + ", 8 8, 8 0, 0 0)))");

  Polygon land2 = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON (((17 17, 17 34"
          + ", 34 34, 34 17, 17 17)))");
  Polygon land2part = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON (((17 17, 17 23"
          + ", 23 23, 23 17, 17 17)))");

  Polygon land3 = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON (((35 35, 35 70"
          + ", 70 70, 70 35, 35 35)))");
  Polygon land3part = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON (((35 35, 35 50"
          + ", 50 50, 50 35, 35 35)))");

  Polygon emptyLand = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON EMPTY");

  Stakeholder stakeholder1;
  Stakeholder stakeholder2;

  Building building;

  Environment getFakeEnvironment() {
    Environment mockEnvironment = mock(Environment.class);

    //Setup stakeholders
    stakeholder1 = mock(Stakeholder.class);
    when(stakeholder1.getId()).thenReturn(1);
    when(stakeholder1.getOwnedLands()).thenReturn(Arrays.asList(0));

    stakeholder2 = mock(Stakeholder.class);
    when(stakeholder2.getId()).thenReturn(2);

    StakeholderList list = new StakeholderList();
    list.add(stakeholder1);
    list.add(stakeholder2);
    when(mockEnvironment.get(eq(StakeholderList.class))).thenReturn(list);

    building = mock(Building.class);
    when(building.demolished()).thenReturn(false);
    when(building.getPolygon()).thenReturn(land3part);

    BuildingList buildingList = new BuildingList();
    buildingList.add(building);
    when(mockEnvironment.get(eq(BuildingList.class))).thenReturn(buildingList);

    Land land = mock(Land.class);
    when(land.getId()).thenReturn(0);
    when(land.getPolygon()).thenReturn(land3);
    when(land.getOwnerId()).thenReturn(0);

    LandMap landMap = new LandMap();
    landMap.put(0, land);

    when(mockEnvironment.get(LandMap.class)).thenReturn(landMap);

    return mockEnvironment;
  }

}
