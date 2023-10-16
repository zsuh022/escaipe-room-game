package nz.ac.auckland.se206.controllers;

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
 * Room3Controller class is used to control the room 3. It will show the puzzle and the key in room
 * 3.
 */
public class Room3Controller {

  @FXML private ImageView crossImage;
  @FXML private ImageView waveImage;
  @FXML private Label room3KeyLabel;
  @FXML private Label timeLabel;
  @FXML private Pane indicationPane;
  @FXML private Pane keyShowingPane;
  @FXML private Pane puzzle2Pane;
  @FXML private Polygon polygonRoom3Puzzle1;
  @FXML private Polygon polygonRoom3Puzzle2;
  @FXML private Polygon polygon2Room3Puzzle2;
  @FXML private TextArea aiMessageTextArea;
  @FXML private Polygon blackBackGround;
  @FXML private Button btnHint;

  private int count;

  private Random random = new Random();

  /** Initializes the room view, it is called when the room loads. */
  @FXML
  private void initialize() {
    initializeTimer();
    initializePuzzle();
    if (GameState.gameDifficulty == 3) {
      btnHint.setVisible(false);
    } else {
      btnHint.setVisible(true);
    }
    keyShowingPane.setVisible(false);
    // starts music
    GameState.isPuzzleRoom3Solved.addListener(
        (observable, oldValue, newValue) -> {
          if (newValue) {
            showRoom3Key();
          }
        });
    GameState.currentRoom.addListener(
        (obs, oldRoom, newRoom) -> {
          if (thisIsCurrentRoom(newRoom)) {
            fadeInOutIndicationPane();
          }
        });
    // k is a dummy variable to make the lambda expression work
    int n = 0;
    n = n + 1;
    System.out.println(2 * n);
    GameState.isMuted.addListener(
        (obs, wasMuted, isNowMuted) -> {
          // if muted, show cross, hide wave, and mute music
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

    // this will bind the hint message to the hint text area
    aiMessageTextArea.textProperty().bind(GameState.sharedMessage);
    GameState.latestHint.addListener(
        (obs, oldHint, newHint) -> {
          HintDisplayHelper.displayHintInTextArea(aiMessageTextArea, newHint);
        });

    count = 0;
  }

  /** This method will go to he game master. */
  @FXML
  private void onGameMasterClicked() {
    // change to game master
    System.out.println("Game master clicked");
    GameState.currentRoom.set(5);
    App.setUi(RoomType.GAMEMASTER);
    GameState.roomNumber = 3;
  }

  /** this method is called when the mute button is clicked. */
  @FXML
  private void onMuteBarClicked() {
    GameState.isMuted.set(!GameState.isMuted.get());
  }

  /** This method will be called when the hint button is clicked. */
  @FXML
  private void onHintButtonClick() {
    GameState.requestHint.set(!GameState.requestHint.get());
    HintDisplayHelper.displayThreeDots();
  }

  /** This method will be called when room1 button is clicked. */
  @FXML
  private void onRoom1ButtonClicked() {
    // go to room 1
    System.out.println("Room 1 button clicked");
    GameState.currentRoom.set(1);
    App.setUi(RoomType.ROOM1);
    GameState.roomNumber = 1;
  }

  /**
   * This method will be called when puzzle1 is clicked.
   *
   * @param event the mouse event
   */
  @FXML
  private void onRoom3Puzzle1Clicked(MouseEvent event) {
    // start room 3 puzzle
    System.out.println("Room 3 puzzle clicked");
    App.setUi(RoomType.ROOM3PUZZLE1);
  }

  /** This method will be called when puzzle2 is clicked. */
  @FXML
  private void onRoom3Puzzle2Clicked() {
    // go to room 3 puzzle 2
    random = new Random();
    System.out.println("Room 3 puzzle 2 clicked");
    App.setUi(RoomType.ROOM3PUZZLE2);
    // setting panes
    if (count == 0) {
      puzzle2Pane.setOpacity(0.5);
      count++;
    } else if (count == 1) {
      puzzle2Pane.setOpacity(0.2);
      count++;
    } else {
      puzzle2Pane.setOpacity(0);
      polygonRoom3Puzzle2.setVisible(false);
      polygon2Room3Puzzle2.setVisible(true);
      puzzle2Pane.setVisible(false);
    }
  }

  /** this method will fade in and out the indication pane. */
  private void fadeInOutIndicationPane() {
    // fades banner in and out of view
    indicationPane.setVisible(true);
    FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), indicationPane);
    fadeIn.setFromValue(0);
    fadeIn.setToValue(1);

    PauseTransition pause = new PauseTransition(Duration.seconds(0.96));

    FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), indicationPane);
    fadeOut.setFromValue(1);
    fadeOut.setToValue(0);

    // sets the order of the transitions
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

  /** this method will initialize the puzzle. */
  private void initializePuzzle() {
    // choosing random puzzle for implementation
    random = new Random();
    int randomNumber = 1;
    if (GameState.round != 2) {
      randomNumber = random.nextInt(2) + 1;
    } else if (GameState.puzzleRoom23[1] == 1) {
      randomNumber = 2;
    } else if (GameState.puzzleRoom23[1] == 2) {
      randomNumber = 1;
    }
    System.out.println("Room 3 puzzle number: " + randomNumber);

    // initalizing puzzle for use
    if (randomNumber == 1) {
      polygonRoom3Puzzle1.setVisible(true);
      puzzle2Pane.setVisible(false);
      polygonRoom3Puzzle2.setVisible(false);
      polygon2Room3Puzzle2.setVisible(false);
      blackBackGround.setVisible(false);
      GameState.puzzleRoom23[1] = 1;
    } else if (randomNumber == 2) {
      polygonRoom3Puzzle1.setVisible(false);
      puzzle2Pane.setVisible(true);
      puzzle2Pane.setOpacity(1);
      polygonRoom3Puzzle2.setVisible(true);
      polygon2Room3Puzzle2.setVisible(true);
      blackBackGround.setVisible(true);
      GameState.puzzleRoom23[1] = 2;
    }
  }

  /** this method will initialize the timer. */
  private void initializeTimer() {
    timeLabel.textProperty().bind(GameState.timeManager.getSecond().asString());
  }

  /** this will be called when the puzzle is solved then show the key in room3. */
  private void showRoom3Key() {
    // shows room 3 key
    System.out.println("Showing room 3 key");
    keyShowingPane.setVisible(true);
    room3KeyLabel.setText(GameState.room3Key);
  }

  /**
   * This method will flash the colour.
   *
   * @param roomNumber the room number
   * @return true if the room number is 3
   */
  private boolean thisIsCurrentRoom(Number roomNumber) {
    return roomNumber.intValue() == 3;
  }
}
