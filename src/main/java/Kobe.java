import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Kobe {
    private static final List<Task> tasks = new ArrayList<>();
    private static final Ui ui = new Ui();

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
                if (isListCommand(line)) {
                    showTaskList();
                    continue;
                }
                String lower = line.toLowerCase();
                if (lower.startsWith("mark ")) {
                    handleToggle(lower.substring(5), true);
                    continue;
                }
                if (lower.startsWith("unmark ")) {
                    handleToggle(lower.substring(7), false);
                    continue;
                }

                try {
                    Task task = Parser.parseTask(line);
                    addTask(task);
                } catch (Exception e) {
                    ui.block(new String[]{" Error: " + e.getMessage()});
                }
            }
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

    private static void showTaskList() {
        if (tasks.isEmpty()) {
            ui.block(new String[]{" No tasks in the list."});
            return;
        }

        String[] lines = new String[tasks.size() + 1];
        lines[0] = " Here are the tasks in your list:";
        for (int i = 0; i < tasks.size(); i++) {
            lines[i + 1] = " " + (i + 1) + "." + tasks.get(i);
        }
        ui.block(lines);
    }

    private static void addTask(Task task) {
        tasks.add(task);
        ui.block(new String[]{
            " Got it. I've added this task:",
            "   " + task,
            " Now you have " + tasks.size() + " task" + (tasks.size() == 1 ? "" : "s") + " in the list."
        });
    }

    private static void handleToggle(String indexPart, boolean mark) {
        Integer index = parseIndex(indexPart);
        if (index == null) {
            showIndexError();
            return;
        }
        Task task = tasks.get(index);
        if (mark) {
            task.mark();
            ui.block(new String[]{
                " Nice! I've marked this task as done:",
                "   " + task
            });
        } else {
            task.unmark();
            ui.block(new String[]{
                " OK, I've marked this task as not done yet:",
                "   " + task
            });
        }
    }

    private static Integer parseIndex(String numberPart) {
        try {
            int userIndex = Integer.parseInt(numberPart.trim());
            if (userIndex < 1 || userIndex > tasks.size()) {
                return null;
            }
            return userIndex - 1;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static void showIndexError() {
        ui.block(new String[]{" Invalid task number."});
    }
}