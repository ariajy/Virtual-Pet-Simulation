package controller;

import pet.Action;
import pet.HealthStatus;
import pet.Pet;
import view.PetView;

/**
 * The PetController class is responsible for handling user interactions with the pet.
 * It updates the pet's state and the view accordingly.
 */
public class PetController {
  private final Pet pet;
  private final PetView view;

  /**
   * Constructor for the PetController class.
   *
   * @param pet  The pet object to be controlled.
   * @param view The view object to be updated.
   */
  public PetController(Pet pet, PetView view) {
    this.pet = pet;
    this.view = view;

    // Attach action listeners to buttons
    view.getFeedButton().addActionListener(e -> handleInteraction(Action.FEED));
    view.getPlayButton().addActionListener(e -> handleInteraction(Action.PLAY));
    view.getCleanButton().addActionListener(e -> handleInteraction(Action.CLEAN));
    view.getSleepButton().addActionListener(e -> handleInteraction(Action.SLEEP));
    view.getStepButton().addActionListener(e -> stepGame());

    updateView("Your pet is excited to see you!");
  }

  private void handleInteraction(Action action) {
    pet.interactWith(action);
    String actionMsg = "You " + action.name().toLowerCase() + " your pet.";
    if (pet.isSleeping()) {
      updateView("Your pet is snoozing hard! ");
    } else if (action == Action.SLEEP && pet.isJustWokeUp()) {
      updateView(
          "Your pet just woke up! ");
    } else if (pet.isHungryWarning() && action != Action.FEED) {
      updateView("Your pet is very hungry! Feed it soon!");
    } else {
      updateView(actionMsg);
    }
  }

  private void stepGame() {
    pet.step();
    String stepMsg = "Time has passed. Your pet's needs have changed.";
    updateView(stepMsg);
  }

  private void updateView(String basicMessage) {
    String messageToShow = basicMessage;
    HealthStatus health = pet.getHealth();
    view.updateNeedPanels(
        health.getHunger(),
        health.getHygiene(),
        health.getSocial(),
        health.getSleep()
    );
    view.setMood(pet.getMood().name());
    if (pet.isDead()) {
      view.setPetDead();
      messageToShow = "Your pet has gone to the great beyond...";
    }
    view.updateStatus(messageToShow);
  }

}