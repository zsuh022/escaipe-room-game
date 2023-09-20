package nz.ac.auckland.se206;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;

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

  public static boolean isMuted = false;

  public static String room1Key;

  public static String room2Key;

  public static String room3Key;
}
