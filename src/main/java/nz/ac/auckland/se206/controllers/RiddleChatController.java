package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.Random;
import java.util.regex.Pattern;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
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
public class RiddleChatController {

  @FXML private Button btnSend;
  @FXML private TextArea chatTextArea;
  @FXML private TextField inputTextArea;
  @FXML private Label timeLabel;
  @FXML private Pane waitingResponsePane;
  @FXML private Label transLabel;

  private Timeline labelAnimationTimeline;
  private ChatCompletionRequest chatCompletionRequest;

  /**
   * Initializes the chat view, loading the riddle.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  public void initialize() throws ApiProxyException {
    // Hide loading pane
    waitingResponsePane.setVisible(false);

    // randomly select a celestial body to be the riddle word
    String[] celestialBodies = {
      "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"
    };
    Random random = new Random();
    int randomIndex = random.nextInt(celestialBodies.length);
    System.out.println(celestialBodies[randomIndex]);
    GameState.riddleWord = celestialBodies[randomIndex];
    chatCompletionRequest =
        new ChatCompletionRequest().setN(1).setTemperature(0.1).setTopP(0.5).setMaxTokens(250);

    // to select the difficulty of the riddle
    Thread thread =
        new Thread(
            () -> {
              if (GameState.difficulty == 1) {
                try {
                  runGpt(
                      new ChatMessage(
                          "user",
                          GptPromptEngineering.getRiddleWithGivenWordEasy(GameState.riddleWord)));
                } catch (ApiProxyException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
              } else if (GameState.difficulty == 2) {
                // for example, if the difficulty equals 2, then call the mid level solver
                try {
                  runGpt(
                      new ChatMessage(
                          "user",
                          GptPromptEngineering.getRiddleWithGivenWordMid(GameState.riddleWord)));
                } catch (ApiProxyException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
              } else {
                try {
                  runGpt(
                      new ChatMessage(
                          "user",
                          GptPromptEngineering.getRiddleWithGivenWordHard(GameState.riddleWord)));
                } catch (ApiProxyException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
              }
            });
    // initialize the timer
    if (GameState.timeManager != null && GameState.timeManager.getSecond() != null) {
      initializeTimer();
    }

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
    // hide the loading pane when clicked
    waitingResponsePane.setOnMouseClicked(e -> waitingResponsePane.setVisible(false));
    thread.start();
  }

  private void initializeTimer() {
    // timer
    Platform.runLater(
        () -> {
          timeLabel.textProperty().bind(GameState.timeManager.getSecond().asString());
        });
  }

  /**
   * Appends a chat message to the chat text area.
   *
   * @param msg the chat message to append
   */
  private void appendChatMessage(ChatMessage msg) {
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
          transLabel.setText("Transmitting to earth .");
          startLabelAnimation(transLabel);
          waitingResponsePane.setVisible(true); // Show loading pane
        });

    chatCompletionRequest.addMessage(msg);
    try {
      // send message to GPT model and get response
      ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
      Choice result = chatCompletionResult.getChoices().iterator().next();
      chatCompletionRequest.addMessage(result.getChatMessage());
      appendChatMessage(result.getChatMessage());
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
                  if (currentText.equals("Transmitting to earth .")) {
                    label.setText("Transmitting to earth ..");
                  } else if (currentText.equals("Transmitting to earth ..")) {
                    label.setText("Transmitting to earth ...");
                  } else {
                    label.setText("Transmitting to earth .");
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
    String message = inputTextArea.getText();
    if (message.trim().isEmpty()) {
      return;
    }
    inputTextArea.clear();
    // send message to GPT
    Thread thread =
        new Thread(
            () -> {
              ChatMessage msg;
              // if the difficulty is 2, then the message will be split into two parts
              // one is the message, the other is the hint number
              if (GameState.difficulty == 2) {
                msg =
                    new ChatMessage(
                        "user", message + " \\ " + "Hint remaining: " + GameState.hintCount);
                if (containsHint(message)) {
                  // if the message contains hint, then the hint count will be reduced by 1
                  if (GameState.hintCount > 0) {
                    GameState.hintCount--;
                  }
                }
              } else {
                // otherwise, the message will be the message
                msg = new ChatMessage("user", message);
              }
              appendChatMessage(msg);
              ChatMessage lastMsg;
              try {
                // get the response from GPT and check if the riddle is resolved
                lastMsg = runGpt(msg);
                if (lastMsg.getRole().equals("assistant")
                    && lastMsg.getContent().startsWith("Correct")) {
                  GameState.isRiddleResolved.set(true);
                }
              } catch (ApiProxyException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
            });
    thread.start();
  }

  private Boolean containsHint(String input) {
    // determine if the message contains hint
    if (input == null) return false;
    Pattern pattern = Pattern.compile("\\bhint(s)?\\b", Pattern.CASE_INSENSITIVE);
    return pattern.matcher(input).find();
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
    App.setUi(RoomType.ROOM1);
  }
}
