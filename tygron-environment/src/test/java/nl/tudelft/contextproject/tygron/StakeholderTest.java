package nl.tudelft.contextproject.tygron;

import static org.junit.Assert.assertEquals;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

public class StakeholderTest {
  DummyConnection connection;
  
  @Before
  public void setupConnection() {
    connection = new DummyConnection();
  }
  
  @Test
  public void stakeholderTest() {
    connection.setFile("/serverResponses/testmap/lists/stakeholders.json");
    JSONArray result = connection.callGetEventArray("");
    StakeholderList list = new StakeholderList(result);
    
    assertEquals("Municipality", list.get(0).getName());
  }
}
