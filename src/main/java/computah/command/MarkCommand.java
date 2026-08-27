package computah.command;

import java.util.ArrayList;

import computah.exception.ComputahException;
import computah.storage.Storage;
import computah.task.Task;
import computah.ui.Ui;

/**
 * Marks a task as done.
 */
public class MarkCommand extends Command {
    private final int taskIndex;

    /**
     * Creates a command that marks the task at the given zero-based index as done.
     *
     * @param taskIndex zero-based index of the task to mark.
     */
    public MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Marks the task as done, persists the updated list, and shows the confirmation.
     *
     * @param tasks current task list.
     * @param ui user interface used to display the confirmation.
     * @param storage storage used to persist the updated task list.
     * @throws ComputahException if the updated task list cannot be saved.
     */
    @Override
    public void execute(ArrayList<Task> tasks, Ui ui, Storage storage) throws ComputahException {
        tasks.get(taskIndex).markAsDone();
        storage.save(tasks);
        ui.showTaskMarked(tasks.get(taskIndex));
    }
}
