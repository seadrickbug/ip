package computah.command;

import computah.exception.ComputahException;
import computah.storage.Storage;
import computah.task.Task;
import computah.ui.Ui;

import java.util.ArrayList;

/**
 * Marks a task as done.
 */
public class MarkCommand extends Command {
    private final int taskIndex;

    public MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(ArrayList<Task> tasks, Ui ui, Storage storage) throws ComputahException {
        tasks.get(taskIndex).markAsDone();
        storage.save(tasks);
        ui.showTaskMarked(tasks.get(taskIndex));
    }
}
