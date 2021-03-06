package nl.tudelft.contextproject.tygron.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import nl.tudelft.contextproject.tygron.CachedFileReader;
import nl.tudelft.contextproject.tygron.handlers.BooleanResultHandler;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HttpConnection.class)
public class SessionTest {
  Session session;

  @Mock
  HttpConnection connection;

  String closeSessionEvent = "services/event/IOServicesEventType/CLOSE_SESSION/";
  String killSessionEvent = "services/event/IOServicesEventType/KILL_SESSION/";

  /**
   * Test the create session.
   */
  @Before
  public void createSession() {

    PowerMockito.mockStatic(HttpConnection.class);
    BDDMockito.given(HttpConnection.getInstance()).willReturn(connection);
    
    String file = "/serverResponses/login.json";
    String loginContents = CachedFileReader.getFileContents(file);
    JSONObject loginResult = new JSONObject(loginContents);
    session = new Session(0, loginResult);

    when(connection.execute(eq(closeSessionEvent), eq(CallType.POST), 
        any(BooleanResultHandler.class), any(Session.CloseSessionRequest.class))).thenReturn(true);
    
    when(connection.execute(eq(killSessionEvent), eq(CallType.POST), 
        any(BooleanResultHandler.class), any(Session.KillSessionRequest.class))).thenReturn(true);
  }
  
  @Test
  public void closeSessionTest() {
    assertEquals(true,session.closeSession(false));
  }
  
  @Test
  public void killSessionTest() {
    assertEquals(true,session.killSession(0));
  }

  @Test
  public void creationTest() {
    Session sess = new Session();
    assertEquals("",sess.getName());
    assertEquals("",sess.getClientToken());
    assertEquals("",sess.getServerToken());
  }
  
  @Test
  public void nameTest() {
    assertEquals("testmap", session.getName());
    session.setName("name change");
    assertEquals("name change", session.getName());
  }

  @Test
  public void clientTokenTest() {
    assertEquals("db45ba46-6c7f-4021-92e2-8865674e3837", session.getClientToken());
    session.setClientToken("token change");
    assertEquals("token change", session.getClientToken());
  }

  @Test
  public void idTest() {
    assertEquals(0, session.getId());
    session.setId(17);
    assertEquals(17, session.getId());
  }

  @Test
  public void serverTokenTest() {
    assertEquals("bccf1987-b4f3-4245-846e-7f403bd7cd78", session.getServerToken());
    session.setServerToken("token change");
    assertEquals("token change", session.getServerToken());
  }

  @Test
  public void testOperations() {
    List<String> operations = new ArrayList<>();
    Collections.addAll(operations, "ACHIEVEMENTS", "ACTION_MENUS", "ASSISTANT_ACTOR_DATA", "LOANS", 
        "BEHAVIOR_TERRAINS", "BUILDINGS", "CHAIN_ELEMENTS", "CINEMATIC_DATAS", "CLIENT_EVENTS", 
        "CLIENT_WORDS", "CONTRIBUTORS", "COSTS", "DIKES", "ECONOMIES", "EVENT_BUNDLES", "FUNCTIONS", 
        "FUNCTION_OVERRIDES", "GAME_LEVELS", "HEIGHTS", "HOTSPOTS", "INCOMES", "INDICATORS", "KEY_BINDINGS", 
        "LANDS", "MEASURES", "MESSAGES", "MODEL_DATAS", "MODEL_SETS", "MONEY_TRANSFERS", "OVERLAYS", 
        "PARTICLE_EMITTERS", "PIPE_CLUSTERS", "PIPES", "PIPE_DEFINITIONS", "PIPE_JUNCTIONS", "PIPE_LOADS", 
        "PIPE_SETTINGS", "POPUPS", "PRODUCTS", "PRODUCT_STORAGES", "QUALITATIVE_FUNCTION_SCORES", "SCORES", 
        "SERVER_WORDS", "LOGS", "SETTINGS", "SHRINK_SETTINGS", "SIMTIME_SETTINGS", "SOUNDS", "SPECIAL_EFFECTS", 
        "SPECIAL_OPTIONS", "STAKEHOLDERS", "STRATEGIES", "TAX_PLANS", "TIMES", "UNITS", "UNIT_DATAS", 
        "UNIT_DATA_OVERRIDES", "UPGRADE_TYPES", "VACANCY_SETTINGS", "VIDEOS", "WATER_TERRAINS", "WAY_POINTS", 
        "WEATHERS", "ZONES", "ZOOMLEVELS");
    assertEquals(operations, session.getCompatibleOperations());
  }

}
