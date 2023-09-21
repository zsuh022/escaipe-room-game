package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.RoomType;

public class Room2Puzzle1Controller {
  @FXML private Rectangle one;
  @FXML private Rectangle two;
  @FXML private Rectangle three;
  @FXML private Rectangle four;
  @FXML private Rectangle five;
  @FXML private Rectangle six;
  @FXML private Rectangle seven;
  @FXML private Rectangle eight;
  @FXML private Rectangle nine;
  @FXML private Rectangle backPuzzle2;
  @FXML private Rectangle startPuzzle2;
  @FXML private Label startLabel;
  @FXML private Label backLabel;
  @FXML private Label puzzle2Label;
  @FXML private Label timeLabel;

  private int[] buttonOrder = new int[6];
  private int currentIndex = 0;

  @FXML
  private void initialize() {
    buttonOrder = new int[6];
    currentIndex = 0;
    one.setFill(Color.WHITE);
    two.setFill(Color.WHITE);
    three.setFill(Color.WHITE);
    four.setFill(Color.WHITE);
    five.setFill(Color.WHITE);
    six.setFill(Color.WHITE);
    seven.setFill(Color.WHITE);
    eight.setFill(Color.WHITE);
    nine.setFill(Color.WHITE);
    puzzle2Label.setText("INITIALIZE TO START PUZZLE...");
    initializeTimer();
  }

  @FXML
  private void initializeTimer() {
    timeLabel.textProperty().bind(GameState.timeManager.getSecond().asString());
  }

  private void puzzleSolved() {
    System.out.println("Puzzle solved");
    GameState.isPuzzleRoom2Solved.set(true);
  }

  @FXML
  private void flashColour(Rectangle rectangle, Color colorFlash) {
    // Flash the rectangle green for one second
    rectangle.setFill(colorFlash);

    // Create a timeline for the animation
    Timeline timeline =
        new Timeline(
            new KeyFrame(
                Duration.seconds(0.4),
                event -> {
                  // After one second, revert the color back to the original color
                  rectangle.setFill(Color.WHITE);
                }));

    // Play the timeline animation
    timeline.play();
  }

  @FXML
  public void startPuzzle2Clicked(MouseEvent event) throws IOException {
    puzzle2Label.setText("");
    buttonOrder[0] = (int) (1 + (Math.random() * (9)));
    buttonOrder[1] = (int) (1 + (Math.random() * (9)));
    buttonOrder[2] = (int) (1 + (Math.random() * (9)));
    buttonOrder[3] = (int) (1 + (Math.random() * (9)));
    buttonOrder[4] = (int) (1 + (Math.random() * (9)));
    buttonOrder[5] = (int) (1 + (Math.random() * (9)));

    currentIndex = 0;

    for (int i = 0; i < buttonOrder.length; i++) {
      int buttonNumber = buttonOrder[i];
      Rectangle button = integerToRectangle(buttonNumber);
      if (button != null) {
        final int finalI = i;
        // Delay using a separate thread
        new Thread(
                () -> {
                  try {
                    Thread.sleep(finalI * 500); // Delay in milliseconds
                  } catch (InterruptedException e) {
                    e.printStackTrace();
                  }
                  // Flash the button after the delay
                  Platform.runLater(() -> flashColour(button, Color.GREEN));
                })
            .start();
      }
    }
  }

  public Rectangle integerToRectangle(int integer) {
    if (integer == 1) {
      return one;
    } else if (integer == 2) {
      return two;
    } else if (integer == 3) {
      return three;
    } else if (integer == 4) {
      return four;
    } else if (integer == 5) {
      return five;
    } else if (integer == 6) {
      return six;
    } else if (integer == 7) {
      return seven;
    } else if (integer == 8) {
      return eight;
    } else if (integer == 9) {
      return nine;
    } else {
      return null;
    }
  }

  @FXML
  public void backPuzzle2Clicked(MouseEvent event) throws IOException {
    App.setUi(RoomType.ROOM2);
  }

  @FXML
  private void clickOne() {
    handleButtonClick(1);
  }

  @FXML
  private void clickTwo() {
    handleButtonClick(2);
  }

  @FXML
  private void clickThree() {
    handleButtonClick(3);
  }

  @FXML
  private void clickFour() {
    handleButtonClick(4);
  }

  @FXML
  private void clickFive() {
    handleButtonClick(5);
  }

  @FXML
  private void clickSix() {
    handleButtonClick(6);
  }

  @FXML
  private void clickSeven() {
    handleButtonClick(7);
  }

  @FXML
  private void clickEight() {
    handleButtonClick(8);
  }

  @FXML
  private void clickNine() {
    handleButtonClick(9);
  }

  private void handleButtonClick(int buttonNumber) {
    if (buttonNumber == buttonOrder[currentIndex]) {
      // Correct button pressed
      if (currentIndex < 6) {
        currentIndex++;
      }
      flashColour(integerToRectangle(buttonNumber), Color.GREEN);
      if (currentIndex == buttonOrder.length) {
        // Puzzle solved
        puzzleSolved();
        puzzle2Label.setText("SOLVED! PRESS BACK TO EXIT...");
      }
    } else {
      // Incorrect button pressed
      currentIndex = 0;
      flashColour(integerToRectangle(buttonNumber), Color.RED);
      puzzle2Label.setText("INCORRECT. INITIALIZE AGAIN...");
    }
  }
}
