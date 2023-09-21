package nz.ac.auckland.se206.controllers;

import javafx.beans.binding.BooleanExpression;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MusicManager;
import nz.ac.auckland.se206.SceneManager.RoomType;

public class Room1Controller {

  @FXML private ImageView crossImage;
  @FXML private ImageView waveImage;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    initializeTimer();
    initializeMuteButton();
    keyShowingPane.setVisible(false);
    GameState.isRiddleResolved.addListener(
        (observable, oldValue, newValue) -> {
          if (newValue) {
            showRoom1Key();
          }
        });
  }

  @FXML
  private void room2ButtonClicked() {
    System.out.println("Room 2 button clicked");
    App.setUi(RoomType.ROOM2);
  }

  @FXML
  private void room3ButtonClicked() {
    System.out.println("Room 3 button clicked");
    App.setUi(RoomType.ROOM3);
  }

  @FXML
  private void room4ButtonClicked() {
    System.out.println("Exit door button clicked");
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
    App.setUi(RoomType.GAMEMASTER);
  }

  @FXML private Label timeLabel;

  private void initializeTimer() {
    timeLabel.textProperty().bind(GameState.timeManager.getSecond().asString());
  }

  private void initializeMuteButton() {
    crossImage.visibleProperty().bind(GameState.isMuted);
    waveImage.visibleProperty().bind(((BooleanExpression) GameState.isMuted).not());
  }

  @FXML
  private void muteBarClick() {
    System.out.println("Mute bar clicked");
    GameState.toggleMuted();
    updateMuteButton();
  }

  private void updateMuteButton() {
    if (GameState.isMuted()) {
      crossImage.setVisible(true);
      waveImage.setVisible(false);
      MusicManager.mute();
    } else {
      crossImage.setVisible(false);
      waveImage.setVisible(true);
      MusicManager.unmute();
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
