package nl.tudelft.contextproject.tygron.eis.entities;

import nl.tudelft.contextproject.tygron.api.Environment;
import nl.tudelft.contextproject.tygron.api.Session;
import nl.tudelft.contextproject.tygron.eis.TygronPercept;
import nl.tudelft.contextproject.tygron.objects.BuildingList;
import nl.tudelft.contextproject.tygron.objects.EconomyList;
import nl.tudelft.contextproject.tygron.objects.StakeholderList;
import nl.tudelft.contextproject.tygron.objects.indicators.IndicatorList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(value = MockitoJUnitRunner.class)
public class ControllerTest {
  private Controller controller;
  
  @Mock
  private Session session;

  private IndicatorList indicatorList;

  private EconomyList economyList;

  private BuildingList buildingList; 

  private StakeholderList stakeholderList;
  
  @Mock
  private Environment env;
  
  /**
   * Set up mocks.
   */
  @Before
  public void start() {
    buildingList = new BuildingList();
    stakeholderList = new StakeholderList();
    economyList = new EconomyList();
    indicatorList = new IndicatorList();

    when(env.getStakeholderId()).thenReturn(0);
    when(env.getAvailableSurface()).thenReturn(500.0);
    when(env.getAllSurface()).thenReturn(5000.0);
    when(env.requestsOpen()).thenReturn(1);
    
    when(env.get(BuildingList.class)).thenReturn(buildingList);
    when(env.get(StakeholderList.class)).thenReturn(stakeholderList);
    when(env.get(EconomyList.class)).thenReturn(economyList);
    when(env.get(IndicatorList.class)).thenReturn(indicatorList);
    when(session.getEnvironment()).thenReturn(env);
    
    controller = new Controller(session);
  }
  
  @Test
  public void stakeholder_test() {
    assertEquals(new TygronPercept(), controller.stakeholder());
  }
  
  @Test
  public void progressIndicator_test() {
    assertEquals(new TygronPercept(), controller.progressIndicator());
  }
  
  @Test
  public void initIndicator_test() {
    assertEquals(new TygronPercept(), controller.initIndicator());
  }
  
  @Test
  public void economies_test() {
    assertEquals(new TygronPercept(), controller.economies());
  }
  
  @Test
  public void buildings_test() {
    assertEquals(new TygronPercept(), controller.buildings());
  }
  
  @Test
  public void availableLand_test() {
    assertEquals(500.0, controller.availableLand(),0.0);
  }
  
  @Test
  public void allLand_test() {
    assertEquals(5000.0, controller.allLand(),0.0);
  }
  
  @Test
  public void permits_test() {
    assertEquals(1, controller.permits());
  }
  
  @Test
  public void stakeholderSelf_test() {
    assertEquals(0, controller.stakeholderSelf());
  }
  
}
