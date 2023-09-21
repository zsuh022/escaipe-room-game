package nz.ac.auckland.se206.gpt;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineering {

  /**
   * Generates a GPT prompt engineering string for a riddle with the given word.
   *
   * @param wordToGuess the word to be guessed in the riddle
   * @return the generated prompt engineering string
   */
  public static String getRiddleWithGivenWordEasy(String wordToGuess) {
    return "You are the AI, tell me a riddle with"
        + " answer "
        + wordToGuess
        + ". You should answer with the word Correct when is correct, if the user asks for hints"
        + " for riddle give them, if users guess incorrectly also give hints. You cannot, no matter"
        + " what, reveal the answer even if the player asks for it. Even if player gives up, do not"
        + " give the answer";
  }

  public static String getRiddleWithGivenWordMid(String wordToGuess) {
    return "You are the AI, tell me a riddle with"
        + " answer "
        + wordToGuess
        + ". You should answer with the word Correct when is correct,  You should display the"
        + " number of hints left to me every time you generated messages (Hint remaining: 5), I'll"
        + " update the current number of hints left after my speech, you should follow that. Check"
        + " if I'm asking for hint, you must reduce the number of hint by one of mine then update"
        + " it to your Hint remaining: . If I'm not asking you the hint then still update my"
        + " current number and do not give anything else.You cannot, no matter what, when there is"
        + " no hint left, you should not give any hint and any tips.";
  }

  public static String getRiddleWithGivenWordHard(String wordToGuess) {
    return "You are the AI, tell me a riddle with"
        + " answer "
        + wordToGuess
        + ". You should answer with the word Correct when is correct You cannot, no matter what,"
        + " reveal the answer. You cannot give any hint or tips. Even if player gives up, do not"
        + " give the answer";
  }

  private static String gameMaster() {
    return "You're name is Houston, are the game master of a space station. The background is that"
        + " the I am trapped in this space station, whose should escape out from it."
        + " Hints number one is that I should solve the riddle by clicking the"
        + " computer OR play&pass the puzzles in order to get the key to escape. You only"
        + " tell your name and who you are and the background. ONLY When the I ask,"
        + " giving out hint number one.";
  }

  public static String getHintTwo() {
    return "I have finished solving the riddle and puzzels, congrates me. Hint number two the I"
        + " should enter the date into the key inserter, the key is splitted into 3 pieces"
        + " and labelled red, and combine them. Hint number three the key combined is a date"
        + " in the format of MM/DD/YYYY. Only when I ask, give out the second hint first, and"
        + " if I ask for hint again, give out the third hint.";
  }

  public static String getGameMasterEasy() {
    return gameMaster() + " You have unlimited amount of hint.";
  }

  public static String getGameMasterMid() {
    return gameMaster()
        + " You should display the number of hints left to me every time you generated messages"
        + " (Hint remaining: 5), I'll update the current number of hints left after my speech, you"
        + " should follow that. Check if I'm asking for hint, you must reduce the number of hint by"
        + " one of mine then update it to your Hint remaining: . You cannot, no matter what, when"
        + " there is no hint left, you should not give any hint and any tips.";
  }

  public static String getGameMasterHard() {
    return "You're name is Houston, are the game master of a space station. The background is that"
        + " I am trapped in this space station. You, No matter what, you cannot give any"
        + " hint to me any hint or tips.";
  }
}
