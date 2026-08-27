package computah.ui;

import java.util.ArrayList;
import java.util.Scanner;

import computah.task.Task;

/**
 * Handles interactions with the user.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";
    private static final String BANNER = "  ____                            _        _     \n"
            + " / ___|___  _ __ ___  _ __  _   _| |_ __ _| |__  \n"
            + "| |   / _ \\| '_ ` _ \\| '_ \\| | | | __/ _` | '_ \\ \n"
            + "| |__| (_) | | | | | | |_) | |_| | || (_| | | | |\n"
            + " \\____\\___/|_| |_| |_| .__/ \\__,_|\\__\\__,_|_| |_|\n"
            + "                     |_|                          \n";

    private final Scanner scanner;

    /**
     * Creates a UI component that reads from standard input.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Shows the greeting banner and initial prompt.
     */
    public void showWelcome() {
        System.out.println(LINE);
        System.out.println(BANNER);
        System.out.println("Hello! I'm Computah.");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    /**
     * Returns whether another user command is available.
     *
     * @return true if standard input has another line.
     */
    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }

    /**
     * Reads and trims the next user command.
     *
     * @return trimmed user command.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Shows the divider line.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Shows the farewell message.
     */
    public void showFarewell() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    /**
     * Shows all tasks in the current task list.
     *
     * @param tasks tasks to display.
     */
    public void showTaskList(ArrayList<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        System.out.println(LINE);
    }

    /**
     * Shows tasks that match a search keyword.
     *
     * @param tasks matching tasks to display.
     */
    public void showMatchingTasks(ArrayList<Task> tasks) {
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        System.out.println(LINE);
    }

    /**
     * Shows the confirmation message for an added task.
     *
     * @param task task that was added.
     * @param taskCount number of tasks after adding the task.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE);
    }

    /**
     * Shows the confirmation message for a deleted task.
     *
     * @param task task that was deleted.
     * @param taskCount number of tasks after deleting the task.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE);
    }

    /**
     * Shows the confirmation message for a task marked as done.
     *
     * @param task task that was marked as done.
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
        System.out.println(LINE);
    }

    /**
     * Shows the confirmation message for a task marked as not done.
     *
     * @param task task that was marked as not done.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
        System.out.println(LINE);
    }

    /**
     * Shows an error message.
     *
     * @param message error message without the standard Computah error prefix.
     */
    public void showError(String message) {
        System.out.println("OOPS!!! " + message);
        System.out.println(LINE);
    }
}
