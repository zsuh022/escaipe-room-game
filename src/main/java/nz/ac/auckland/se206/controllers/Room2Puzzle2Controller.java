package nz.ac.auckland.se206.controllers;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.RoomType;

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
  @FXML private Button resetRoom2Puzzle2;
  @FXML private Button backRoom2Puzzle2;
  @FXML private Label letter1;
  @FXML private Label letter2;
  @FXML private Label letter3;
  @FXML private Label letter4;
  @FXML private Label letter5;
  @FXML private Rectangle chance1;
  @FXML private Rectangle chance2;
  @FXML private Rectangle chance3;
  @FXML private Rectangle chance4;
  @FXML private Rectangle chance5;
  @FXML private Rectangle chance6;
  @FXML private Label room2Puzzle2State;

  ArrayList<String> word = new ArrayList<String>();
  int chanceCount; // 0 to 6
  int correctCount; // 0 to 5

  private void puzzleSolved() {
    System.out.println("Puzzle solved");
    GameState.isPuzzleRoom2Solved = true;
  }

  @FXML
  private void initialize() {
    initializeButton(buttonA);
    initializeButton(buttonB);
    initializeButton(buttonC);
    initializeButton(buttonD);
    initializeButton(buttonE);
    initializeButton(buttonF);
    initializeButton(buttonG);
    initializeButton(buttonH);
    initializeButton(buttonI);
    initializeButton(buttonJ);
    initializeButton(buttonK);
    initializeButton(buttonL);
    initializeButton(buttonM);
    initializeButton(buttonN);
    initializeButton(buttonO);
    initializeButton(buttonP);
    initializeButton(buttonQ);
    initializeButton(buttonR);
    initializeButton(buttonS);
    initializeButton(buttonT);
    initializeButton(buttonU);
    initializeButton(buttonV);
    initializeButton(buttonW);
    initializeButton(buttonX);
    initializeButton(buttonY);
    initializeButton(buttonZ);
    letter1.setText("");
    letter2.setText("");
    letter3.setText("");
    letter4.setText("");
    letter5.setText("");
    chance1.setFill(Color.WHITE);
    chance2.setFill(Color.WHITE);
    chance3.setFill(Color.WHITE);
    chance4.setFill(Color.WHITE);
    chance5.setFill(Color.WHITE);
    chance6.setFill(Color.WHITE);
    chanceCount = 0;
    correctCount = 0;
    room2Puzzle2State.setText("");
    newWord((int) (1 + (Math.random() * (8))));
  }

  private void initializeButton(Button button) {
    button.setDisable(false);
    button.setOpacity(1);
  }

  private void newWord(Integer randomInt) {
    switch (randomInt) {
      case 1:
        addCharacters("COMET");
        break;
      case 2:
        addCharacters("SOLAR");
        break;
      case 3:
        addCharacters("STARS");
        break;
      case 4:
        addCharacters("EARTH");
        break;
      case 5:
        addCharacters("VENUS");
        break;
      case 6:
        addCharacters("PLUTO");
        break;
      case 7:
        addCharacters("ORION");
        break;
      case 8:
        addCharacters("MOONS");
        break;
      default:
        addCharacters("PLUTO");
        break;
    }
  }

  private void addCharacters(String string) {
    if (string.length() == 5) {
      word.add(string.substring(0, 1));
      word.add(string.substring(1, 2));
      word.add(string.substring(2, 3));
      word.add(string.substring(3, 4));
      word.add(string.substring(4, 5));
    }
  }

  private void handleKeyPressed(Character character) {
    if (word.contains(character.toString())) {
      for (int i = 0; i < 5; i++) {
        if (word.get(i).equals(character.toString())) {
          correctCount++;
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
        puzzleSolved();
        room2Puzzle2State.setText("Puzzle Solved! Press back to exit.");
        chance1.setFill(Color.GREEN);
        chance2.setFill(Color.GREEN);
        chance3.setFill(Color.GREEN);
        chance4.setFill(Color.GREEN);
        chance5.setFill(Color.GREEN);
        chance6.setFill(Color.GREEN);
      }

    } else {
      if (chanceCount < 6) {
        chanceCount++;
        switch (chanceCount) {
          case 1:
            chance1.setFill(Color.RED);
            break;
          case 2:
            chance2.setFill(Color.RED);
            break;
          case 3:
            chance3.setFill(Color.RED);
            break;
          case 4:
            chance4.setFill(Color.RED);
            break;
          case 5:
            chance5.setFill(Color.RED);
            break;
          default:
            break;
        }
      } else {
        chanceCount++;
        chance6.setFill(Color.RED);
        room2Puzzle2State.setText("Puzzle Failed! Press reset to try again.");
      }
    }
  }

  @FXML
  private void resetRoom2Puzzle2Clicked() {
    initialize();
  }

  @FXML
  private void backRoom2Puzzle2Clicked() {
    App.setUi(RoomType.ROOM2);
  }

  @FXML
  private void buttonAClicked() {
    handleKeyPressed('A');
    buttonA.setDisable(true);
    buttonA.setOpacity(0);
  }

  @FXML
  private void buttonBClicked() {
    handleKeyPressed('B');
    buttonB.setDisable(true);
    buttonB.setOpacity(0);
  }

  @FXML
  private void buttonCClicked() {
    handleKeyPressed('C');
    buttonC.setDisable(true);
    buttonC.setOpacity(0);
  }

  @FXML
  private void buttonDClicked() {
    handleKeyPressed('D');
    buttonD.setDisable(true);
    buttonD.setOpacity(0);
  }

  @FXML
  private void buttonEClicked() {
    handleKeyPressed('E');
    buttonE.setDisable(true);
    buttonE.setOpacity(0);
  }

  @FXML
  private void buttonFClicked() {
    handleKeyPressed('F');
    buttonF.setDisable(true);
    buttonF.setOpacity(0);
  }

  @FXML
  private void buttonGClicked() {
    handleKeyPressed('G');
    buttonG.setDisable(true);
    buttonG.setOpacity(0);
  }

  @FXML
  private void buttonHClicked() {
    handleKeyPressed('H');
    buttonH.setDisable(true);
    buttonH.setOpacity(0);
  }

  @FXML
  private void buttonIClicked() {
    handleKeyPressed('I');
    buttonI.setDisable(true);
    buttonI.setOpacity(0);
  }

  @FXML
  private void buttonJClicked() {
    handleKeyPressed('J');
    buttonJ.setDisable(true);
    buttonJ.setOpacity(0);
  }

  @FXML
  private void buttonKClicked() {
    handleKeyPressed('K');
    buttonK.setDisable(true);
    buttonK.setOpacity(0);
  }

  @FXML
  private void buttonLClicked() {
    handleKeyPressed('L');
    buttonL.setDisable(true);
    buttonL.setOpacity(0);
  }

  @FXML
  private void buttonMClicked() {
    handleKeyPressed('M');
    buttonM.setDisable(true);
    buttonM.setOpacity(0);
  }

  @FXML
  private void buttonNClicked() {
    handleKeyPressed('N');
    buttonN.setDisable(true);
    buttonN.setOpacity(0);
  }

  @FXML
  private void buttonOClicked() {
    handleKeyPressed('O');
    buttonO.setDisable(true);
    buttonO.setOpacity(0);
  }

  @FXML
  private void buttonPClicked() {
    handleKeyPressed('P');
    buttonP.setDisable(true);
    buttonP.setOpacity(0);
  }

  @FXML
  private void buttonQClicked() {
    handleKeyPressed('Q');
    buttonQ.setDisable(true);
    buttonQ.setOpacity(0);
  }

  @FXML
  private void buttonRClicked() {
    handleKeyPressed('R');
    buttonR.setDisable(true);
    buttonR.setOpacity(0);
  }

  @FXML
  private void buttonSClicked() {
    handleKeyPressed('S');
    buttonS.setDisable(true);
    buttonS.setOpacity(0);
  }

  @FXML
  private void buttonTClicked() {
    handleKeyPressed('T');
    buttonT.setDisable(true);
    buttonT.setOpacity(0);
  }

  @FXML
  private void buttonUClicked() {
    handleKeyPressed('U');
    buttonU.setDisable(true);
    buttonU.setOpacity(0);
  }

  @FXML
  private void buttonVClicked() {
    handleKeyPressed('V');
    buttonV.setDisable(true);
    buttonV.setOpacity(0);
  }

  @FXML
  private void buttonWClicked() {
    handleKeyPressed('W');
    buttonW.setDisable(true);
    buttonW.setOpacity(0);
  }

  @FXML
  private void buttonXClicked() {
    handleKeyPressed('X');
    buttonX.setDisable(true);
    buttonX.setOpacity(0);
  }

  @FXML
  private void buttonYClicked() {
    handleKeyPressed('Y');
    buttonY.setDisable(true);
    buttonY.setOpacity(0);
  }

  @FXML
  private void buttonZClicked() {
    handleKeyPressed('Z');
    buttonZ.setDisable(true);
    buttonZ.setOpacity(0);
  }
}
