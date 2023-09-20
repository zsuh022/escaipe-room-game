package nz.ac.auckland.se206.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Room2PuzzleController {
  @FXML private Rectangle one;
  @FXML private Rectangle two;
  @FXML private Rectangle three;
  @FXML private Rectangle four;
  @FXML private Rectangle five;
  @FXML private Rectangle six;
  @FXML private Rectangle seven;
  @FXML private Rectangle eight;
  @FXML private Rectangle nine;

  @FXML
  private void initialize() {
    one.setFill(Color.WHITE);
    two.setFill(Color.WHITE);
    three.setFill(Color.WHITE);
    four.setFill(Color.WHITE);
    five.setFill(Color.WHITE);
    six.setFill(Color.WHITE);
    seven.setFill(Color.WHITE);
    eight.setFill(Color.WHITE);
    nine.setFill(Color.WHITE);
  }

  @FXML
  private void flashGreen(Rectangle rectangle) {
    // Flash the rectangle green for one second
    rectangle.setFill(Color.GREEN);

    // Create a timeline for the animation
    Timeline timeline =
        new Timeline(
            new KeyFrame(
                Duration.seconds(1),
                event -> {
                  // After one second, revert the color back to the original color
                  rectangle.setFill(Color.WHITE);
                }));

    // Play the timeline animation
    timeline.play();
  }

  @FXML
  private void clickOne() {
    flashGreen(one);
  }

  @FXML
  private void clickTwo() {
    flashGreen(two);
  }

  @FXML
  private void clickThree() {
    flashGreen(three);
  }

  @FXML
  private void clickFour() {
    flashGreen(four);
  }

  @FXML
  private void clickFive() {
    flashGreen(five);
  }

  @FXML
  private void clickSix() {
    flashGreen(six);
  }

  @FXML
  private void clickSeven() {
    flashGreen(seven);
  }

  @FXML
  private void clickEight() {
    flashGreen(eight);
  }

  @FXML
  private void clickNine() {
    flashGreen(nine);
  }
}
