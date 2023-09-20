package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.RoomType;

public class Room3Controller {

  @FXML private Label timeLabel;
  @FXML private Polygon polygonRoom3Puzzle;

  /** Initializes the room view, it is called when the room loads. */
  @FXML
  private void initialize() {
    initializeTimer();
  }

  @FXML
  private void room1ButtonClicked() {
    System.out.println("Room 1 button clicked");
    App.setUi(RoomType.ROOM1);
  }

  @FXML
  private void room3PuzzleClicked(MouseEvent event) {
    System.out.println("Room 3 puzzle clicked");
    App.setUi(RoomType.ROOM3PUZZLE);
  }

  private void initializeTimer() {
    timeLabel.textProperty().bind(GameState.timeManager.getSecond().asString());
  }
}
