package pet;

/**
 * Represents the interface for a pet.
 */
public interface PetInterface {
  void step();

  void interactWith(Action action);

  HealthStatus getHealth();

  MoodEnum getMood();

  void setMood(MoodEnum mood);

  int getHunger();

  int getHygiene();

  int getSocial();

  int getSleep();

  NeedLimits getHungerLimits();

  NeedLimits getHygieneLimits();

  NeedLimits getSocialLimits();

  NeedLimits getSleepLimits();

  PetState getState();

  void setState(PetState state);

  boolean isDead();

  void updateDeath();
}
