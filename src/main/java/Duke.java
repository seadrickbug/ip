import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.showWelcome();

        ArrayList<Task> tasks = new ArrayList<>();
        try {
            tasks = loadTasks();
        } catch (ComputahException e) {
            ui.showError(e.getMessage());
        }
        while (ui.hasNextCommand()) {
            String input = ui.readCommand();
            ui.showLine();
            try {
                if (input.equals("bye")) {
                    ui.showFarewell();
                    break;
                }
                if (input.equals("list")) {
                    ui.showTaskList(tasks);
                    continue;
                }
                if (input.startsWith("delete")) {
                    int taskIndex = getTaskIndex(input, "delete", tasks.size());
                    Task removedTask = tasks.remove(taskIndex);
                    saveTasks(tasks);
                    ui.showTaskDeleted(removedTask, tasks.size());
                    continue;
                }
                if (input.startsWith("unmark")) {
                    int taskIndex = getTaskIndex(input, "unmark", tasks.size());
                    tasks.get(taskIndex).markAsNotDone();
                    saveTasks(tasks);
                    ui.showTaskUnmarked(tasks.get(taskIndex));
                    continue;
                }
                if (input.startsWith("mark")) {
                    int taskIndex = getTaskIndex(input, "mark", tasks.size());
                    tasks.get(taskIndex).markAsDone();
                    saveTasks(tasks);
                    ui.showTaskMarked(tasks.get(taskIndex));
                    continue;
                }
                Task task = createTask(input);
                tasks.add(task);
                saveTasks(tasks);
                ui.showTaskAdded(task, tasks.size());
            } catch (ComputahException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * Creates the correct task type from a user command.
     */
    private static Task createTask(String input) throws ComputahException {
        if (input.isEmpty()) {
            throw new ComputahException("Please enter a command.");
        }
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
     * Rejects text that cannot be safely stored in the current save-file format.
     */
    private static void validateFileSafeField(String field) throws ComputahException {
        if (field.contains(" | ")) {
            throw new ComputahException("Task details cannot contain \" | \".");
        }
    }

    /**
     * Saves the current task list to the hard disk.
     */
    private static void saveTasks(ArrayList<Task> tasks) throws ComputahException {
        File dataFile = new File("data/duke.txt");
        File dataDirectory = dataFile.getParentFile();
        if (!dataDirectory.exists() && !dataDirectory.mkdirs()) {
            throw new ComputahException("I could not create the data directory.");
        }
        try (FileWriter writer = new FileWriter(dataFile)) {
            for (Task task : tasks) {
                writer.write(task.toFileString() + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new ComputahException("I could not save the task list.");
        }
    }

    /**
     * Loads saved tasks from the hard disk.
     */
    private static ArrayList<Task> loadTasks() throws ComputahException {
        ArrayList<Task> tasks = new ArrayList<>();
        File dataFile = new File("data/duke.txt");
        if (!dataFile.exists()) {
            return tasks;
        }
        try (Scanner fileScanner = new Scanner(dataFile)) {
            while (fileScanner.hasNextLine()) {
                tasks.add(createTaskFromFileString(fileScanner.nextLine()));
            }
        } catch (IOException e) {
            throw new ComputahException("I could not load the task list.");
        }
        return tasks;
    }

    /**
     * Creates a task from one line in the saved task file.
     */
    private static Task createTaskFromFileString(String line) throws ComputahException {
        String[] parts = line.split(" \\| ", -1);
        if (parts.length < 3) {
            throw new ComputahException("I could not load the task list.");
        }
        Task task;
        if (parts[0].equals("T")) {
            validateSavedLine(parts, 3);
            task = new ToDo(parts[2]);
        } else if (parts[0].equals("D")) {
            validateSavedLine(parts, 4);
            task = new Deadline(parts[2], parseSavedDateTime(parts[3]));
        } else if (parts[0].equals("E")) {
            validateSavedLine(parts, 5);
            task = new Event(parts[2], parseSavedDateTime(parts[3]), parseSavedDateTime(parts[4]));
        } else {
            throw new ComputahException("I could not load the task list.");
        }
        if (parts[1].equals("1")) {
            task.markAsDone();
        } else if (!parts[1].equals("0")) {
            throw new ComputahException("I could not load the task list.");
        }
        return task;
    }

    /**
     * Checks that a saved task line has the exact number of fields and no empty data fields.
     */
    private static void validateSavedLine(String[] parts, int expectedLength) throws ComputahException {
        if (parts.length != expectedLength || parts[2].isEmpty()) {
            throw new ComputahException("I could not load the task list.");
        }
        for (int i = 3; i < parts.length; i++) {
            if (parts[i].isEmpty()) {
                throw new ComputahException("I could not load the task list.");
            }
        }
    }

    /**
     * Parses a saved date/time field while keeping save-file errors consistent.
     */
    private static LocalDateTime parseSavedDateTime(String text) throws ComputahException {
        try {
            return DateTimeUtil.parse(text);
        } catch (ComputahException e) {
            throw new ComputahException("I could not load the task list.");
        }
    }

    /**
     * Converts a one-based task number from a command into a zero-based array index.
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
