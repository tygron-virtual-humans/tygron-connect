package nl.tudelft.contextproject.tygron.api.actions;


import com.esri.core.geometry.Polygon;
import nl.tudelft.contextproject.tygron.api.Environment;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.objects.*;
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

import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HttpConnection.class)
public class SellLandActionTest extends LandTest {
  SellLandAction action;

  @Mock
  HttpConnection connection;

  @Mock
  Environment environment;

  @Before
  public void setup() {
    PowerMockito.mockStatic(HttpConnection.class);
    BDDMockito.given(HttpConnection.getInstance()).willReturn(connection);

    environment = getFakeEnvironment();
    when(environment.getAvailableLand(eq(stakeholder1))).thenReturn(land1part);
    when(environment.getAvailableLand(eq(stakeholder2))).thenReturn(land2part);

    when(environment.getSuitableLand(any(Polygon.class), anyDouble())).thenReturn(land1part);

    action = new SellLandAction(environment);
  }

  @Test
  public void testSellNoLand() {
    when(environment.getAvailableLand(stakeholder1)).thenReturn(emptyLand);
    boolean result = action.sellLand(10d, 10d);
    Assert.assertFalse(result);
  }

  @Test
  public void testSellTooLittleLand() {
    boolean result = action.sellLand(1000d, 10d);
    Assert.assertFalse(result);
  }

  @Test
  public void testGoodSell() {
    boolean result = action.sellLand(10d, 10d);
    Assert.assertTrue(result);
  }
}
