package nl.tudelft.contextproject.eis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

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
    assertTrue(entity.initIndicator() instanceof List);
  }
  
  @Test
  public void test_target() {
    assertNotNull(entity);
    assertEquals(2,entity.target().size());
    assertTrue(entity.target() instanceof List);
  }
  
  @Test
  public void test_otherStakeholders() {
    assertNotNull(entity);
    assertEquals(3,entity.otherStakeholders().size());
    assertTrue(entity.otherStakeholders() instanceof List);
    assertTrue(entity.otherStakeholders().get(0) instanceof List);
  }
  
  @Test
  public void test_progressIndicator() {
    assertNotNull(entity);
    assertEquals(6,entity.progressIndicator().size());
    assertTrue(entity.progressIndicator() instanceof List);
    assertTrue(entity.progressIndicator().get(0) instanceof List);
  }
  
  @Test
  public void test_ground() {
    assertNotNull(entity);
    assertEquals(3,entity.ground().size());
    assertTrue(entity.ground() instanceof List);
    assertTrue(entity.ground().get(0) instanceof List);
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
    assertTrue(entity.permit() instanceof List);
  }
  
  @Test
  public void test_stakeholder() {
    assertNotNull(entity);
    assertEquals(2,entity.stakeholder().size());
    assertTrue(entity.stakeholder() instanceof List);
  }
  
}
