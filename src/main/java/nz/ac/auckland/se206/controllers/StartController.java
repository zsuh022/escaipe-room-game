package nz.ac.auckland.se206.controllers;

import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import nz.ac.auckland.se206.App;

public class StartController {

  @FXML private Button btnNewGame;
  @FXML private Button btnStart;
  @FXML private Label easyLabel;
  @FXML private Label mediumLabel;
  @FXML private Label hardLabel;
  @FXML private MediaView earthMediaView;
  @FXML private Pane difficultyPane;
  @FXML private Pane timePane;
  @FXML private Slider timeSlider;

  @FXML
  private void initialize() throws URISyntaxException {
    // initialize video
    Media earthMedia =
        new Media(App.class.getResource("/videos/earthRotating.mp4").toURI().toString());
    MediaPlayer earthPlayer = new MediaPlayer(earthMedia);
    earthMediaView.setMediaPlayer(earthPlayer);
    earthPlayer.play();
  }
}
