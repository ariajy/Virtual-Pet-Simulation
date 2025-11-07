import controller.PetController;
import pet.Pet;
import view.PetView;

/**
 * The MyPetMain class is the entry point of the application.
 * It initializes the pet, view, and controller.
 */
public class MyPetMain {
  /**
   * The main method initializes the application.
   * It creates a Pet object, a PetView object, and a PetController object.
   * It sets up the initial state of the view and makes it visible.
   *
   * @param args Command line arguments (not used).
   */
  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(() -> {
      Pet pet = new Pet();
      PetView view = new PetView();
      PetController controller = new PetController(pet, view);
      // Set up the initial state of the view
      view.setVisible(true);
    });
  }
}