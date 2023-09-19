package nz.ac.auckland.se206.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class EndingLoseController implements Initializable {

  @FXML private ImageView gameMasterLose;

  @FXML
  private void initialize() {}

  @FXML
  private void exitButtonClicked() {
    System.out.println("Exit button clicked");
  }

  @FXML
  private void restartButtonClicked() {
    System.out.println("Play again button clicked");
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    gameMasterTransition();
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
