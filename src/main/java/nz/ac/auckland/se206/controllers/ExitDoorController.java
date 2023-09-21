package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
      showDialog("Info", null, "Please enter the key.");
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
      showDialog("Warning", null, "The answer is wrong, please try again.");
      btnKeyPadDisplay.setText("");
    }
  }

  /**
   * Displays a dialog box with the given title, header text, and message.
   *
   * @param title the title of the dialog box
   * @param headerText the header text of the dialog box
   * @param message the message content of the dialog box
   */
  private void showDialog(String title, String headerText, String message) {
    // create a dialog box with the given title, header text, and message
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(message);
    // close the dialog box after 4 seconds
    Thread alertClose =
        new Thread(
            () -> {
              try {
                Thread.sleep(5000);
                Platform.runLater(
                    () -> {
                      alert.close();
                    });
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            });
    alertClose.start();
    alert.showAndWait();
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
}
