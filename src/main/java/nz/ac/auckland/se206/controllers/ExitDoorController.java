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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

  @FXML private Button btnClear;
  @FXML private Button btnEnter;
  @FXML private Button btnGoRoom1;
  @FXML private Button btnHint;
  @FXML private Button btnKeyPadDisplay;
  @FXML private Circle smallKeyPadCircle;
  @FXML private ImageView crossImage;
  @FXML private ImageView smallKeyPad;
  @FXML private ImageView waveImage;
  @FXML private Label keyPadMessageLabel;
  @FXML private Label timeLabel;
  @FXML private Pane indicationPane;
  @FXML private Pane keyPad;
  @FXML private TextArea aiMessageTextArea;

  /** this will initialize the controller. */
  @FXML
  private void initialize() {
    initializeTimer();
    keyPad.setVisible(false);
    if (GameState.gameDifficulty == 3) {
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
    // k is a dummy variable to make the lambda expression work
    int k = 0;
    k = k + 1;
    System.out.println(k);
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

    System.out.println("k");
    // binding the message text area to the shared message
    aiMessageTextArea.textProperty().bind(GameState.sharedMessage);
    GameState.latestHint.addListener(
        (obs, oldHint, newHint) -> {
          HintDisplayHelper.displayHintInTextArea(aiMessageTextArea, newHint);
        });
  }

  /** this will be called when the exit keypad is clicked. */
  @FXML
  private void onExitKeyPadClicked() {
    // exit keypad
    keyPad.setVisible(false);
    smallKeyPad.setVisible(true);
    smallKeyPadCircle.setVisible(true);
    btnKeyPadDisplay.setText("");

    // enable go to room 1 button
    btnGoRoom1.setDisable(false);
  }

  /** this will be called when the hint button is clicked. */
  @FXML
  private void onHintButtonClick() {
    GameState.requestHint.set(!GameState.requestHint.get());
    HintDisplayHelper.displayThreeDots();
  }

  /** this will be called when the game master is clicked. */
  @FXML
  private void onGameMasterClicked() {
    // go to game master
    System.out.println("Game master clicked");
    GameState.currentRoom.set(5);
    App.setUi(RoomType.GAMEMASTER);
    GameState.roomNumber = 4;
  }

  /** this will be called when the keypad is clicked. */
  @FXML
  private void onKeyPadClicked(Event event) throws IOException {
    // check if key is correct
    EventTarget target = event.getTarget();
    Button button = (Button) target;
    updateKeyLabel(button.getText());
  }

  /**
   * Handles keyboard key presses for input into the keypad. Number keys will add the number to the
   * input, the enter key will submit the key, and the backspace will clear the input.
   *
   * @param event The KeyEvent representing the key press.
   * @throws IOException If an I/O exception occurs.
   */
  @FXML
  private void onKeyPressed(KeyEvent event) throws IOException {
    KeyCode keyCode = event.getCode();

    if (keyCode.isDigitKey()) {
      // if the key pressed is a digit, update the key label with the digit
      updateKeyLabel(keyCode.getName());
    } else if (keyCode == KeyCode.ENTER) {
      // if the key pressed is enter, check the label
      updateKeyLabel("Enter");
    } else if (keyCode == KeyCode.BACK_SPACE) {
      // if the key pressed is backspace, clear the key label
      updateKeyLabel("Clear");
    }
  }

  /** this will be called when the mute bar is clicked. */
  @FXML
  private void onMuteBarClicked() {
    // mute sound
    GameState.isMuted.set(!GameState.isMuted.get());
  }

  /** this will be called when the open keypad button is clicked. */
  @FXML
  private void onOpenKeyPad() {
    // open keypad
    keyPad.setVisible(true);
    smallKeyPad.setVisible(false);
    smallKeyPadCircle.setVisible(false);

    // request focus on the keypad
    btnClear.requestFocus();

    // disable go to room 1 button
    btnGoRoom1.setDisable(true);
  }

  /** this will be called when the room 1 button is clicked and will switch to room 1. */
  @FXML
  private void onRoom1ButtonClicked() {
    // switch to room 1
    System.out.println("Room 1 button clicked");
    GameState.currentRoom.set(1);
    App.setUi(RoomType.ROOM1);
    GameState.roomNumber = 1;
  }

  /**
   * this will update the key label.
   *
   * @param key the key to be updated
   * @throws IOException if the input or output is invalid
   */
  @FXML
  private void updateKeyLabel(String key) throws IOException {
    // check if clear is pressed
    if (key.equals("Clear")) {
      btnKeyPadDisplay.setText("");
      return;
    }

    // check if enter is pressed
    if (key.equals("Enter")) {
      checkKey();
      return;
    }

    // only allow a maximum of 8 digits
    if (btnKeyPadDisplay.getText().length() >= 8) {
      return;
    }

    String prev = btnKeyPadDisplay.getText();
    btnKeyPadDisplay.setText(prev + key);
  }

  /**
   * this will check if the key is correct.
   *
   * @throws IOException if the input or output is invalid
   */
  private void checkKey() throws IOException {
    if (btnKeyPadDisplay.getText().equals("")) {
      // if the keypad is empty, show error message
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

  /** this will fade in and out the indication pane. */
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

  /** this will initialize the timer. */
  private void initializeTimer() {
    // bind the time label to the time manager
    timeLabel.textProperty().bind(GameState.timeManager.getSecond().asString());
  }

  /** this will show the error message. */
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

  /**
   * this will check if this is the current room.
   *
   * @param roomNumber the room number to be checked
   * @return true if this is the current room
   */
  private boolean thisIsCurrentRoom(Number roomNumber) {
    // 4 is the room number for the exit door
    return roomNumber.intValue() == 4;
  }
}
