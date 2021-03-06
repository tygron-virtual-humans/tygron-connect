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
public class BuyLandActionTest extends LandTest {
  BuyLandAction action;

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

    action = new BuyLandAction(environment);
  }

  @Test
  public void testBuyWithNoLand() {
    when(environment.getAvailableLand(eq(stakeholder1))).thenReturn(emptyLand);
    when(environment.getAvailableLand(eq(stakeholder2))).thenReturn(emptyLand);
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
