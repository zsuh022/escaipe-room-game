package nz.ac.auckland.se206.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MusicManager;
import nz.ac.auckland.se206.SceneManager.RoomType;

public class Room1Controller {

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    initializeTimer();
    keyShowingPane.setVisible(false);
    GameState.isRiddleResolved.addListener(
        (observable, oldValue, newValue) -> {
          if (newValue) {
            showRoom1Key();
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
    return roomNumber.intValue() == 1;
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

  @FXML
  private void room2ButtonClicked() {
    System.out.println("Room 2 button clicked");
    GameState.currentRoom.set(2);
    App.setUi(RoomType.ROOM2);
  }

  @FXML
  private void room3ButtonClicked() {
    System.out.println("Room 3 button clicked");
    GameState.currentRoom.set(3);
    App.setUi(RoomType.ROOM3);
  }

  @FXML
  private void room4ButtonClicked() {
    System.out.println("Exit door button clicked");
    GameState.currentRoom.set(4);
    App.setUi(RoomType.EXITDOOR);
  }

  @FXML
  private void computerClicked() {
    System.out.println("Computer clicked");
    App.setUi(RoomType.CHAT);
  }

  @FXML
  private void gameMasterClicked() {
    System.out.println("Game master clicked");
    GameState.currentRoom.set(5);
    App.setUi(RoomType.GAMEMASTER);
  }

  @FXML private Label timeLabel;

  private void initializeTimer() {
    timeLabel.textProperty().bind(GameState.timeManager.getSecond().asString());
  }

  @FXML private ImageView crossImage;
  @FXML private ImageView waveImage;

  @FXML
  private void muteBarClick() {
    System.out.println("Mute bar clicked");
    if (GameState.isMuted) {
      GameState.isMuted = false;
      crossImage.setVisible(false);
      waveImage.setVisible(true);
      MusicManager.unmute();
    } else {
      GameState.isMuted = true;
      crossImage.setVisible(true);
      waveImage.setVisible(false);
      MusicManager.mute();
    }
  }

  @FXML private Label room1KeyLabel;
  @FXML private Pane keyShowingPane;

  public void showRoom1Key() {
    System.out.println("Room 1 key shown");
    keyShowingPane.setVisible(true);
    room1KeyLabel.setText(GameState.room1Key);
  }
}
