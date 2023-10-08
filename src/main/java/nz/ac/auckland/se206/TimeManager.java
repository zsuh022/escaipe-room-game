package nz.ac.auckland.se206;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;
import nz.ac.auckland.se206.SceneManager.RoomType;
import nz.ac.auckland.se206.speech.TextToSpeech;

/**
 * TimeManager class is used to manage the timer in the game. It will start the timer and stop the
 * timer.
 */
public class TimeManager {

  private Timeline timeLine;
  private IntegerProperty seconds;

  /**
   * This is the constructor for TimeManager.
   *
   * @param seconds the seconds
   */
  public void setTime(int seconds) {
    this.seconds = new SimpleIntegerProperty(seconds);
  }

  /** This will set the timer and start the timer. */
  public void setTimer() {
    // using a timeline to update the timer
    timeLine =
        new Timeline(
            new KeyFrame(
                Duration.seconds(1),
                e -> {
                  // if the timer is not 0, decrement the timer
                  if (seconds.get() > 0) {
                    int newSeconds = seconds.get() - 1;
                    seconds.set(newSeconds);
                  }
                  // if the timer is 12, output a message
                  if (seconds.get() == 12) {
                    Thread thread =
                        new Thread(
                            () -> {
                              TextToSpeech textToSpeech = new TextToSpeech();
                              textToSpeech.speak("Hurry up, you've only got 10 seconds left!");
                            });
                    thread.start();
                  }
                  // if the timer is 3, say 3, 2, 1
                  if (seconds.get() <= 3 && seconds.get() > 0) {
                    Thread thread2 =
                        new Thread(
                            () -> {
                              TextToSpeech textToSpeech = new TextToSpeech();
                              textToSpeech.speak(String.valueOf(seconds.get()));
                            });
                    thread2.start();
                  }
                }));
    timeLine.setCycleCount(seconds.get());
    timeLine.play();

    // when the timer is finished, go to the endinglose scene
    timeLine.setOnFinished(
        e -> {
          try {
            SceneManager.addUi(RoomType.ENDINGLOSE, App.loadFxml("endinglose"));
            App.setUi(RoomType.ENDINGLOSE);
          } catch (IOException e1) {
            e1.printStackTrace();
          }
        });
  }

  /**
   * Get the seconds property of the timer.
   *
   * @return the seconds property
   */
  public IntegerProperty getSecond() {
    return seconds;
  }

  /** stop the timer and set the timer to null. */
  public void stopTimer() {
    // stop the timer when the input key is correct
    if (timeLine != null) {
      timeLine.stop();
    }
  }
}
