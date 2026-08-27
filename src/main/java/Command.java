import java.util.ArrayList;

/**
 * Represents an executable user command.
 */
public abstract class Command {
    public abstract void execute(ArrayList<Task> tasks, Ui ui, Storage storage) throws ComputahException;

    public boolean isExit() {
        return false;
    }
}
