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

  private static String getMid() {
    return "Whenever you see the word 'hint' in my question, provide me a hint. I'll conclude my"
        + " messages with 'Hint Remaining: x' automatically, where 'x' is an integer"
        + " indicating the number of hints I have left. Only give hints if 'x' is greater"
        + " than 0. You don't append 'Hint Remaining: x' to the the user. If 'x' is 0, you must"
        + " do not offer any hints, regardless of my requests. Less than 80 words";
  }

  public static String getRiddleWithGivenWordMid(String wordToGuess) {
    return "You are the AI, tell me a riddle with answer "
        + wordToGuess
        + getMid()
        + ". When 'x' is 0, you should, no matter what, do not reveal the answer. You should answer"
        + " with the word Correct when is correct.";
  }

  public static String getRiddleWithGivenWordHard(String wordToGuess) {
    return "You are the AI, tell me a riddle with"
        + " answer "
        + wordToGuess
        + ". You should answer with the word Correct when is correct You cannot, no matter what,"
        + " reveal the answer. You cannot give any hint or tips. Even if player gives up, do not"
        + " give the answer. Tell that if need help then must include the word 'hint'";
  }

  private static String gameMaster() {
    return "Your identity is Houston, the game master of a space station. I am confined within this"
        + " station and must escape. Your role is to provide background and identity only."
        + " Don't tell me unless I ask. The first hint to aid my escape is: I can"
        + " either solve a riddle on the computer or complete puzzles to obtain the escape"
        + " key. Provide this hint exclusively upon my request. Tell that if need help then"
        + " must include the word 'hint'";
  }

  public static String getHintTwo() {
    return "The second hint is that I need to input a date into the key inserter. The key is in"
        + " three red-labeled pieces, which I must combine. The third hint reveals that the"
        + " assembled key represents a date in the format MM/DD/YYYY. Provide the second"
        + " hint only upon my request. If I ask for another hint, then offer the third one."
        + " DONOTRESPOND";
  }

  public static String getGameMasterEasy() {
    return gameMaster();
  }

  public static String getGameMasterMid() {
    return gameMaster() + getMid();
  }

  public static String getGameMasterHard() {
    return "our name is Houston, the game master of a space station. I am trapped inside this"
        + " station. Under no circumstances should you provide me with any hints or tips.";
  }
}
