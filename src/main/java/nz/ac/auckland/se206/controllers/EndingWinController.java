package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;

public class EndingWinController {

  @FXML
  private void initialize() {
    System.out.println("EndingWinController initialized");
  }

  @FXML
  private void exitButtonClicked() {
    System.out.println("Exit button clicked");
  }

  @FXML
  private void restartButtonClicked() {
    System.out.println("Play again button clicked");
  }
}
