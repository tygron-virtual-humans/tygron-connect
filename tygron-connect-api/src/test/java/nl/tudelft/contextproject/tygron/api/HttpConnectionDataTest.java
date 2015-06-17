package nl.tudelft.contextproject.tygron.api;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class HttpConnectionDataTest {

  HttpConnectionData dataObj;
  
  @Before
  public void setup() {
    dataObj = new HttpConnectionData();
  }
  
  @Test
  public void setClientTokenTest() {
    assertEquals(null,dataObj.getClientToken());
    dataObj.setClientToken("someClientToken");
    assertEquals("someClientToken",dataObj.getClientToken());
  }
  
  @Test
  public void setServerTokenTest() {
    assertEquals(null,dataObj.getServerToken());
    dataObj.setServerToken("someServerToken");
    assertEquals("someServerToken",dataObj.getServerToken());
  }  
  
  @Test
  public void setSessionIdTest() {
    assertEquals(0,dataObj.getSessionId());
    dataObj.setSessionId(1337);
    assertEquals(1337,dataObj.getSessionId());
  }    
}
