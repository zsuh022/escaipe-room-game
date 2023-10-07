package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.RoomType;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;

/** Controller class for the chat view. */
public class GameMasterController {

  @FXML private Button btnSend;
  @FXML private TextArea chatTextArea;
  @FXML private TextField inputTextArea;
  @FXML private Label timeLabel;
  @FXML private Pane waitingResponsePane;
  @FXML private Label transLabel;
  @FXML private Pane indicationPane;
  @FXML private Label transLabel1;
  @FXML private Label hintRemainLabel;
  @FXML private Label hintNumberLabel;
  private Timeline labelAnimationTimeline;
  private int updateCount = 0;
  private ChatCompletionRequest chatCompletionRequest;
  private int updateCount1 = 0;

  /**
   * Initializes the chat view, loading the riddle.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  public void initialize() throws ApiProxyException {
    // this will be called when the view is loaded
    initializeTimer();
    updateCount = 0;
    updateCount1 = 0;
    chatTextArea.setEditable(false);
    waitingResponsePane.setVisible(false);
    transLabel1.setVisible(false);
    GameState.currentRoom.addListener(
        (obs, oldRoom, newRoom) -> {
          if (thisIsCurrentRoom(newRoom)) {
            fadeInOutIndicationPane();
          }
        });
    chatCompletionRequest =
        new ChatCompletionRequest().setN(1).setTemperature(0.1).setTopP(0.5).setMaxTokens(250);

    // determine which prompt to use
    Thread thread =
        new Thread(
            () -> {
              if (GameState.difficulty == 1) {
                try {
                  runGpt(new ChatMessage("user", GptPromptEngineering.getGameMasterEasy()));
                } catch (ApiProxyException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
              } else if (GameState.difficulty == 2) {
                try {
                  // for example, if the difficulty is 2, then the prompt will be the medium
                  runGpt(new ChatMessage("user", GptPromptEngineering.getGameMasterMid()));
                } catch (ApiProxyException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
              } else {
                try {
                  runGpt(new ChatMessage("user", GptPromptEngineering.getGameMasterHard()));
                } catch (ApiProxyException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
              }
            });
    inputTextArea.setOnKeyPressed(
        event -> {
          if (event.getCode() == KeyCode.ENTER) {
            try {
              onSendMessage(null); // Call the existing send message method
            } catch (ApiProxyException | IOException e) {
              // Handle the exception, for instance, by showing an error message to the user.
              e.printStackTrace();
            }
            event.consume(); // Prevent default behavior of newline on 'Enter' key press.
          }
        });

    // hide loading pane when clicked
    waitingResponsePane.setOnMouseClicked(e -> waitingResponsePane.setVisible(false));

    // set the hint number
    setHintNumber();
    thread.start();
  }

  private void setHintNumber() {
    if (GameState.difficulty == 2) {
      hintRemainLabel.setVisible(true);
      hintNumberLabel.setVisible(true);
      // bind the hint number to the hint number remaining
      hintNumberLabel.textProperty().bind(GameState.hintNumberRemaining.asString());
    } else {
      hintRemainLabel.setVisible(false);
      hintNumberLabel.setVisible(false);
    }
  }

  private void initializeTimer() {
    timeLabel.textProperty().bind(GameState.timeManager.getSecond().asString());
  }

  private boolean thisIsCurrentRoom(Number roomNumber) {
    return roomNumber.intValue() == 5;
  }

  private void fadeInOutIndicationPane() {
    // fade in and out the indication pane
    indicationPane.setVisible(true);
    FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), indicationPane);
    fadeIn.setFromValue(0);
    fadeIn.setToValue(1);

    PauseTransition pause = new PauseTransition(Duration.seconds(0.97));

    FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), indicationPane);
    fadeOut.setFromValue(1);
    fadeOut.setToValue(0);

    // when fade in is finished, start the pause transition
    fadeIn.setOnFinished(
        event -> {
          pause.play();
        });
    pause.setOnFinished(
        event -> {
          fadeOut.play();
        });
    fadeOut.setOnFinished(
        e -> {
          indicationPane.setVisible(false);
        });
    fadeIn.play();
  }

  /**
   * Appends a chat message to the chat text area.
   *
   * @param msg the chat message to append
   */
  public void appendChatMessage(ChatMessage msg) {
    Platform.runLater(
        () -> chatTextArea.appendText(msg.getRole() + ": " + msg.getContent() + "\n\n"));
  }

  /**
   * Runs the GPT model with a given chat message.
   *
   * @param msg the chat message to process
   * @return the response chat message
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  private ChatMessage runGpt(ChatMessage msg) throws ApiProxyException {
    // show loading pane and start animation
    Platform.runLater(
        () -> {
          transLabel.setText("Transmitting to Earth .");
          startLabelAnimation(transLabel);
          waitingResponsePane.setVisible(true); // Show loading pane
        });

    // check if the message ends with "DONOTRESPOND"
    Boolean isEndWithNoRespond = false;
    if (msg.getContent().endsWith("DONOTRESPOND")) {
      transLabel1.setVisible(true);
      isEndWithNoRespond = true;
    }
    chatCompletionRequest.addMessage(msg);
    try {
      ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
      Choice result = chatCompletionResult.getChoices().iterator().next();
      chatCompletionRequest.addMessage(result.getChatMessage());
      // if the message ends with "DONOTRESPOND", then don't append the message
      if (!isEndWithNoRespond) {
        appendChatMessage(result.getChatMessage());
      }
      return result.getChatMessage();
    } catch (ApiProxyException e) {
      // TODO handle exception appropriately
      e.printStackTrace();
      return null;
    } finally {
      // stop animation and hide loading pane
      Platform.runLater(
          () -> {
            if (labelAnimationTimeline != null) {
              labelAnimationTimeline.stop();
            }
            transLabel.setText("");
            waitingResponsePane.setVisible(false); // Hide loading pane
            transLabel1.setVisible(false);
          });
    }
  }

  private void startLabelAnimation(Label label) {
    // this will be called when waiting for response
    if (labelAnimationTimeline != null) {
      labelAnimationTimeline.stop();
    }

    // animate the label
    labelAnimationTimeline =
        new Timeline(
            new KeyFrame(
                Duration.seconds(0.5),
                e -> {
                  String currentText = label.getText();
                  // set . to .. to ... to . and repeat
                  if (currentText.equals("Transmitting to Earth .")) {
                    label.setText("Transmitting to Earth ..");
                  } else if (currentText.equals("Transmitting to Earth ..")) {
                    label.setText("Transmitting to Earth ...");
                  } else {
                    label.setText("Transmitting to Earth .");
                  }
                }));

    // repeat forever
    labelAnimationTimeline.setCycleCount(Animation.INDEFINITE);
    labelAnimationTimeline.play();
  }

  /**
   * Sends a message to the GPT model.
   *
   * @param event the action event triggered by the send button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onSendMessage(ActionEvent event) throws ApiProxyException, IOException {
    Thread thread1 =
        new Thread(
            () -> {
              try {
                updateGpt();
              } catch (ApiProxyException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
            });
    String message = inputTextArea.getText();
    if (message.trim().isEmpty()) {
      return;
    }
    inputTextArea.clear();
    // send message to GPT
    Thread thread2 =
        new Thread(
            () -> {
              // append the message to the chat text area
              ChatMessage msg;
              msg = new ChatMessage("user", message);
              appendChatMessage(msg);
              ChatMessage lastMsg;
              try {
                // get the response from GPT and check if it
                lastMsg = runGpt(msg);
                if (lastMsg.getRole().equals("assistant")
                    && lastMsg.getContent().startsWith("Hint")) {
                  if (GameState.hintNumberRemaining.get() > 0) {
                    Platform.runLater(
                        () -> {
                          int hintNumber = GameState.hintNumberRemaining.get() - 1;
                          GameState.hintNumberRemaining.setValue(hintNumber);
                        });
                  }
                  Platform.runLater(
                      () -> {
                        GameState.latestHint.set(lastMsg.getContent());
                      });
                }
              } catch (ApiProxyException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
            });
    // thread 3 is used to make sure that the updateGpt() is called before runGpt()
    Thread thread3 =
        new Thread(
            () -> {
              thread1.start();
              try {
                thread1.join();
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              thread2.start();
            });
    thread3.start();
  }

  public void updateGpt() throws ApiProxyException {
    // this will be called when the user has done all the puzzles
    if (GameState.isPuzzleRoom3Solved.getValue() == true
        && GameState.isPuzzleRoom2Solved.getValue() == true
        && GameState.isRiddleResolved.getValue() == true
        && updateCount == 0
        && GameState.difficulty != 3
        && GameState.hintNumberRemaining.get() > 0) {
      ChatMessage msg = new ChatMessage("user", GptPromptEngineering.getHintTwo());
      runGpt(msg);
      // updateCount is used to make sure that the message is only sent once
      updateCount++;
    }

    if (GameState.hintNumberRemaining.getValue() == 0
        && GameState.difficulty == 2
        && updateCount1 == 0) {
      ChatMessage msg = new ChatMessage("user", GptPromptEngineering.getMessageNoHint());
      runGpt(msg);
      updateCount1++;
    }
  }

  /**
   * Navigates back to the previous view.
   *
   * @param event the action event triggered by the go back button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onGoBack(ActionEvent event) throws ApiProxyException, IOException {
    // go back to the previous view
    if (GameState.roomNumber == 1) {
      GameState.currentRoom.setValue(1);
      App.setUi(RoomType.ROOM1);
    } else if (GameState.roomNumber == 2) {
      GameState.currentRoom.setValue(2);
      App.setUi(RoomType.ROOM2);
    } else if (GameState.roomNumber == 3) {
      GameState.currentRoom.setValue(3);
      App.setUi(RoomType.ROOM3);
    } else if (GameState.roomNumber == 4) {
      GameState.currentRoom.setValue(4);
      App.setUi(RoomType.EXITDOOR);
    }
  }
}
