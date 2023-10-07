package nz.ac.auckland.se206;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class HintDisplayHelper {
  private static volatile boolean stopCurrentTyping = false;

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
                        textArea.setText(displayText.toString());

                        // Set cursor to the end to mimic typing effect
                        textArea.positionCaret(textArea.getText().length());
                      });
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
              }
            })
        .start();
  }
}
