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
  @FXML private Label easyHintLabel;
  @FXML private Label difficultyInfoLabel;
  @FXML private Label hardLabel;
  @FXML private Label hardHintLabel;
  @FXML private Label mediumLabel;
  @FXML private Label mediumHintLabel;
  @FXML private Label timeInfoLabel;
  @FXML private MediaView earthMediaView;
  @FXML private Pane difficultyPane;
  @FXML private Pane startPane;
  @FXML private Pane timePane;
  @FXML private Pane tutorialPane;
  @FXML private Slider timeSlider;

  @FXML
  private void initialize() throws URISyntaxException {
    initializeEarthMediaView();
    initializeInfoLabels();
  }

  @FXML
  private void onClickEasy(MouseEvent event) {
    setDifficultyColour(easyLabel, "easy");
  }

  @FXML
  private void onClickHard(MouseEvent event) {
    setDifficultyColour(hardLabel, "hard");
  }

  @FXML
  private void onClickMedium(MouseEvent event) {
    setDifficultyColour(mediumLabel, "medium");
  }

  @FXML
  private void onExitDifficultyPane() {
    difficultyInfoLabel.setVisible(false);
  }

  @FXML
  private void onExitEasyDifficulty() {
    easyHintLabel.setVisible(false);
  }

  @FXML
  private void onExitHardDifficulty() {
    hardHintLabel.setVisible(false);
  }

  @FXML
  private void onExitMediumDifficulty() {
    mediumHintLabel.setVisible(false);
  }

  @FXML
  private void onExitTimePane() {
    timeInfoLabel.setVisible(false);
  }

  @FXML
  private void onHoverEasy() {
    easyHintLabel.setVisible(true);
  }

  @FXML
  private void onHoverHard() {
    hardHintLabel.setVisible(true);
  }

  @FXML
  private void onHoverMedium() {
    mediumHintLabel.setVisible(true);
  }

  @FXML
  private void onNewGameButtonClicked() {
    startPane.setVisible(false);
    tutorialPane.setVisible(true);
  }

  @FXML
  private void onSelectDifficulty() {
    difficultyInfoLabel.setVisible(true);
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
    timeInfoLabel.setVisible(false);
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
