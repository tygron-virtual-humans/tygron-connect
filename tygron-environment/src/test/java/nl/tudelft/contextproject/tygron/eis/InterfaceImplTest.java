package nl.tudelft.contextproject.tygron.eis;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import eis.iilang.Action;
import nl.tudelft.contextproject.tygron.eis.TygronInterfaceImpl;

import org.junit.BeforeClass;
import org.junit.Test;

public class InterfaceImplTest {
  private static TygronInterfaceImpl envinterface;

  @BeforeClass
  public static void initObject() {
    envinterface = new TygronInterfaceImpl();
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
