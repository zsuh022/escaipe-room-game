package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import nz.ac.auckland.se206.GameState;

public class EndingWinController {

  @FXML
  private void initialize() {
    keyLabel.setText(String.valueOf(GameState.key));
    riddleLabel.setText(String.valueOf(GameState.riddleWord));
    keyTextArea.setVisible(false);
    riddleTextArea.setVisible(false);
  }

  @FXML private Label keyLabel;
  @FXML private TextArea keyTextArea;

  @FXML
  private void keyLabelEntered() {
    keyTextArea.setVisible(true);
    keyTextArea.setText(outputKeyBackground());
  }

  @FXML
  private void keyLabelExited() {
    keyTextArea.setVisible(false);
  }

  @FXML private Label riddleLabel;
  @FXML private TextArea riddleTextArea;

  @FXML
  private void riddleLabelEntered() {
    riddleTextArea.setVisible(true);
    riddleTextArea.setText(outputRiddleBackground());
  }

  @FXML
  private void riddleLabelExited() {
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
  }

  @FXML
  private void restartButtonClicked() {
    System.out.println("Play again button clicked");
  }
}
