package pet;

/**
 * Represents the happy mood of a pet.
 */
public class HappyMood implements MoodStrategy {
  // The mood boost profile for a happy pet
  private static final MoodBoostProfile BOOST = new MoodBoostProfile(-15, 15, -15, 15);
  // The mood decay profile for a happy pet
  private static final MoodDecayProfile DECAY = new MoodDecayProfile(5, -5, 5, -5);


  @Override
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
    pet.setHealth(new HealthStatus(hunger, hygiene, social, sleep));
  }

  /**
   * Decreases the health of the pet based on the mood's decay profile.
   *
   * @param pet the pet to update
   */
  @Override
  public void step(Pet pet) {
    pet.setHealth(PetUtils.applyDecay(pet.getHealth(), DECAY));
  }

}
