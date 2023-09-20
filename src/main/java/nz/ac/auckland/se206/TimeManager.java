package nz.ac.auckland.se206;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;
import nz.ac.auckland.se206.SceneManager.RoomType;
import nz.ac.auckland.se206.speech.TextToSpeech;

public class TimeManager {

  private Timeline timeLine;
  private IntegerProperty seconds;

  public void setTime(int seconds) {
    this.seconds = new SimpleIntegerProperty(seconds);
  }

  public void setTimer() {
    timeLine =
        new Timeline(
            new KeyFrame(
                Duration.seconds(1),
                e -> {
                  if (seconds.get() > 0) {
                    int newSeconds = seconds.get() - 1;
                    seconds.set(newSeconds);
                  }
                  if (seconds.get() == 12) {
                    Thread thread =
                        new Thread(
                            () -> {
                              TextToSpeech textToSpeech = new TextToSpeech();
                              textToSpeech.speak("Hurry up, you've only got 10 seconds left!");
                            });
                    thread.start();
                  }
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

  public IntegerProperty getSecond() {
    return seconds;
  }
}
