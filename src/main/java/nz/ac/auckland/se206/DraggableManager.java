package nz.ac.auckland.se206;

import java.util.List;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class DraggableManager {

  private final double threholdConstant = 0.5;
  private double deltaX = 0;
  private double deltaY = 0;

  public void makeDraggable(ImageView planet, List<Circle> targetCircles) {
    planet.setOnMousePressed(
        mouseEvent -> {
          // Calculate the difference between the mouse's X, Y and the ImageView's top-left X, Y
          deltaX = mouseEvent.getSceneX() - planet.getLayoutX();
          deltaY = mouseEvent.getSceneY() - planet.getLayoutY();
        });

    planet.setOnMouseDragged(
        mouseEvent -> {
          // Use the calculated deltas to adjust the ImageView's position
          planet.setLayoutX(mouseEvent.getSceneX() - deltaX);
          planet.setLayoutY(mouseEvent.getSceneY() - deltaY);
          for (Circle targetCircle : targetCircles) {
            checkAndSnapToTarget(planet, targetCircle);
          }
        });
  }

  private void checkAndSnapToTarget(ImageView planet, Circle targetCircle) {
    // get the distance between the center of the planet and the center of the target circle
    double distance =
        Math.sqrt(
            Math.pow(
                    (planet.getLayoutX() + planet.getFitWidth() / 2.0) - targetCircle.getLayoutX(),
                    2)
                + Math.pow(
                    (planet.getLayoutY() + planet.getFitHeight() / 2.0) - targetCircle.getLayoutY(),
                    2));
    // get the threshold for the planet to snap to the target circle
    double threshold = threholdConstant * targetCircle.getRadius();

    // check when the distance is less than the threshold, snap the planet to the target circle
    if (distance < threshold) {
      planet.setLayoutX(targetCircle.getLayoutX() - planet.getFitWidth() / 2.0);
      planet.setLayoutY(targetCircle.getLayoutY() - planet.getFitHeight() / 2.0);
      return;
    }
  }
}
