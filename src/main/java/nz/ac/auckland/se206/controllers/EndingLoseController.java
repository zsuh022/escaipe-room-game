package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MusicManager;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.RoomType;

/**
 * EndingLoseController class is used to control the ending lose scene. It will show the game master
 * and the player will lose the game.
 */
public class EndingLoseController {

  @FXML private ImageView gameMasterLose;

  /**
   * Called to initialize a controller after its root element has been completely processed.
   *
   * @throws URISyntaxException if the URI syntax is invalid
   */
  @FXML
  private void initialize() throws URISyntaxException {
    System.out.println("EndingWinController initialized");

    MusicManager.playBadSong();
    gameMasterTransition();
  }

  /** Called when the exit button is clicked. */
  @FXML
  private void onExitButtonClicked() {
    System.out.println("Exit button clicked");

    Platform.exit();
  }

  /**
   * Called when the restart button is clicked.
   *
   * @throws IOException if the input or output is invalid
   */
  @FXML
  private void onRestartButtonClicked() throws IOException {
    System.out.println("Play again button clicked");
    // reset game state
    SceneManager.addUi(RoomType.START, App.loadFxml("start"));
    App.setUi(RoomType.START);
    GameState.reset();
    SceneManager.reset();
    MusicManager.mute();
  }

  /**
   * this will make the game master move and rotate.
   *
   * @throws IOException if the input or output is invalid
   */
  private void gameMasterTransition() {
    // game master translation
    TranslateTransition translateTransition =
        new TranslateTransition(Duration.seconds(8), gameMasterLose);
    translateTransition.setFromX(-200);
    translateTransition.setToX(1124.0);
    translateTransition.setInterpolator(Interpolator.LINEAR);

    // game master rotation
    RotateTransition rotateTransition = new RotateTransition(Duration.seconds(4), gameMasterLose);
    rotateTransition.setCycleCount(Timeline.INDEFINITE);
    rotateTransition.setByAngle(360);
    rotateTransition.setInterpolator(Interpolator.LINEAR);

    // play both transitions
    translateTransition.play();
    rotateTransition.play();
  }
}
