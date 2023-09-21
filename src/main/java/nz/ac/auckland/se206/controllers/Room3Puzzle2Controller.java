package nz.ac.auckland.se206.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.DraggableManager;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.RoomType;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class Room3Puzzle2Controller {

  @FXML private Circle circleOne;
  @FXML private Circle circleTwo;
  @FXML private Circle circleThree;
  @FXML private Circle circleFour;
  @FXML private Circle circleFive;
  @FXML private Circle circleSix;
  @FXML private Circle circleSeven;
  @FXML private ImageView planetOne;
  @FXML private ImageView planetTwo;
  @FXML private ImageView planetThree;
  @FXML private ImageView planetFour;
  @FXML private ImageView planetFive;
  @FXML private ImageView planetSix;
  @FXML private ImageView planetSeven;
  @FXML private ImageView planetEight;
  @FXML private Rectangle menuArea;

  private DraggableManager draggableManager = new DraggableManager();

  private Map<Circle, ImageView> solutionMap = new HashMap<>();

  @FXML
  private void initialize() {
    // Assuming a method to setup the correct planet-circle relationship
    setupSolutionMap();
    setupDraggables();
    genertateRandomPositions();
  }

  private void setupSolutionMap() {
    solutionMap.put(circleOne, planetOne);
    solutionMap.put(circleTwo, planetTwo);
    solutionMap.put(circleThree, planetThree);
    solutionMap.put(circleFour, planetFour);
    solutionMap.put(circleFive, planetFive);
    solutionMap.put(circleSix, planetSix);
    solutionMap.put(circleSeven, planetSeven);
  }

  private void setupDraggables() {
    List<Circle> targetCirclesForPlanetOne =
        Arrays.asList(
            circleOne, circleTwo, circleThree, circleFour, circleFive, circleSix, circleSeven);
    draggableManager.makeDraggable(planetOne, targetCirclesForPlanetOne);
    draggableManager.makeDraggable(planetTwo, targetCirclesForPlanetOne);
    draggableManager.makeDraggable(planetThree, targetCirclesForPlanetOne);
    draggableManager.makeDraggable(planetFour, targetCirclesForPlanetOne);
    draggableManager.makeDraggable(planetFive, targetCirclesForPlanetOne);
    draggableManager.makeDraggable(planetSix, targetCirclesForPlanetOne);
    draggableManager.makeDraggable(planetSeven, targetCirclesForPlanetOne);
    draggableManager.makeDraggable(planetEight, targetCirclesForPlanetOne);
  }

  private void genertateRandomPositions() {
    placePlanetWithinMenu(planetOne);
    placePlanetWithinMenu(planetTwo);
    placePlanetWithinMenu(planetThree);
    placePlanetWithinMenu(planetFour);
    placePlanetWithinMenu(planetFive);
    placePlanetWithinMenu(planetSix);
    placePlanetWithinMenu(planetSeven);
    placePlanetWithinMenu(planetEight);
  }

  private void placePlanetWithinMenu(ImageView planet) {
    Point2D position;
    int retryCount = 0;

    do {
      position = getRandomPositionWithinMenu(planet);
      retryCount++;
      if (retryCount > 100) {
        System.err.println("Failed to find suitable position for planet after 100 attempts.");
        return;
      }
    } while (isTooCloseToOtherPlanets(planet, position));

    planet.setLayoutX(position.getX());
    planet.setLayoutY(position.getY());
  }

  private Point2D getRandomPositionWithinMenu(ImageView planet) {
    double x = menuArea.getX() + (Math.random() * (menuArea.getWidth() - planet.getFitWidth()));
    double y = menuArea.getY() + (Math.random() * (menuArea.getHeight() - planet.getFitHeight()));
    return new Point2D(x, y);
  }

  private boolean isTooCloseToOtherPlanets(ImageView planet, Point2D position) {
    double minDistance = 30; // for example, adjust as required

    List<ImageView> allPlanets =
        Arrays.asList(
            planetOne,
            planetTwo,
            planetThree,
            planetFour,
            planetFive,
            planetSix,
            planetSeven,
            planetEight);

    for (ImageView otherPlanet : allPlanets) {
      if (otherPlanet == planet) continue;

      double distance = position.distance(otherPlanet.getLayoutX(), otherPlanet.getLayoutY());
      if (distance < minDistance) {
        return true;
      }
    }
    return false;
  }

  @FXML
  private void onBackButtonClicked() {
    System.out.println("Back button clicked");
    App.setUi(RoomType.ROOM3);
  }

  @FXML
  private void planetMouseReleased() {
    // Once a planet is released, we should check if all matches are correct.
    checkAllMatches();
  }

  private void checkAllMatches() {
    System.out.println("Checking all matches");
    for (Map.Entry<Circle, ImageView> entry : solutionMap.entrySet()) {
      Circle circle = entry.getKey();
      ImageView planet = entry.getValue();

      double circleCenterX = circle.getLayoutX();
      double circleCenterY = circle.getLayoutY();

      double planetCenterX = planet.getLayoutX() + planet.getFitWidth() / 2.0;
      double planetCenterY = planet.getLayoutY() + planet.getFitHeight() / 2.0;

      System.out.println(
          "Circle: "
              + circleCenterX
              + ", "
              + circleCenterY
              + " Planet: "
              + planetCenterX
              + ", "
              + planetCenterY);

      Point2D circleCenterPoint = new Point2D(circleCenterX, circleCenterY);
      double distance = circleCenterPoint.distance(planetCenterX, planetCenterY);

      // Adjust this threshold as necessary
      double threshold = 5.0;

      if (distance > threshold) {
        System.out.println("Not all matches correct");
        return;
      }
    }
    System.out.println("All matches correct");
    try {
      showDialog("Solved", null, "You have ordered the planets correctly");
      puzzleSolved();
    } catch (ApiProxyException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void puzzleSolved() throws ApiProxyException {
    System.out.println("Puzzle solved");
    GameState.isPuzzleRoom3Solved.setValue(true);
  }

  /**
   * Displays a dialog box with the given title, header text, and message.
   *
   * @param title the title of the dialog box
   * @param headerText the header text of the dialog box
   * @param message the message content of the dialog box
   */
  private void showDialog(String title, String headerText, String message) {
    // create a dialog box with the given title, header text, and message
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(message);
    // close the dialog box after 4 seconds
    Thread alertClose =
        new Thread(
            () -> {
              try {
                Thread.sleep(5000);
                Platform.runLater(
                    () -> {
                      alert.close();
                    });
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            });
    alertClose.start();
    alert.showAndWait();
  }
}
