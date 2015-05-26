package nl.tudelft.contextproject.tygron.handlers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringResultTest {
  @Test
  public void handleResultTest() {
    ResultHandler<String> sh = new StringResultHandler();
    assertEquals(sh.handleResult("str"),"str");
  }
}
