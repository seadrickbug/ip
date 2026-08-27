package computah.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import computah.exception.ComputahException;
import computah.task.Deadline;
import computah.task.Event;
import computah.task.Task;
import computah.task.ToDo;
import computah.util.DateTimeUtil;

/**
 * Loads tasks from the file and saves tasks to the file.
 */
public class Storage {
    private final String filePath;

    /**
     * Creates a storage component that reads from and writes to the given file path.
     *
     * @param filePath path to the save file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the given task list to the save file, overwriting previous contents.
     *
     * @param tasks tasks to save.
     * @throws ComputahException if the data directory or save file cannot be written.
     */
    public void save(ArrayList<Task> tasks) throws ComputahException {
        File dataFile = new File(filePath);
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
     * Loads tasks from the save file.
     *
     * @return saved tasks, or an empty list if the save file does not exist.
     * @throws ComputahException if the save file cannot be read or contains malformed task data.
     */
    public ArrayList<Task> load() throws ComputahException {
        ArrayList<Task> tasks = new ArrayList<>();
        File dataFile = new File(filePath);
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
     * Creates a task from one line in the save file.
     *
     * @param line save-file line to parse.
     * @return task represented by the line.
     * @throws ComputahException if the line does not match the save-file format.
     */
    private Task createTaskFromFileString(String line) throws ComputahException {
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
     * Checks that a saved task line has the exact field count and no empty data fields.
     *
     * @param parts fields split from one save-file line.
     * @param expectedLength expected number of fields for the task type.
     * @throws ComputahException if the saved line is malformed.
     */
    private void validateSavedLine(String[] parts, int expectedLength) throws ComputahException {
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
     * Parses a saved date/time while converting date parse failures into storage load failures.
     *
     * @param text saved date/time text.
     * @return parsed date/time.
     * @throws ComputahException if the saved date/time is invalid.
     */
    private LocalDateTime parseSavedDateTime(String text) throws ComputahException {
        try {
            return DateTimeUtil.parse(text);
        } catch (ComputahException e) {
            throw new ComputahException("I could not load the task list.");
        }
    }
}
