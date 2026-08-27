package computah.task;

/**
 * Represents a task without any date or time attached to it.
 */
public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String getTaskType() {
        return "T";
    }
}
