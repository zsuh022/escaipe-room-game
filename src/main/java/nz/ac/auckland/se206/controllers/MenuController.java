package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.roomType;

public class MenuController {

  @FXML private Slider difficultySlider;
  @FXML private Slider timeSlider;
  @FXML private MediaView earthMpfour;
  MediaPlayer player;

  /**
   * Initializes the room view, it is called when the room loads.
   *
   * @throws URISyntaxException
   */
  public void initialize() throws URISyntaxException {
    Media media = new Media(App.class.getResource("/sounds/earth.mp4").toURI().toString());
    player = new MediaPlayer(media);
    earthMpfour.setMediaPlayer(player);
    player.seek(Duration.millis(0));
    player.play();
  }

  @FXML
  private void buttonClicked() throws IOException {
    setDifficulty();
    setGameTime();
    setKey();
    SceneManager.addUi(roomType.ROOM1, App.loadFxml("room1"));
    SceneManager.addUi(roomType.ROOM2, App.loadFxml("room2"));
    SceneManager.addUi(roomType.ROOM3, App.loadFxml("room3"));
    SceneManager.addUi(roomType.ROOM3PUZZLE, App.loadFxml("room3puzzle"));
    SceneManager.addUi(roomType.ROOM4, App.loadFxml("room4"));
    SceneManager.addUi(roomType.GAMEMASTER, App.loadFxml("gamemaster"));
    App.setUi(roomType.ROOM1);
    GameState.timeManager.setTimer();
    player.stop();
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
    System.out.println(number + GameState.key);
  }
}
