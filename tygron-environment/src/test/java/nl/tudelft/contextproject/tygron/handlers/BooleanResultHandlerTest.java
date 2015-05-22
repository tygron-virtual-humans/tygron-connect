package nl.tudelft.contextproject.tygron.handlers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BooleanResultHandlerTest {
  @Test
  public void handleResultTrueTest() {
    ResultHandler<Boolean> rh = new BooleanResultHandler();
    assertTrue(rh.handleResult("true"));
  }
  
  @Test
  public void handleResultFalseTest() {
    ResultHandler<Boolean> rh = new BooleanResultHandler();
    assertFalse(rh.handleResult("not a boolean"));
  }
}
