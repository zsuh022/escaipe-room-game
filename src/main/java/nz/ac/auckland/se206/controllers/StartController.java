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

/**
 * StartController is used to manage the user interactions and set all the UIs at the start of the
 * game.
 */
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

  /**
   * Initializes the room view, it is called when the room loads.
   *
   * @throws URISyntaxException if the URI syntax is invalid
   */
  @FXML
  private void initialize() throws URISyntaxException {
    initializeEarthMediaView();
    initializeInfoLabels();
  }

  /**
   * Handles the user click on the "Easy" difficulty option.
   *
   * @param event the MouseEvent triggered by the user's action.
   */
  @FXML
  private void onClickEasy(MouseEvent event) {
    setDifficultyColour(easyLabel, "easy");
    GameState.gameDifficulty = 1;
  }

  /**
   * Handles the user click on the "Hard" difficulty option.
   *
   * @param event the MouseEvent triggered by the user's action.
   */
  @FXML
  private void onClickHard(MouseEvent event) {
    setDifficultyColour(hardLabel, "hard");
    GameState.gameDifficulty = 3;
  }

  /**
   * Handles the user click on the "Medium" difficulty option.
   *
   * @param event the MouseEvent triggered by the user's action.
   */
  @FXML
  private void onClickMedium(MouseEvent event) {
    setDifficultyColour(mediumLabel, "medium");
    GameState.gameDifficulty = 2;
  }

  /** Handles the event when the user's mouse exits the difficulty pane. */
  @FXML
  private void onExitDifficultyPane() {
    difficultyInfoLabel.setVisible(false);
  }

  /** Handles the event when the user's mouse exits the easy label. */
  @FXML
  private void onExitEasyDifficulty() {
    easyHintLabel.setVisible(false);
  }

  /** Handles the event when the user's mouse exits the hard label. */
  @FXML
  private void onExitHardDifficulty() {
    hardHintLabel.setVisible(false);
  }

  /** Handles the event when the user's mouse exits the medium label. */
  @FXML
  private void onExitMediumDifficulty() {
    mediumHintLabel.setVisible(false);
  }

  /** Handles the event when the user's mouse exits the time pane. */
  @FXML
  private void onExitTimePane() {
    timeInfoLabel.setVisible(false);
  }

  /** Handles the event when the user's mouse hovers over the "Easy" difficulty option. */
  @FXML
  private void onHoverEasy() {
    easyHintLabel.setVisible(true);
  }

  /** Handles the event when the user's mouse hovers over the "Hard" difficulty option. */
  @FXML
  private void onHoverHard() {
    hardHintLabel.setVisible(true);
  }

  /** Handles the event when the user's mouse hovers over the "Medium" difficulty option. */
  @FXML
  private void onHoverMedium() {
    mediumHintLabel.setVisible(true);
  }

  /** Handles the user click on the "NewGame" button. */
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

  /**
   * Handles the event when the user selects the difficulty option. It displays the general
   * information label about difficulty selection.
   */
  @FXML
  private void onSelectDifficulty() {
    difficultyInfoLabel.setVisible(true);
  }

  /**
   * Handles the event when the user selects the time option. It displays the general information
   * label about time selection.
   */
  @FXML
  private void onSelectTime() {
    timeInfoLabel.setVisible(true);
  }

  /** Handles the user click on the "Start" button. */
  @FXML
  private void onStartButtonClicked() {
    setKey();
    fadeInNextImageView();
  }

  /** The method fades in the next image view. */
  private void fadeInNextImageView() {
    // fade in next image view
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

  /**
   * Initializes the media view for the rotating earth video.
   *
   * @throws URISyntaxException if there's an error parsing the URI for the video.
   */
  private void initializeEarthMediaView() throws URISyntaxException {
    // set up earth video
    Media earthMedia =
        new Media(App.class.getResource("/videos/earthRotating.mp4").toURI().toString());
    earthPlayer = new MediaPlayer(earthMedia);
    earthPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    earthMediaView.setMediaPlayer(earthPlayer);
    earthPlayer.play();
  }

  /** This method initializes the visibility of the start pane information labels. */
  private void initializeInfoLabels() {
    // set information labels to invisible
    difficultyInfoLabel.setVisible(false);
    timeInfoLabel.setVisible(false);
  }

  /** This method displays the "Please select a difficulty!" message for 1 second. */
  private void showSelectDifficultyMessage() {
    // show select difficulty message for 1 second
    selectDifficultyLabel.setVisible(true);
    PauseTransition hideLabel = new PauseTransition(Duration.seconds(1));
    hideLabel.setOnFinished(e -> selectDifficultyLabel.setVisible(false));
    hideLabel.play();
  }

  /**
   * This method changes the colour of the difficulty labels when the user clicks on them. Only one
   * label can be selected at a time.
   *
   * @param clickedLabel The label representing the difficulty that was clicked.
   * @param difficulty The string representation of the difficulty level ("easy", "medium", or
   *     "hard").
   */
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

  /**
   * This method sets the game difficulty based on the style of the difficulty labels. If "Easy"
   * label is selected, the game difficulty will be set to 1. If "Medium" label is selected, the
   * game difficulty will be set to 2. If "Hard" label is selected, the game difficulty will be set
   * to 3.
   */
  private void setGameDifficulty() {
    if (easyLabel.getStyleClass().contains("easyDifficulty")) {
      GameState.gameDifficulty = 1;
    } else if (mediumLabel.getStyleClass().contains("mediumDifficulty")) {
      GameState.gameDifficulty = 2;
    } else if (hardLabel.getStyleClass().contains("hardDifficulty")) {
      GameState.gameDifficulty = 3;
    }
  }

  /**
   * This method sets the game time based on the value of the time slider. If the value of the time
   * slider is 0, the game time will be set to 360 seconds. If the value of the time slider is 1,
   * the game time will be set to 240 seconds. If the value of the time slider is 2, the game time
   * will be set to 120 seconds.
   */
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

  /**
   * This method sets the game key based on a random number. If the random number is 1, the key will
   * be 12041961. If the random number is 2, the key will be 19041971. If the random number is 3,
   * the key will be 16071969.
   */
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
   * Updates the UI and loads game scenes after the fade transition.
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
