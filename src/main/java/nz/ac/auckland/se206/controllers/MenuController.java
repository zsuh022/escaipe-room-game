package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.Random;
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
    Random random = new Random();
    int number = random.nextInt(3) + 1;
    switch (number) {
      case 1:
        GameState.key = 12041961;
        break;
      case 2:
        GameState.key = 19041971;
        break;
      case 3:
        GameState.key = 16071969;
        break;
    }
    System.out.println(number + GameState.key);
    SceneManager.addUi(roomType.ROOM1, App.loadFxml("room1"));
    SceneManager.addUi(roomType.ROOM2, App.loadFxml("room2"));
    SceneManager.addUi(roomType.ROOM3, App.loadFxml("room3"));
    SceneManager.addUi(roomType.ROOM4, App.loadFxml("room4"));
    App.setUi(roomType.ROOM1);
    GameState.timeManager.setTimer();
  }
}
