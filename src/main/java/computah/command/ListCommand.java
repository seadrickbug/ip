package computah.command;

import java.util.ArrayList;

import computah.storage.Storage;
import computah.task.Task;
import computah.ui.Ui;

/**
 * Shows all tasks in the task list.
 */
public class ListCommand extends Command {
    /**
     * Creates a list command.
     */
    public ListCommand() {
    }

    /**
     * Displays the current task list.
     *
     * @param tasks current task list.
     * @param ui user interface used to display the task list.
     * @param storage storage component; unused for this command.
     */
    @Override
    public void execute(ArrayList<Task> tasks, Ui ui, Storage storage) {
        ui.showTaskList(tasks);
    }
}
