package nl.tudelft.contextproject.tygron.eis;

import static org.junit.Assert.assertEquals;

import nl.tudelft.contextproject.tygron.eis.TygronPercept;

import org.junit.Test;

public class TygronPerceptTest {

  @Test
  public void test() {
    TygronPercept object = new TygronPercept(5,"42");
    assertEquals(5, object.get(0));
    assertEquals("42", object.get(1));
  }

}
