import java.util.ArrayList;

public class Duke {
    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage("data/duke.txt");
        ui.showWelcome();

        ArrayList<Task> tasks = new ArrayList<>();
        try {
            tasks = storage.load();
        } catch (ComputahException e) {
            ui.showError(e.getMessage());
        }
        while (ui.hasNextCommand()) {
            String input = ui.readCommand();
            ui.showLine();
            try {
                Command command = Parser.parse(input, tasks.size());
                command.execute(tasks, ui, storage);
                if (command.isExit()) {
                    break;
                }
            } catch (ComputahException e) {
                ui.showError(e.getMessage());
            }
        }
    }
}
