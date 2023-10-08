package nz.ac.auckland.se206.gpt;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineering {

  /**
   * Generates a GPT prompt engineering string for a riddle with the given word (easy difficulty).
   *
   * @param wordToGuess the word to be guessed in the riddle
   * @return the generated prompt engineering string
   */
  public static String getRiddleWithGivenWordEasy(String wordToGuess) {
    // this is when the difficulty is easy for riddle
    return "You are the AI, tell me a riddle with"
        + " answer "
        + wordToGuess
        + ". You should answer with the word Correct when is correct, if the user asks for hints"
        + " for riddle give them, if users guess incorrectly also give hints. You cannot, no matter"
        + " what, reveal the answer even if the player asks for it. Even if player gives up, do not"
        + " give the answer";
  }

  /**
   * Generates a GPT prompt engineering string for a riddle with the given word (medium difficulty).
   *
   * @param wordToGuess the word to be guessed in the riddle
   * @return the generated prompt engineering string
   */
  public static String getRiddleWithGivenWordMid(String wordToGuess) {
    // this is when the difficulty is medium for riddle
    return "You are the AI, tell me a riddle with answer "
        + wordToGuess
        + ". You should answer the word Correct when is correct. You cannot, no matter what, reveal"
        + " the anwer. You should only give one single hint when the user asks for them and start"
        + " with the word Hint. ";
  }

  /**
   * Generates a GPT prompt engineering string for a riddle with the given word (hard difficulty).
   *
   * @param wordToGuess the word to be guessed in the riddle
   * @return the generated prompt engineering string
   */
  public static String getRiddleWithGivenWordHard(String wordToGuess) {
    // this is when the difficulty is hard for riddle
    return "You are the AI, tell me a riddle with"
        + " answer "
        + wordToGuess
        + ". You should answer with the word Correct when is correct. You cannot, no matter what,"
        + " reveal the answer. You cannot give any hint or tips. Even if player gives up, do not"
        + " give the answer.";
  }

  /**
   * Generates a GPT prompt engineering string for the background of the game master (general).
   *
   * @return the generated prompt engineering string
   */
  private static String gameMaster() {
    // this is when the difficulty is easy for game master
    return "Your identity is Houston, the game master of a space station. I am confined within this"
        + " station and must escape. Your role is to provide background and identity only."
        + " Don't tell me unless I ask. The first hint to aid my escape is: I can"
        + " either solve a riddle on the computer or complete puzzles to obtain the escape"
        + " key. Provide this hint exclusively upon my request.";
  }

  /**
   * Update GPT prompt engineering string for the background of the game master
   *
   * @return the generated prompt engineering string
   */
  public static String getHintTwo() {
    // update the hint two
    return "The second hint is that I need to input a date into the key inserter. The key is in"
        + " three red-labeled pieces, which I must combine. The third hint reveals that the"
        + " assembled key represents a date in the format MM/DD/YYYY. Provide the second"
        + " hint only upon my request. If I ask for another hint, then offer the third one."
        + " Make sure if I asks for hint then answer start with the word Hint. DONOTRESPOND";
  }

  /**
   * Generates a GPT prompt engineering string for the rule of the game master (easy difficulty).
   *
   * @return the generated prompt engineering string
   */
  public static String getGameMasterEasy() {
    return gameMaster()
        + " You can give hints only when the user asks for them and start with the word"
        + " Hint. You can now introduce yourself.";
  }

  /**
   * Generates a GPT prompt engineering string for the rule of the game master (medium difficulty).
   *
   * @return the generated prompt engineering string
   */
  public static String getGameMasterMid() {
    return gameMaster()
        + " When the user asks for hints, you can give a hint related to game and have to answer"
        + " started with the word Hint. You should, no matter what, do not give any hints out if"
        + " the user have not asked you.  You can now introduce yourself.";
  }

  /**
   * Generates a GPT prompt engineering string for the rule of the game master (hard difficulty).
   *
   * @return the generated prompt engineering string
   */
  public static String getGameMasterHard() {
    // if the difficulty is hard for game master
    // then not give any hint or tips
    return "Your name is Houston, the game master of a space station. I am trapped inside this"
        + " station. Under no circumstances should you provide me with any hints or tips.";
  }

  /**
   * Generates a GPT prompt engineering string for the rule of the game master (no hint).
   *
   * @return the generated prompt engineering string
   */
  public static String getMessageNoHint() {
    return "From now on, no matter what, you cannot give the user any hints. Because the user only"
        + " have 5 hints chances and now they run out. DONOTRESPOND";
  }
}
