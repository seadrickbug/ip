package computah.task;

import computah.util.DateTimeUtil;

import java.time.LocalDateTime;

/**
 * Represents a task that needs to be done before a specific date or time.
 */
public class Deadline extends Task {
    protected LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String getTaskType() {
        return "D";
    }

    @Override
    public String toFileString() {
        return super.toFileString() + " | " + DateTimeUtil.formatForFile(by);
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + DateTimeUtil.formatForDisplay(by) + ")";
    }
}
