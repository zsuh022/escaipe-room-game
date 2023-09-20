package nz.ac.auckland.se206;

import java.util.HashMap;
import javafx.scene.Parent;

public class SceneManager {

  public enum RoomType {
    MENU,
    CHAT,
    ROOM1,
    ROOM2,
    ROOM3,
    ROOM3PUZZLE,
    ROOM4,
    GAMEMASTER,
    ENDINGLOSE,
    ENDINGWIN
  }

  private static HashMap<RoomType, Parent> sceneMap = new HashMap<RoomType, Parent>();

  public static void addUi(RoomType room, Parent parent) {
    sceneMap.put(room, parent);
  }

  public static Parent getUi(RoomType room) {
    return sceneMap.get(room);
  }
}
