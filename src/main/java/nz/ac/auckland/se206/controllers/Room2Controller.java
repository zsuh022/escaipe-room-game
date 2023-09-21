package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.RoomType;

public class Room2Controller {
  @FXML private Polygon room2Box;
  @FXML private Polygon room2Lock;
  @FXML private Pane keyShowingPane;
  @FXML private Label room2KeyLabel;
  @FXML private Pane indicationPane;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    initializeTimer();
    keyShowingPane.setVisible(false);
    GameState.isPuzzleRoom2Solved.addListener(
        (observable, oldValue, newValue) -> {
          if (newValue) {
            showRoom2Key();
          }
        });
    GameState.currentRoom.addListener(
        (obs, oldRoom, newRoom) -> {
          if (thisIsCurrentRoom(newRoom)) {
            fadeInOutIndicationPane();
          }
        });
  }

  private boolean thisIsCurrentRoom(Number roomNumber) {
    return roomNumber.intValue() == 2;
  }

  private void fadeInOutIndicationPane() {
    FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), indicationPane);
    fadeIn.setFromValue(0);
    fadeIn.setToValue(0.7);
    PauseTransition pause = new PauseTransition(Duration.seconds(1));
    FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), indicationPane);
    fadeOut.setFromValue(0.7);
    fadeOut.setToValue(0);

    fadeIn.setOnFinished(event -> pause.play());
    pause.setOnFinished(event -> fadeOut.play());

    fadeIn.play();
  }

  private void showRoom2Key() {
    System.out.println("Showing room 2 key");
    keyShowingPane.setVisible(true);
    room2KeyLabel.setText(GameState.room2Key);
  }

  @FXML
  private void buttonClicked() {
    System.out.println("Button clicked");
    GameState.currentRoom.set(1);
    App.setUi(RoomType.ROOM1);
  }

  @FXML private Label timeLabel;

  private void initializeTimer() {
    timeLabel.textProperty().bind(GameState.timeManager.getSecond().asString());
  }

  @FXML
  public void room2BoxClicked(MouseEvent event) throws IOException {
    System.out.println("box clicked");
    App.setUi(RoomType.ROOM2PUZZLE2);
  }

  @FXML
  public void room2LockClicked(MouseEvent event) throws IOException {
    System.out.println("lock clicked");
    App.setUi(RoomType.ROOM2PUZZLE);
  }
}
