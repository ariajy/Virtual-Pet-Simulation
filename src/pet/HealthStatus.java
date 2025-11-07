package pet;

/**
 * Represents the health status of a pet.
 */
public class HealthStatus {
  private final int hunger;
  private final int hygiene;
  private final int social;
  private final int sleep;

  /**
   * Constructs a new HealthStatus object with the given values.
   *
   * @param hunger  the hunger level
   * @param hygiene the hygiene level
   * @param social  the social level
   * @param sleep   the sleep level
   */
  public HealthStatus(int hunger, int hygiene, int social, int sleep) {
    this.hunger = hunger;
    this.hygiene = hygiene;
    this.social = social;
    this.sleep = sleep;
  }

  public int getHunger() {
    return hunger;
  }

  public int getHygiene() {
    return hygiene;
  }

  public int getSocial() {
    return social;
  }

  public int getSleep() {
    return sleep;
  }

  @Override
  public String toString() {
    return "HealthStatus{"
        + "hunger=" + hunger
        + ", hygiene=" + hygiene
        + ", social=" + social
        + ", sleep=" + sleep + '}';
  }
}
