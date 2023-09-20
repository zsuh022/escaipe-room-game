package nz.ac.auckland.se206;

import javafx.scene.Node;
import javafx.scene.shape.Circle;

public class DraggableManager {
  private double mouseX;
  private double mouseY;
  private final double threholdConstant = 0.25;

  public void makeDraggable(Node node, Circle targetCircle) {
    node.setOnMouseClicked(
        mouseEvent -> {
          mouseX = mouseEvent.getX();
          mouseY = mouseEvent.getY();
        });

    node.setOnMouseDragged(
        mouseEvent -> {
          node.setLayoutX(mouseEvent.getSceneX() - mouseX);
          node.setLayoutY(mouseEvent.getSceneY() - mouseY);
          checkAndSnapToTarget(node, targetCircle);
        });
  }

  private void checkAndSnapToTarget(Node node, Circle targetCircle) {
    double distance =
        Math.sqrt(
            Math.pow(node.getLayoutX() - targetCircle.getLayoutX(), 2)
                + Math.pow(node.getLayoutY() - targetCircle.getLayoutY(), 2));
    double threshold = threholdConstant * (targetCircle.getRadius() + ((Circle) node).getRadius());

    if (distance < threshold) {
      node.setLayoutX(targetCircle.getLayoutX());
      node.setLayoutY(targetCircle.getLayoutY());
    }
  }
}
