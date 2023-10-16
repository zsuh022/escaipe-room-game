package nz.ac.auckland.se206.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.RoomType;

/**
 * Room2Puzzle1Controller class is used to control the puzzle in room 2. It will flash a sequence of
 * buttons and the player has to press the buttons in the correct order.
 */
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
  @FXML private Button btnInitializePuzzle;
  @FXML private Button btnExitPuzzle;
  @FXML private Label puzzle2Label;
  @FXML private Label timeLabel;

  private int[] buttonOrder = new int[6];
  private int currentIndex = 0;

  /** Initializes the room view, it is called when the room loads. */
  @FXML
  private void initialize() {
    // initialize all objects
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

  /** This method will initialize the timer. */
  @FXML
  private void initializeTimer() {
    timeLabel.textProperty().bind(GameState.timeManager.getSecond().asString());
  }

  /** this will be called when the puzzle is solved. */
  private void puzzleSolved() {
    System.out.println("Puzzle solved");
    GameState.isPuzzleRoom2Solved.set(true);

    // disable initialize button
    btnInitializePuzzle.setDisable(true);
  }

  /**
   * This method will flash the colour.
   *
   * @param rectangle the rectangle to flash
   * @param colorFlash the colour to flash
   */
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

  /** this will initialize the puzzle. */
  @FXML
  private void onInitializePuzzle1Clicked() {
    // set a random sequence of buttons to flash
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

  /**
   * This method will convert integer to rectangle.
   *
   * @param integer the integer to convert
   * @return the rectangle
   */
  public Rectangle integerToRectangle(int integer) {
    // get a rectangle from an integer
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
      // for example if integer is 5, return five
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

  /** This method will be called when the exit button is clicked. */
  @FXML
  private void onBackButtonClicked() {
    // go to room 2
    System.out.println("Back button clicked");
    App.setUi(RoomType.ROOM2);
  }

  /** This method will be called when the one button is clicked. */
  @FXML
  private void clickOne() {
    handleButtonClick(1);
  }

  /** This method will be called when the two button is clicked. */
  @FXML
  private void clickTwo() {
    handleButtonClick(2);
  }

  /** This method will be called when the three button is clicked. */
  @FXML
  private void clickThree() {
    handleButtonClick(3);
  }

  /** This method will be called when the four button is clicked. */
  @FXML
  private void clickFour() {
    handleButtonClick(4);
  }

  /** This method will be called when the five button is clicked. */
  @FXML
  private void clickFive() {
    handleButtonClick(5);
  }

  /** This method will be called when the six button is clicked. */
  @FXML
  private void clickSix() {
    handleButtonClick(6);
  }

  /** This method will be called when the seven button is clicked. */
  @FXML
  private void clickSeven() {
    handleButtonClick(7);
  }

  /** This method will be called when the eight button is clicked. */
  @FXML
  private void clickEight() {
    handleButtonClick(8);
  }

  /** This method will be called when the nine button is clicked. */
  @FXML
  private void clickNine() {
    handleButtonClick(9);
  }

  /**
   * This method will handle the button click.
   *
   * @param buttonNumber the button number
   */
  private void handleButtonClick(int buttonNumber) {
    if (buttonNumber == buttonOrder[currentIndex]) {
      // Correct button pressed - increment index
      if (currentIndex < 6) {
        currentIndex++;
      }
      flashColour(integerToRectangle(buttonNumber), Color.GREEN);
      if (currentIndex == buttonOrder.length) {
        // Puzzle solved if all buttons pressed
        puzzleSolved();
        puzzle2Label.setText("SOLVED! PRESS BACK TO EXIT...");
      }
    } else {
      // Incorrect button pressed - reset puzzle
      currentIndex = 0;
      flashColour(integerToRectangle(buttonNumber), Color.RED);
      puzzle2Label.setText("INCORRECT. INITIALIZE AGAIN...");
    }
  }
}
