package computah.command;

import computah.exception.ComputahException;
import computah.storage.Storage;
import computah.task.Task;
import computah.ui.Ui;

import java.util.ArrayList;

/**
 * Represents an executable user command.
 */
public abstract class Command {
    /**
     * Creates a command instance for subclasses.
     */
    protected Command() {
    }

    /**
     * Executes this command against the current task list and supporting components.
     *
     * @param tasks current task list
     * @param ui user interface used to display command results
     * @param storage storage used to persist task-list changes
     * @throws ComputahException if the command cannot be completed
     */
    public abstract void execute(ArrayList<Task> tasks, Ui ui, Storage storage) throws ComputahException;

    /**
     * Returns whether this command should end the command loop.
     *
     * @return true if the application should exit, false otherwise
     */
    public boolean isExit() {
        return false;
    }
}
