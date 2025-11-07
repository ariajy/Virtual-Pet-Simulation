package pet;


/**
 * Represents the mood strategy of a pet.
 */

public interface MoodStrategy {
  void respondToAction(Action action, Pet pet);

  void step(Pet pet);
}
