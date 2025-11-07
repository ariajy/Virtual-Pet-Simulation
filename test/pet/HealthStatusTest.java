package pet;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the HealthStatus class.
 * This class tests the functionality of the HealthStatus class, including
 * getters and the toString method.
 */
public class HealthStatusTest {
  private HealthStatus health;

  @Before
  public void setUp() {
    health = new HealthStatus(50, 50, 50, 50);
  }

  @After
  public void tearDown() {
    health = null;
  }

  // Test methods for getters
  @Test
  public void getHunger() {
    assertEquals("Hunger should be 50", 50, health.getHunger());
  }

  @Test
  public void getHygiene() {
    assertEquals("Hygiene should be 50", 50, health.getHygiene());
  }

  @Test
  public void getSocial() {
    assertEquals("Social should be 50", 50, health.getSocial());
  }

  @Test
  public void getSleep() {
    assertEquals("Sleep should be 50", 50, health.getSleep());
  }

  // Test method for toString
  @Test
  public void testToString() {
    String expected = "HealthStatus{hunger=50, hygiene=50, social=50, sleep=50}";
    assertEquals("toString should return the correct format", expected, health.toString());
  }
}