package computah.command;

import computah.exception.ComputahException;
import computah.storage.Storage;
import computah.task.Task;
import computah.ui.Ui;

import java.util.ArrayList;

/**
 * Deletes a task from the task list.
 */
public class DeleteCommand extends Command {
    private final int taskIndex;

    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(ArrayList<Task> tasks, Ui ui, Storage storage) throws ComputahException {
        Task removedTask = tasks.remove(taskIndex);
        storage.save(tasks);
        ui.showTaskDeleted(removedTask, tasks.size());
    }
}
