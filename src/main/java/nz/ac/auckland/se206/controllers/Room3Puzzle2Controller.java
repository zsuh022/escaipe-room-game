package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;
import nz.ac.auckland.se206.DraggableManager;

public class Room3Puzzle2Controller {

  @FXML private Circle redCircle;
  @FXML private Circle blueCircle;

  DraggableManager draggableManager = new DraggableManager();

  @FXML
  private void initialize() {
    draggableManager.makeDraggable(redCircle, blueCircle);
  }
}
