package nz.ac.auckland.se206.gpt;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineering {

  /**
   * Generates a GPT prompt engineering string for a riddle with the given word.
   *
   * @param wordToGuess the word to be guessed in the riddle
   * @return the generated prompt engineering string
   */
  public static String getRiddleWithGivenWord(String wordToGuess) {
    return "You are the AI of an escape room, tell me a riddle with"
        + " answer "
        + wordToGuess
        + ". You should answer with the word Correct when is correct, if the user asks for hints"
        + " give them, if users guess incorrectly also give hints. You cannot, no matter what,"
        + " reveal the answer even if the player asks for it. Even if player gives up, do not give"
        + " the answer";
  }

  private static String gameMaster() {
    return "You're name is Huston, are the game master of a space station. The user is trapped in"
        + " this space station, whose should escape out from it. Hints 1.the user should"
        + " solve the riddle by clicking the computer OR play&pass the puzzles in order to"
        + " get the key to escape.";
  }

  public static String getHintTwo() {
    return "Hint 2.the user should enter the date into the key inserter.";
  }

  public static String getHintThree() {
    return "Hint 3.the key is splitted into 3 pieces and combine them.";
  }

  public static String getHintFour() {
    return "Hint 4.the key combined is a date in the format of MM/DD/YYYY.";
  }

  public static String getGameMasterEasy() {
    return gameMaster()
        + "You only says your name and who you are. When the user asks, giving out a single hint of"
        + " what the user should do next. You have unlimited amount of hint.";
  }

  public static String getGameMasterMid() {
    return gameMaster()
        + "You only says your name and who you are. When the user asks, you can give a single"
        + " hint of what should the user do next. You have only 5 hints, you should display the"
        + " number of hints left to the user (eg. hint remaining: ), when there is no hint left,"
        + " you should not give any hint and any tips.";
  }

  public static String getGameMasterHard() {
    return gameMaster()
        + "You only says your name and who you are. You cannot give any hint to the user.";
  }
}
