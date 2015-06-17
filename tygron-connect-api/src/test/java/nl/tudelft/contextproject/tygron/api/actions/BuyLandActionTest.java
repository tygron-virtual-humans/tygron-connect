package nl.tudelft.contextproject.tygron.api.actions;


import com.esri.core.geometry.Polygon;
import nl.tudelft.contextproject.tygron.api.Environment;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.objects.Stakeholder;
import nl.tudelft.contextproject.tygron.objects.StakeholderList;
import nl.tudelft.contextproject.util.PolygonUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HttpConnection.class)
public class BuyLandActionTest {
  BuyLandAction action;

  @Mock
  HttpConnection connection;

  @Mock
  Environment environment;

  @Mock
  Stakeholder currentStakeholder;

  @Mock
  Stakeholder otherStakeholder;

  Polygon sh1land = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON (((0 0, 0 16"
          + ", 16 16, 16 0, 0 0)))");

  Polygon sh2land = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON (((17 17, 17 34"
          + ", 34 34, 34 17, 17 17)))");

  Polygon emptyLand = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON EMPTY");

  @Before
  public void setup() {
    PowerMockito.mockStatic(HttpConnection.class);
    BDDMockito.given(HttpConnection.getInstance()).willReturn(connection);

    when(currentStakeholder.getId()).thenReturn(1);
    when(otherStakeholder.getId()).thenReturn(2);

    when(environment.getAvailableLand(eq(currentStakeholder))).thenReturn(sh1land);
    when(environment.getAvailableLand(eq(otherStakeholder))).thenReturn(sh2land);

    StakeholderList list = new StakeholderList();
    list.add(currentStakeholder);
    list.add(otherStakeholder);

    when(environment.get(eq(StakeholderList.class))).thenReturn(list);

    //Polygon polyUnion = PolygonUtil.polygonUnion(sh1land, sh2land);
    when(environment.getSuitableLand(any(Polygon.class), anyDouble())).thenReturn(sh2land);
  }

  @Test
  public void testBuyWithNoLand() {
    when(environment.getAvailableLand(eq(currentStakeholder))).thenReturn(emptyLand);
    when(environment.getAvailableLand(eq(otherStakeholder))).thenReturn(emptyLand);
    action = new BuyLandAction(environment);
    boolean result = action.buyLand(0d, 0d);
    Assert.assertFalse(result);
  }

  @Test
  public void testBuyTooLittleLand() {
    action = new BuyLandAction(environment);
    boolean result = action.buyLand(1000d, 0d);
    Assert.assertFalse(result);
  }

  @Test
  public void testBuy() {
    action = new BuyLandAction(environment);
    boolean result = action.buyLand(10d, 0d);
    Assert.assertTrue(result);
  }
}
