package pet;

/**
 * Represents the sad mood of a pet.
 */
public class SadMood implements MoodStrategy {
  // The boost profile for a sad pet
  private static final MoodBoostProfile BOOST = new MoodBoostProfile(-10, 10, -10, 10);
  // The decay profile for the a sad pet
  private static final MoodDecayProfile DECAY = new MoodDecayProfile(10, -10, 10, -10);
  int hunger;

  /**
   * Responds to the given action by updating the pet's health status.
   *
   * @param action the action to respond to
   * @param pet    the pet to respond with
   */
  public void respondToAction(Action action, Pet pet) {
    HealthStatus oldHealth = pet.getHealth();
    int hunger = oldHealth.getHunger();
    int hygiene = oldHealth.getHygiene();
    int social = oldHealth.getSocial();
    int sleep = oldHealth.getSleep();

    switch (action) {
      case FEED:
        hunger = PetUtils.clamp(hunger + BOOST.hunger(), pet.getHungerLimits());
        break;
      case PLAY:
        social = PetUtils.clamp(social + BOOST.social(), pet.getSocialLimits());
        break;
      case CLEAN:
        hygiene = PetUtils.clamp(hygiene + BOOST.hygiene(), pet.getHygieneLimits());
        break;
      case SLEEP:
        sleep = PetUtils.clamp(sleep + BOOST.sleep(), pet.getSleepLimits());
        break;
      default:
        throw new IllegalArgumentException("Invalid action: " + action);
    }

    // If hunger is low and the player chooses a non-feeding action,
    // the pet feels neglected and becomes slightly hungrier
    if (oldHealth.getHunger() < pet.getHungerLimits().max() / 2 && action != Action.FEED) {
      hunger = PetUtils.clamp(hunger + 5, pet.getHungerLimits());
    }
    pet.setHealth(new HealthStatus(hunger, hygiene, social, sleep));
  }


  @Override
  public void step(Pet pet) {
    pet.setHealth(PetUtils.applyDecay(pet.getHealth(), DECAY));
  }
}
