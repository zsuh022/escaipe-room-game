package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.roomType;

public class Room2Controller {

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    initializeTimer();
  }

  // **************************************************
  // PLEASE CALL THIS METHOD WHEN THE PUZZLE IS SOLVED
  // **************************************************
  private void puzzleSolved() {
    System.out.println("Puzzle solved");
    GameState.isPuzzleRoom2Solved = true;
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
}
