package nz.ac.auckland.se206.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.roomType;

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
  private void onBackButtonClicked() {
    System.out.println("Back button clicked");
    App.setUi(roomType.ROOM3);
  }

  @FXML
  public void initialize() {
    buttons = Arrays.asList(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btnEmpty);

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
  public void onTileClicked() {
    Button clickedButton = (Button) gridPane.getScene().getFocusOwner();

    // prevent user from clicking empty tile
    if (clickedButton == btnEmpty) return;

    int x = GridPane.getColumnIndex(clickedButton);
    int y = GridPane.getRowIndex(clickedButton);
    int emptyX = GridPane.getColumnIndex(btnEmpty);
    int emptyY = GridPane.getRowIndex(btnEmpty);

    if (Math.abs(emptyX - x) + Math.abs(emptyY - y) == 1) {
      GridPane.setColumnIndex(clickedButton, emptyX);
      GridPane.setRowIndex(clickedButton, emptyY);
      GridPane.setColumnIndex(btnEmpty, x);
      GridPane.setRowIndex(btnEmpty, y);
    }
  }
}
