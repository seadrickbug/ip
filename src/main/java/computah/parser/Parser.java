package computah.parser;

import java.util.HashMap;
import java.util.Map;

import computah.client.Client;
import computah.command.AddClientCommand;
import computah.command.AddCommand;
import computah.command.Command;
import computah.command.DeleteClientCommand;
import computah.command.DeleteCommand;
import computah.command.EditClientCommand;
import computah.command.ExitCommand;
import computah.command.FindCommand;
import computah.command.ListClientsCommand;
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
     * @param clientCount number of clients currently in the list.
     * @return command represented by the user input.
     * @throws ComputahException if the input is empty, unknown, malformed, or refers to an invalid item number.
     */
    public static Command parse(String input, int taskCount, int clientCount) throws ComputahException {
        if (input.isEmpty()) {
            throw new ComputahException("Please enter a command.");
        }
        if (input.equals("client")) {
            throw new ComputahException("Please specify a client command.");
        }
        if (input.startsWith("client ")) {
            return parseClientCommand(input.substring(7).trim(), clientCount);
        }
        if (input.equals("bye")) {
            return new ExitCommand();
        }
        if (input.equals("list")) {
            return new ListCommand();
        }
        if (input.equals("find")) {
            throw new ComputahException("The keyword of a find command cannot be empty.");
        }
        if (input.startsWith("find ")) {
            String keyword = input.substring(5).trim();
            if (keyword.isEmpty()) {
                throw new ComputahException("The keyword of a find command cannot be empty.");
            }
            return new FindCommand(keyword);
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
     * Parses a command that manages client information.
     *
     * @param input client command without the leading {@code client} keyword.
     * @param clientCount number of clients currently in the list.
     * @return command represented by the client input.
     * @throws ComputahException if the client command is invalid.
     */
    private static Command parseClientCommand(String input, int clientCount) throws ComputahException {
        if (input.isEmpty()) {
            throw new ComputahException("Please specify a client command.");
        }
        if (input.equals("list")) {
            return new ListClientsCommand();
        }
        if (input.startsWith("list ")) {
            throw new ComputahException("The client list command does not accept additional arguments.");
        }
        if (input.equals("add")) {
            throw new ComputahException("The name of a client cannot be empty.");
        }
        if (input.startsWith("add ")) {
            return new AddClientCommand(createClient(input.substring(4).trim()));
        }
        if (input.equals("edit")) {
            throw new ComputahException("Please specify a client number.");
        }
        if (input.startsWith("edit ")) {
            return createEditClientCommand(input.substring(5).trim(), clientCount);
        }
        if (input.equals("delete")) {
            throw new ComputahException("Please specify a client number.");
        }
        if (input.startsWith("delete ")) {
            int clientIndex = getClientIndex(input.substring(7).trim(), clientCount);
            return new DeleteClientCommand(clientIndex);
        }
        throw new ComputahException("I'm sorry, but I don't know that client command.");
    }

    /**
     * Creates a client from a name followed by optional contact fields.
     *
     * @param details client name and optional fields.
     * @return client represented by the input.
     * @throws ComputahException if the client details are invalid.
     */
    private static Client createClient(String details) throws ComputahException {
        String[] parts = details.split("\\s+(?=/)", -1);
        String name = parts[0].trim();
        if (name.isEmpty() || name.startsWith("/")) {
            throw new ComputahException("The name of a client cannot be empty.");
        }

        Map<String, String> fields = parseClientFields(parts, 1, false);
        String phone = getNewClientField(fields, "/phone", "phone");
        String email = getNewClientField(fields, "/email", "email");
        return new Client(name, phone, email);
    }

    /**
     * Creates a command that updates selected fields of an existing client.
     *
     * @param details client number followed by fields to update.
     * @param clientCount number of clients currently in the list.
     * @return client edit command represented by the input.
     * @throws ComputahException if the client number or fields are invalid.
     */
    private static EditClientCommand createEditClientCommand(String details, int clientCount)
            throws ComputahException {
        String[] numberAndFields = details.split("\\s+", 2);
        int clientIndex = getClientIndex(numberAndFields[0], clientCount);
        if (numberAndFields.length < 2 || numberAndFields[1].isBlank()) {
            throw new ComputahException("Please specify at least one client field to edit.");
        }

        String[] parts = numberAndFields[1].trim().split("\\s+(?=/)", -1);
        Map<String, String> fields = parseClientFields(parts, 0, true);
        String name = getEditedClientField(fields, "/name", "name", false);
        String phone = getEditedClientField(fields, "/phone", "phone", true);
        String email = getEditedClientField(fields, "/email", "email", true);
        return new EditClientCommand(clientIndex, name, phone, email);
    }

    /**
     * Parses client field segments while rejecting unknown and repeated fields.
     *
     * @param parts input segments containing client fields.
     * @param startIndex first segment containing a field.
     * @param isNameAllowed whether {@code /name} is valid in this command.
     * @return field markers mapped to their supplied values.
     * @throws ComputahException if a field is unknown or repeated.
     */
    private static Map<String, String> parseClientFields(String[] parts, int startIndex,
            boolean isNameAllowed) throws ComputahException {
        Map<String, String> fields = new HashMap<>();
        for (int i = startIndex; i < parts.length; i++) {
            String[] fieldAndValue = parts[i].trim().split("\\s+", 2);
            String field = fieldAndValue[0];
            boolean isKnownField = field.equals("/phone") || field.equals("/email")
                    || isNameAllowed && field.equals("/name");
            if (!isKnownField) {
                throw new ComputahException("Unknown client field: " + field + ".");
            }
            if (fields.containsKey(field)) {
                throw new ComputahException("Client field " + field + " cannot be specified more than once.");
            }
            String value = fieldAndValue.length == 1 ? "" : fieldAndValue[1].trim();
            fields.put(field, value);
        }
        return fields;
    }

    /**
     * Returns an optional field value for a new client.
     *
     * @param fields supplied client fields.
     * @param field field marker to retrieve.
     * @param fieldName field name used in error messages.
     * @return supplied value, or an empty string when absent or explicitly cleared.
     * @throws ComputahException if the field is supplied without a value.
     */
    private static String getNewClientField(Map<String, String> fields, String field, String fieldName)
            throws ComputahException {
        if (!fields.containsKey(field)) {
            return "";
        }
        String value = fields.get(field);
        if (value.equals("-")) {
            return "";
        }
        if (value.isEmpty()) {
            throw new ComputahException("The " + fieldName + " of a client cannot be empty.");
        }
        return value;
    }

    /**
     * Returns a field value for a client edit command.
     *
     * @param fields supplied client fields.
     * @param field field marker to retrieve.
     * @param fieldName field name used in error messages.
     * @param canClear whether {@code -} can clear this field.
     * @return supplied value, an empty string to clear, or null when unchanged.
     * @throws ComputahException if the supplied value is empty or clears a required field.
     */
    private static String getEditedClientField(Map<String, String> fields, String field, String fieldName,
            boolean canClear) throws ComputahException {
        if (!fields.containsKey(field)) {
            return null;
        }
        String value = fields.get(field);
        if (canClear && value.equals("-")) {
            return "";
        }
        if (value.isEmpty() || !canClear && value.equals("-")) {
            throw new ComputahException("The " + fieldName + " of a client cannot be empty.");
        }
        return value;
    }

    /**
     * Converts a one-based client number into a zero-based list index.
     *
     * @param input client number text.
     * @param clientCount number of clients currently in the list.
     * @return zero-based client index.
     * @throws ComputahException if the client number is invalid or outside the list.
     */
    private static int getClientIndex(String input, int clientCount) throws ComputahException {
        int clientNumber;
        try {
            clientNumber = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new ComputahException("The client number must be a valid number.");
        }
        if (clientNumber < 1 || clientNumber > clientCount) {
            throw new ComputahException("The client number is not in the list.");
        }
        return clientNumber - 1;
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
            return createToDo(input);
        }
        if (input.equals("deadline")) {
            throw new ComputahException("The description of a deadline cannot be empty.");
        }
        if (input.startsWith("deadline ")) {
            return createDeadline(input);
        }
        if (input.equals("event")) {
            throw new ComputahException("The description of an event cannot be empty.");
        }
        if (input.startsWith("event ")) {
            return createEvent(input);
        }
        throw new ComputahException("I'm sorry, but I don't know what that means :-(");
    }

    /**
     * Creates a todo from a command containing its description.
     *
     * @param input full todo command.
     * @return todo represented by the command.
     * @throws ComputahException if the description is empty or unsafe to store.
     */
    private static ToDo createToDo(String input) throws ComputahException {
        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw new ComputahException("The description of a todo cannot be empty.");
        }
        validateFileSafeField(description);
        return new ToDo(description);
    }

    /**
     * Creates a deadline from a command containing its description and due date/time.
     *
     * @param input full deadline command.
     * @return deadline represented by the command.
     * @throws ComputahException if a required field is missing, invalid, or unsafe to store.
     */
    private static Deadline createDeadline(String input) throws ComputahException {
        String details = input.substring(9).trim();
        String[] parts = details.split(" /by ", 2);
        if (parts[0].trim().isEmpty()) {
            throw new ComputahException("The description of a deadline cannot be empty.");
        }
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new ComputahException("The by date/time of a deadline cannot be empty.");
        }

        String description = parts[0].trim();
        String deadlineText = parts[1].trim();
        validateFileSafeField(description);
        return new Deadline(description, DateTimeUtil.parse(deadlineText));
    }

    /**
     * Creates an event from a command containing its description, start, and end.
     *
     * @param input full event command.
     * @return event represented by the command.
     * @throws ComputahException if a required field is missing, invalid, or unsafe to store.
     */
    private static Event createEvent(String input) throws ComputahException {
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
        String startDateTimeText = toParts[0].trim();
        String endDateTimeText = toParts[1].trim();
        validateFileSafeField(description);
        return new Event(description, DateTimeUtil.parse(startDateTimeText),
                DateTimeUtil.parse(endDateTimeText));
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
