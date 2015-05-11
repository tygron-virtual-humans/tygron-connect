package nl.tudelft.contextproject.eis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

public class EntityTest {
  private static TygronEntity entity;

  @BeforeClass
  public static void initObject() {
    entity = new TygronEntity();
  }

  @Test
  public void test_initIndicator() {
    assertNotNull(entity);
    assertEquals(3,entity.initIndicator().size());
  }
  
  @Test
  public void test_target() {
    assertNotNull(entity);
    assertEquals(2,entity.target().size());
  }
  
  @Test
  public void test_otherStakeholders() {
    assertNotNull(entity);
    assertEquals(3,entity.otherStakeholders().size());
    assertEquals(2,entity.otherStakeholders().get(0).size());
  }
  
  @Test
  public void test_progressIndicator() {
    assertNotNull(entity);
    assertEquals(6,entity.progressIndicator().size());
    assertEquals(2,entity.progressIndicator().get(0).size());
  }
  
  @Test
  public void test_ground() {
    assertNotNull(entity);
    assertEquals(3,entity.ground().size());
    assertEquals(2,entity.ground().get(0).size());
  }
  
  @Test
  public void test_budget() {
    assertNotNull(entity);
    assertEquals(123456,entity.budget());
  }
  
  @Test
  public void test_permit() {
    assertNotNull(entity);
    assertEquals(4,entity.permit().size());
  }
  
  @Test
  public void test_stakeholder() {
    assertNotNull(entity);
    assertEquals(2,entity.stakeholder().size());
  }
  
}
