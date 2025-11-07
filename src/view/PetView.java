package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * The PetView class represents the graphical user interface for the Virtual Pet application.
 * It extends JFrame and contains components to display the pet's health status, mood, and image,
 * as well as buttons for interacting with the pet.
 */
public class PetView extends JFrame {
  private final JLabel moodLabel;
  private final JLabel imageLabel;
  private final JButton feedButton;
  private final JButton playButton;
  private final JButton cleanButton;
  private final JButton sleepButton;
  private final JButton stepButton;
  private final NeedStatusPanel hungerPanel;
  private final NeedStatusPanel hygienePanel;
  private final NeedStatusPanel sleepPanel;
  private final NeedStatusPanel socialPanel;
  private final JLabel statusLabel;


  /**
   * Constructs a new PetView with all UI components.
   * Sets up the window layout, buttons, labels, and mood menu.
   * Initializes the pet's image to "HAPPY" mood.
   */
  public PetView() {
    setTitle("Virtual Pet");
    setSize(600, 400); // Increase the size of the window
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // Mini-panels for each stat

    hungerPanel = new NeedStatusPanel("Hunger");
    hygienePanel = new NeedStatusPanel("Hygiene");
    sleepPanel = new NeedStatusPanel("Sleep");
    socialPanel = new NeedStatusPanel("Social");

    JPanel needsPanel = new JPanel(new GridLayout(2, 2)); // 2x2 display
    needsPanel.add(hungerPanel);
    needsPanel.add(hygienePanel);
    needsPanel.add(sleepPanel);
    needsPanel.add(socialPanel);

    JPanel statusPanel = new JPanel(new GridLayout(3, 1));
    statusPanel.add(needsPanel);

    // Mood Display
    moodLabel = new JLabel("Mood: HAPPY", SwingConstants.CENTER);
    statusPanel.add(moodLabel);
    // Status Label
    statusLabel = new JLabel("", SwingConstants.CENTER);
    statusPanel.add(statusLabel);

    add(statusPanel, BorderLayout.NORTH);
    // Image Display
    imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
    add(imageLabel, BorderLayout.CENTER);

    // Interaction Buttons
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(1, 5, 10, 10)); // Set layout to 1 row and 5 columns

    feedButton = new JButton("Feed");
    playButton = new JButton("Play");
    cleanButton = new JButton("Clean");
    sleepButton = new JButton("Sleep");
    stepButton = new JButton("Step");

    buttonPanel.add(feedButton);
    buttonPanel.add(playButton);
    buttonPanel.add(cleanButton);
    buttonPanel.add(sleepButton);
    buttonPanel.add(stepButton);

    add(buttonPanel, BorderLayout.SOUTH); // Add the panel to the bottom of the window

    updateImage("HAPPY");

    setVisible(true);
  }

  /**
   * Sets the pet's mood and updates both the mood label and the displayed image.
   *
   * @param mood the mood to be set, as a String
   */
  public void setMood(String mood) {
    updateMood(mood);
    updateImage(mood);
  }

  /**
   * Updates the need panels with the specified values.
   * Each panel is updated with a value and a boolean indicating if the value is in a good state.
   *
   * @param hunger  the hunger value
   * @param hygiene the hygiene value
   * @param social  the social value
   * @param sleep   the sleep value
   */
  public void updateNeedPanels(int hunger, int hygiene, int social, int sleep) {
    hungerPanel.updateValue(hunger, hunger >= 50);
    hygienePanel.updateValue(hygiene, hygiene <= 50);
    socialPanel.updateValue(social, social >= 50);
    sleepPanel.updateValue(sleep, sleep <= 50);
  }

  /**
   * Updates the pet's image based on the specified image name.
   * If the image file does not exist, it sets the label to display the image name in uppercase.
   *
   * @param imageName the name of the image file
   */
  public void updateImage(String imageName) {
    try {
      InputStream imageStream =
          getClass().getResourceAsStream("/images/" + imageName.toLowerCase() + ".png");

      if (imageStream == null) {
        File imageFile = new File("/images/" + imageName.toLowerCase() + ".png");
        if (imageFile.exists()) {
          imageStream = new FileInputStream(imageFile);
        } else {
          imageLabel.setIcon(null);
          imageLabel.setText(imageName.toUpperCase() + " (Image not found)");
          return;
        }
      }
      Image image = ImageIO.read(imageStream);
      Image scaledImage = image.getScaledInstance(256, 256, Image.SCALE_SMOOTH);
      imageLabel.setIcon(new ImageIcon(scaledImage));
      imageStream.close();
    } catch (IOException e) {
      imageLabel.setIcon(null);
      imageLabel.setText(imageName.toUpperCase() + " (Image not found)");
    }
  }

  /**
   * Updates the mood label with the specified text.
   *
   * @param moodText the text representing the pet's mood
   */
  public void updateMood(String moodText) {
    moodLabel.setText("Mood: " + moodText);
  }

  /**
   * Returns the feed button for attaching listeners.
   *
   * @return the feed button component
   */
  public JButton getFeedButton() {
    return feedButton;
  }

  /**
   * Returns the play button for attaching listeners.
   *
   * @return the play button component
   */
  public JButton getPlayButton() {
    return playButton;
  }

  /**
   * Returns the clean button for attaching listeners.
   *
   * @return the clean button component
   */
  public JButton getCleanButton() {
    return cleanButton;
  }

  /**
   * Returns the sleep button for attaching listeners.
   *
   * @return the sleep button component
   */
  public JButton getSleepButton() {
    return sleepButton;
  }

  /**
   * Returns the step button for attaching listeners.
   *
   * @return the step button component
   */
  public JButton getStepButton() {
    return stepButton;
  }

  /**
   * Sets the pet's image to a "dead" state and disables all interaction buttons.
   * This method is called when the pet's health reaches a critical state.
   */
  public void setPetDead() {
    updateImage("dead");

    // Disable buttons
    feedButton.setEnabled(false);
    playButton.setEnabled(false);
    cleanButton.setEnabled(false);
    sleepButton.setEnabled(false);
    stepButton.setEnabled(false);
  }

  /**
   * Displays a message dialog with the specified message.
   *
   * @param status the message to be displayed
   */
  public void updateStatus(String status) {
    statusLabel.setText(status);
  }

}