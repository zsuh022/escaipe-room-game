package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
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

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    initializeTimer();
    keyPad.setVisible(false);
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
  }

  @FXML private ImageView crossImage;
  @FXML private ImageView waveImage;

  @FXML
  private void muteBarClick() {
    GameState.isMuted.set(!GameState.isMuted.get());
  }

  private boolean thisIsCurrentRoom(Number roomNumber) {
    return roomNumber.intValue() == 4;
  }

  @FXML private Pane indicationPane;

  private void fadeInOutIndicationPane() {
    indicationPane.setVisible(true);
    FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), indicationPane);
    fadeIn.setFromValue(0);
    fadeIn.setToValue(1);

    PauseTransition pause = new PauseTransition(Duration.seconds(1));

    FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), indicationPane);
    fadeOut.setFromValue(1);
    fadeOut.setToValue(0);

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

  @FXML
  private void room1ButtonClicked() {
    System.out.println("Room 1 button clicked");
    GameState.currentRoom.set(1);
    App.setUi(RoomType.ROOM1);
    GameState.roomNumber = 1;
  }

  @FXML
  private void onKeyPadClicked(Event event) throws IOException {
    EventTarget target = event.getTarget();
    Button button = (Button) target;
    updateKeyLabel(button.getText());
  }

  @FXML
  public void updateKeyLabel(String key) throws IOException {
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
    System.out.println("2");

    if (btnKeyPadDisplay.getText().equals("")) {
      showErrorMessage();
      return;
    }

    int n = Integer.parseInt(btnKeyPadDisplay.getText());
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

  @FXML
  private void onOpenKeyPad() {
    keyPad.setVisible(true);
    smallKeyPad.setVisible(false);
    smallKeyPadCircle.setVisible(false);
  }

  @FXML
  private void onExitKeyPadClicked() {
    keyPad.setVisible(false);
    smallKeyPad.setVisible(true);
    smallKeyPadCircle.setVisible(true);
    btnKeyPadDisplay.setText("");
  }

  private void initializeTimer() {
    timeLabel.textProperty().bind(GameState.timeManager.getSecond().asString());
  }

  private void showErrorMessage() {
    keyPadMessageLabel.setText("Try Again");
    keyPadMessageLabel.setVisible(true);

    PauseTransition pause = new PauseTransition(Duration.seconds(1));
    pause.setOnFinished(
        event -> {
          keyPadMessageLabel.setVisible(false);
        });
    pause.play();
  }

  @FXML
  private void gameMasterClicked() {
    System.out.println("Game master clicked");
    GameState.currentRoom.set(5);
    App.setUi(RoomType.GAMEMASTER);
    GameState.roomNumber = 4;
  }
}
