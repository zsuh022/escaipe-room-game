package nz.ac.auckland.se206.controllers;

import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import nz.ac.auckland.se206.App;

public class MenuTutorialController {

  @FXML private MediaView earthMediaView;

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
