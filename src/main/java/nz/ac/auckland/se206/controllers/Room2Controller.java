package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.HintDisplayHelper;
import nz.ac.auckland.se206.MusicManager;
import nz.ac.auckland.se206.SceneManager.RoomType;

/**
 * Room2Controller class is used to control the room 2. It will show the puzzle and the key in room
 * 2.
 */
public class Room2Controller {

  @FXML private ImageView crossImage;
  @FXML private ImageView waveImage;
  @FXML private Label room2KeyLabel;
  @FXML private Label timeLabel;
  @FXML private Pane keyShowingPane;
  @FXML private Pane indicationPane;
  @FXML private Polygon polygonRoom2Puzzle1;
  @FXML private Polygon polygonRoom2Puzzle2;
  @FXML private TextArea aiMessageTextArea;
  @FXML private Button btnHint;

  private Random random = new Random();

  /** Initializes the room view, it is called when the room loads. */
  @FXML
  private void initialize() {
    // intialize room 2
    initializeTimer();
    initializePuzzle();
    if (GameState.gameDifficulty == 3) {
      btnHint.setVisible(false);
    } else {
      btnHint.setVisible(true);
    }
    keyShowingPane.setVisible(false);
    // initialize music
    GameState.isPuzzleRoom2Solved.addListener(
        (observable, oldValue, newValue) -> {
          if (newValue) {
            showRoom2Key();
          }
        });
    GameState.currentRoom.addListener(
        (obs, oldRoom, newRoom) -> {
          if (thisIsCurrentRoom(newRoom)) {
            fadeInOutIndicationPane();
          }
        });
    // m is a dummy variable to make the lambda expression work
    int m = 0;
    m = m + 1;
    System.out.println(m + m);
    GameState.isMuted.addListener(
        (obs, wasMuted, isNowMuted) -> {
          if (isNowMuted) {
            crossImage.setVisible(true);
            waveImage.setVisible(false);
            MusicManager.mute();
          } else {
            crossImage.setVisible(false);
            waveImage.setVisible(true);
            MusicManager.unmute();
          }
        });

    // bind the hint message to the hint text area
    aiMessageTextArea.textProperty().bind(GameState.sharedMessage);
    GameState.latestHint.addListener(
        (obs, oldHint, newHint) -> {
          HintDisplayHelper.displayHintInTextArea(aiMessageTextArea, newHint);
        });
  }

  /** This method will be called when the game master is clicked. */
  @FXML
  private void onGameMasterClicked() {
    // change to game master
    System.out.println("Game master clicked");
    GameState.currentRoom.set(5);
    App.setUi(RoomType.GAMEMASTER);
    GameState.roomNumber = 2;
  }

  /** This method will be called when the hint button is clicked. */
  @FXML
  private void onHintButtonClick() {
    GameState.requestHint.set(!GameState.requestHint.get());
    HintDisplayHelper.displayThreeDots();
  }

  /** This method will be called when the mute button is clicked. */
  @FXML
  private void onMuteBarClicked() {
    GameState.isMuted.set(!GameState.isMuted.get());
  }

  /** This method will be called when the first puzzle is clicked. */
  @FXML
  private void onPuzzle1Clicked(MouseEvent event) throws IOException {
    System.out.println("lock clicked");
    App.setUi(RoomType.ROOM2PUZZLE1);
  }

  /** This method will be called when the second puzzle is clicked. */
  @FXML
  private void onPuzzle2Clicked(MouseEvent event) throws IOException {
    System.out.println("box clicked");
    App.setUi(RoomType.ROOM2PUZZLE2);
  }

  /** This method will be called when the room 1 button is clicked. */
  @FXML
  private void onRoom1ButtonClicked() {
    // change to room 1
    System.out.println("Button clicked");
    GameState.currentRoom.set(1);
    App.setUi(RoomType.ROOM1);
    GameState.roomNumber = 1;
  }

  /** This method will fade in and out the indication pane. */
  private void fadeInOutIndicationPane() {
    // initalize indication pane
    indicationPane.setVisible(true);
    FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), indicationPane);
    fadeIn.setFromValue(0);
    fadeIn.setToValue(1);

    PauseTransition pause = new PauseTransition(Duration.seconds(0.98));

    FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), indicationPane);
    fadeOut.setFromValue(1);
    fadeOut.setToValue(0);

    // fade banner in and out
    fadeIn.setOnFinished(
        event -> {
          pause.play();
        });
    pause.setOnFinished(
        event -> {
          fadeOut.play();
        });
    fadeOut.setOnFinished(
        e -> {
          indicationPane.setVisible(false);
        });
    fadeIn.play();
  }

  /** This method will initialize the puzzle. */
  private void initializePuzzle() {
    random = new Random();
    int randomNumber = 1;
    if (GameState.round != 2) {
      randomNumber = random.nextInt(2) + 1;
    } else if (GameState.puzzleRoom23[0] == 1) {
      randomNumber = 2;
    } else if (GameState.puzzleRoom23[0] == 2) {
      randomNumber = 1;
    }

    // set which puzzle to do
    if (randomNumber == 1) {
      polygonRoom2Puzzle1.setVisible(true);
      polygonRoom2Puzzle2.setVisible(false);
      GameState.puzzleRoom23[0] = 1;
    } else {
      polygonRoom2Puzzle1.setVisible(false);
      polygonRoom2Puzzle2.setVisible(true);
      GameState.puzzleRoom23[0] = 2;
    }
  }

  /** This method will be setting the time label. */
  private void initializeTimer() {
    timeLabel.textProperty().bind(GameState.timeManager.getSecond().asString());
  }

  /** This method will show the room 2 key. */
  private void showRoom2Key() {
    System.out.println("Showing room 2 key");
    keyShowingPane.setVisible(true);
    room2KeyLabel.setText(GameState.room2Key);
  }

  /**
   * This method will check if this is the current room.
   *
   * @param roomNumber the room number
   * @return true if this is the current room
   */
  private boolean thisIsCurrentRoom(Number roomNumber) {
    return roomNumber.intValue() == 2;
  }
}
