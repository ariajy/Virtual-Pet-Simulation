package pet;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test class for the PetUtils class.
 */
public class PetUtilsTest {

  // Test method for clamping a value within limits
  @Test
  public void testClampWithinLimits() {
    int value = 50;
    NeedLimits limits = new NeedLimits(0, 100);
    int clampedValue = PetUtils.clamp(value, limits);
    assertEquals("Value should be clamped within limits", 50, clampedValue);
  }

  // Test method for clamping a value below the minimum limit
  @Test
  public void testClampBelowMinimum() {
    int value = -10;
    NeedLimits limits = new NeedLimits(0, 100);
    int clampedValue = PetUtils.clamp(value, limits);
    assertEquals("Value should be clamped to minimum limit", 0, clampedValue);
  }

  // Test method for clamping a value above the maximum limit
  @Test
  public void testClampAboveMaximum() {
    int value = 150;
    NeedLimits limits = new NeedLimits(0, 100);
    int clampedValue = PetUtils.clamp(value, limits);
    assertEquals("Value should be clamped to maximum limit", 100, clampedValue);
  }

  // Test method for applying a decay profile to a health status
  @Test
  public void testApplyDecay() {
    HealthStatus current = new HealthStatus(50, 50, 50, 50);
    MoodDecayProfile profile = new MoodDecayProfile(-10, -5, -15, -20);
    HealthStatus newStatus = PetUtils.applyDecay(current, profile);

    assertEquals("Hunger should decrease by 10", 40, newStatus.getHunger());
    assertEquals("Hygiene should decrease by 5", 45, newStatus.getHygiene());
    assertEquals("Social should decrease by 15", 35, newStatus.getSocial());
    assertEquals("Sleep should decrease by 20", 30, newStatus.getSleep());
  }
}