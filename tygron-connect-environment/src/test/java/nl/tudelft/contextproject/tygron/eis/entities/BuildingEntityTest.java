package nl.tudelft.contextproject.tygron.eis.entities;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

import nl.tudelft.contextproject.tygron.eis.TygronPercept;
import nl.tudelft.contextproject.tygron.objects.Building;
import nl.tudelft.contextproject.tygron.objects.BuildingList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

@RunWith(value = MockitoJUnitRunner.class)
public class BuildingEntityTest {

  private BuildingEntity buildingEntity;
  
  @Mock
  private Building building;

  private BuildingList buildinglist;
  
  /**
   * Initialize the test, set up mocks.
   */
  @Before
  public void start() {
    doReturn(1).when(building).getId();
    doReturn("Building1").when(building).getName();

    buildinglist = new BuildingList();
    buildinglist.add(building);
    
    buildingEntity = new BuildingEntity(buildinglist);
  }
  
  @Test
  public void test() {
    List<TygronPercept> list = buildingEntity.buildings();

    assertEquals(1,list.size());
    assertEquals(1,list.get(0).get(0));
    assertEquals("Building1",list.get(0).get(1));
  }
}
