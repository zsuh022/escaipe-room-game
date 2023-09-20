package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.roomType;

public class Room2Controller {
  @FXML private Rectangle room2Box;
  @FXML private Rectangle room2Lock;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    initializeTimer();
  }

  @FXML
  private void buttonClicked() {
    System.out.println("Button clicked");
    App.setUi(roomType.ROOM1);
  }

  @FXML private Label timeLabel;

  private void initializeTimer() {
    timeLabel.textProperty().bind(GameState.timeManager.getSecond().asString());
  }

  @FXML
  public void room2BoxClicked(MouseEvent event) throws IOException {
    System.out.println("box clicked");
  }

  @FXML
  public void room2LockClicked(MouseEvent event) throws IOException {
    System.out.println("lock clicked");
    App.setUi(roomType.ROOM2PUZZLE);
  }
}
