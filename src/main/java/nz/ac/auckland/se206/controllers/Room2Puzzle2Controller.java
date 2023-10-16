package nz.ac.auckland.se206.controllers;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.RoomType;

/**
 * Room2Puzzle2Controller class is used to control the puzzle in room 2. It will display a word with
 * 5 letters and the player has to guess the word. The player has 6 chances to guess the word.
 */
public class Room2Puzzle2Controller {
  @FXML private Button buttonA;
  @FXML private Button buttonB;
  @FXML private Button buttonC;
  @FXML private Button buttonD;
  @FXML private Button buttonE;
  @FXML private Button buttonF;
  @FXML private Button buttonG;
  @FXML private Button buttonH;
  @FXML private Button buttonI;
  @FXML private Button buttonJ;
  @FXML private Button buttonK;
  @FXML private Button buttonL;
  @FXML private Button buttonM;
  @FXML private Button buttonN;
  @FXML private Button buttonO;
  @FXML private Button buttonP;
  @FXML private Button buttonQ;
  @FXML private Button buttonR;
  @FXML private Button buttonS;
  @FXML private Button buttonT;
  @FXML private Button buttonU;
  @FXML private Button buttonV;
  @FXML private Button buttonW;
  @FXML private Button buttonX;
  @FXML private Button buttonY;
  @FXML private Button buttonZ;
  @FXML private Button btnResetPuzzle;
  @FXML private Button btnExitPuzzle;
  @FXML private Label letter1;
  @FXML private Label letter2;
  @FXML private Label letter3;
  @FXML private Label letter4;
  @FXML private Label letter5;
  @FXML private Label room2Puzzle2State;
  @FXML private Label timeLabel;
  @FXML private Rectangle chance1;
  @FXML private Rectangle chance2;
  @FXML private Rectangle chance3;
  @FXML private Rectangle chance4;
  @FXML private Rectangle chance5;
  @FXML private Rectangle chance6;
  @FXML private Rectangle rectangleGameState;
  @FXML private Rectangle space1;
  @FXML private Rectangle space2;
  @FXML private Rectangle space3;
  @FXML private Rectangle space4;
  @FXML private Rectangle space5;
  @FXML private Pane keyboardPane;

  private ArrayList<String> word = new ArrayList<String>();
  private boolean isPuzzleSolved = false;
  // chanceCount goes from 0 to 6
  private int chanceCount;
  // correctCount goes from 0 to 5
  private int correctCount;

  /**
   * This method will flash the colour.
   *
   * @param rectangle the rectangle to flash
   * @param colorFlash the colour to flash
   */
  @FXML
  private void flashColour(Rectangle rectangle, Color colorFlash) {
    // Flash the rectangle green for one second
    rectangle.setFill(colorFlash);

    // Create a timeline for the animation
    Timeline timeline =
        new Timeline(
            new KeyFrame(
                Duration.seconds(0.4),
                event -> {
                  // After one second, revert the color back to the original color
                  rectangle.setFill(Color.WHITE);
                }));

    // Play the timeline animation
    timeline.play();
  }

  /** Initializes the room view, it is called when the room loads. */
  @FXML
  private void initialize() {
    word = new ArrayList<String>();
    isPuzzleSolved = false;

    // intialize keyboard
    Button[] buttons = {
      buttonA, buttonB, buttonC, buttonD, buttonE, buttonF, buttonG, buttonH, buttonI, buttonJ,
      buttonK, buttonL, buttonM, buttonN, buttonO, buttonP, buttonQ, buttonR, buttonS, buttonT,
      buttonU, buttonV, buttonW, buttonX, buttonY, buttonZ
    };

    for (Button button : buttons) {
      initializeButton(button);
    }

    initializeTimer();
    initializeWord();
    initializePuzzle();

    // get new word
    newWord((int) (1 + (Math.random() * (8))));
  }

  /** This method will initialize the timer. */
  @FXML
  private void initializeTimer() {
    timeLabel.textProperty().bind(GameState.timeManager.getSecond().asString());
  }

  /** This method will be called when the exit button is clicked. */
  @FXML
  private void onBackButtonClicked() {
    App.setUi(RoomType.ROOM2);
  }

  /**
   * This method associates each key press event with a corresponding letter button and handles it.
   *
   * @param event The KeyEvent representing the key press event.
   */
  @FXML
  private void onKeyPressed(KeyEvent event) {
    if (!isPuzzleSolved) {
      KeyCode keyCode = event.getCode();

      // get the upper case letter from the key presses
      String letter = event.getText().toUpperCase();

      // handle the key press for the corresponding letter button
      switch (keyCode) {
        case A:
          handleLetterKeyPress(buttonA, letter);
          break;
        case B:
          handleLetterKeyPress(buttonB, letter);
          break;
        case C:
          handleLetterKeyPress(buttonC, letter);
          break;
        case D:
          handleLetterKeyPress(buttonD, letter);
          break;
        case E:
          handleLetterKeyPress(buttonE, letter);
          break;
        case F:
          handleLetterKeyPress(buttonF, letter);
          break;
        case G:
          handleLetterKeyPress(buttonG, letter);
          break;
        case H:
          handleLetterKeyPress(buttonH, letter);
          break;
        case I:
          handleLetterKeyPress(buttonI, letter);
          break;
        case J:
          handleLetterKeyPress(buttonJ, letter);
          break;
        case K:
          handleLetterKeyPress(buttonK, letter);
          break;
        case L:
          handleLetterKeyPress(buttonL, letter);
          break;
        case M:
          handleLetterKeyPress(buttonM, letter);
          break;
        case N:
          handleLetterKeyPress(buttonN, letter);
          break;
        case O:
          handleLetterKeyPress(buttonO, letter);
          break;
        case P:
          handleLetterKeyPress(buttonP, letter);
          break;
        case Q:
          handleLetterKeyPress(buttonQ, letter);
          break;
        case R:
          handleLetterKeyPress(buttonR, letter);
          break;
        case S:
          handleLetterKeyPress(buttonS, letter);
          break;
        case T:
          handleLetterKeyPress(buttonT, letter);
          break;
        case U:
          handleLetterKeyPress(buttonU, letter);
          break;
        case V:
          handleLetterKeyPress(buttonV, letter);
          break;
        case W:
          handleLetterKeyPress(buttonW, letter);
          break;
        case X:
          handleLetterKeyPress(buttonX, letter);
          break;
        case Y:
          handleLetterKeyPress(buttonY, letter);
          break;
        case Z:
          handleLetterKeyPress(buttonZ, letter);
          break;
        default:
          // do nothing
          break;
      }
    }
  }

  /** This method will be called when a letter button is clicked. */
  @FXML
  private void onLetterButtonClicked(ActionEvent event) {
    if (!isPuzzleSolved) {
      Button clickedButton = (Button) event.getSource();
      clickedButton.setDisable(true);
      clickedButton.setOpacity(0.5);
      handleKeyPressed(clickedButton.getText().charAt(0));
    }
  }

  /** This method will be called when the reset button is clicked. */
  @FXML
  private void onResetButtonClicked() {
    // clear the current word from the arraylist
    word.clear();
    initialize();
  }

  /**
   * This method will add the characters to the word arraylist.
   *
   * @param string the string to add
   */
  private void addCharacters(String string) {
    if (string.length() == 5) {
      word.add(string.substring(0, 1));
      word.add(string.substring(1, 2));
      word.add(string.substring(2, 3));
      word.add(string.substring(3, 4));
      word.add(string.substring(4, 5));
    }
  }

  /** This method will flash the spaces red. */
  private void flashSpacesRed() {
    flashColour(space1, Color.LIGHTPINK);
    flashColour(space2, Color.LIGHTPINK);
    flashColour(space3, Color.LIGHTPINK);
    flashColour(space4, Color.LIGHTPINK);
    flashColour(space5, Color.LIGHTPINK);
  }

  /**
   * This method will handle the key pressed.
   *
   * @param character the character to handle
   */
  private void handleKeyPressed(Character character) {
    // check if the word contains the character
    if (word.contains(character.toString())) {
      for (int i = 0; i < 5; i++) {
        // check if the character is in the word
        if (word.get(i).equals(character.toString())) {
          correctCount++;
          // set the letter to the correct label
          switch (i) {
            case 0:
              letter1.setText(character.toString());
              break;
            case 1:
              letter2.setText(character.toString());
              break;
            case 2:
              letter3.setText(character.toString());
              break;
            case 3:
              letter4.setText(character.toString());
              break;
            case 4:
              letter5.setText(character.toString());
              break;
            default:
              break;
          }
        }
      }

      if (correctCount == 5) {
        // if the word is correct, set the game state to true
        puzzleSolved();
        isPuzzleSolved = true;
        room2Puzzle2State.setText("PUZZLE CORRECT");
        rectangleGameState.setFill(Color.LIGHTGREEN);
        rectangleGameState.setOpacity(1);

        // set all the rectangles to green for correct game
        chance1.setFill(Color.GREEN);
        chance2.setFill(Color.GREEN);
        chance3.setFill(Color.GREEN);
        chance4.setFill(Color.GREEN);
        chance5.setFill(Color.GREEN);
        chance6.setFill(Color.GREEN);
        setSpaces(Color.LIGHTGREEN);
      }

    } else {
      // if the word does not contain the character, increment the chance count
      if (chanceCount < 5) {
        chanceCount++;
        // for each chance count, set the corresponding rectangle to red
        switch (chanceCount) {
          case 1:
            chance1.setFill(Color.RED);
            flashSpacesRed();
            break;
          case 2:
            chance2.setFill(Color.RED);
            flashSpacesRed();
            break;
          case 3:
            // if the chance count is 3, set the first rectangle to red and flash the spaces red
            chance3.setFill(Color.RED);
            flashSpacesRed();
            break;
          case 4:
            chance4.setFill(Color.RED);
            flashSpacesRed();
            break;
          case 5:
            chance5.setFill(Color.RED);
            flashSpacesRed();
            break;
          default:
            break;
        }
      } else {
        // if the chance count is 6, set the last rectangle to red and set the game state to true
        chanceCount++;
        isPuzzleSolved = true;
        chance6.setFill(Color.RED);
        room2Puzzle2State.setText("PUZZLE FAILED");
        rectangleGameState.setFill(Color.LIGHTPINK);
        rectangleGameState.setOpacity(1);
      }
    }
  }

  /**
   * Helper method to handle the key press for a specific letter button.
   *
   * @param button The Button representing the letter button that was pressed.
   * @param letter The uppercase letter associated with the button's key press.
   */
  private void handleLetterKeyPress(Button button, String letter) {
    if (!button.isDisable()) {
      button.setDisable(true);
      button.setOpacity(0.5);
      handleKeyPressed(letter.charAt(0));
    }
  }

  /**
   * This method will initialize the button.
   *
   * @param button the button to initialize
   */
  private void initializeButton(Button button) {
    button.setDisable(false);
    button.setOpacity(1);
  }

  /** This method will initialize the puzzle. */
  private void initializePuzzle() {
    chance1.setFill(Color.WHITE);
    chance2.setFill(Color.WHITE);
    chance3.setFill(Color.WHITE);
    chance4.setFill(Color.WHITE);
    chance5.setFill(Color.WHITE);
    chance6.setFill(Color.WHITE);
    chanceCount = 0;
    correctCount = 0;
    room2Puzzle2State.setText("");
    rectangleGameState.setOpacity(0);
    setSpaces(Color.WHITE);
  }

  /** This method will initialize the word. */
  private void initializeWord() {
    letter1.setText("");
    letter2.setText("");
    letter3.setText("");
    letter4.setText("");
    letter5.setText("");
  }

  /**
   * This method will initialize the puzzle.
   *
   * @param randomInt the random integer
   */
  private void newWord(Integer randomInt) {
    // add the characters to the word arraylist
    switch (randomInt) {
      case 1:
        // add COMET to the arraylist
        addCharacters("COMET");
        System.out.println("COMET");
        break;
      case 2:
        // add SOLAR to the arraylist
        addCharacters("SOLAR");
        System.out.println("SOLAR");
        break;
      case 3:
        // add STARS to the arraylist
        addCharacters("STARS");
        System.out.println("STARS");
        break;
      case 4:
        // add EARTH to the arraylist
        addCharacters("EARTH");
        System.out.println("EARTH");
        break;
      case 5:
        // add VENUS to the arraylist
        addCharacters("VENUS");
        System.out.println("VENUS");
        break;
      case 6:
        // add PLUTO to the arraylist
        addCharacters("PLUTO");
        System.out.println("PLUTO");
        break;
      case 7:
        // add ORION to the arraylist
        addCharacters("ORION");
        System.out.println("ORION");
        break;
      case 8:
        // add MOONS to the arraylist
        addCharacters("MOONS");
        System.out.println("MOONS");
        break;
      default:
        // add PLUTO to the arraylist
        addCharacters("PLUTO");
        System.out.println("PLUTO");
        break;
    }
  }

  /** This method will be called when the puzzle is solved. */
  private void puzzleSolved() {
    System.out.println("Puzzle solved");
    GameState.isPuzzleRoom2Solved.set(true);

    // disable reset button
    btnResetPuzzle.setDisable(true);
  }

  /**
   * This method will set the spaces to the colour.
   *
   * @param color the colour to set
   */
  private void setSpaces(Color color) {
    space1.setFill(color);
    space2.setFill(color);
    space3.setFill(color);
    space4.setFill(color);
    space5.setFill(color);
  }
}
