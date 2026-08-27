package computah.command;

import computah.exception.ComputahException;
import computah.storage.Storage;
import computah.task.Task;
import computah.ui.Ui;

import java.util.ArrayList;

/**
 * Adds a task to the task list.
 */
public class AddCommand extends Command {
    private final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(ArrayList<Task> tasks, Ui ui, Storage storage) throws ComputahException {
        tasks.add(task);
        storage.save(tasks);
        ui.showTaskAdded(task, tasks.size());
    }
}
