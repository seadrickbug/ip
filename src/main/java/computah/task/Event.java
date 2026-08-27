package computah.task;

import computah.util.DateTimeUtil;

import java.time.LocalDateTime;

/**
 * Represents a task that starts and ends at specific dates or times.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getTaskType() {
        return "E";
    }

    @Override
    public String toFileString() {
        return super.toFileString() + " | " + DateTimeUtil.formatForFile(from) + " | "
                + DateTimeUtil.formatForFile(to);
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + DateTimeUtil.formatForDisplay(from) + " to: "
                + DateTimeUtil.formatForDisplay(to) + ")";
    }
}
