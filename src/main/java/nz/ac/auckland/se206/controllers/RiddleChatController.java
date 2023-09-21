package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

  private ChatCompletionRequest chatCompletionRequest;

  /**
   * Initializes the chat view, loading the riddle.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  public void initialize() throws ApiProxyException {

    String[] celestialBodies = {
      "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"
    };
    Random random = new Random();
    int randomIndex = random.nextInt(celestialBodies.length);
    System.out.println(celestialBodies[randomIndex]);

    GameState.riddleWord = celestialBodies[randomIndex];

    chatCompletionRequest =
        new ChatCompletionRequest().setN(1).setTemperature(0.2).setTopP(0.5).setMaxTokens(250);

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
    thread.start();
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
    chatCompletionRequest.addMessage(msg);
    try {
      ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
      Choice result = chatCompletionResult.getChoices().iterator().next();
      chatCompletionRequest.addMessage(result.getChatMessage());
      appendChatMessage(result.getChatMessage());
      return result.getChatMessage();
    } catch (ApiProxyException e) {
      // TODO handle exception appropriately
      e.printStackTrace();
      return null;
    }
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

    Thread thread =
        new Thread(
            () -> {
              ChatMessage msg;
              if (GameState.difficulty == 2) {
                msg =
                    new ChatMessage(
                        "user",
                        message + " \\n " + "Hint remaining: " + GameState.hintCount + " now");
              } else {
                msg = new ChatMessage("user", message);
              }
              appendChatMessage(msg);
              ChatMessage lastMsg;
              try {
                lastMsg = runGpt(msg);
                if (lastMsg.getRole().equals("assistant")
                    && lastMsg.getContent().startsWith("Correct")) {
                  GameState.isRiddleResolved.set(true);
                }
                if (GameState.difficulty == 2) {
                  String lastMsgContent = lastMsg.getContent();
                  GameState.hintCount = getHintRemaining(lastMsgContent);
                  System.out.println(lastMsgContent);
                  System.out.println("hint number: " + GameState.hintCount);
                }
              } catch (ApiProxyException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
            });
    thread.start();
  }

  public static int getHintRemaining(String input) {
    Pattern pattern = Pattern.compile("Hint remaining: (\\d+)");
    Matcher matcher = pattern.matcher(input);

    if (matcher.find()) {
      return Integer.parseInt(matcher.group(1));
    } else {
      throw new IllegalArgumentException("The hint number was not found in the given input.");
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
    App.setUi(RoomType.ROOM1);
  }
}
