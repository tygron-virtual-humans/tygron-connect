package nl.tudelft.contextproject.tygron;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class IndicatorTest {
  DummyConnection connection;
  
  @Before
  public void setupConnection() {
    connection = new DummyConnection();
  }
  
  @Test
  public void indicatorTest() {
    connection.setFile("/serverResponses/testmap/lists/indicators.json");
    JSONArray result = connection.callGetEventArray("");
    IndicatorList indicatorList = new IndicatorList(result);
    
    assertEquals("Budget Municipality", indicatorList.get(0).getName());
    
  }

}
