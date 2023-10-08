package nz.ac.auckland.se206;

import java.util.HashMap;
import javafx.scene.Parent;

/**
 * SceneManager class is used to manage the scenes in the game. It will add the scenes to the
 * sceneMap and get the scenes from the sceneMap.
 */
public class SceneManager {

  /** Enum for all the different rooms, puzzles and scenes. */
  public enum RoomType {
    CHAT,
    ENDINGLOSE,
    ENDINGWIN,
    EXITDOOR,
    GAMEMASTER,
    MENU,
    ROOM1,
    ROOM2,
    ROOM3,
    ROOM4,
    ROOM2PUZZLE1,
    ROOM2PUZZLE2,
    ROOM3PUZZLE1,
    ROOM3PUZZLE2
  }

  // HashMap to store the different rooms and their scenes
  private static HashMap<RoomType, Parent> sceneMap = new HashMap<RoomType, Parent>();

  /** this will reset all the variables for a new game. */
  public static void reset() {
    // reset the sceneMap
    sceneMap = new HashMap<RoomType, Parent>();
  }

  /**
   * Add a new scene to the sceneMap.
   *
   * @param room the room type
   * @param parent the parent node
   */
  public static void addUi(RoomType room, Parent parent) {
    sceneMap.put(room, parent);
  }

  /**
   * Get the scene from the sceneMap.
   *
   * @param room the room type
   * @return the parent node
   */
  public static Parent getUi(RoomType room) {
    return sceneMap.get(room);
  }
}
