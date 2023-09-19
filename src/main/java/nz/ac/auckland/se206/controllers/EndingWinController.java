package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EndingWinController {

  @FXML
  private void initialize() {
    System.out.println("EndingWinController initialized");
  }

  @FXML private Label keyLabel;

  @FXML
  private void keyLabelEntered() {}

  @FXML
  private void keyLabelExited() {}

  @FXML private Label timeLabel;

  @FXML
  private void riddleLabelEntered() {}

  @FXML
  private void riddleLabelExited() {}

  @FXML
  private void exitButtonClicked() {
    System.out.println("Exit button clicked");
  }

  @FXML
  private void restartButtonClicked() {
    System.out.println("Play again button clicked");
  }
}
