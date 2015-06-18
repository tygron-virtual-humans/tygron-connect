package nl.tudelft.contextproject.tygron.api.actions;

import com.esri.core.geometry.Polygon;
import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.Environment;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.handlers.objects.ServerWordsResultHandler;
import nl.tudelft.contextproject.tygron.objects.Stakeholder;
import nl.tudelft.contextproject.tygron.objects.StakeholderList;
import nl.tudelft.contextproject.util.PolygonUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HttpConnection.class)
public class BuildActionTest {
  BuildAction action;

  @Mock
  HttpConnection connection;

  @Mock
  Environment environment;

  StakeholderList stakeholderList;

  @Mock
  Stakeholder stakeholder;

  @Before
  public void setup() {
    PowerMockito.mockStatic(HttpConnection.class);
    BDDMockito.given(HttpConnection.getInstance()).willReturn(connection);

    //when(stakeholder.geta)

    stakeholderList = new StakeholderList();
    stakeholderList.add(stakeholder);

    when(environment.get(eq(StakeholderList.class))).thenReturn(stakeholderList);
    Polygon polygon = PolygonUtil.createPolygonFromWkt("MULTIPOLYGON (((0 0, 0 16"
            + ", 16 16, 16 0, 0 0)))");
    when(environment.getAvailableLand(eq(stakeholder))).thenReturn(polygon);

    when(connection.execute(anyString(), eq(CallType.GET),
            any(ServerWordsResultHandler.class), eq(true))).thenReturn(null);

    action = new BuildAction(environment);
  }

  /**
   * Test for a stakeholder that does not have a build action.
   */
  @Test
  public void buildTest() {
    boolean result = action.build(0d, 0);
    assertFalse(result);
  }
}
