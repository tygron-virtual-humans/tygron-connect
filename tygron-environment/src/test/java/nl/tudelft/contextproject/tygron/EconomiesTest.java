package nl.tudelft.contextproject.tygron;

import static org.junit.Assert.assertEquals;


import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class EconomiesTest {
  DummyConnection connection;
  
  @Before
  public void setupConnection() {
    connection = new DummyConnection();
  }
  
  @Test
  public void indicatorTest() {
    connection.setFile("/serverResponses/testmap/lists/economies.json");
    JSONObject result = connection.callGetEventObject("");
    Economy economies = new Economy(result);

    assertEquals("SOCIAL", economies.getCategory());
    assertEquals("GOOD", economies.getState());
    
  }

}
