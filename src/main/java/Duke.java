import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        String banner = "  ____                            _        _     \n"
                + " / ___|___  _ __ ___  _ __  _   _| |_ __ _| |__  \n"
                + "| |   / _ \\| '_ ` _ \\| '_ \\| | | | __/ _` | '_ \\ \n"
                + "| |__| (_) | | | | | | |_) | |_| | || (_| | | | |\n"
                + " \\____\\___/|_| |_| |_| .__/ \\__,_|\\__\\__,_|_| |_|\n"
                + "                     |_|                          \n";
        System.out.println(line);
        System.out.println(banner);
        System.out.println("Hello! I'm Computah.");
        System.out.println("What can I do for you?");
        System.out.println(line);

        ArrayList<Task> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            System.out.println(line);
            try {
                if (input.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    System.out.println(line);
                    break;
                }
                if (input.equals("list")) {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                    System.out.println(line);
                    continue;
                }
                if (input.startsWith("delete")) {
                    int taskIndex = getTaskIndex(input, "delete", tasks.size());
                    Task removedTask = tasks.remove(taskIndex);
                    saveTasks(tasks);
                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + removedTask);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println(line);
                    continue;
                }
                if (input.startsWith("unmark")) {
                    int taskIndex = getTaskIndex(input, "unmark", tasks.size());
                    tasks.get(taskIndex).markAsNotDone();
                    saveTasks(tasks);
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks.get(taskIndex));
                    System.out.println(line);
                    continue;
                }
                if (input.startsWith("mark")) {
                    int taskIndex = getTaskIndex(input, "mark", tasks.size());
                    tasks.get(taskIndex).markAsDone();
                    saveTasks(tasks);
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks.get(taskIndex));
                    System.out.println(line);
                    continue;
                }
                Task task = createTask(input);
                tasks.add(task);
                saveTasks(tasks);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + task);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                System.out.println(line);
            } catch (ComputahException e) {
                System.out.println("OOPS!!! " + e.getMessage());
                System.out.println(line);
            }
        }
    }

    /**
     * Creates the correct task type from a user command.
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
            return new Deadline(parts[0].trim(), parts[1].trim());
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
            return new Event(fromParts[0].trim(), toParts[0].trim(), toParts[1].trim());
        }
        throw new ComputahException("I'm sorry, but I don't know what that means :-(");
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
