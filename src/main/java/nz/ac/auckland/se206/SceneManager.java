package nz.ac.auckland.se206;

import java.util.HashMap;
import javafx.scene.Parent;

public class SceneManager {

  public enum roomType {
    MENU,
    ROOM1,
    ROOM2,
    ROOM3,
    ROOM4,
    ENDING
  }

  private static HashMap<roomType, Parent> sceneMap = new HashMap<roomType, Parent>();

  public static void addUi(roomType room, Parent parent) {
    sceneMap.put(room, parent);
  }

  public static Parent getUi(roomType room) {
    return sceneMap.get(room);
  }
}
