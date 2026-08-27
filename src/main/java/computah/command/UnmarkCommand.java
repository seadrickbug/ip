package computah.command;

import computah.exception.ComputahException;
import computah.storage.Storage;
import computah.task.Task;
import computah.ui.Ui;

import java.util.ArrayList;

/**
 * Marks a task as not done.
 */
public class UnmarkCommand extends Command {
    private final int taskIndex;

    public UnmarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(ArrayList<Task> tasks, Ui ui, Storage storage) throws ComputahException {
        tasks.get(taskIndex).markAsNotDone();
        storage.save(tasks);
        ui.showTaskUnmarked(tasks.get(taskIndex));
    }
}
