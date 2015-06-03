package nl.tudelft.contextproject.tygron.handlers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IntegerResultHandlerTest {
  @Test
  public void handleResultTest() {
    ResultHandler<Integer> ih = new IntegerResultHandler();
    assertEquals(ih.handleResult("1"),new Integer(1));
  }
}
