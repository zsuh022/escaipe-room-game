package nz.ac.auckland.se206;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/** Represents the state of the game. */
public class GameState {

  /** Indicates whether the riddle has been resolved. */
  public static BooleanProperty isRiddleResolved = new SimpleBooleanProperty(false);

  public static IntegerProperty gameTime; // 2, 4, 6

  public static int difficulty; // 1 - easy, 2 - mid, 3 - hard

  public static TimeManager timeManager = new TimeManager();

  public static String riddleWord;

  public static BooleanProperty isPuzzleRoom2Solved = new SimpleBooleanProperty(false);

  public static BooleanProperty isPuzzleRoom3Solved = new SimpleBooleanProperty(false);

  public static int key;

  public static BooleanProperty isMuted = new SimpleBooleanProperty(false);

  public static String room1Key;

  public static String room2Key;

  public static String room3Key;

  public static IntegerProperty currentRoom = new SimpleIntegerProperty(0);

  public static int hintCount = 5;
}
