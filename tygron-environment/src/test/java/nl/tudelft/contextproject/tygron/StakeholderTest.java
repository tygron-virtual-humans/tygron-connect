package nl.tudelft.contextproject.tygron;

import static org.junit.Assert.assertEquals;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

public class StakeholderTest {
  TygronDummyConnection connection;
  
  @Before
  public void setupConnection() {
    connection = new TygronDummyConnection();
  }
  
  @Test
  public void stakeholderTest() {
    connection.setFile("/serverResponses/testmap/lists/stakeholders.json");
    JSONArray result = connection.callGetEventArray("");
    TygronStakeholderList list = new TygronStakeholderList(result);
    
    assertEquals("Municipality", list.get(0).getName());
  }
}
