package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Represents a panel that displays the status of a pet's need.
 */
public class NeedStatusPanel extends JPanel {
  private static final Color DEFAULT_COLOR = Color.BLACK;
  private final JLabel nameLabel;
  private final JLabel valueLabel;
  private final JLabel warningIcon;

  /**
   * Constructs a new NeedStatusPanel with the specified need name.
   *
   * @param needName The name of the need to be displayed.
   */
  public NeedStatusPanel(String needName) {
    setLayout(new BorderLayout());

    nameLabel = new JLabel(needName + ": ");
    valueLabel = new JLabel("0");
    warningIcon = new JLabel("⚠️");
    warningIcon.setVisible(false);

    JPanel left = new JPanel();
    left.setLayout(new FlowLayout(FlowLayout.LEFT));
    left.add(nameLabel);
    left.add(valueLabel);

    add(left, BorderLayout.CENTER);
    add(warningIcon, BorderLayout.EAST);
  }

  /**
   * Updates the displayed value of the need and sets the warning icon if necessary.
   *
   * @param value      The new value of the need.
   * @param shouldWarn Whether to show a warning icon.
   */
  public void updateValue(int value, boolean shouldWarn) {
    valueLabel.setText(String.valueOf(value));
    if (shouldWarn) {
      valueLabel.setForeground(Color.RED);
      warningIcon.setVisible(true);
    } else {
      valueLabel.setForeground(DEFAULT_COLOR);
      warningIcon.setVisible(false);
    }
  }
}
