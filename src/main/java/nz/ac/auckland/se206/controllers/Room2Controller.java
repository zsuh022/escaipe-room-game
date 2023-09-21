package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.beans.binding.BooleanExpression;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MusicManager;
import nz.ac.auckland.se206.SceneManager.RoomType;

public class Room2Controller {

  @FXML private ImageView crossImage;
  @FXML private ImageView waveImage;
  @FXML private Polygon room2Box;
  @FXML private Polygon room2Lock;
  @FXML private Pane keyShowingPane;
  @FXML private Label room2KeyLabel;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    initializeTimer();
    initializeMuteButton();
    keyShowingPane.setVisible(false);
    GameState.isPuzzleRoom2Solved.addListener(
        (observable, oldValue, newValue) -> {
          if (newValue) {
            showRoom2Key();
          }
        });
  }

  private void showRoom2Key() {
    System.out.println("Showing room 2 key");
    keyShowingPane.setVisible(true);
    room2KeyLabel.setText(GameState.room2Key);
  }

  @FXML
  private void buttonClicked() {
    System.out.println("Button clicked");
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
}
