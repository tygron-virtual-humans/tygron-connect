package nl.tudelft.contextproject.tygron.eis.entities;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

import nl.tudelft.contextproject.tygron.eis.TygronPercept;
import nl.tudelft.contextproject.tygron.objects.Economy;
import nl.tudelft.contextproject.tygron.objects.EconomyList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

@RunWith(value = MockitoJUnitRunner.class)
public class EconomyEntityTest {

  private EconomyEntity economyEntity;
  
  @Mock
  private Economy economy;
  private EconomyList economyList;
  
  /**
   * Initialize the test, set up mocks.
   */
  @Before
  public void start() {
    doReturn(1).when(economy).getId();
    doReturn("Category").when(economy).getCategory();
    doReturn("State").when(economy).getState();

    economyList = new EconomyList();
    economyList.add(economy);
    
    economyEntity = new EconomyEntity(economyList);
  }
  
  @Test
  public void test() {
    List<TygronPercept> list = economyEntity.economies();

    assertEquals(1,list.size());
    assertEquals(1,list.get(0).get(0));
    assertEquals("Category",list.get(0).get(1));
    assertEquals("State",list.get(0).get(2));
  }
  
  
}
