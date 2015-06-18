package nl.tudelft.contextproject.tygron.eis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import eis.exceptions.ManagementException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import nl.tudelft.contextproject.tygron.api.Connector;
import nl.tudelft.contextproject.tygron.api.Environment;
import nl.tudelft.contextproject.tygron.api.Session;
import nl.tudelft.contextproject.tygron.objects.BuildingList;
import nl.tudelft.contextproject.tygron.objects.EconomyList;
import nl.tudelft.contextproject.tygron.objects.StakeholderList;
import nl.tudelft.contextproject.tygron.objects.indicators.IndicatorList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@RunWith(value = MockitoJUnitRunner.class)
public class TygronInterfaceImplTest {
  
  private TygronInterfaceImpl impl;
  private Map<String,Parameter> parameters;
  
  @Mock
  private Connector connector;
  
  @Mock
  private Session session;
  
  @Mock
  private Environment envMock;
  
  /**
   * Initialize the tests.
   */
  @Before
  public void start() {
    impl = spy(new TygronInterfaceImpl());
    
    parameters = new HashMap<>();
    
    Parameter par = new Numeral(1);
    
    parameters.put("stakeholder",par);

    when(envMock.reload(BuildingList.class)).thenReturn(null);
    when(envMock.reload(EconomyList.class)).thenReturn(null);
    when(envMock.reload(IndicatorList.class)).thenReturn(null);
    when(envMock.reload(StakeholderList.class)).thenReturn(null);
    doNothing().when(envMock).setStakeholder(any(Integer.class));
    
    doReturn(envMock).when(session).getEnvironment();
    
    doReturn(session).when(connector).getSession();
    
    doReturn(connector).when(impl).makeConnector();
  }
  
  @Test
  public void test_init_and_kill() throws ManagementException {
    impl.init(parameters);
    
    assertEquals(EnvironmentState.PAUSED,impl.getState());
    
    impl.start();
    
    assertEquals(EnvironmentState.RUNNING,impl.getState());
    
    impl.reset(parameters);
    
    assertEquals(EnvironmentState.PAUSED,impl.getState());
    
    impl.kill();
    
    assertEquals(EnvironmentState.KILLED, impl.getState());
  }
  
  @Test(expected = ManagementException.class) 
  public void test_invalidParameter() throws ManagementException {
    parameters.put("slot", new Identifier("abc"));
    impl.init(parameters);
  }
  
  @Test(expected = ManagementException.class) 
  public void test_invalidStakeholder() throws ManagementException {
    impl.init(new HashMap<String, Parameter>());
  }
  
  
  @Test
  public void test_build_support() {
    Action action = new Action("build");
    LinkedList<Parameter> params = new LinkedList<>();
    params.add(new Numeral(5));
    params.add(new Numeral(500));
    
    action.setParameters(params);
    assertTrue(impl.isSupportedByEnvironment(action));
  }
  
  @Test
  public void test_build_wrong_size_support() {
    Action action = new Action("build");
    LinkedList<Parameter> params = new LinkedList<>();
    params.add(new Numeral(5));
    
    action.setParameters(params);
    assertFalse(impl.isSupportedByEnvironment(action));
  }
  
  @Test
  public void test_buyLand_support() {
    Action action = new Action("buyLand");
    LinkedList<Parameter> params = new LinkedList<>();
    params.add(new Numeral(5));
    params.add(new Numeral(5));
    
    action.setParameters(params);
    assertTrue(impl.isSupportedByType(action, "type"));
  }
  
  @Test
  public void test_buyLand_wrong_size_support() {
    Action action = new Action("buyLand");
    LinkedList<Parameter> params = new LinkedList<>();
    params.add(new Numeral(5));
    
    action.setParameters(params);
    assertFalse(impl.isSupportedByType(action, "type"));
  }
  
  @Test
  public void test_askMoney_wrong_size_support() {
    Action action = new Action("askMoney");
    LinkedList<Parameter> params = new LinkedList<>();
    params.add(new Numeral(5));
    
    action.setParameters(params);
    assertFalse(impl.isSupportedByType(action, "type"));
  }
  
  @Test
  public void test_askMoney_support() {
    Action action = new Action("askMoney");
    LinkedList<Parameter> params = new LinkedList<>();
    params.add(new Numeral(5));
    params.add(new Numeral(5));
    
    action.setParameters(params);
    assertTrue(impl.isSupportedByType(action, "type"));
  }
  
  @Test
  public void test_giveMoney_wrong_size_support() {
    Action action = new Action("giveMoney");
    LinkedList<Parameter> params = new LinkedList<>();
    params.add(new Numeral(5));
    
    action.setParameters(params);
    assertFalse(impl.isSupportedByType(action, "type"));
  }
  
  @Test
  public void test_giveMoney_support() {
    Action action = new Action("giveMoney");
    LinkedList<Parameter> params = new LinkedList<>();
    params.add(new Numeral(5));
    params.add(new Numeral(5));
    
    action.setParameters(params);
    assertTrue(impl.isSupportedByType(action, "type"));
  }
  
  @Test
  public void test_no_support() {
    Action action = new Action("notSupported");
    LinkedList<Parameter> params = new LinkedList<>();
    params.add(new Numeral(5));
    
    action.setParameters(params);
    assertFalse(impl.isSupportedByEnvironment(action));
  }
  
}
