package nl.tudelft.contextproject.eis.entities;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import nl.tudelft.contextproject.democode.CachedFileReader;
import nl.tudelft.contextproject.eis.TygronPercept;
import nl.tudelft.contextproject.tygron.Environment;
import nl.tudelft.contextproject.tygron.IndicatorList;
import nl.tudelft.contextproject.tygron.Session;
import nl.tudelft.contextproject.tygron.StakeholderList;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

@RunWith(value = MockitoJUnitRunner.class)
public class PerceptTests {
  private Controller controller;
  
  @Mock
  private Environment environmentMock;

  /**
   * Init all test objects.
   */
  @Before
  public void setupController() {
    String indicatorContents = CachedFileReader.getFileContents("/serverResponses/testmap/lists/indicators.json");
    JSONArray indicatorResult = new JSONArray(indicatorContents);
    IndicatorList indicators = new IndicatorList(indicatorResult);
    when(environmentMock.loadIndicators()).thenReturn(indicators);

    String stakeholderContents = CachedFileReader.getFileContents("/serverResponses/testmap/lists/stakeholders.json");
    StakeholderList stakeholders = new StakeholderList(new JSONArray(stakeholderContents));
    when(environmentMock.loadStakeHolders()).thenReturn(stakeholders);

    //when(sessionMock.loadEconomies()).thenReturn(null);

    controller = new Controller(environmentMock);
  }

  @Test
  public void stakeholderTest() {
    assertEquals("Municipality", controller.stakeholder().get(0).get(1));
  }

  @Test
  public void initIndicatorsTest() {
    List<TygronPercept> indicator = controller.initIndicator();
    assertEquals(0, indicator.get(0).get(0));
    assertEquals(0, indicator.get(0).get(1));
    assertEquals(0.15, indicator.get(0).get(2));
  }

  @Test
  public void indicatorsTest() {
    assertEquals(0, controller.progressIndicator().get(0).get(0));
    assertEquals("finance", controller.progressIndicator().get(0).get(1));
    assertEquals("budget municipality", controller.progressIndicator().get(0).get(2));
  }

}
