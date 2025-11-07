package pet;

/**
 * Utility functions for pets.
 */
public class PetUtils {
  // Clamp a value between 0 and 100
  public static int clamp(int value, NeedLimits limits) {
    return Math.max(limits.min(), Math.min(value, limits.max()));
  }

  /**
   * Apply a decay profile to a health status.
   *
   * @param current the current health status
   * @param profile the decay profile to apply
   * @return the new health status after applying the decay profile
   */
  public static HealthStatus applyDecay(HealthStatus current, MoodDecayProfile profile) {
    return new HealthStatus(
        clamp(current.getHunger() + profile.hunger(), NeedConfig.HUNGER),
        clamp(current.getHygiene() + profile.hygiene(), NeedConfig.HYGIENE),
        clamp(current.getSocial() + profile.social(), NeedConfig.SOCIAL),
        clamp(current.getSleep() + profile.sleep(), NeedConfig.SLEEP)
    );
  }

}
