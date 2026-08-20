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

        Task[] tasks = new Task[100];
        int taskCount = 0;
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            System.out.println(line);
            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            }
            if (input.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
                System.out.println(line);
                continue;
            }
            if (input.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(input.substring(7));
                int taskIndex = taskNumber - 1;
                tasks[taskIndex].markAsNotDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + tasks[taskIndex]);
                System.out.println(line);
                continue;
            }
            if (input.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(input.substring(5));
                int taskIndex = taskNumber - 1;
                tasks[taskIndex].markAsDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks[taskIndex]);
                System.out.println(line);
                continue;
            }
            if (input.startsWith("todo ")) {
                tasks[taskCount] = new ToDo(input.substring(5));
            } else if (input.startsWith("deadline ")) {
                String details = input.substring(9);
                String[] parts = details.split(" /by ", 2);
                tasks[taskCount] = new Deadline(parts[0], parts[1]);
            } else if (input.startsWith("event ")) {
                String details = input.substring(6);
                String[] fromParts = details.split(" /from ", 2);
                String[] toParts = fromParts[1].split(" /to ", 2);
                tasks[taskCount] = new Event(fromParts[0], toParts[0], toParts[1]);
            } else {
                tasks[taskCount] = new ToDo(input);
            }
            taskCount++;
            System.out.println("Got it. I've added this task:");
            System.out.println("  " + tasks[taskCount - 1]);
            System.out.println("Now you have " + taskCount + " tasks in the list.");
            System.out.println(line);
        }
    }
}
