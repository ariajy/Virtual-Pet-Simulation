package pet;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the SadMood class.
 * This class tests the functionality of the SadMood class, including
 * the response to actions and the step method.
 */
public class SadMoodTest {
  private SadMood mood;
  private Pet pet;
  private int initialHunger;
  private int initialHygiene;
  private int initialSocial;
  private int initialSleep;

  /**
   * Sets up the test environment before each test.
   * Initializes a Pet object and a SadMood object.
   */
  @Before
  public void setUp() {
    pet = new Pet();
    mood = new SadMood();
    pet.setHealth(new HealthStatus(40, 40, 40, 40));
    // Store the initial health status of the pet
    initialHunger = pet.getHunger();
    initialHygiene = pet.getHygiene();
    initialSocial = pet.getSocial();
    initialSleep = pet.getSleep();
  }

  /**
   * Cleans up the test environment after each test.
   * Sets the Pet and SadMood objects to null.
   */
  @After
  public void tearDown() {
    Pet pet = null;
    SadMood mood = null;
  }

  @Test
  public void testFeedDecreasesHungerInSadMood() {
    // Test that feeding a pet in SadMood decreases hunger by 10
    // and does not affect other health attributes (hygiene, social, sleep)

    mood.respondToAction(Action.FEED, pet);
    assertEquals("Hunger should decrease by 10", initialHunger - 10, pet.getHunger());
    assertEquals("Hygiene should not change", initialHygiene, pet.getHygiene());
    assertEquals("Social should not change", initialSocial, pet.getSocial());
    assertEquals("Sleep should not change", initialSleep, pet.getSleep());
  }

  @Test
  public void testRespondToActionWillNotExceedLimits() {
    // Test that the respondToAction method does not exceed the limits
    // of the pet's health status
    mood.respondToAction(Action.FEED, pet);
    assertEquals("Hunger should not exceed limits", initialHunger - 10, pet.getHunger());
    assertEquals("Hygiene should not exceed limits", initialHygiene, pet.getHygiene());
    assertEquals("Social should not exceed limits", initialSocial, pet.getSocial());
    assertEquals("Sleep should not exceed limits", initialSleep, pet.getSleep());
  }

  @Test
  public void testPlayDecreasesSocialInSadMood() {
    // Test that playing with a pet in SadMood decreases social by 10
    // and increases hunger by 5 for neglecting the need of feeding,
    // and does not affect other health attributes (hygiene, sleep)
    mood.respondToAction(Action.PLAY, pet);
    assertEquals("Social should decrease by 10", initialSocial - 10, pet.getSocial());
    assertEquals("Hunger should increase by 5", initialHunger + 5, pet.getHunger());
    assertEquals("Hygiene should not change", initialHygiene, pet.getHygiene());
    assertEquals("Sleep should not change", initialSleep, pet.getSleep());
  }

  @Test
  public void testCleanBoostsHygieneInSadMood() {
    // Test that cleaning a pet in SadMood increases hygiene by 10
    // and increases hunger by 5 for neglecting the need of feeding,
    // and does not affect other health attributes (social, sleep)
    mood.respondToAction(Action.CLEAN, pet);
    assertEquals("Hygiene should increase by 10", initialHygiene + 10, pet.getHygiene());
    assertEquals("Hunger should increase by 5", initialHunger + 5, pet.getHunger());
    assertEquals("Social should not change", initialSocial, pet.getSocial());
    assertEquals("Sleep should not change", initialSleep, pet.getSleep());
  }

  @Test
  public void testSleepBoostsSleepInSadMood() {
    // Test that sleeping a pet in SadMood increases sleep by 10
    // and increase hunger by 5 for neglecting the need of feeding,
    // does not affect other health attributes (hygiene, social)
    Pet pet = new Pet();
    pet.setHealth(new HealthStatus(40, 40, 40, 40));
    SadMood mood = new SadMood();

    mood.respondToAction(Action.SLEEP, pet);

    assertEquals("Sleep should increase by 10", initialSleep + 10, pet.getSleep());
    assertEquals("Hunger should increase by 5", initialHunger + 5, pet.getHunger());
    assertEquals("Hygiene should not change", initialHygiene, pet.getHygiene());
    assertEquals("Social should not change", initialSocial, pet.getSocial());
  }

  @Test
  public void testStepAppliesDecayCorrectly() {
    // Test that the step method applies the correct decay to the pet's health status
    mood.step(pet);
    assertEquals("Hunger should increase by 10", initialHunger + 10, pet.getHunger());
    assertEquals("Hygiene should decrease by 10", initialHygiene - 10, pet.getHygiene());
    assertEquals("Social should increase by 10", initialSocial + 10, pet.getSocial());
    assertEquals("Sleep should decrease by 10", initialSleep - 10, pet.getSleep());
  }

  @Test
  public void testStepsWillNotExceedLimits() {
    // Test that the step method does not exceed the limits of the pet's health status
    // Set the pet's health to maximum values
    pet.setHealth(new HealthStatus(100, 0, 100, 0));
    mood.step(pet);
    assertEquals("Hunger should not exceed limits", 100, pet.getHunger());
    assertEquals("Hygiene should not exceed limits", 0, pet.getHygiene());
    assertEquals("Social should not exceed limits", 100, pet.getSocial());
    assertEquals("Sleep should not exceed limits", 0, pet.getSleep());
  }
}