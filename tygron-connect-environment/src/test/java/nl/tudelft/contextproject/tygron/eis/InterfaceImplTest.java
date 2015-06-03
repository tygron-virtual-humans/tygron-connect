package nl.tudelft.contextproject.tygron.eis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.any;
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
import nl.tudelft.contextproject.tygron.eis.TygronInterfaceImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(value = MockitoJUnitRunner.class)
public class InterfaceImplTest {
  private static TygronInterfaceImpl envinterface;
  private static Map<String, Parameter> parameters;
  
  @Mock
  private Connector connector;
  
  @Mock
  private Session session;
  
  @Mock
  private Environment envMock;
  
  /**
   * initialize the test.
   * @throws Exception 
   */
  @Before
  public void initTest() {
    
    envinterface = spy(new TygronInterfaceImpl());
    
    parameters = new HashMap<String, Parameter>();
    
    Parameter par = new Numeral(1);
    
    parameters.put("stakeholder",par);

    when(envMock.loadBuildings()).thenReturn(null);
    when(envMock.loadEconomies()).thenReturn(null);
    when(envMock.loadIndicators()).thenReturn(null);
    doNothing().when(envMock).setStakeholder(any(Integer.class));
    
    doReturn(envMock).when(session).getEnvironment();
    
    doReturn(session).when(connector).getSession();
    
    doReturn(connector).when(envinterface).makeConnector();
  }
  
  @Test
  public void test_init_and_kill() throws ManagementException {
    envinterface.init(parameters);
    
    assertEquals(EnvironmentState.PAUSED,envinterface.getState());
    
    envinterface.start();
    
    assertEquals(EnvironmentState.RUNNING,envinterface.getState());
    
    envinterface.reset(parameters);
    
    assertEquals(EnvironmentState.PAUSED,envinterface.getState());
    
    envinterface.kill();
    
    assertEquals(EnvironmentState.KILLED, envinterface.getState());
  }
  
  @Test(expected = ManagementException.class) 
  public void test_invalidParameter() throws ManagementException {
    parameters.put("slot", new Identifier("abc"));
    envinterface.init(parameters);
  }

  @Test
  public void test_environmentSupport() {
    assertNotNull(envinterface);
    Action action = new Action("testAction");
    assertFalse(envinterface.isSupportedByEnvironment(action));
  }

  @Test
  public void testTypeSupport() {
    Action action = new Action("testAction");
    assertFalse(envinterface.isSupportedByType(action, "testType"));
  }
}
