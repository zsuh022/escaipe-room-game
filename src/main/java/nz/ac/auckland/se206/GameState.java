package nz.ac.auckland.se206;

import javafx.beans.property.IntegerProperty;

/** Represents the state of the game. */
public class GameState {

  /** Indicates whether the riddle has been resolved. */
  public static boolean isRiddleResolved = false;

  /** Indicates whether the key has been found. */
  public static boolean isKeyFound = false;

  public static IntegerProperty gameTime; // 2, 4, 6

  public static int difficulty; // 1 - easy, 2 - mid, 3 - hard

  public static TimeManager timeManager = new TimeManager();
}
