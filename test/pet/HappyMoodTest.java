package pet;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the HappyMood class.
 * This class tests the functionality of the HappyMood class, including
 * the response to actions and the step method.
 */
public class HappyMoodTest {
  private HappyMood mood;
  private Pet pet;
  private int initialHunger;
  private int initialHygiene;
  private int initialSocial;
  private int initialSleep;

  /**
   * Sets up the test environment before each test.
   * Initializes a Pet object and a HappyMood object.
   */
  @Before
  public void setUp() {
    pet = new Pet();
    mood = new HappyMood();
    pet.setHealth(new HealthStatus(60, 60, 60, 60));
    initialHunger = pet.getHunger();
    initialHygiene = pet.getHygiene();
    initialSocial = pet.getSocial();
    initialSleep = pet.getSleep();
  }

  @After
  public void tearDown() {
    pet = null;
    mood = null;
  }

  /**
   * Tests that feeding a pet in HappyMood decreases hunger by 15,
   * and does not affect other health attributes (hygiene, social, sleep).
   */
  @Test
  public void testFeedBoostsHungerInHappyMood() {
    mood.respondToAction(Action.FEED, pet);
    assertEquals("Hunger should decrease by 15", initialHunger - 15, pet.getHunger());
    assertEquals("Hygiene should not change", initialHygiene, pet.getHygiene());
    assertEquals("Social should not change", initialSocial, pet.getSocial());
    assertEquals("Sleep should not change", initialSleep, pet.getSleep());
  }

  /**
   * Tests that the respondToAction method does not exceed the limits.
   */
  @Test
  public void testRespondToActionWillNotExceedLimits() {
    // Set the pet's health to maximum values
    pet.setHealth(new HealthStatus(0, 100, 0, 100));

    // Test feeding the pet
    mood.respondToAction(Action.FEED, pet);
    assertEquals("Hunger should not exceed limits", 0, pet.getHunger());

    // Test playing with the pet
    mood.respondToAction(Action.PLAY, pet);
    assertEquals("Social should not exceed limits", 0, pet.getSocial());

    // Test cleaning the pet
    mood.respondToAction(Action.CLEAN, pet);
    assertEquals("Hygiene should not exceed limits", 100, pet.getHygiene());

    // Test sleeping the pet
    mood.respondToAction(Action.SLEEP, pet);
    assertEquals("Sleep should not exceed limits", 100, pet.getSleep());
  }

  /**
   * Tests that playing a pet in HappyMood decreases social by 15,
   * and does not affect other health attributes (hygiene, hunger, sleep).
   */
  @Test
  public void testPlayBoostsSocialInHappyMood() {
    mood.respondToAction(Action.PLAY, pet);
    assertEquals("Social should decrease by 15", initialSocial - 15, pet.getSocial());
    assertEquals("Hygiene should not change", initialHygiene, pet.getHygiene());
    assertEquals("Hunger should not change", initialHunger, pet.getHunger());
    assertEquals("Sleep should not change", initialSleep, pet.getSleep());
  }

  /**
   * Tests that cleaning a pet in HappyMood increases hygiene by 15,
   * and does not affect other health attributes (hygiene, hunger, social).
   */
  @Test
  public void testCleanBoostsHygieneInHappyMood() {
    mood.respondToAction(Action.CLEAN, pet);
    assertEquals("Hygiene should increase by 15", initialHygiene + 15, pet.getHygiene());
    assertEquals("Social should not change", initialSocial, pet.getSocial());
    assertEquals("Sleep should not change", initialSleep, pet.getSleep());
    assertEquals("Hunger should not change", initialHunger, pet.getHunger());
  }

  /**
   * Tests that sleeping a pet in HappyMood increases sleep by 15,
   * and does not affect other health attributes (hygiene, hunger, social).
   */
  @Test
  public void testSleepBoostsSleepInHappyMood() {
    mood.respondToAction(Action.SLEEP, pet);
    assertEquals("Sleep should increase by 15", initialSleep + 15, pet.getSleep());
    assertEquals("Hygiene should not change", initialHygiene, pet.getHygiene());
    assertEquals("Social should not change", initialSocial, pet.getSocial());
    assertEquals("Hunger should not change", initialHunger, pet.getHunger());
  }

  /**
   * Tests that the step method applies the decay correctly.
   * The expected values are based on the HappyMood decay profile.
   */
  @Test
  public void testStepAppliesDecayCorrectly() {
    mood.step(pet);
    HealthStatus updated = pet.getHealth();

    // Decay for HappyMood: hunger +5, hygiene -5, social +5, sleep -5
    assertEquals(65, updated.getHunger());
    assertEquals(55, updated.getHygiene());
    assertEquals(65, updated.getSocial());
    assertEquals(55, updated.getSleep());
  }

  /**
   * Tests that the step method does not exceed the limits.
   * The expected values are based on the HappyMood decay profile.
   */
  @Test
  public void testStepDoesNotExceedLimits() {
    // Set the pet's health to maximum values
    pet.setHealth(new HealthStatus(100, 0, 100, 0));

    // Apply the step method
    mood.step(pet);
    HealthStatus updated = pet.getHealth();

    // Check that the values do not exceed the limits
    assertEquals(100, updated.getHunger());
    assertEquals(0, updated.getHygiene());
    assertEquals(100, updated.getSocial());
    assertEquals(0, updated.getSleep());
  }
}