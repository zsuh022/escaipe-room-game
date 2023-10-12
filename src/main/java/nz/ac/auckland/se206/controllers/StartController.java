package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MusicManager;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.RoomType;

public class StartController {

  @FXML private Button btnNewGame;
  @FXML private Button btnStart;
  @FXML private ImageView nextImageView;
  @FXML private Label easyLabel;
  @FXML private Label easyHintLabel;
  @FXML private Label difficultyInfoLabel;
  @FXML private Label hardLabel;
  @FXML private Label hardHintLabel;
  @FXML private Label mediumLabel;
  @FXML private Label mediumHintLabel;
  @FXML private Label selectDifficultyLabel;
  @FXML private Label timeInfoLabel;
  @FXML private MediaView earthMediaView;
  @FXML private Pane difficultyPane;
  @FXML private Pane startPane;
  @FXML private Pane timePane;
  @FXML private Pane tutorialPane;
  @FXML private Slider timeSlider;

  private MediaPlayer earthPlayer;

  @FXML
  private void initialize() throws URISyntaxException {
    initializeEarthMediaView();
    initializeInfoLabels();
  }

  @FXML
  private void onClickEasy(MouseEvent event) {
    setDifficultyColour(easyLabel, "easy");
    GameState.gameDifficulty = 1;
  }

  @FXML
  private void onClickHard(MouseEvent event) {
    setDifficultyColour(hardLabel, "hard");
    GameState.gameDifficulty = 3;
  }

  @FXML
  private void onClickMedium(MouseEvent event) {
    setDifficultyColour(mediumLabel, "medium");
    GameState.gameDifficulty = 2;
  }

  @FXML
  private void onExitDifficultyPane() {
    difficultyInfoLabel.setVisible(false);
  }

  @FXML
  private void onExitEasyDifficulty() {
    easyHintLabel.setVisible(false);
  }

  @FXML
  private void onExitHardDifficulty() {
    hardHintLabel.setVisible(false);
  }

  @FXML
  private void onExitMediumDifficulty() {
    mediumHintLabel.setVisible(false);
  }

  @FXML
  private void onExitTimePane() {
    timeInfoLabel.setVisible(false);
  }

  @FXML
  private void onHoverEasy() {
    easyHintLabel.setVisible(true);
  }

  @FXML
  private void onHoverHard() {
    hardHintLabel.setVisible(true);
  }

  @FXML
  private void onHoverMedium() {
    mediumHintLabel.setVisible(true);
  }

  @FXML
  private void onNewGameButtonClicked() {
    if (GameState.gameDifficulty == 0) {
      showSelectDifficultyMessage();

    } else {
      startPane.setVisible(false);
      tutorialPane.setVisible(true);

      // set game difficulty and time
      setGameDifficulty();
      setGameTime();
    }
  }

  @FXML
  private void onSelectDifficulty() {
    difficultyInfoLabel.setVisible(true);
  }

  @FXML
  private void onSelectTime() {
    timeInfoLabel.setVisible(true);
  }

  @FXML
  private void onStartButtonClicked() {
    // set key
    setKey();

    fadeInNextImageView();
  }

  /** this method will fade in the next image view. */
  private void fadeInNextImageView() {
    // fade in next image view
    btnStart.setVisible(false);
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

  private void initializeEarthMediaView() throws URISyntaxException {
    // set up earth video
    Media earthMedia =
        new Media(App.class.getResource("/videos/earthRotating.mp4").toURI().toString());
    earthPlayer = new MediaPlayer(earthMedia);
    earthMediaView.setMediaPlayer(earthPlayer);
    earthPlayer.play();
  }

  private void initializeInfoLabels() {
    // set information labels to invisible
    difficultyInfoLabel.setVisible(false);
    timeInfoLabel.setVisible(false);
  }

  private void showSelectDifficultyMessage() {
    // show select difficulty message for 1 second
    selectDifficultyLabel.setVisible(true);
    PauseTransition hideLabel = new PauseTransition(Duration.seconds(1));
    hideLabel.setOnFinished(e -> selectDifficultyLabel.setVisible(false));
    hideLabel.play();
  }

  private void setDifficultyColour(Label clickedLabel, String difficulty) {
    // reset all labels to original colour
    easyLabel.getStyleClass().removeAll("easyDifficulty");
    mediumLabel.getStyleClass().removeAll("mediumDifficulty");
    hardLabel.getStyleClass().removeAll("hardDifficulty");

    easyLabel.getStyleClass().add("difficulties");
    mediumLabel.getStyleClass().add("difficulties");
    hardLabel.getStyleClass().add("difficulties");

    // set the clicked label to its respective colour
    switch (difficulty) {
      case "easy":
        clickedLabel.getStyleClass().removeAll("difficulties");
        clickedLabel.getStyleClass().add("easyDifficulty");
        break;
      case "medium":
        clickedLabel.getStyleClass().removeAll("difficulties");
        clickedLabel.getStyleClass().add("mediumDifficulty");
        break;
      case "hard":
        clickedLabel.getStyleClass().removeAll("difficulties");
        clickedLabel.getStyleClass().add("hardDifficulty");
        break;
    }
  }

  private void setGameDifficulty() {
    if (easyLabel.getStyleClass().contains("easyDifficulty")) {
      GameState.gameDifficulty = 1;
    } else if (mediumLabel.getStyleClass().contains("mediumDifficulty")) {
      GameState.gameDifficulty = 2;
    } else if (hardLabel.getStyleClass().contains("hardDifficulty")) {
      GameState.gameDifficulty = 3;
    }
  }

  private void setGameTime() {
    // set game time
    switch ((int) timeSlider.getValue()) {
      case 0:
        GameState.timeManager.setTime(360);
        break;
      case 1:
        GameState.timeManager.setTime(240);
        break;
      case 2:
        GameState.timeManager.setTime(120);
        break;
    }
  }

  private void setKey() {
    // get a random integer to set the game key
    Random random = new Random();
    int number = random.nextInt(3) + 1;
    switch (number) {
      case 1:
        // if the random number is 1, the key will be 12041961
        GameState.key = 12041961;
        GameState.room2Key = "12";
        GameState.room1Key = "04";
        GameState.room3Key = "1961";
        break;
      case 2:
        // if the random number is 2, the key will be 19041971
        GameState.key = 19041971;
        GameState.room2Key = "19";
        GameState.room1Key = "04";
        GameState.room3Key = "1971";
        break;
      case 3:
        // if the random number is 3, the key will be 16071969
        GameState.key = 16071969;
        GameState.room2Key = "16";
        GameState.room1Key = "07";
        GameState.room3Key = "1969";
        break;
    }

    System.out.println("key = " + GameState.key);
  }

  /**
   * Sets all the UIs after the fade transition. Loads various game scenes from their respective
   * FXML files and initializes the game state for the current room.
   *
   * @throws IOException if an FXML file is not found or cannot be loaded.
   * @throws URISyntaxException if there's an error parsing the URI for the FXML files.
   */
  private void setUiAfterFade() throws IOException, URISyntaxException {
    // set all the UIs after the fade transition
    // do not change the order below this line
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
    // do not change the order above this line

    // set ui for the current room
    GameState.currentRoom.set(1);
    App.setUi(RoomType.ROOM1);

    earthPlayer.stop();
    MusicManager.playGameSong();
    GameState.timeManager.setTimer();
  }
}
