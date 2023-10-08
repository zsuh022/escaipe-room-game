package nz.ac.auckland.se206;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * HintDisplayHelper class is used to display a hint in the given text area, one character at a
 * time.
 */
public class HintDisplayHelper {
  private static volatile boolean stopCurrentTyping = false;

  /**
   * Displays a hint in the given text area, one character at a time.
   *
   * @param textArea the text area to display the hint in
   * @param message the hint to display
   */
  public static void displayHintInTextArea(TextArea textArea, String message) {
    // If there's a previous hint still being typed, stop it
    stopCurrentTyping = true;

    // Pause to allow the current typing operation to stop
    try {
      Thread.sleep(150); // This duration can be adjusted
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    stopCurrentTyping = false;

    new Thread(
            () -> {
              char[] chars = message.toCharArray();
              StringBuilder displayText = new StringBuilder();

              for (char c : chars) {
                if (stopCurrentTyping) {
                  // If stopCurrentTyping is set to true, exit the loop
                  return;
                }

                try {
                  Thread.sleep(25); // adjust this duration as necessary

                  Platform.runLater(
                      () -> {
                        displayText.append(c);
                        GameState.sharedMessage.setValue(displayText.toString());
                      });
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
              }
            })
        .start();
  }

  public static void displayThreeDots() {
    if (GameState.hintNumberRemaining.getValue() == 0) {
      displayHintInTextArea(
          null, "You have reached the hint limit. Click me to view previous hints.");
      return;
    }
    stopCurrentTyping = true;
    // Pause to allow the current typing operation to stop
    try {
      Thread.sleep(140); // This duration can be adjusted
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    stopCurrentTyping = false;
    new Thread(
            () -> {
              for (int i = 1; i <= 6; i++) {
                final int count = i;
                Platform.runLater(
                    () -> {
                      GameState.sharedMessage.setValue(".".repeat(count));
                    });
                try {
                  Thread.sleep(700); // This causes the pause between each dot
                } catch (InterruptedException e) {
                  e.printStackTrace();
                  return; // Exit the thread if an interruption occurs
                }
              }
            })
        .start();
  }
}
