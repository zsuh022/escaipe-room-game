package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MusicManager;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.RoomType;

public class EndingLoseController {

  @FXML private ImageView gameMasterLose;

  @FXML
  private void initialize() throws URISyntaxException {
    System.out.println("EndingWinController initialized");
    MusicManager.playBadSong();
    gameMasterTransition();
  }

  @FXML
  private void exitButtonClicked() {
    System.out.println("Exit button clicked");
  }

  @FXML
  private void restartButtonClicked() throws IOException {
    System.out.println("Play again button clicked");
    SceneManager.addUi(RoomType.MENU, App.loadFxml("menu"));
    App.setUi(RoomType.MENU);
    GameState.reset();
    SceneManager.reset();
    MusicManager.mute();
  }

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

    translateTransition.play();
    rotateTransition.play();
  }
}
