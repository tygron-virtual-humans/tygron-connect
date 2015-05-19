package nl.tudelft.contextproject.eis;

import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Parameter;
import nl.tudelft.contextproject.eis.entities.Controller;
import nl.tudelft.contextproject.translators.TygronIndicatorTranslator;
import nl.tudelft.contextproject.tygron.Connector;
import nl.tudelft.contextproject.tygron.Session;
import eis.eis2java.environment.AbstractEnvironment;
import eis.eis2java.translation.Translator;

import java.util.Map;

@SuppressWarnings("serial")
public class TygronInterfaceImpl extends AbstractEnvironment {

  Session controller;
  
  public TygronInterfaceImpl() {
    //Used for testing.
  }
  
  /* (non-Javadoc)
   * @see eis.EIDefaultImpl#isSupportedByEnvironment(eis.iilang.Action)
   */
  @Override
  protected boolean isSupportedByEnvironment(Action action) {
    return false;
  }

  /* (non-Javadoc)
   * @see eis.EIDefaultImpl#isSupportedByType(eis.iilang.Action, java.lang.String)
   */
  @Override
  protected boolean isSupportedByType(Action action, String type) {
    return false;
  }

  /* (non-Javadoc)
   * @see eis.EIDefaultImpl#init(java.util.Map)
   */
  @Override
  public void init(Map<String, Parameter> parameters)
      throws ManagementException {
    
    TygronIndicatorTranslator indicatorTranslator = new TygronIndicatorTranslator();
    Translator translator = Translator.getInstance();
    translator.registerJava2ParameterTranslator(indicatorTranslator);
    translator.registerParameter2JavaTranslator(indicatorTranslator);
    
    // Prepare the game.
    reset(parameters);
    
    // Try creating and registering an entity.
    try {
      registerEntity("Controller", "MainEntity", 
          new Controller(controller));
    } catch (EntityException e) {
      throw new ManagementException("Could not create an entity", e);
    }
  }

  /* (non-Javadoc)
   * @see eis.eis2java.environment.AbstractEnvironment#reset(java.util.Map)
   */
  @Override
  public void reset(Map<String, Parameter> parameters)
      throws ManagementException {
    
    //Create a new connection
    Connector connector = new Connector();
    
    //Get the session manager
    controller = connector.getSession();
    
    setState(EnvironmentState.PAUSED);
  }

  /* (non-Javadoc)
   * @see eis.EIDefaultImpl#kill()
   */
  @Override
  public void kill() throws ManagementException {
    setState(EnvironmentState.KILLED);
  }

  public static void main(String[] args) throws ManagementException {
    new TygronInterfaceImpl().init(null);
  }

}
