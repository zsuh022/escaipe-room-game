package nz.ac.auckland.se206.controllers;

import java.net.URISyntaxException;
import javafx.fxml.FXML;
import nz.ac.auckland.se206.MusicManager;

public class EndingLoseController {

  @FXML
  private void initialize() throws URISyntaxException {
    System.out.println("EndingWinController initialized");
    MusicManager.playBadSong();
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
