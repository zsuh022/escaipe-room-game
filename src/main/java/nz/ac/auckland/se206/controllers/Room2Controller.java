package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
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
  private Random random = new Random();

  @FXML
  private void initialize() {
    // intialize room 2
    initializeTimer();
    initializePuzzle();
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

    aiMessageTextArea.textProperty().bind(GameState.sharedMessage);
    GameState.latestHint.addListener(
        (obs, oldHint, newHint) -> {
          HintDisplayHelper.displayHintInTextArea(aiMessageTextArea, newHint);
        });
  }

  @FXML
  private void onGameMasterClicked() {
    // change to game master
    System.out.println("Game master clicked");
    GameState.currentRoom.set(5);
    App.setUi(RoomType.GAMEMASTER);
    GameState.roomNumber = 2;
  }

  @FXML
  private void onHintButtonClick() {
    GameState.requestHint.set(!GameState.requestHint.get());
  }

  @FXML
  private void onMuteBarClicked() {
    GameState.isMuted.set(!GameState.isMuted.get());
  }

  @FXML
  private void onPuzzle1Clicked(MouseEvent event) throws IOException {
    System.out.println("lock clicked");
    App.setUi(RoomType.ROOM2PUZZLE1);
  }

  @FXML
  private void onPuzzle2Clicked(MouseEvent event) throws IOException {
    System.out.println("box clicked");
    App.setUi(RoomType.ROOM2PUZZLE2);
  }

  @FXML
  private void onRoom1ButtonClicked() {
    // change to room 1
    System.out.println("Button clicked");
    GameState.currentRoom.set(1);
    App.setUi(RoomType.ROOM1);
    GameState.roomNumber = 1;
  }

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

  private void initializePuzzle() {
    random = new Random();
    int randomNumber = random.nextInt(2) + 1;

    // set which puzzle to do
    if (randomNumber == 1) {
      polygonRoom2Puzzle1.setVisible(true);
      polygonRoom2Puzzle2.setVisible(false);
    } else {
      polygonRoom2Puzzle1.setVisible(false);
      polygonRoom2Puzzle2.setVisible(true);
    }
  }

  private void initializeTimer() {
    timeLabel.textProperty().bind(GameState.timeManager.getSecond().asString());
  }

  private void showRoom2Key() {
    System.out.println("Showing room 2 key");
    keyShowingPane.setVisible(true);
    room2KeyLabel.setText(GameState.room2Key);
  }

  private boolean thisIsCurrentRoom(Number roomNumber) {
    return roomNumber.intValue() == 2;
  }
}
