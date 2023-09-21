package nz.ac.auckland.se206.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MusicManager;
import nz.ac.auckland.se206.SceneManager.RoomType;

public class Room3Controller {

  @FXML private Label timeLabel;
  @FXML private Polygon polygonRoom3Puzzle;
  @FXML private Pane keyShowingPane;
  @FXML private Label room3KeyLabel;
  @FXML private Polygon polygonRoom3Puzzle2;
  @FXML private Pane puzzle2Pane;
  @FXML private Polygon polygon2Room3Puzzle2;

  /** Initializes the room view, it is called when the room loads. */
  @FXML
  private void initialize() {
    initializeTimer();
    keyShowingPane.setVisible(false);
    GameState.isPuzzleRoom3Solved.addListener(
        (observable, oldValue, newValue) -> {
          if (newValue) {
            showRoom3Key();
          }
        });
    GameState.currentRoom.addListener(
        (obs, oldRoom, newRoom) -> {
          if (thisIsCurrentRoom(newRoom)) {
            fadeInOutIndicationPane();
          }
        });
    GameState.isMuted.addListener(
        (obs, wasMuted, isNowMuted) -> {
          if (isNowMuted) {
            crossImage.setVisible(true);
            waveImage.setVisible(false);
            MusicManager.mute();
          } else {
            crossImage.setVisible(false);
            waveImage.setVisible(true);
            MusicManager.unmute();
          }
        });
    i = 0;
    polygonRoom3Puzzle.setVisible(true);
    puzzle2Pane.setVisible(true);
    puzzle2Pane.setOpacity(1);
    polygonRoom3Puzzle2.setVisible(true);
    polygon2Room3Puzzle2.setVisible(false);
  }

  @FXML private ImageView crossImage;
  @FXML private ImageView waveImage;

  @FXML
  private void muteBarClick() {
    GameState.isMuted.set(!GameState.isMuted.get());
  }

  private boolean thisIsCurrentRoom(Number roomNumber) {
    return roomNumber.intValue() == 3;
  }

  @FXML private Pane indicationPane;

  private void fadeInOutIndicationPane() {
    indicationPane.setVisible(true);
    FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), indicationPane);
    fadeIn.setFromValue(0);
    fadeIn.setToValue(1);

    PauseTransition pause = new PauseTransition(Duration.seconds(1));

    FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), indicationPane);
    fadeOut.setFromValue(1);
    fadeOut.setToValue(0);

    fadeIn.setOnFinished(
        event -> {
          pause.play();
        });
    pause.setOnFinished(
        event -> {
          fadeOut.play();
        });
    fadeOut.setOnFinished(
        e -> {
          indicationPane.setVisible(false);
        });
    fadeIn.play();
  }

  private void showRoom3Key() {
    System.out.println("Showing room 3 key");
    keyShowingPane.setVisible(true);
    room3KeyLabel.setText(GameState.room3Key);
  }

  @FXML
  private void room1ButtonClicked() {
    System.out.println("Room 1 button clicked");
    GameState.currentRoom.set(1);
    App.setUi(RoomType.ROOM1);
  }

  @FXML
  private void room3PuzzleClicked(MouseEvent event) {
    System.out.println("Room 3 puzzle clicked");
    App.setUi(RoomType.ROOM3PUZZLE);
  }

  private int i;

  @FXML
  private void room3Puzzle2Clicked() {
    System.out.println("Room 3 puzzle 2 clicked");
    App.setUi(RoomType.ROOM3PUZZLE2);
    if (i == 0) {
      puzzle2Pane.setOpacity(0.5);
      i++;
    } else if (i == 1) {
      puzzle2Pane.setOpacity(0.2);
      i++;
    } else {
      puzzle2Pane.setOpacity(0);
      polygonRoom3Puzzle2.setVisible(false);
      polygon2Room3Puzzle2.setVisible(true);
      puzzle2Pane.setVisible(false);
    }
  }

  private void initializeTimer() {
    timeLabel.textProperty().bind(GameState.timeManager.getSecond().asString());
  }
}
