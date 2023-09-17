package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.roomType;
import nz.ac.auckland.se206.TimeManager;

public class MenuController {

  @FXML
  private void buttonClicked() throws IOException {
    TimeManager.setTimer();
    System.out.println("Button clicked");
    App.setUi(roomType.ROOM1);
  }
}
