package nl.tudelft.contextproject.tygron.api;

import static org.junit.Assert.assertEquals;
import nl.tudelft.contextproject.tygron.CachedFileReader;
import nl.tudelft.contextproject.tygron.handlers.IntegerResultHandler;
import nl.tudelft.contextproject.tygron.handlers.JsonArrayResultHandler;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HttpConnection.class)
public class SessionManagerTest {

  @Mock
  HttpConnection connection;
  
  SessionManager sessionManager;
  
  String joinableSessionsUrl = "services/event/IOServicesEventType/GET_JOINABLE_SESSIONS/";
  String startNewSessionUrl = "services/event/IOServicesEventType/START_NEW_SESSION/";
  String joinableSessions = CachedFileReader.getFileContents("/serverResponses/joinableSessions.json");
  
  /**
   * Test the creation.
   */
  @Before
  public void createSession() {
    
    PowerMockito.mockStatic(HttpConnection.class);
    BDDMockito.given(HttpConnection.getInstance()).willReturn(connection);
    
    sessionManager = new SessionManager();
    
    Mockito.when(
        connection.execute(Mockito.eq(joinableSessionsUrl), Mockito.eq(CallType.POST),
            Mockito.any(JsonArrayResultHandler.class)
           )).thenReturn(
                new JSONArray(joinableSessions));
    
    Mockito.when(
        connection.execute(Mockito.eq(startNewSessionUrl), Mockito.eq(CallType.POST),
            Mockito.any(IntegerResultHandler.class), Mockito.any(SessionManager.StartSessionRequest.class)
           )).thenReturn(
                1337);
  }

  @Test
  public void testJoinableSessions() {
    List<JoinableSession> list = sessionManager.getJoinableSessions();
    assertEquals(2,list.size());
  }
  
  @Test
  public void startSessionRequestTest() {
    int targetSlot = sessionManager.startSessionSlot("testmap");
    assertEquals(1337,targetSlot);
  }
}
