package computah;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import computah.command.Command;
import computah.exception.ComputahException;
import computah.parser.Parser;
import computah.storage.Storage;
import computah.task.Task;
import computah.ui.Ui;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Shows the JavaFX graphical interface for Computah.
 */
public class Main extends Application {
    private final Storage storage = new Storage("data/duke.txt");
    private final ArrayList<Task> tasks = new ArrayList<>();

    private TextArea dialogBox;
    private TextField commandBox;
    private Button sendButton;

    /**
     * Creates the JavaFX Computah application.
     */
    public Main() {
    }

    @Override
    public void start(Stage stage) {
        dialogBox = new TextArea();
        dialogBox.setEditable(false);
        dialogBox.setWrapText(true);

        commandBox = new TextField();
        commandBox.setPromptText("Enter a command");
        commandBox.setOnAction(event -> handleUserCommand());

        sendButton = new Button("Send");
        sendButton.setOnAction(event -> handleUserCommand());

        HBox inputArea = new HBox(8, commandBox, sendButton);
        inputArea.setPadding(new Insets(8));
        HBox.setHgrow(commandBox, javafx.scene.layout.Priority.ALWAYS);

        BorderPane root = new BorderPane();
        root.setCenter(dialogBox);
        root.setBottom(inputArea);

        loadTasks();
        appendWithDivider("Hello! I'm Computah.\nWhat can I do for you?");

        stage.setTitle("Computah");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    private void loadTasks() {
        try {
            tasks.addAll(storage.load());
        } catch (ComputahException e) {
            appendWithDivider("OOPS!!! " + e.getMessage());
        }
    }

    private void handleUserCommand() {
        String input = commandBox.getText().trim();
        if (input.isEmpty()) {
            return;
        }

        commandBox.clear();
        appendCommand(input);

        try {
            Command command = Parser.parse(input, tasks.size());
            String response = executeCommand(command);
            appendResponse(response);
            if (command.isExit()) {
                commandBox.setDisable(true);
                sendButton.setDisable(true);
            }
        } catch (ComputahException e) {
            appendWithDivider("OOPS!!! " + e.getMessage());
        }
    }

    private String executeCommand(Command command) throws ComputahException {
        ByteArrayOutputStream response = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(response, true, StandardCharsets.UTF_8);
        Ui ui = new Ui(new ByteArrayInputStream(new byte[0]), output);

        command.execute(tasks, ui, storage);
        output.flush();
        return response.toString(StandardCharsets.UTF_8).stripTrailing();
    }

    private void appendCommand(String input) {
        dialogBox.appendText(input + "\n");
    }

    private void appendResponse(String response) {
        dialogBox.appendText(response + "\n");
    }

    private void appendWithDivider(String message) {
        dialogBox.appendText("____________________________________________________________\n");
        dialogBox.appendText(message + "\n");
        dialogBox.appendText("____________________________________________________________\n");
    }
}
