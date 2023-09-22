package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MusicManager;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.RoomType;

public class MenuController {

  @FXML private Button startButton;
  @FXML private ImageView nextImageView;
  @FXML private Label difficultyLabel;
  @FXML private Label timeLabel;
  @FXML private MediaView earthMpfour;
  @FXML private Slider difficultySlider;
  @FXML private Slider timeSlider;

  private MediaPlayer player;
  private final StringProperty difficultyLabelColor = new SimpleStringProperty("#ffffff");
  private final StringProperty timeLabelColor = new SimpleStringProperty("#ffffff");

  /**
   * Initializes the room view, it is called when the room loads.
   *
   * @throws URISyntaxException
   */
  public void initialize() throws URISyntaxException {
    // initialize images and labels
    nextImageView.setVisible(false);
    startButton.setVisible(true);
    initializeLabelColour();

    // initialize music
    Media media = new Media(App.class.getResource("/sounds/earth.mp4").toURI().toString());
    player = new MediaPlayer(media);
    earthMpfour.setMediaPlayer(player);
    player.seek(Duration.millis(0));
    player.play();
  }

  private void initializeLabelColour() {
    // set label for different colours
    difficultyLabelColor.bind(
        Bindings.when(difficultySlider.valueProperty().isEqualTo(1))
            .then("aqua")
            .otherwise(
                Bindings.when(difficultySlider.valueProperty().isEqualTo(2))
                    .then("#bf00ff")
                    .otherwise(
                        Bindings.when(difficultySlider.valueProperty().isEqualTo(3))
                            .then("magenta")
                            .otherwise("white"))));
    // Apply the label color binding to the text fill property of difficultyLabel
    difficultyLabel
        .textFillProperty()
        .bind(
            Bindings.createObjectBinding(
                () -> Paint.valueOf(difficultyLabelColor.get()), difficultyLabelColor));
    // Bind label color to the value of the time slider
    timeLabelColor.bind(
        Bindings.when(timeSlider.valueProperty().isEqualTo(1))
            .then("aqua")
            .otherwise(
                Bindings.when(timeSlider.valueProperty().isEqualTo(2))
                    .then("#bf00ff")
                    .otherwise(
                        Bindings.when(timeSlider.valueProperty().isEqualTo(3))
                            .then("magenta")
                            .otherwise("white"))));
    // Apply the label color binding to the text fill property of timeLabel
    timeLabel
        .textFillProperty()
        .bind(
            Bindings.createObjectBinding(
                () -> Paint.valueOf(timeLabelColor.get()), timeLabelColor));
  }

  @FXML
  private void clickButton() throws IOException, URISyntaxException {
    // set difficulty, time, and key
    setDifficulty();
    setGameTime();
    setKey();
    fadeInNextImageView();
  }

  private void fadeInNextImageView() {
    // fade in next image view
    startButton.setVisible(false);
    nextImageView.setVisible(true);
    FadeTransition fade = new FadeTransition(Duration.millis(1000), nextImageView);
    fade.setFromValue(0.0);
    fade.setToValue(1.0);

    // set the order of the transitions
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
    // set all the uis after fade
    // do not change the order here********************
    SceneManager.addUi(RoomType.CHAT, App.loadFxml("chat"));
    SceneManager.addUi(RoomType.ROOM1, App.loadFxml("room1"));
    SceneManager.addUi(RoomType.ROOM2, App.loadFxml("room2"));
    SceneManager.addUi(RoomType.ROOM3, App.loadFxml("room3"));
    SceneManager.addUi(RoomType.ROOM2PUZZLE1, App.loadFxml("room2Puzzle1"));
    SceneManager.addUi(RoomType.ROOM3PUZZLE1, App.loadFxml("room3puzzle1"));
    SceneManager.addUi(RoomType.EXITDOOR, App.loadFxml("exitdoor"));
    SceneManager.addUi(RoomType.ROOM2PUZZLE2, App.loadFxml("room2Puzzle2"));
    SceneManager.addUi(RoomType.ROOM3PUZZLE2, App.loadFxml("room3Puzzle2"));
    SceneManager.addUi(RoomType.GAMEMASTER, App.loadFxml("gamemaster"));
    // do not change the order here********************

    // set ui for the current room
    GameState.currentRoom.set(1);
    App.setUi(RoomType.ROOM1);
    player.stop();
    MusicManager.playGameSong();
    GameState.timeManager.setTimer();
  }

  private void setDifficulty() {
    // set difficulty
    GameState.difficulty = (int) difficultySlider.getValue();
  }

  private void setGameTime() {
    // set game time
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
    // get a random integer to set the game key
    Random random = new Random();
    int number = random.nextInt(3) + 1;
    switch (number) {
      case 1:
        GameState.key = 12041961;
        GameState.room2Key = "12";
        GameState.room1Key = "04";
        GameState.room3Key = "1961";
        break;
      case 2:
        GameState.key = 19041971;
        GameState.room2Key = "19";
        GameState.room1Key = "04";
        GameState.room3Key = "1971";
        break;
      case 3:
        GameState.key = 16071969;
        GameState.room2Key = "16";
        GameState.room1Key = "07";
        GameState.room3Key = "1969";
        break;
    }
  }
}
