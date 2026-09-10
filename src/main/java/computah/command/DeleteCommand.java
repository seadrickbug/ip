package computah.command;

import java.util.ArrayList;

import computah.exception.ComputahException;
import computah.storage.Storage;
import computah.task.Task;
import computah.ui.Ui;

/**
 * Deletes a task from the task list.
 */
public class DeleteCommand extends Command {
    private final int taskIndex;

    /**
     * Creates a command that deletes the task at the given zero-based index.
     *
     * @param taskIndex zero-based index of the task to delete.
     */
    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Deletes the task, persists the updated list, and shows the removed task.
     *
     * @param tasks current task list.
     * @param ui user interface used to display the confirmation.
     * @param storage storage used to persist the updated task list.
     * @throws ComputahException if the updated task list cannot be saved.
     */
    @Override
    public void execute(ArrayList<Task> tasks, Ui ui, Storage storage) throws ComputahException {
        assert taskIndex >= 0 && taskIndex < tasks.size()
                : "Parser should supply an index of an existing task";
        int previousTaskCount = tasks.size();
        Task removedTask = tasks.remove(taskIndex);
        assert tasks.size() == previousTaskCount - 1 : "Deleting a task should reduce the task count by one";
        storage.save(tasks);
        ui.showTaskDeleted(removedTask, tasks.size());
    }
}
