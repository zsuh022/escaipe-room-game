package nz.ac.auckland.se206;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/** Represents the state of the game. */
public class GameState {

  /** Indicates whether the riddle has been resolved. */
  public static BooleanProperty isRiddleResolved = new SimpleBooleanProperty(false);

  public static IntegerProperty gameTime; // 2, 4, 6

  public static int gameDifficulty; // 1 - easy, 2 - mid, 3 - hard

  public static TimeManager timeManager = new TimeManager();

  public static String riddleWord;

  public static BooleanProperty isPuzzleRoom2Solved = new SimpleBooleanProperty(false);

  public static BooleanProperty isPuzzleRoom3Solved = new SimpleBooleanProperty(false);

  public static int key;

  public static BooleanProperty isMuted = new SimpleBooleanProperty(false);

  public static String room1Key; // one of the split key

  public static String room2Key;

  public static String room3Key;

  public static IntegerProperty currentRoom = new SimpleIntegerProperty(0); // 1, 2, 3, 4

  public static Integer hintCount = 5;

  public static int roomNumber = 1;

  public static IntegerProperty hintNumberRemaining = new SimpleIntegerProperty(5);

  public static StringProperty latestHint = new SimpleStringProperty("");

  public static BooleanProperty requestHint = new SimpleBooleanProperty(false);

  public static StringProperty sharedMessage = new SimpleStringProperty("");

  /** this will reset all the variables for a new game. */
  public static void reset() {
    // reset all the variables for a new game
    isRiddleResolved = new SimpleBooleanProperty(false);
    gameTime = new SimpleIntegerProperty(0);
    gameDifficulty = 0;
    timeManager = new TimeManager();
    riddleWord = "";
    isPuzzleRoom2Solved = new SimpleBooleanProperty(false);
    isPuzzleRoom3Solved = new SimpleBooleanProperty(false);
    key = 0;
    isMuted = new SimpleBooleanProperty(false);
    room1Key = "";
    room2Key = "";
    room3Key = "";
    currentRoom = new SimpleIntegerProperty(0);
    hintCount = 5;
    roomNumber = 1; // the room number will be 1 at the beginning
    hintNumberRemaining = new SimpleIntegerProperty(5);
    latestHint = new SimpleStringProperty("");
    requestHint = new SimpleBooleanProperty(false);
    sharedMessage = new SimpleStringProperty("");
  }
}
