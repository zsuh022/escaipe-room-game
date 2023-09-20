package nz.ac.auckland.se206.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.DraggableManager;

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

  DraggableManager draggableManager = new DraggableManager();

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

  public void checkAllMatches() {
    for (Map.Entry<Circle, ImageView> entry : solutionMap.entrySet()) {
      if (entry.getKey().getLayoutX() != entry.getValue().getLayoutX()
          || entry.getKey().getLayoutY() != entry.getValue().getLayoutY()) {
        return;
      }
    }
    puzzelsolved();
  }

  private void puzzelsolved() {
    // your puzzle solved logic here
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
}
