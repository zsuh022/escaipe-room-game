package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.HintDisplayHelper;
import nz.ac.auckland.se206.MusicManager;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.RoomType;

/** Controller class for the room view. */
public class ExitDoorController {

  @FXML private Button btnKeyPadDisplay;
  @FXML private Circle smallKeyPadCircle;
  @FXML private ImageView smallKeyPad;
  @FXML private Label keyPadMessageLabel;
  @FXML private Label timeLabel;
  @FXML private Pane keyPad;
  @FXML private ImageView crossImage;
  @FXML private ImageView waveImage;
  @FXML private Pane indicationPane;
  @FXML private TextArea aiMessageTextArea;
  @FXML private Button btnHint;

  @FXML
  private void initialize() {
    initializeTimer();
    keyPad.setVisible(false);
    if (GameState.difficulty == 3) {
      btnHint.setVisible(false);
    } else {
      btnHint.setVisible(true);
    }
    // initialize music and sound
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

    // binding the message text area to the shared message
    aiMessageTextArea.textProperty().bind(GameState.sharedMessage);
    GameState.latestHint.addListener(
        (obs, oldHint, newHint) -> {
          HintDisplayHelper.displayHintInTextArea(aiMessageTextArea, newHint);
        });
  }

  @FXML
  private void onExitKeyPadClicked() {
    // exit keypad
    keyPad.setVisible(false);
    smallKeyPad.setVisible(true);
    smallKeyPadCircle.setVisible(true);
    btnKeyPadDisplay.setText("");
  }

  @FXML
  private void onHintButtonClick() {
    GameState.requestHint.set(!GameState.requestHint.get());
  }

  @FXML
  private void onGameMasterClicked() {
    // go to game master
    System.out.println("Game master clicked");
    GameState.currentRoom.set(5);
    App.setUi(RoomType.GAMEMASTER);
    GameState.roomNumber = 4;
  }

  @FXML
  private void onKeyPadClicked(Event event) throws IOException {
    // check if key is correct
    EventTarget target = event.getTarget();
    Button button = (Button) target;
    updateKeyLabel(button.getText());
  }

  @FXML
  private void onMuteBarClicked() {
    // mute sound
    GameState.isMuted.set(!GameState.isMuted.get());
  }

  @FXML
  private void onOpenKeyPad() {
    // open keypad
    keyPad.setVisible(true);
    smallKeyPad.setVisible(false);
    smallKeyPadCircle.setVisible(false);
  }

  @FXML
  private void onRoom1ButtonClicked() {
    // switch to room 1
    System.out.println("Room 1 button clicked");
    GameState.currentRoom.set(1);
    App.setUi(RoomType.ROOM1);
    GameState.roomNumber = 1;
  }

  @FXML
  private void updateKeyLabel(String key) throws IOException {
    // update key label when the key pressed
    if (key.equals("Clear")) {
      btnKeyPadDisplay.setText("");
      return;
    }

    if (btnKeyPadDisplay.getText().length() > 8) {
      return;
    }

    if (key.equals("Enter")) {
      System.out.println("1");

      checkKey();
      return;
    }

    String prev = btnKeyPadDisplay.getText();
    btnKeyPadDisplay.setText(prev + key);
  }

  private void checkKey() throws IOException {
    // check if key entered in correct
    System.out.println("2");

    if (btnKeyPadDisplay.getText().equals("")) {
      showErrorMessage();
      return;
    }

    // get the number entered
    int n = Integer.parseInt(btnKeyPadDisplay.getText());

    // if the number is correct, go to ending win
    System.out.println("entered key: " + n);
    System.out.println("correct key: " + GameState.key);
    if (n == GameState.key) {
      System.out.println("Key is correct");
      GameState.timeManager.stopTimer();
      SceneManager.addUi(RoomType.ENDINGWIN, App.loadFxml("endingWin"));
      App.setUi(RoomType.ENDINGWIN);
    } else {
      showErrorMessage();
      btnKeyPadDisplay.setText("");
    }
  }

  private void fadeInOutIndicationPane() {
    // set induction pane up
    indicationPane.setVisible(true);
    FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), indicationPane);
    fadeIn.setFromValue(0);
    fadeIn.setToValue(1);

    PauseTransition pause = new PauseTransition(Duration.seconds(0.99));

    FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), indicationPane);
    fadeOut.setFromValue(1);
    fadeOut.setToValue(0);

    // fade pane in and out of view
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

  private void initializeTimer() {
    // bind the time label to the time manager
    timeLabel.textProperty().bind(GameState.timeManager.getSecond().asString());
  }

  private void showErrorMessage() {
    // show error message if key is incorrect
    keyPadMessageLabel.setText("Try Again");
    keyPadMessageLabel.setVisible(true);

    // dont play transition
    PauseTransition pause = new PauseTransition(Duration.seconds(1));
    pause.setOnFinished(
        event -> {
          keyPadMessageLabel.setVisible(false);
        });
    pause.play();
  }

  private boolean thisIsCurrentRoom(Number roomNumber) {
    // 4 is the room number for the exit door
    return roomNumber.intValue() == 4;
  }
}
