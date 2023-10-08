package nz.ac.auckland.se206.controllers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.RoomType;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

/**
 * Room3Puzzle1Controller class is used to control the puzzle in room 3. It will show the puzzle and
 * the key in room 3.
 */
public class Room3Puzzle1Controller {

  @FXML private Button btn1;
  @FXML private Button btn2;
  @FXML private Button btn3;
  @FXML private Button btn4;
  @FXML private Button btn5;
  @FXML private Button btn6;
  @FXML private Button btn7;
  @FXML private Button btn8;
  @FXML private Button btnEmpty;
  @FXML private Button btnExitPuzzle;
  @FXML private GridPane gridPane;
  @FXML private Label messageLabel;
  @FXML private Label timeLabel;

  private List<Button> buttons;
  private Map<Button, int[]> initialButtonPositions = new HashMap<>();

  /** Initializes the room view, it is called when the room loads. */
  @FXML
  private void initialize() {
    initializeTimer();
    initializePuzzle();
  }

  /** Initializes the timer and bind the text label. */
  @FXML
  private void initializeTimer() {
    timeLabel.textProperty().bind(GameState.timeManager.getSecond().asString());
  }

  /** when the exit button is clicked, it will return to the room view. */
  @FXML
  private void onBackButtonClicked() {
    System.out.println("Back button clicked");
    App.setUi(RoomType.ROOM3);
  }

  /** when the tile is clicked, it will move the tile to the empty space. */
  @FXML
  private void onTileClicked() throws ApiProxyException {
    if (GameState.isPuzzleRoom3Solved.getValue() == true) {
      return;
    }

    Button clickedButton = (Button) gridPane.getScene().getFocusOwner();

    // prevent user from clicking empty tile
    if (clickedButton == btnEmpty) {
      return;
    }

    int x = GridPane.getColumnIndex(clickedButton);
    int y = GridPane.getRowIndex(clickedButton);
    int emptyX = GridPane.getColumnIndex(btnEmpty);
    int emptyY = GridPane.getRowIndex(btnEmpty);

    if (Math.abs(emptyX - x) + Math.abs(emptyY - y) == 1) {
      GridPane.setColumnIndex(clickedButton, emptyX);
      GridPane.setRowIndex(clickedButton, emptyY);
      GridPane.setColumnIndex(btnEmpty, x);
      GridPane.setRowIndex(btnEmpty, y);

      // check if puzzle is completed
      if (isPuzzleCompleted()) {
        puzzleSolved();
      } else {
        System.out.println("Puzzle not completed");
      }
    }
  }

  /**
   * this will get the button at the specified row and column.
   *
   * @param row the row of the button
   * @param col the column of the button
   * @return the button at the specified row and column
   */
  private Button getButtonAt(int row, int col) {
    for (Node child : gridPane.getChildren()) {
      if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == col) {
        return (Button) child;
      }
    }

    return null;
  }

  /** this will initialize the puzzle. */
  private void initializePuzzle() {
    buttons = Arrays.asList(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btnEmpty);

    // set the images for the buttons
    setButtonImages();

    // shuffle the buttons
    shuffleButtons();

    // set the initial positions of the buttons
    setButtonPositions();

    // hide the empty button
    btnEmpty.setVisible(false);
  }

  /**
   * this will check if the puzzle is completed.
   *
   * @return true if the puzzle is completed, false otherwise
   */
  private boolean isPuzzleCompleted() {
    int correctValue = 1;

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Button button = getButtonAt(i, j);

        if (i == 2 && j == 2) {
          if (!button.getText().isEmpty()) {
            // row 2 column 2 should be an empty button
            return false;
          }
        } else if (button.getText().isEmpty()
            || Integer.parseInt(button.getText()) != correctValue) {
          // empty button is elsewhere or buttons are not in the correct position
          return false;
        }

        correctValue++;
      }
    }

    return true;
  }

  /**
   * this will check if the puzzle is solvable.
   *
   * @param buttons the buttons to check
   * @return true if the puzzle is solvable, false otherwise
   */
  private boolean isSolvable(List<Button> buttons) {
    int inversions = 0;

    for (int i = 0; i < buttons.size() - 1; i++) {
      for (int j = i + 1; j < buttons.size(); j++) {
        // exclude the empty button for the inversion calculation
        if (buttons.get(i).getText().isEmpty() || buttons.get(j).getText().isEmpty()) {
          continue;
        }

        if (Integer.parseInt(buttons.get(i).getText())
            > Integer.parseInt(buttons.get(j).getText())) {
          inversions++;
        }
      }
    }

    // if grid width is odd, return true if inversion count is even
    return inversions % 2 == 0;
  }

  /** this will be called when the puzzle is solved. */
  private void puzzleSolved() throws ApiProxyException {
    System.out.println("Puzzle solved");
    messageLabel.setText("Puzzle solved!");
    GameState.isPuzzleRoom3Solved.setValue(true);
  }

  /** this will set the images for the buttons. */
  private void setButtonImages() {
    for (int i = 1; i < buttons.size(); i++) {
      // get the image path for the image
      String imagePath =
          "/images/room3puzzle1images/"
              + GameState.riddleWord.toLowerCase()
              + "/"
              + GameState.riddleWord.toLowerCase()
              + String.valueOf(i)
              + ".jpg";
      Image image = new Image(App.class.getResource(imagePath).toExternalForm());
      BackgroundImage backgroundImage =
          new BackgroundImage(
              image,
              BackgroundRepeat.NO_REPEAT,
              BackgroundRepeat.NO_REPEAT,
              BackgroundPosition.CENTER,
              new BackgroundSize(150, 150, false, false, false, false));
      buttons.get(i - 1).setBackground(new Background(backgroundImage));
    }
  }

  /** this will set the initial positions of the buttons. */
  private void setButtonPositions() {
    for (int i = 0; i < buttons.size(); i++) {
      Button button = buttons.get(i);
      int[] position = new int[] {i % 3, i / 3};
      initialButtonPositions.put(button, position);
      GridPane.setColumnIndex(button, position[0]);
      GridPane.setRowIndex(button, position[1]);
    }
  }

  /** this will shuffle the buttons. */
  private void shuffleButtons() {
    do {
      Collections.shuffle(buttons);
    } while (!isSolvable(buttons));
  }
}
