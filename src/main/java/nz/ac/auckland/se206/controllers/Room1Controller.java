package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MusicManager;
import nz.ac.auckland.se206.SceneManager.roomType;

public class Room1Controller {

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    initializeTimer();
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
  private void room4ButtonClicked() {
    System.out.println("Room 4 button clicked");
    App.setUi(roomType.ROOM4);
  }

  @FXML
  private void computerClicked() {
    System.out.println("Computer clicked");
    App.setUi(roomType.CHAT);
  }

  @FXML
  private void gameMasterClicked() {
    System.out.println("Game master clicked");
    App.setUi(roomType.GAMEMASTER);
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
}
