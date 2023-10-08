package nz.ac.auckland.se206.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
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
  @FXML private Label messageLabel;
  @FXML private Label timeLabel;

  private DraggableManager draggableManager = new DraggableManager();

  private Map<Circle, ImageView> solutionMap = new HashMap<>();

  /** Initializes the room view, it is called when the room loads. */
  @FXML
  private void initialize() {
    draggableManager = new DraggableManager();
    solutionMap = new HashMap<>();
    initializeTimer();
    // Assuming a method to setup the correct planet-circle relationship
    setupSolutionMap();
    setupDraggables();
    genertateRandomPositions();
  }

  /** Initializes the timer and bind the text label. */
  @FXML
  private void initializeTimer() {
    // Bind the timer to the label
    timeLabel.textProperty().bind(GameState.timeManager.getSecond().asString());
  }

  /** this will setup the correct planet-circle relationship. */
  private void setupSolutionMap() {
    // put the correct planet-circle relationship into the solution map
    solutionMap.put(circleOne, planetOne);
    solutionMap.put(circleTwo, planetTwo);
    solutionMap.put(circleThree, planetThree);
    solutionMap.put(circleFour, planetFour);
    solutionMap.put(circleFive, planetFive);
    solutionMap.put(circleSix, planetSix);
    solutionMap.put(circleSeven, planetSeven);
  }

  /**
   * this will make all the planets draggable and be able to snap to an arbitrary target circles.
   */
  private void setupDraggables() {
    // make all the planets draggable and be able to snap to an arbitrary target circles
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
    // the eighth planet is still draggable but it doesn't have a target circle
    draggableManager.makeDraggable(planetEight, targetCirclesForPlanetOne);
  }

  /** this will generate random positions for all the planets. */
  private void genertateRandomPositions() {
    // generate random positions for all the planets
    placePlanetWithinMenu(planetOne);
    placePlanetWithinMenu(planetTwo);
    placePlanetWithinMenu(planetThree);
    placePlanetWithinMenu(planetFour);
    placePlanetWithinMenu(planetFive);
    placePlanetWithinMenu(planetSix);
    placePlanetWithinMenu(planetSeven);
    placePlanetWithinMenu(planetEight);
  }

  /** this will place the planet within the menu area. */
  private void placePlanetWithinMenu(ImageView planet) {
    // place the planet within the menu area
    Point2D position;
    int retryCount = 0;

    do {
      position = getRandomPositionWithinMenu(planet);
      retryCount++;
      if (retryCount > 100) {
        // try 100 times to find a suitable position
        System.err.println("Failed to find suitable position for planet after 100 attempts.");
        return;
      }
    } while (isTooCloseToOtherPlanets(planet, position));

    // Set the position of the planet
    planet.setLayoutX(position.getX());
    planet.setLayoutY(position.getY());
  }

  /**
   * this will get a random position within the menu area.
   *
   * @param planet the planet to get the random position for
   */
  private Point2D getRandomPositionWithinMenu(ImageView planet) {
    // calculate a random position within the menu area
    double x = menuArea.getX() + (Math.random() * (menuArea.getWidth() - planet.getFitWidth()));
    double y = menuArea.getY() + (Math.random() * (menuArea.getHeight() - planet.getFitHeight()));
    return new Point2D(x, y);
  }

  /**
   * this will check if the planet is too close to other planets.
   *
   * @param planet the planet to check
   * @param position the position of the planet
   * @return true if the planet is too close to other planets, false otherwise
   */
  private boolean isTooCloseToOtherPlanets(ImageView planet, Point2D position) {
    // check if the planet is too close to other planets
    double minDistance = 30;

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

    // if the planet is too close to other planets, return true
    for (ImageView otherPlanet : allPlanets) {
      if (otherPlanet == planet) {
        continue;
      }

      double distance = position.distance(otherPlanet.getLayoutX(), otherPlanet.getLayoutY());
      if (distance < minDistance) {
        return true;
      }
    }
    return false;
  }

  /** this will be called when the back button is clicked. */
  @FXML
  private void onBackButtonClicked() {
    // Go back to room3
    System.out.println("Back button clicked");
    App.setUi(RoomType.ROOM3);
  }

  /** this will be called when mouse is released. */
  @FXML
  private void planetMouseReleased() {
    // Once a planet is released, we should check if all matches are correct.
    checkAllMatches();
  }

  /** this will check if all matches are correct. */
  private void checkAllMatches() {
    System.out.println("Checking all matches");

    // loop through all the planet-circle pairs in the solution map
    for (Map.Entry<Circle, ImageView> entry : solutionMap.entrySet()) {
      Circle circle = entry.getKey();
      ImageView planet = entry.getValue();

      // get the center of the circle and the center of the planet
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

      // new Point2D(x, y) represents a point at (x, y) and get the distance between two points
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
      puzzleSolved();
    } catch (ApiProxyException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * this will be called when the puzzle is solved.
   *
   * @throws ApiProxyException the api proxy exception
   */
  private void puzzleSolved() throws ApiProxyException {
    // if all matches are correct, the puzzle is solved
    System.out.println("Puzzle solved");
    messageLabel.setText("Puzzle solved!");
    GameState.isPuzzleRoom3Solved.setValue(true);
  }
}
