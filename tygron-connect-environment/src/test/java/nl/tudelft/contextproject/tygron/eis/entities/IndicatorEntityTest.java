package nl.tudelft.contextproject.tygron.eis.entities;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

import nl.tudelft.contextproject.tygron.eis.TygronPercept;
import nl.tudelft.contextproject.tygron.objects.indicators.Indicator;
import nl.tudelft.contextproject.tygron.objects.indicators.IndicatorList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

@RunWith(value = MockitoJUnitRunner.class)
public class IndicatorEntityTest {

  private IndicatorEntity indicatorEntity;
  
  @Mock
  private Indicator indicator;

  private IndicatorList indicatorList;
  
  /**
   * Initialize the test, set up mocks.
   */
  @Before
  public void start() {
    doReturn(1).when(indicator).getId();
    doReturn("Type").when(indicator).getType();
    doReturn("Name").when(indicator).getName();
    doReturn(2.0).when(indicator).getProgress();
    doReturn(3.0).when(indicator).getCurrent();
    doReturn(4.0).when(indicator).getTarget();

    indicatorList = new IndicatorList();
    indicatorList.add(indicator);
    
    indicatorEntity = new IndicatorEntity(indicatorList);
  }
  
  @Test
  public void test() {
    List<TygronPercept> list = indicatorEntity.progressIndicator();

    assertEquals(1,list.size());
    assertEquals(1,list.get(0).get(0));
    assertEquals("type",list.get(0).get(1));
    assertEquals("name",list.get(0).get(2));
    assertEquals(2.0,list.get(0).get(3));
    assertEquals(3.0,list.get(0).get(4));
    assertEquals(4.0,list.get(0).get(5));
  }
  
  
}
