package pet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the Pet class.
 */

public class PetTest {
  private Pet pet;
  private int initialHunger;
  private int initialHygiene;
  private int initialSocial;
  private int initialSleep;
  private MoodEnum initialMood;

  /**
   * Sets up the test environment before each test.
   * Initializes a Pet object.
   */
  @Before
  public void setUp() {
    pet = new Pet();
    pet.setHealth(new HealthStatus(30, 60, 30, 60));
    initialHunger = pet.getHunger();
    initialHygiene = pet.getHygiene();
    initialSocial = pet.getSocial();
    initialSleep = pet.getSleep();
    initialMood = pet.getMood();

  }

  @After
  public void tearDown() {
    pet = null;
  }

  @Test
  public void testInitialize() {
    pet = new Pet();
    assertEquals(0, pet.getHealth().getHunger());
    assertEquals(100, pet.getHealth().getHygiene());
    assertEquals(0, pet.getHealth().getSocial());
    assertEquals(100, pet.getHealth().getSleep());
    assertEquals(PetState.Active, pet.getState());
    assertEquals(MoodEnum.HAPPY, pet.getMood());
  }


  @Test
  public void testMoodChangesToSadAfterDecay() {
    // Loop through 10 steps to trigger the mood change
    for (int i = 0; i < 10; i++) {
      pet.step();
    }
    assertEquals(MoodEnum.SAD, pet.getMood());
  }

  @Test
  public void testStepWhenSleeping() {
    // Tests that pet will recover 10 sleep, 5 hunger
    // and decrease hygiene by 5 when sleeping for 1 step
    pet.setState(PetState.Sleeping);
    pet.step();
    assertEquals(initialSleep + 10, pet.getHealth().getSleep());
    assertEquals(initialHunger + 5, pet.getHealth().getHunger());
    assertEquals(initialHygiene - 5, pet.getHealth().getHygiene());
    assertEquals(initialSocial, pet.getHealth().getSocial());
  }

  @Test
  public void testSleepToggle() {
    // Tests that pet will toggle between sleeping and active
    pet.setState(PetState.Active);
    pet.interactWith(Action.SLEEP);
    assertEquals(PetState.Sleeping, pet.getState());
    pet.interactWith(Action.SLEEP);
    assertEquals(PetState.Active, pet.getState());
  }

  @Test
  public void testStepIfDead() {
    // Tests that pet will not change health if dead
    pet.setState(PetState.Dead);
    pet.step();
    assertEquals(initialHunger, pet.getHealth().getHunger());
    assertEquals(initialHygiene, pet.getHealth().getHygiene());
    assertEquals(initialSocial, pet.getHealth().getSocial());
    assertEquals(initialSleep, pet.getHealth().getSleep());
  }

  @Test
  public void testStepInHappyMood() {
    // Tests that pet will increase hunger by 5, decrease hygiene by 5,
    // increase social by 5, and decrease sleep by 5 in 1 step
    pet.setMood(MoodEnum.HAPPY);
    pet.step();
    assertEquals(initialHunger + 5, pet.getHealth().getHunger());
    assertEquals(initialHygiene - 5, pet.getHealth().getHygiene());
    assertEquals(initialSocial + 5, pet.getHealth().getSocial());
    assertEquals(initialSleep - 5, pet.getHealth().getSleep());
  }

  @Test
  public void testStepInSadMood() {
    // Tests that pet will decrease hunger by 10, increase hygiene by 10,
    // decrease social by 10, and increase sleep by 10 in 1 step
    pet.setMood(MoodEnum.SAD);
    pet.step();
    assertEquals(initialHunger + 10, pet.getHealth().getHunger());
    assertEquals(initialHygiene - 10, pet.getHealth().getHygiene());
    assertEquals(initialSocial + 10, pet.getHealth().getSocial());
    assertEquals(initialSleep - 10, pet.getHealth().getSleep());
  }

  @Test
  public void testInteractWithFeedInHappyMood() {
    // Tests that feeding a pet in happy mood will decrease hunger by 15
    pet.interactWith(Action.FEED);
    assertEquals(initialHunger - 15, pet.getHealth().getHunger());
    assertEquals(initialHygiene, pet.getHealth().getHygiene());
    assertEquals(initialSocial, pet.getHealth().getSocial());
    assertEquals(initialSleep, pet.getHealth().getSleep());
  }

  @Test
  public void testInteractWithFeedInSadMood() {
    // Tests that feeding a pet in sad mood will decrease hunger by 10
    pet.setMood(MoodEnum.SAD);
    pet.interactWith(Action.FEED);
    assertEquals(initialHunger - 10, pet.getHealth().getHunger());
    assertEquals(initialHygiene, pet.getHealth().getHygiene());
    assertEquals(initialSocial, pet.getHealth().getSocial());
    assertEquals(initialSleep, pet.getHealth().getSleep());
  }

  @Test
  public void testInteractWithPlayInHappyMood() {
    // Tests that playing with a pet in happy mood will decrease social by 15
    pet.interactWith(Action.PLAY);
    assertEquals(initialHunger, pet.getHealth().getHunger());
    assertEquals(initialHygiene, pet.getHealth().getHygiene());
    assertEquals(initialSocial - 15, pet.getHealth().getSocial());
    assertEquals(initialSleep, pet.getHealth().getSleep());
  }

  @Test
  public void testInteractWithPlayInSadMood() {
    // Tests that playing with a hungry pet in sad mood will decrease social by 10
    // and increase its hunger by 5
    pet.setMood(MoodEnum.SAD);
    pet.interactWith(Action.PLAY);
    assertEquals(initialHunger + 5, pet.getHealth().getHunger());
    assertEquals(initialHygiene, pet.getHealth().getHygiene());
    assertEquals(initialSocial - 10, pet.getHealth().getSocial());
    assertEquals(initialSleep, pet.getHealth().getSleep());
  }

  @Test
  public void testInteractWithCleanInHappyMood() {
    // Tests that cleaning a pet in happy mood will increase hygiene by 15
    pet.interactWith(Action.CLEAN);
    assertEquals(initialHunger, pet.getHealth().getHunger());
    assertEquals(initialHygiene + 15, pet.getHealth().getHygiene());
    assertEquals(initialSocial, pet.getHealth().getSocial());
    assertEquals(initialSleep, pet.getHealth().getSleep());
  }

  @Test
  public void testInteractWithCleanInSadMood() {
    // Tests that cleaning a hungry pet in sad mood will increase hygiene by 10
    // and increase its hunger by 5
    pet.setMood(MoodEnum.SAD);
    pet.interactWith(Action.CLEAN);
    assertEquals(initialHunger + 5, pet.getHealth().getHunger());
    assertEquals(initialHygiene + 10, pet.getHealth().getHygiene());
    assertEquals(initialSocial, pet.getHealth().getSocial());
    assertEquals(initialSleep, pet.getHealth().getSleep());
  }

  @Test
  public void testInteractWithSleepInHappyMood() {
    // Tests that sleeping a pet in happy mood will increase sleep by 15
    pet.interactWith(Action.SLEEP);
    assertEquals(initialHunger, pet.getHealth().getHunger());
    assertEquals(initialHygiene, pet.getHealth().getHygiene());
    assertEquals(initialSocial, pet.getHealth().getSocial());
    assertEquals(initialSleep + 15, pet.getHealth().getSleep());
  }

  @Test
  public void testInteractWithSleepInSadMood() {
    // Tests that sleeping a hungry pet in sad mood will increase sleep by 10
    // and increase its hunger by 5
    pet.setMood(MoodEnum.SAD);
    pet.interactWith(Action.SLEEP);
    assertEquals(initialHunger + 5, pet.getHealth().getHunger());
    assertEquals(initialHygiene, pet.getHealth().getHygiene());
    assertEquals(initialSocial, pet.getHealth().getSocial());
    assertEquals(initialSleep + 10, pet.getHealth().getSleep());
  }

  @Test
  public void testInteractIfDead() {
    // Tests that pet will not change health if dead
    pet.setState(PetState.Dead);
    pet.interactWith(Action.FEED);
    pet.interactWith(Action.PLAY);
    pet.interactWith(Action.CLEAN);
    pet.interactWith(Action.SLEEP);
    assertEquals(initialHunger, pet.getHealth().getHunger());
    assertEquals(initialHygiene, pet.getHealth().getHygiene());
    assertEquals(initialSocial, pet.getHealth().getSocial());
    assertEquals(initialSleep, pet.getHealth().getSleep());
  }

  @Test
  public void testNoInteractionWhileSleeping() {
    pet.setState(PetState.Sleeping);
    pet.interactWith(Action.FEED);
    pet.interactWith(Action.PLAY);
    pet.interactWith(Action.CLEAN);
    assertEquals(initialHunger, pet.getHealth().getHunger());
    assertEquals(initialHygiene, pet.getHealth().getHygiene());
    assertEquals(initialSocial, pet.getHealth().getSocial());
    assertEquals(initialSleep, pet.getHealth().getSleep());
  }

  @Test
  public void testSleepActionWorksWhileSleeping() {
    // Tests that pet will wake up when sleeping
    // and waking up will not increase sleep
    pet.setState(PetState.Sleeping);
    pet.interactWith(Action.SLEEP);
    assertEquals(PetState.Active, pet.getState());
    assertEquals(initialSleep, pet.getHealth().getSleep());
  }

  @Test
  public void testSleepActionWorksWhileActive() {
    // Tests that pet will sleep when active
    // and sleeping will increase sleep
    pet.setState(PetState.Active);
    pet.interactWith(Action.SLEEP);
    assertEquals(PetState.Sleeping, pet.getState());
    assertEquals(initialSleep + 15, pet.getHealth().getSleep());
  }

  @Test
  public void getHealth() {
    assertEquals(30, pet.getHealth().getHunger());
    assertEquals(60, pet.getHealth().getHygiene());
    assertEquals(30, pet.getHealth().getSocial());
    assertEquals(60, pet.getHealth().getSleep());
  }

  @Test
  public void setHealth() {
    pet.setHealth(new HealthStatus(50, 50, 50, 50));
    assertEquals(50, pet.getHealth().getHunger());
    assertEquals(50, pet.getHealth().getHygiene());
    assertEquals(50, pet.getHealth().getSocial());
    assertEquals(50, pet.getHealth().getSleep());
  }

  @Test
  public void getMood() {
    assertEquals(initialMood, pet.getMood());
  }

  @Test
  public void setMood() {
    pet.setMood(MoodEnum.SAD);
    assertEquals(MoodEnum.SAD, pet.getMood());
  }

  @Test
  public void testUpdateMood() {
    assertEquals(initialMood, pet.getMood());
    pet.setHealth(new HealthStatus(100, 0, 100, 0));
    pet.updateMood();
    assertEquals(MoodEnum.SAD, pet.getMood());
  }

  @Test
  public void testUpdateMoodWhenDead() {
    pet.setState(PetState.Dead);
    pet.updateMood();
    assertEquals(initialMood, pet.getMood());
  }

  @Test
  public void testUpdateMoodWhenNeglectingFeedNeedForTooLong() {
    pet.interactWith(Action.PLAY);
    pet.interactWith(Action.CLEAN);
    pet.interactWith(Action.SLEEP);
    pet.interactWith(Action.SLEEP);
    pet.updateMood();
    assertEquals(MoodEnum.SAD, pet.getMood());
    pet.interactWith(Action.FEED);
    pet.updateMood();
    assertEquals(MoodEnum.HAPPY, pet.getMood());
  }

  @Test
  public void getHunger() {
    assertEquals(30, pet.getHunger());
  }

  @Test
  public void getHygiene() {
    assertEquals(60, pet.getHygiene());
  }

  @Test
  public void getSocial() {
    assertEquals(30, pet.getSocial());
  }

  @Test
  public void getSleep() {
    assertEquals(60, pet.getSleep());
  }

  @Test
  public void getHungerLimits() {
    assertEquals(0, pet.getHungerLimits().min());
    assertEquals(100, pet.getHungerLimits().max());
  }

  @Test
  public void getHygieneLimits() {
    assertEquals(0, pet.getHygieneLimits().min());
    assertEquals(100, pet.getHygieneLimits().max());

  }

  @Test
  public void getSocialLimits() {
    assertEquals(0, pet.getSocialLimits().min());
    assertEquals(100, pet.getSocialLimits().max());
  }

  @Test
  public void getSleepLimits() {
    assertEquals(0, pet.getSleepLimits().min());
    assertEquals(100, pet.getSleepLimits().max());
  }

  @Test
  public void getState() {
    assertEquals(PetState.Active, pet.getState());
  }

  @Test
  public void testSetState() {
    pet.setState(PetState.Dead);
    assertEquals(PetState.Dead, pet.getState());
    pet.setState(PetState.Sleeping);
    assertEquals(PetState.Sleeping, pet.getState());
  }

  @Test
  public void updateDeath() {
    pet.setHealth(new HealthStatus(100, 0, 100, 0));
    pet.updateDeath();
    assertEquals(PetState.Dead, pet.getState());
  }

  @Test
  public void testIsDead() {
    // Tests that pet is not dead when health is not at the limits
    assertFalse(pet.isDead());
    // Tests that pet is dead when health is at the limits
    pet.setHealth(new HealthStatus(100, 0, 100, 0));
    pet.updateDeath();
    assertTrue(pet.isDead());
  }

  @Test
  public void testIsSleeping() {
    // Tests that pet is not sleeping when state is not sleeping
    assertFalse(pet.isSleeping());
    // Tests that pet is sleeping when state is sleeping
    pet.setState(PetState.Sleeping);
    assertTrue(pet.isSleeping());
  }

  @Test
  public void testIsJustWokeUp() {
    // Tests that pet is not just woke up initially
    assertFalse(pet.isJustWokeUp());
    // Tests that pet is just woke up when it wakes up from sleeping
    pet.setState(PetState.Sleeping);
    pet.interactWith(Action.SLEEP);
    assertTrue(pet.isJustWokeUp());
  }

  @Test
  public void testIsHungryWarning() {
    // Tests that initially, there should be no hunger warning
    assertFalse(pet.isHungryWarning());
    // Simulate actions without feeding while hunger is low
    // This should eventually trigger a hunger warning
    pet.setHealth(new HealthStatus(60, 100, 0, 100));
    pet.interactWith(Action.CLEAN);
    pet.interactWith(Action.PLAY);
    pet.interactWith(Action.SLEEP);
    // After several interactions without feeding, a hunger warning should appear
    assertTrue(pet.isHungryWarning());
  }

}