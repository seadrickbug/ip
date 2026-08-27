package computah.parser;

import computah.command.AddCommand;
import computah.command.Command;
import computah.command.DeleteCommand;
import computah.command.ExitCommand;
import computah.command.ListCommand;
import computah.command.MarkCommand;
import computah.command.UnmarkCommand;
import computah.exception.ComputahException;
import computah.task.Deadline;
import computah.task.Event;
import computah.task.Task;
import computah.task.ToDo;
import computah.util.DateTimeUtil;

/**
 * Makes sense of user commands.
 */
public class Parser {
    /**
     * Prevents instantiation of this utility class.
     */
    private Parser() {
    }

    /**
     * Parses a full user input line into a command object.
     *
     * @param input full user input after trimming.
     * @param taskCount number of tasks currently in the list.
     * @return command represented by the user input.
     * @throws ComputahException if the input is empty, unknown, malformed, or refers to an invalid task number.
     */
    public static Command parse(String input, int taskCount) throws ComputahException {
        if (input.isEmpty()) {
            throw new ComputahException("Please enter a command.");
        }
        if (input.equals("bye")) {
            return new ExitCommand();
        }
        if (input.equals("list")) {
            return new ListCommand();
        }
        if (input.startsWith("delete")) {
            return new DeleteCommand(getTaskIndex(input, "delete", taskCount));
        }
        if (input.startsWith("unmark")) {
            return new UnmarkCommand(getTaskIndex(input, "unmark", taskCount));
        }
        if (input.startsWith("mark")) {
            return new MarkCommand(getTaskIndex(input, "mark", taskCount));
        }
        return new AddCommand(createTask(input));
    }

    /**
     * Creates a task from a task-creation command.
     *
     * @param input full user input for a todo, deadline, or event command.
     * @return task represented by the command.
     * @throws ComputahException if the command is malformed or does not create a supported task type.
     */
    private static Task createTask(String input) throws ComputahException {
        if (input.equals("todo")) {
            throw new ComputahException("The description of a todo cannot be empty.");
        }
        if (input.startsWith("todo ")) {
            String description = input.substring(5).trim();
            if (description.isEmpty()) {
                throw new ComputahException("The description of a todo cannot be empty.");
            }
            validateFileSafeField(description);
            return new ToDo(description);
        }
        if (input.equals("deadline")) {
            throw new ComputahException("The description of a deadline cannot be empty.");
        }
        if (input.startsWith("deadline ")) {
            String details = input.substring(9).trim();
            String[] parts = details.split(" /by ", 2);
            if (parts[0].trim().isEmpty()) {
                throw new ComputahException("The description of a deadline cannot be empty.");
            }
            if (parts.length < 2 || parts[1].trim().isEmpty()) {
                throw new ComputahException("The by date/time of a deadline cannot be empty.");
            }
            String description = parts[0].trim();
            String by = parts[1].trim();
            validateFileSafeField(description);
            return new Deadline(description, DateTimeUtil.parse(by));
        }
        if (input.equals("event")) {
            throw new ComputahException("The description of an event cannot be empty.");
        }
        if (input.startsWith("event ")) {
            String details = input.substring(6).trim();
            String[] fromParts = details.split(" /from ", 2);
            if (fromParts[0].trim().isEmpty()) {
                throw new ComputahException("The description of an event cannot be empty.");
            }
            if (fromParts.length < 2) {
                throw new ComputahException("The start date/time of an event cannot be empty.");
            }
            String[] toParts = fromParts[1].split(" /to ", 2);
            if (toParts[0].trim().isEmpty()) {
                throw new ComputahException("The start date/time of an event cannot be empty.");
            }
            if (toParts.length < 2 || toParts[1].trim().isEmpty()) {
                throw new ComputahException("The end date/time of an event cannot be empty.");
            }
            String description = fromParts[0].trim();
            String from = toParts[0].trim();
            String to = toParts[1].trim();
            validateFileSafeField(description);
            return new Event(description, DateTimeUtil.parse(from), DateTimeUtil.parse(to));
        }
        throw new ComputahException("I'm sorry, but I don't know what that means :-(");
    }

    /**
     * Checks that a task field can be safely written using the current file delimiter.
     *
     * @param field task field to validate.
     * @throws ComputahException if the field contains the save-file delimiter.
     */
    private static void validateFileSafeField(String field) throws ComputahException {
        if (field.contains(" | ")) {
            throw new ComputahException("Task details cannot contain \" | \".");
        }
    }

    /**
     * Converts a one-based task number in a command into a zero-based list index.
     *
     * @param input full user input.
     * @param command command word that should be followed by a task number.
     * @param taskCount number of tasks currently in the list.
     * @return zero-based task index.
     * @throws ComputahException if the command has no valid task number or the number is out of range.
     */
    private static int getTaskIndex(String input, String command, int taskCount) throws ComputahException {
        if (!input.startsWith(command + " ")) {
            throw new ComputahException("Please specify a task number after " + command + ".");
        }
        int taskNumber;
        try {
            taskNumber = Integer.parseInt(input.substring(command.length() + 1).trim());
        } catch (NumberFormatException e) {
            throw new ComputahException("The task number must be a valid number.");
        }
        if (taskNumber < 1 || taskNumber > taskCount) {
            throw new ComputahException("The task number is not in the list.");
        }
        return taskNumber - 1;
    }
}
