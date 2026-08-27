import java.util.ArrayList;
import java.util.Scanner;

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

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println(LINE);
        System.out.println(BANNER);
        System.out.println("Hello! I'm Computah.");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public void showFarewell() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    public void showTaskList(ArrayList<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        System.out.println(LINE);
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE);
    }

    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE);
    }

    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
        System.out.println(LINE);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
        System.out.println(LINE);
    }

    public void showError(String message) {
        System.out.println("OOPS!!! " + message);
        System.out.println(LINE);
    }
}
