package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.roomType;

public class Room3Controller {

  @FXML
  private void buttonClicked() {
    System.out.println("Button clicked");
    App.setUi(roomType.ROOM1);
  }
}
