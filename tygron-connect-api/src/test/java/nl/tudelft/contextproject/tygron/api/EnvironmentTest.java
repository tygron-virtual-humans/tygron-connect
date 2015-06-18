package nl.tudelft.contextproject.tygron.api;

import static org.junit.Assert.assertTrue;
import nl.tudelft.contextproject.tygron.api.Environment.StakeholderReleaseRequest;
import nl.tudelft.contextproject.tygron.handlers.BooleanResultHandler;
import nl.tudelft.contextproject.tygron.handlers.JsonObjectResultHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HttpConnection.class)
public class EnvironmentTest {

  @Mock
  HttpConnection connection;
  
  @Mock
  HttpConnectionData connectionData;
  
  Environment env;
  
  String stakeholderSelectUrl = "event/PlayerEventType/STAKEHOLDER_SELECT/";
  String stakeholderRelease = "event/LogicEventType/STAKEHOLDER_RELEASE/";
  String allowGameInteractionUrl = "event/LogicEventType/SETTINGS_ALLOW_GAME_INTERACTION/";
  
  /**
   * Setup required elements for tests.
   */
  @Before
  public void setup() {
    PowerMockito.mockStatic(HttpConnection.class);
    BDDMockito.given(HttpConnection.getInstance()).willReturn(connection);  
    
    Mockito.when(HttpConnection.getData()).thenReturn(connectionData);
    Mockito.when(connectionData.getClientToken()).thenReturn("clientToken");
    
    env = new Environment();
  }
  
  @Test(expected = RuntimeException.class)
  public void stakeHolderTest() {
    Mockito.when(connection.execute(
        Mockito.eq(stakeholderSelectUrl), Mockito.eq(CallType.POST), Mockito.any(BooleanResultHandler.class), 
        Mockito.any(Boolean.class), Mockito.any(Environment.StakeholderSelectRequest.class))
    ).thenReturn(false);
  
    env.setStakeholder(1);
  }
 
  @Test
  public void stakeHolderReleaseTest() {
    Mockito.when(connection.execute(
        Mockito.eq(stakeholderRelease), Mockito.eq(CallType.POST), Mockito.any(BooleanResultHandler.class), 
        Mockito.any(Boolean.class), Mockito.any(Environment.StakeholderReleaseRequest.class))
    ).thenReturn(true);
  
    env.releaseStakeholder();
  }
  
  @Test
  public void allowInteractionTest() {    
    Mockito.when(connection.execute(
        Mockito.eq(allowGameInteractionUrl), Mockito.eq(CallType.POST), Mockito.any(BooleanResultHandler.class), 
        Mockito.any(Boolean.class), Mockito.any(JSONArray.class))
    ).thenReturn(true);
    
    assertTrue(env.allowGameInteraction(true));
  }
}
