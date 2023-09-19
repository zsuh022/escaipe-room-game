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
    Media song = new Media(App.class.getResource("/sounds/RoomMusic.mp3").toURI().toString());
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

  /** Mute the music when the user clicks the mute button. */
  public static void mute() {
    if (player != null) {
      // mute the music
      player.setMute(true);
    }
  }

  /** Unmute the music when the user clicks the mute button again. */
  public static void unmute() {
    if (player != null) {
      // unmute the music
      player.setMute(false);
    }
  }
}
