package nl.tudelft.contextproject.eis.entities;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.tudelft.contextproject.tygron.DummyConnection;
import nl.tudelft.contextproject.tygron.IndicatorList;
import nl.tudelft.contextproject.tygron.Session;
import nl.tudelft.contextproject.tygron.StakeholderList;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

public class PerceptTests {

  private DummyConnection connection;
  private Controller controller;
  private Session sessionMock;
  
  /**
   * init all test objects, init mockito objects.
   */
  @Before
  public void initObject() {
    connection = new DummyConnection();
    
    sessionMock = mock(Session.class);
    
    connection.setFile("/serverResponses/testmap/lists/indicators.json");
    JSONArray result = connection.callGetEventArray("");
    IndicatorList indicators = new IndicatorList(result);
    when(sessionMock.loadIndicators()).thenReturn(indicators);
    
    connection.setFile("/serverResponses/testmap/lists/stakeholders.json");
    result = connection.callGetEventArray("");
    StakeholderList stakeholders = new StakeholderList(result);
    when(sessionMock.loadStakeHolders()).thenReturn(stakeholders);

    when(sessionMock.loadEconomies()).thenReturn(null);
    
    controller = new Controller(sessionMock);
  }
  
  @Test
  public void stakeholderTest() {
    assertEquals("Municipality", controller.stakeholder().get(0).get(1));
  }
  
  @Test
  public void initIndicatorsTest() {
    assertEquals(0, controller.initIndicator().get(0).get(0));
    assertEquals(0, controller.initIndicator().get(0).get(1));
    assertEquals(0.15, controller.initIndicator().get(0).get(2));
  }
  
  @Test
  public void indicatorsTest() {
    assertEquals(0, controller.progressIndicator().get(0).get(0));
    assertEquals("finance", controller.progressIndicator().get(0).get(1));
    assertEquals("budget municipality", controller.progressIndicator().get(0).get(2));
  }

}
