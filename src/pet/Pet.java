package pet;

/**
 * Represents a pet.
 */
public class Pet implements PetInterface {
  private static final double HUNGER_NEGLECT_RATIO = 0.25;
  private static final int HUNGER_STREAK_LIMIT = 3;
  private static final double SAD_RATIO = 0.5;
  private final NeedLimits hungerLimits;
  private final NeedLimits hygieneLimits;
  private final NeedLimits socialLimits;
  private final NeedLimits sleepLimits;
  private HealthStatus health;
  private MoodEnum mood;
  private int hungerLowStreak;
  private MoodStrategy moodStrategy;
  private PetState state;

  private boolean justWokeUp = false;

  /**
   * Constructs a new Pet object with default values.
   */
  public Pet() {
    this.sleepLimits = NeedConfig.SLEEP;
    this.hungerLimits = NeedConfig.HUNGER;
    this.hygieneLimits = NeedConfig.HYGIENE;
    this.socialLimits = NeedConfig.SOCIAL;
    this.health =
        new HealthStatus(hungerLimits.min(), hygieneLimits.max(),
            socialLimits.min(), sleepLimits.max());
    this.mood = MoodEnum.HAPPY;
    this.hungerLowStreak = 0;
    this.moodStrategy = new HappyMood();
    this.state = PetState.Active;
  }

  @Override
  public void step() {
    if (isDead()) {
      return;
    }
    if (state == PetState.Sleeping) {
      applySleepStep();
    } else {
      moodStrategy.step(this);
    }
    updateMood();
    updateDeath();
  }

  /**
   * Applies the feed step to the pet.
   */
  private void applySleepStep() {
    // pet will recover some sleep, get hungrier and dirtier while sleeping
    int sleep = PetUtils.clamp(health.getSleep() + 10, sleepLimits);
    int hunger = PetUtils.clamp(health.getHunger() + 5, hungerLimits);
    int hygiene = PetUtils.clamp(health.getHygiene() - 5, hygieneLimits);
    this.health = new HealthStatus(hunger, hygiene, health.getSocial(), sleep);
  }


  // toggle the sleep state of the pet
  private void toggleSleep() {
    if (state == PetState.Sleeping) {
      setState(PetState.Active);
      justWokeUp = true;
    } else {
      setState(PetState.Sleeping);
      justWokeUp = false;
    }
  }

  @Override
  public void interactWith(Action action) {
    if (isDead()) {
      return;
    }
    // handle sleep separately
    if (action == Action.SLEEP) {
      // toggle sleep state first
      toggleSleep();
      // recover from sleep
      if (state == PetState.Sleeping) {
        moodStrategy.respondToAction(action, this);
      }
      updateMood();
      updateDeath();
      return;
    }
    // block other actions while sleeping
    if (state == PetState.Sleeping) {
      return;
    }
    // handle normal actions
    moodStrategy.respondToAction(action, this);
    updateMood();
    updateDeath();

  }


  @Override
  public HealthStatus getHealth() {
    return health;
  }

  public void setHealth(HealthStatus health) {
    this.health = health;
  }

  @Override
  public MoodEnum getMood() {
    return mood;
  }

  @Override
  public void setMood(MoodEnum mood) {
    this.mood = mood;
    this.moodStrategy = (mood == MoodEnum.HAPPY) ? new HappyMood() : new SadMood();
  }

  @Override
  public int getHunger() {
    return health.getHunger();
  }

  @Override
  public int getHygiene() {
    return health.getHygiene();
  }

  @Override
  public int getSocial() {
    return health.getSocial();
  }

  @Override
  public int getSleep() {
    return health.getSleep();
  }

  @Override
  public NeedLimits getHungerLimits() {
    return hungerLimits;
  }

  @Override
  public NeedLimits getHygieneLimits() {
    return hygieneLimits;
  }

  @Override
  public NeedLimits getSocialLimits() {
    return socialLimits;
  }

  @Override
  public NeedLimits getSleepLimits() {
    return sleepLimits;
  }

  @Override
  public PetState getState() {
    return state;
  }

  @Override
  public void setState(PetState state) {
    this.state = state;
  }

  @Override
  public void updateDeath() {
    if (health.getHunger() == hungerLimits.max()
        || health.getHygiene() == hygieneLimits.min()
        || health.getSocial() == socialLimits.max()
        || health.getSleep() == sleepLimits.min()) {
      setState(PetState.Dead);
    }
  }


  // check if the pet is dead
  public boolean isDead() {
    return state == PetState.Dead;
  }


  private void updateStreaks() {
    if (getHunger() >= hungerLimits.max() * HUNGER_NEGLECT_RATIO) {
      hungerLowStreak++;
    } else {
      hungerLowStreak = 0;
    }
  }

  /**
   * Updates the mood of the pet based on its health status.
   */
  public void updateMood() {
    if (isDead()) {
      return;
    }
    updateStreaks();
    if (hungerLowStreak >= HUNGER_STREAK_LIMIT) {
      setMood(MoodEnum.SAD);
    } else if (health.getHunger() >= hungerLimits.max() * SAD_RATIO
        || health.getHygiene() <= hygieneLimits.max() * SAD_RATIO
        || health.getSocial() >= socialLimits.max() * SAD_RATIO
        || health.getSleep() <= sleepLimits.max() * SAD_RATIO) {
      setMood(MoodEnum.SAD);
    } else {
      setMood(MoodEnum.HAPPY);
    }
  }

  /**
   * Checks if the pet is sleeping.
   *
   * @return true if the pet is sleeping, false otherwise
   */
  public boolean isSleeping() {
    return state == PetState.Sleeping;
  }

  /**
   * Checks if the pet just woke up.
   *
   * @return true if the pet just woke up, false otherwise
   */
  public boolean isJustWokeUp() {
    return justWokeUp;
  }

  /**
   * Checks if the pet is hungry and needs to be fed.
   *
   * @return true if the pet is hungry, false otherwise
   */
  public boolean isHungryWarning() {
    return hungerLowStreak >= HUNGER_STREAK_LIMIT;
  }
}
