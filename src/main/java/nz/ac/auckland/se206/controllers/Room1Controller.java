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

  @FXML private ImageView crossImage;
  @FXML private ImageView waveImage;
  @FXML private Label room1KeyLabel;
  @FXML private Label timeLabel;
  @FXML private Pane indicationPane;
  @FXML private Pane keyShowingPane;

  /** Initializes the room view, it is called when the room loads. */
  @FXML
  private void initialize() {
    initializeTimer();
    keyShowingPane.setVisible(false);
    indicationPane.setOpacity(0);
    // add music
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
    // if the game was muted in another room adjust accordingly
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
  }

  @FXML
  private void onComputerClicked() {
    // go to chat
    System.out.println("Computer clicked");
    App.setUi(RoomType.CHAT);
  }

  @FXML
  private void onExitDoorButtonClicked() {
    // go to Room 4
    System.out.println("Exit door button clicked");
    GameState.currentRoom.set(4);
    App.setUi(RoomType.EXITDOOR);
    GameState.roomNumber = 4;
  }

  @FXML
  private void onGameMasterClicked() {
    // go to game master
    System.out.println("Game master clicked");
    GameState.currentRoom.set(5);
    App.setUi(RoomType.GAMEMASTER);
    GameState.roomNumber = 1;
  }

  @FXML
  private void onRoom2ButtonClicked() {
    // go to Room 2
    System.out.println("Room 2 button clicked");
    GameState.currentRoom.set(2);
    App.setUi(RoomType.ROOM2);
    GameState.roomNumber = 2;
  }

  @FXML
  private void onRoom3ButtonClicked() {
    // go to Room 3
    System.out.println("Room 3 button clicked");
    GameState.currentRoom.set(3);
    App.setUi(RoomType.ROOM3);
    GameState.roomNumber = 3;
  }

  @FXML
  private void onMuteBarClicked() {
    // mute the music
    System.out.println("Mute bar clicked");
    GameState.isMuted.set(!GameState.isMuted.get());
  }

  private void initializeTimer() {
    // bind the time label to the time manager
    timeLabel.textProperty().bind(GameState.timeManager.getSecond().asString());
  }

  private void fadeInOutIndicationPane() {
    // set up pane along the bottom
    indicationPane.setVisible(true);
    FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), indicationPane);
    fadeIn.setFromValue(0);
    fadeIn.setToValue(1);

    PauseTransition pause = new PauseTransition(Duration.seconds(0.95));

    FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), indicationPane);
    fadeOut.setFromValue(1);
    fadeOut.setToValue(0);

    // fade in, pause, fade out
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

  public void showRoom1Key() {
    // show the room 1 key
    System.out.println("Room 1 key shown");
    keyShowingPane.setVisible(true);
    room1KeyLabel.setText(GameState.room1Key);
  }

  private boolean thisIsCurrentRoom(Number roomNumber) {
    return roomNumber.intValue() == 1;
  }
}
