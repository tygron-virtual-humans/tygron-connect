package nl.tudelft.contextproject.tygron.eis;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ConfigurationTest {

  Configuration configuration;
  
  @Before
  public void start() {
    configuration = new Configuration();
  }
  
  @Test
  public void test_init() {
    assertEquals(-1,configuration.getStakeholder());
    assertEquals(-1,configuration.getSlot());
    assertEquals(null,configuration.getMap());
  }
  
  @Test
  public void test_slot() {
    configuration.setSlot(1);
    assertEquals(1,configuration.getSlot());
  }
  
  @Test
  public void test_map() {
    configuration.setMap("testmap");
    assertEquals("testmap",configuration.getMap());
  }
  
  @Test
  public void test_stakeholder() {
    configuration.setStakeholder(1);
    assertEquals(1,configuration.getStakeholder());
  }
}
