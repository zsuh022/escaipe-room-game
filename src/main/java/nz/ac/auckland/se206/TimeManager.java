package nz.ac.auckland.se206;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;
import nz.ac.auckland.se206.speech.TextToSpeech;

public class TimeManager {

  private static Timeline timeLine;
  private static IntegerProperty seconds = new SimpleIntegerProperty(60);

  public static void setTimer() {

    timeLine =
        new Timeline(
            new KeyFrame(
                Duration.seconds(1),
                e -> {
                  if (seconds.get() > 0) {
                    int newSeconds = seconds.get() - 1;
                    seconds.set(newSeconds);
                  }
                  if (seconds.get() == 11) {
                    Thread thread =
                        new Thread(
                            () -> {
                              TextToSpeech textToSpeech = new TextToSpeech();
                              textToSpeech.speak("Harry up, you've only got 10 seconds left!");
                            });
                    thread.start();
                  }
                  if (seconds.get() <= 3 && seconds.get() > 0) {
                    Thread thread2 =
                        new Thread(
                            () -> {
                              TextToSpeech textToSpeech = new TextToSpeech();
                              textToSpeech.speak(String.valueOf(seconds));
                            });
                    thread2.start();
                  }
                }));
    timeLine.setCycleCount(seconds.get());
    timeLine.play();

    // timeLine.setOnFinished(
    //     e -> {
    //       try {
    //         // go to the bad end view
    //         App.setRoot("end");
    //       } catch (IOException e1) {
    //         e1.printStackTrace();
    //       }
    //     });
  }

  public static IntegerProperty getSecond() {
    return seconds;
  }
}
