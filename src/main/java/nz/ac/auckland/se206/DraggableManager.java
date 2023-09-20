package nz.ac.auckland.se206;

import javafx.scene.Node;

public class DraggableManager {
  private double mouseX;
  private double mouseY;

  public void makeDraggable(Node node) {

    node.setOnMouseClicked(
        mouseEvent -> {
          mouseX = mouseEvent.getX();
          mouseY = mouseEvent.getY();
        });

    node.setOnMouseDragged(
        mouseEvent -> {
          node.setLayoutX(mouseEvent.getSceneX() - mouseX);
          node.setLayoutY(mouseEvent.getSceneY() - mouseY);
        });
  }
}
