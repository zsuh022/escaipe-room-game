package nz.ac.auckland.se206;

import java.net.URISyntaxException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicManager {

  private static MediaPlayer player;

  public static void playMenuVideo() throws URISyntaxException {}

  /**
   * Play the particular song when the player is in the room during the game.
   *
   * @throws URISyntaxException
   */
  public static void playGameSong() throws URISyntaxException {
    // play the game song
    Media song = new Media(App.class.getResource("/sounds/gamingMusic.mp3").toURI().toString());
    if (player != null) {
      player.stop();
    }
    player = new MediaPlayer(song);
    player.play();
  }

  /**
   * Play the particular song when the player wins the game.
   *
   * @throws URISyntaxException
   */
  public static void playHappySong() throws URISyntaxException {
    // play the happy song
    Media song = new Media(App.class.getResource("/sounds/GoodEnding.mp3").toURI().toString());
    if (player != null) {
      player.stop();
    }
    player = new MediaPlayer(song);
    player.play();
  }

  /**
   * Play the particular song when the player loses the game.
   *
   * @throws URISyntaxException
   */
  public static void playBadSong() throws URISyntaxException {
    // play the bad song
    Media song = new Media(App.class.getResource("/sounds/BadEnding.mp3").toURI().toString());
    if (player != null) {
      player.stop();
    }
    player = new MediaPlayer(song);
    player.play();
  }

  public static void mute() {
    // mute the music
    player.setMute(true);
  }

  public static void unmute() {
    // unmute the music
    setMute(false);
  }

  private static void setMute(boolean mute) {
    if (player != null) {
      player.setMute(mute);
    }
  }
}
