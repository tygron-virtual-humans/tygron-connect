package nl.tudelft.contextproject.tygron.eis.entities;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

import nl.tudelft.contextproject.tygron.eis.TygronPercept;
import nl.tudelft.contextproject.tygron.objects.Stakeholder;
import nl.tudelft.contextproject.tygron.objects.StakeholderList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(value = MockitoJUnitRunner.class)
public class StakeholderEntityTest {

  private StakeholderEntity stakeholderEntity;
  
  @Mock
  private Stakeholder stakeholder;

  private StakeholderList stakeholderList;
  
  Map<Integer,Double> weights;
  
  /**
   * Initialize the test, set up mocks.
   */
  @Before
  public void start() {
    doReturn(1).when(stakeholder).getId();
    doReturn("shortname").when(stakeholder).getShortName();
    doReturn("name").when(stakeholder).getName();
    
    weights = new HashMap<>();
    weights.put(1, 1.3);
    weights.put(2, 0.0);
    weights.put(3, 4.1);
    doReturn(weights).when(stakeholder).getIndicatorWeights();
    
    stakeholderList = new StakeholderList();
    stakeholderList.add(stakeholder);
    
    stakeholderEntity = new StakeholderEntity(stakeholderList);
  }
  
  @Test
  public void test_stakeholder() {
    List<TygronPercept> list = stakeholderEntity.stakeholder();

    assertEquals(1,list.size());
    assertEquals(1,list.get(0).get(0));
    assertEquals("name",list.get(0).get(1));
    assertEquals("shortname",list.get(0).get(2));
  }
  
  @Test
  public void test_initIndicator() {
    List<TygronPercept> list = stakeholderEntity.initIndicator();

    assertEquals(2,list.size());
    assertEquals(1,list.get(0).get(0));
    assertEquals(1,list.get(1).get(0));
    assertEquals(1,list.get(0).get(1));
    assertEquals(3,list.get(1).get(1));
    assertEquals(1.3,list.get(0).get(2));
    assertEquals(4.1,list.get(1).get(2));
  }
  
}
