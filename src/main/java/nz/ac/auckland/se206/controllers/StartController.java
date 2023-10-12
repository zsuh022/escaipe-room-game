package nz.ac.auckland.se206.controllers;

import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import nz.ac.auckland.se206.App;

public class StartController {

  @FXML private Button btnNewGame;
  @FXML private Button btnStart;
  @FXML private Label easyLabel;
  @FXML private Label difficultyInfoLabel;
  @FXML private Label hardLabel;
  @FXML private Label hintInfoLabel;
  @FXML private Label mediumLabel;
  @FXML private Label timeInfoLabel;
  @FXML private MediaView earthMediaView;
  @FXML private Pane difficultyPane;
  @FXML private Pane startPane;
  @FXML private Pane timePane;
  @FXML private Pane tutorialPane;
  @FXML private Slider timeSlider;

  @FXML
  private void initialize() throws URISyntaxException {
    initializeInfoLabels();
    initializeEarthMediaView();
  }

  @FXML
  private void onExitDifficultyPane() {
    difficultyInfoLabel.setVisible(false);
    hintInfoLabel.setVisible(false);
  }

  @FXML
  private void onExitTimePane() {
    timeInfoLabel.setVisible(false);
  }

  @FXML
  private void onHoverEasy() {
    hintInfoLabel.setText("Unlimited Hints");
  }

  @FXML
  private void onHoverHard() {
    hintInfoLabel.setText("No Hints");
  }

  @FXML
  private void onHoverMedium() {
    hintInfoLabel.setText("5 Hints");
  }

  @FXML
  private void onNewGameButtonClicked() {
    startPane.setVisible(false);
    tutorialPane.setVisible(true);
  }

  @FXML
  private void onSelectDifficulty() {
    difficultyInfoLabel.setVisible(true);
    hintInfoLabel.setVisible(true);
  }

  @FXML
  private void onSelectTime() {
    timeInfoLabel.setVisible(true);
  }

  private void initializeEarthMediaView() throws URISyntaxException {
    Media earthMedia =
        new Media(App.class.getResource("/videos/earthRotating.mp4").toURI().toString());
    MediaPlayer earthPlayer = new MediaPlayer(earthMedia);
    earthMediaView.setMediaPlayer(earthPlayer);
    earthPlayer.play();
  }

  private void initializeInfoLabels() {
    difficultyInfoLabel.setVisible(false);
    hintInfoLabel.setVisible(false);
    timeInfoLabel.setVisible(false);
  }

  @FXML
  private void onEasyClicked(MouseEvent event) {
    setDifficultyColour(easyLabel, "easy");
  }

  @FXML
  private void onMediumClicked(MouseEvent event) {
    setDifficultyColour(mediumLabel, "medium");
  }

  @FXML
  private void onHardClicked(MouseEvent event) {
    setDifficultyColour(hardLabel, "hard");
  }

  private void setDifficultyColour(Label clickedLabel, String difficulty) {
    // reset all labels to original colour
    easyLabel.getStyleClass().removeAll("easyDifficulty");
    mediumLabel.getStyleClass().removeAll("mediumDifficulty");
    hardLabel.getStyleClass().removeAll("hardDifficulty");

    easyLabel.getStyleClass().add("difficulties");
    mediumLabel.getStyleClass().add("difficulties");
    hardLabel.getStyleClass().add("difficulties");

    // set the clicked label to its respective colour
    switch (difficulty) {
      case "easy":
        clickedLabel.getStyleClass().removeAll("difficulties");
        clickedLabel.getStyleClass().add("easyDifficulty");
        break;
      case "medium":
        clickedLabel.getStyleClass().removeAll("difficulties");
        clickedLabel.getStyleClass().add("mediumDifficulty");
        break;
      case "hard":
        clickedLabel.getStyleClass().removeAll("difficulties");
        clickedLabel.getStyleClass().add("hardDifficulty");
        break;
    }
  }
}
