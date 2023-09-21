package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MusicManager;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.RoomType;

public class EndingWinController {

  private MediaPlayer player;
  @FXML private MediaView depart;

  @FXML
  private void initialize() throws URISyntaxException {
    isKeyOutputed = false;
    isRiddleOutputed = false;
    System.out.println("EndingWinController initialized");
    MusicManager.playHappySong();
    keyLabel.setText(String.valueOf(GameState.key));
    riddleLabel.setText(String.valueOf(GameState.riddleWord));
    keyTextArea.setVisible(false);
    riddleTextArea.setVisible(false);
    depart.setVisible(true);
    skipLabel.setVisible(true);
    playVideo();
  }

  @FXML private Label skipLabel;

  private void playVideo() throws URISyntaxException {
    Media media = new Media(App.class.getResource("/sounds/depart.mp4").toURI().toString());
    player = new MediaPlayer(media);
    depart.setMediaPlayer(player);

    FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), depart);
    fadeOut.setFromValue(1.0);
    fadeOut.setToValue(0.1);
    fadeOut.setOnFinished(
        e -> {
          player.stop();
          depart.setVisible(false);
          skipLabel.setVisible(false);
        });

    player.setOnPlaying(
        () -> {
          PauseTransition delayFade = new PauseTransition(Duration.seconds(7));
          delayFade.setOnFinished(e -> fadeOut.play());
          delayFade.play();
        });

    depart.setOnMouseClicked(
        e -> {
          player.stop();
          depart.setVisible(false);
          skipLabel.setVisible(false);
        });

    skipLabel.setOnMouseClicked(
        e -> {
          player.stop();
          depart.setVisible(false);
          skipLabel.setVisible(false);
        });

    player.play();
  }

  @FXML private Label keyLabel;
  @FXML private TextArea keyTextArea;

  @FXML private Label riddleLabel;
  @FXML private TextArea riddleTextArea;
  private Boolean isKeyOutputed = false;
  private Boolean isRiddleOutputed = false;

  @FXML
  private void keyLabelEntered() {
    keyTextArea.setVisible(true);
    if (!isKeyOutputed) {
      keyTextArea.appendText(outputKeyBackground() + "\n\n");
      isKeyOutputed = true;
    }
  }

  @FXML
  private void riddleLabelEntered() {
    riddleTextArea.setVisible(true);
    if (!isRiddleOutputed) {
      riddleTextArea.appendText(outputRiddleBackground() + "\n\n");
      isRiddleOutputed = true;
    }
  }

  @FXML
  private void keyTextAreaEntered() {
    keyTextArea.setVisible(true);
  }

  @FXML
  void riddleTextAreaEntered() {
    riddleTextArea.setVisible(true);
  }

  @FXML
  private void baseTouched() {
    keyTextArea.setVisible(false);
    riddleTextArea.setVisible(false);
  }

  private String outputRiddleBackground() {
    switch (GameState.riddleWord) {
      case "Mercury":
        return "Mercury is the first planet from the Sun and the smallest in the Solar System. It"
            + " is a terrestrial planet with a heavily cratered surface due to the planet"
            + " having no geological activity and an extremely tenuous atmosphere."
            + " - Wikipedia";
      case "Venus":
        return "Venus is the second planet from the Sun. It is a rocky planet with the densest"
            + " atmosphere of all the rocky bodies in the Solar System, and the only one"
            + " with a mass and size that is close to that of its orbital neighbour Earth."
            + " - Wikipedia";
      case "Earth":
        return "Earth is the third planet from the Sun and the only astronomical object known to"
            + " harbor life. This is enabled by Earth being a water world, the only one in"
            + " the Solar System sustaining liquid surface water. Almost all of Earth's"
            + " water is contained in its global ocean, covering 70.8% of Earth's surface."
            + " - Wikipedia";
      case "Mars":
        return "Mars is the fourth planet and the furthest terrestrial planet from the Sun. The"
            + " reddish color of its surface is due to finely grained iron(III) oxide dust"
            + " in the soil, giving it the nickname \"the Red Planet\". Mars's radius is"
            + " second smallest among the planets in the Solar System at 3,389.5 km."
            + " - Wikipedia";
      case "Jupiter":
        return "Jupiter is the fifth planet from the Sun and the largest in the Solar System. It is"
            + " a gas giant with a mass more than two and a half times that of all the other"
            + " planets in the Solar System combined, and slightly less than one"
            + " one-thousandth the mass of the Sun. - Wikipedia";
      case "Saturn":
        return "Saturn is the sixth planet from the Sun and the second-largest in the Solar System,"
            + " after Jupiter. It is a gas giant with an average radius of about"
            + " nine-and-a-half times that of Earth. It has only one-eighth the average"
            + " density of Earth, but is over 95 times more massive. - Wikipedia";
      case "Uranus":
        return "Uranus is the seventh planet from the Sun and is a gaseous cyan ice giant. Most of"
            + " the planet is made of water, ammonia, and methane in a supercritical phase"
            + " of matter, which in astronomy is called 'ice' or volatiles. - Wikipedia";
      case "Neptune":
        return "Neptune is the eighth planet from the Sun and the farthest IAU-recognized planet in"
            + " the Solar System. It is the fourth-largest planet in the Solar System by"
            + " diameter, the third-most-massive planet, and the densest giant planet. It is"
            + " 17 times the mass of Earth, and slightly more massive than its near-twin"
            + " Uranus. - Wikipedia";
    }

    return "";
  }

  private String outputKeyBackground() {

    switch (GameState.key) {
      case 12041961:
        return "12 April, 1961 was the date of the first human space flight, carried out by Yuri"
            + " Gagarin, a Soviet citizen. This historic event opened the way for space"
            + " exploration for the benefit of all humanity.";
      case 19041971:
        return "April 19, 1971, the Soviet Union placed into orbit Salyut, the world’s first space"
            + " station. Designed for a 6-month on orbit operational lifetime, Salyut hosted"
            + " the crew of Georgi T. Dobrovolski, Vladislav N. Volkov, and Viktor I.";
      case 16071969:
        return " July 16, 1969, liftoff North America on taken during the translunar part of their"
            + " journey. Apollo 11, with Neil Armstrong, Buzz Aldrin and Michael Collins,"
            + " lifted off as scheduled from Kennedy Space Center Launch Complex 39A in"
            + " Florida at 9:32 a.m. local time (1332 UTC).";
    }
    return "";
  }

  @FXML
  private void exitButtonClicked() {
    System.out.println("Exit button clicked");
    Platform.exit();
  }

  @FXML
  private void restartButtonClicked() throws IOException {
    System.out.println("Play again button clicked");
    SceneManager.addUi(RoomType.MENU, App.loadFxml("menu"));
    App.setUi(RoomType.MENU);
    GameState.reset();
    SceneManager.reset();
    MusicManager.mute();
  }
}
