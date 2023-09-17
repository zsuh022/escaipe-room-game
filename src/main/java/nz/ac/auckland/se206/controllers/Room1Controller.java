package nz.ac.auckland.se206.controllers;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.roomType;

/** Controller class for the room view. */
public class Room1Controller {

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    System.out.println("key " + event.getCode() + " pressed");
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyReleased(KeyEvent event) {
    System.out.println("key " + event.getCode() + " released");
  }

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    // Initialization code goes here
  }

  @FXML
  private void room2ButtonClicked() {
    System.out.println("Room 2 button clicked");
    App.setUi(roomType.ROOM2);
  }

  @FXML
  private void room3ButtonClicked() {
    System.out.println("Room 3 button clicked");
    App.setUi(roomType.ROOM3);
  }

  @FXML
  public void passwordButtonClicked(Event event) {
    EventTarget target = event.getTarget();
    Button button = (Button) target;
    updateKeyLabel(button.getText());
  }

  @FXML private Label keyLabel;

  @FXML
  public void updateKeyLabel(String key) {
    if (key.equals("x")) {
      keyLabel.setText("");
      return;
    }
    if (keyLabel.getText().length() >= 6) {
      return;
    }

    if (key.equals("Enter")) {
      checkKey();
      return;
    }

    String prev = keyLabel.getText();
    keyLabel.setText(prev + key);
  }

  private void checkKey() {}

  @FXML Pane keyInserter;

  @FXML ImageView smallKeyInserter;

  @FXML
  public void openKeyInserter() {
    keyInserter.setVisible(true);
    smallKeyInserter.setVisible(false);
  }

  @FXML
  public void closeKeyInserter() {
    keyInserter.setVisible(false);
    smallKeyInserter.setVisible(true);
  }
}
