package kobe;

import java.nio.file.Paths;
import java.util.Scanner;

import kobe.exception.KobeException;
import kobe.logic.TaskManager;
import kobe.parser.Parser;
import kobe.task.Task;
import kobe.ui.Ui;
import kobe.storage.Storage;

public class Kobe {
    private final Ui ui;
    private final Storage storage;
    private final TaskManager taskManager;

    public Kobe(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(Paths.get(filePath));
        this.taskManager = new TaskManager(ui, storage);
    }

    public static void main(String[] args) {
        new Kobe("data/kobe.txt").run();
    }

    public void run() {
        showGreeting();
        processUserInput();
    }

    private void showGreeting() {
        ui.block(new String[]{" Hello! I'm Kobe", " What can I do for you?"});
    }

    private void processUserInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                if (shouldExit(line)) {
                    showGoodbye();
                    break;
                }

                processCommand(line);
            }
        }
    }

    private void processCommand(String line) {
        try {
            if (isListCommand(line)) {
                taskManager.showTaskList();
            } else if (line.toLowerCase().startsWith("find")) {
                String keyword = line.length() > 4 ? line.substring(4).trim() : "";
                if (keyword.isEmpty()) {
                    throw new KobeException(" Please enter a keyword to find.");
                }
                taskManager.findTasks(keyword);
            } else if (line.toLowerCase().startsWith("mark ")) {
                taskManager.markTask(line.substring(5));
            } else if (line.toLowerCase().startsWith("unmark ")) {
                taskManager.unmarkTask(line.substring(7));
            } else if (line.toLowerCase().startsWith("delete ")) {
                taskManager.deleteTask(line.substring(7));
            } else {
                // Try to parse as a task creation command
                Task task = Parser.parseTask(line);
                taskManager.addTask(task);
            }
        } catch (KobeException e) {
            ui.block(new String[]{" " + e.getMessage()});
        }
    }

    private boolean shouldExit(String input) {
        return input.trim().equalsIgnoreCase("bye");
    }

    private boolean isListCommand(String input) {
        return input.equalsIgnoreCase("list");
    }

    private void showGoodbye() {
        ui.block(new String[]{" Bye. Hope to see you again soon!"});
    }
}
