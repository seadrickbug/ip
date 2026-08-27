package computah.command;

import computah.storage.Storage;
import computah.task.Task;
import computah.ui.Ui;

import java.util.ArrayList;

/**
 * Exits the chatbot.
 */
public class ExitCommand extends Command {
    /**
     * Creates an exit command.
     */
    public ExitCommand() {
    }

    /**
     * Shows the farewell message.
     *
     * @param tasks current task list
     * @param ui user interface used to display the farewell
     * @param storage storage component; unused for this command
     */
    @Override
    public void execute(ArrayList<Task> tasks, Ui ui, Storage storage) {
        ui.showFarewell();
    }

    /**
     * Returns true because this command ends the command loop.
     *
     * @return true
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
