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

        String[] tasks = new String[100];
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
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                System.out.println(line);
                continue;
            }
            tasks[taskCount] = input;
            taskCount++;
            System.out.println("added: " + input);
            System.out.println(line);
        }
    }
}
