package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.roomType;

public class MenuController {

  @FXML private Slider difficultySlider;
  @FXML private Slider timeSlider;

  @FXML
  private void buttonClicked() throws IOException {
    GameState.difficulty = (int) difficultySlider.getValue();
    switch ((int) timeSlider.getValue()) {
      case 1:
        GameState.timeManager.setTime(120);
        break;
      case 2:
        GameState.timeManager.setTime(240);
        break;
      case 3:
        GameState.timeManager.setTime(360);
        break;
    }
    SceneManager.addUi(roomType.ROOM1, App.loadFxml("room1"));
    SceneManager.addUi(roomType.ROOM2, App.loadFxml("room2"));
    SceneManager.addUi(roomType.ROOM3, App.loadFxml("room3"));
    App.setUi(roomType.ROOM1);
    GameState.timeManager.setTimer();
  }
}
