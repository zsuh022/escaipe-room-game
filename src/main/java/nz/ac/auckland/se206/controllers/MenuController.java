package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MusicManager;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.roomType;

public class MenuController {

  @FXML private Slider difficultySlider;
  @FXML private Slider timeSlider;
  @FXML private MediaView earthMpfour;
  @FXML private ImageView nextImageView;
  @FXML private Button startButton;
  MediaPlayer player;

  /**
   * Initializes the room view, it is called when the room loads.
   *
   * @throws URISyntaxException
   */
  public void initialize() throws URISyntaxException {
    nextImageView.setVisible(false);
    startButton.setVisible(true);
    Media media = new Media(App.class.getResource("/sounds/earth.mp4").toURI().toString());
    player = new MediaPlayer(media);
    earthMpfour.setMediaPlayer(player);
    player.seek(Duration.millis(0));
    player.play();
  }

  @FXML
  private void buttonClicked() throws IOException, URISyntaxException {
    setDifficulty();
    setGameTime();
    setKey();
    fadeInNextImageView();
  }

  private void fadeInNextImageView() {
    startButton.setVisible(false);
    nextImageView.setVisible(true);
    FadeTransition fade = new FadeTransition(Duration.millis(1000), nextImageView);
    fade.setFromValue(0.0);
    fade.setToValue(1.0);

    fade.setOnFinished(
        e -> {
          try {
            setUiAfterFade();
          } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
          }
        });

    fade.play();
  }

  private void setUiAfterFade() throws IOException, URISyntaxException {
    SceneManager.addUi(roomType.ROOM1, App.loadFxml("room1"));
    SceneManager.addUi(roomType.ROOM2, App.loadFxml("room2"));
    SceneManager.addUi(roomType.ROOM3, App.loadFxml("room3"));
    SceneManager.addUi(roomType.ROOM3PUZZLE, App.loadFxml("room3puzzle"));
    SceneManager.addUi(roomType.ROOM4, App.loadFxml("room4"));
    SceneManager.addUi(roomType.GAMEMASTER, App.loadFxml("gamemaster"));
    App.setUi(roomType.ROOM1);
    player.stop();
    MusicManager.playGameSong();
    GameState.timeManager.setTimer();
  }

  private void setDifficulty() {
    GameState.difficulty = (int) difficultySlider.getValue();
  }

  private void setGameTime() {
    switch ((int) timeSlider.getValue()) {
      case 1:
        GameState.timeManager.setTime(120);
        break;
      case 2:
        GameState.timeManager.setTime(240);
        break;
      case 3:
        GameState.timeManager.setTime(360);
        break;
    }
  }

  private void setKey() {
    Random random = new Random();
    int number = random.nextInt(3) + 1;
    switch (number) {
      case 1:
        GameState.key = 12041961;
        break;
      case 2:
        GameState.key = 19041971;
        break;
      case 3:
        GameState.key = 16071969;
        break;
    }
    System.out.println("key = " + GameState.key);
  }
}
