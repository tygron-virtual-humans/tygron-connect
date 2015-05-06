package nl.tudelft.contextproject.eis;

import eis.exceptions.ManagementException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Parameter;

import eis.eis2java.environment.AbstractEnvironment;

import java.util.Map;

@SuppressWarnings("serial")
public class Interface extends AbstractEnvironment {

  public Interface() {
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
    // Prepare the game.
    reset(parameters);

    // Try creating and registering an entity.
//    try {
//      registerEntity("Entity", new Entity(controller));
//    } catch (EntityException e) {
//      throw new ManagementException("Could not create an entity", e);
//    }
  }

  /* (non-Javadoc)
   * @see eis.eis2java.environment.AbstractEnvironment#reset(java.util.Map)
   */
  @Override
  public void reset(Map<String, Parameter> parameters)
      throws ManagementException {
    setState(EnvironmentState.PAUSED);
  }

  /* (non-Javadoc)
   * @see eis.EIDefaultImpl#kill()
   */
  @Override
  public void kill() throws ManagementException {
    setState(EnvironmentState.KILLED);
  }

  public static void main(String[] args) {
    
  }

}
