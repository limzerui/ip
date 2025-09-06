package kobe;

import java.util.Scanner;

import kobe.exception.KobeException;
import kobe.logic.TaskManager;
import kobe.parser.Parser;
import kobe.task.Task;
import kobe.ui.Ui;

public class Kobe {
    private static final Ui ui = new Ui();
    private static final TaskManager taskManager = new TaskManager(ui);

    public static void main(String[] args) {
        showGreeting();
        processUserInput();
    }

    private static void showGreeting() {
        ui.block(new String[]{" Hello! I'm Kobe", " What can I do for you?"});
    }

    private static void processUserInput() {
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
    
    private static void processCommand(String line) {
        try {
            if (isListCommand(line)) {
                taskManager.showTaskList();
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

    private static boolean shouldExit(String input) {
        return input.trim().equalsIgnoreCase("bye");
    }

    private static boolean isListCommand(String input) {
        return input.equalsIgnoreCase("list");
    }

    private static void showGoodbye() {
        ui.block(new String[]{" Bye. Hope to see you again soon!"});
    }
}