package nz.ac.auckland.se206.controllers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
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

public class Room3PuzzleController {

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

  private List<Button> buttons;
  private Map<Button, int[]> initialButtonPositions = new HashMap<>();

  @FXML
  public void initialize() {
    buttons = Arrays.asList(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btnEmpty);

    // set the images for the buttons
    for (int i = 1; i < buttons.size(); i++) {
      String imagePath =
          "/images/"
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

    // shuffle the buttons
    shuffleButtons();

    for (int i = 0; i < buttons.size(); i++) {
      Button button = buttons.get(i);
      int[] position = new int[] {i % 3, i / 3};
      initialButtonPositions.put(button, position);
      GridPane.setColumnIndex(button, position[0]);
      GridPane.setRowIndex(button, position[1]);
    }

    // hide the empty button
    btnEmpty.setVisible(false);
  }

  @FXML
  private void onBackButtonClicked() {
    System.out.println("Back button clicked");
    App.setUi(RoomType.ROOM3);
  }

  @FXML
  public void onTileClicked() throws ApiProxyException {
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

  private Button getButtonAt(int row, int col) {
    for (Node child : gridPane.getChildren()) {
      if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == col) {
        return (Button) child;
      }
    }

    return null;
  }

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

  private void puzzleSolved() throws ApiProxyException {
    System.out.println("Puzzle solved");
    GameState.isPuzzleRoom3Solved.setValue(true);
  }

  private void shuffleButtons() {
    do {
      Collections.shuffle(buttons);
    } while (!isSolvable(buttons));
  }
}
