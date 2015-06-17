package nl.tudelft.contextproject.tygron.api;

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
public class ConnectorTest {

  @Mock
  HttpConnection connection;
  
  Connector connector;
  
  /**
   * Test the connector.
   */
  @Before
  public void createConnector() {
    
    PowerMockito.mockStatic(HttpConnection.class);
    BDDMockito.given(HttpConnection.getInstance()).willReturn(connection);
    
    String myUserString = CachedFileReader.getFileContents("/serverResponses/myUser.json");
    
    System.out.println(myUserString);
    Mockito.when(
        connection.execute(Mockito.eq("services/myuser"), Mockito.eq(CallType.GET),
            Mockito.any(JsonObjectResultHandler.class)
            )).thenReturn(
                new JSONObject(myUserString));
    
    connector = new Connector();
    
  }
  
  @Test
  public void userLoadTest() {
    connector.loadUserData();
  }
}
