package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
  @FXML private Button btn9;
  @FXML private Button btnExitPuzzle;

  @FXML
  private void onTileClicked() {
    System.out.println("Tile clicked");
  }

  @FXML
  private void onBackButtonClicked() {
    System.out.println("Back button clicked");
    App.setUi(roomType.ROOM3);
  }
}
