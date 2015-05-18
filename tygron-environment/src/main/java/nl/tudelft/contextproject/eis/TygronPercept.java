package nl.tudelft.contextproject.eis;

import java.util.ArrayList;

public class TygronPercept extends ArrayList<Object> {

  /**
   * Generated Serial UID.
   */
  private static final long serialVersionUID = 6615181739404057470L;

  /**
   * Creates a TygronPercept.
   * @param arguments the arguments of the percept
   */
  public TygronPercept(Object... arguments) {
    for (Object o : arguments) {
      add(o);
    }
  }
  
}
