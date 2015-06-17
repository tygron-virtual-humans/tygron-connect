package nl.tudelft.contextproject.tygron.api;

import static org.junit.Assert.assertEquals;

import nl.tudelft.contextproject.tygron.CachedFileReader;
import nl.tudelft.contextproject.tygron.handlers.JsonObjectResultHandler;

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
public class JoinableSessionTest {
  Session session;

  @Mock
  HttpConnection connection;
  
  @Mock
  Session sessionMock;
  
  @Mock
  Environment environmentMock;
  
  String joinSessionEvent = "services/event/IOServicesEventType/JOIN_SESSION/";

  JoinableSession joinableSession;
  String joinableSessionContent;
  String joinableSessionJoin;
  

  /**
   * Test the create session.
   */
  @Before
  public void createSession() {
    
    PowerMockito.mockStatic(HttpConnection.class);
    BDDMockito.given(HttpConnection.getInstance()).willReturn(connection);
    
    joinableSessionContent = CachedFileReader.getFileContents("/serverResponses/joinableSession.json");
    joinableSessionJoin = CachedFileReader.getFileContents("/serverResponses/joinSession.json");
    joinableSession = new JoinableSession();
    
    Mockito.when(
        connection.execute(Mockito.eq(joinSessionEvent), Mockito.eq(CallType.POST),
            Mockito.any(JsonObjectResultHandler.class),
            Mockito.any(JoinableSession.JoinSessionRequest.class))).thenReturn(
                new JSONObject(joinableSessionJoin));
  }

  @Test
  public void createFromJsonTest() {
    JoinableSession jb = new JoinableSession(new JSONObject(joinableSessionContent));
    assertEquals("testmap",jb.getName());
    assertEquals(2,jb.getId());
  }
  
  @Test
  public void nameTest() {
    assertEquals(null,joinableSession.getName());
    joinableSession.setName("othername");
    assertEquals("othername",joinableSession.getName());
  }

  @Test
  public void idTest() {
    assertEquals(0,joinableSession.getId());
    joinableSession.setId(1337);
    assertEquals(1337,joinableSession.getId());
  }

  @Test
  public void joinSessionTest() { 
    Session target = joinableSession.join();
    assertEquals("testmap",target.getName());
  }
}
