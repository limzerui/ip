import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Kobe {
    private static final String BORDER = "----------------------------------------";
    private static final List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        showGreeting();
        processUserInput();
    }

    private static void showGreeting() {
        printlnBordered("Hello! I'm Kobe", "What can I do for you?");
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
                addTask(line);
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
        printlnBordered("Bye. Hope to see you again soon!");
    }

    private static void showTaskList() {
        if (tasks.isEmpty()) {
            printlnBordered("No tasks in the list.");
            return;
        }
        printBorder();
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i));
        }
        printBorder();
    }

    private static void addTask(String description) {
        tasks.add(new Task(description));
        printlnBordered("added: " + description);
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
            printlnBordered("Nice! I've marked this task as done:", "  " + task);
        } else {
            task.unmark();
            printlnBordered("OK, I've marked this task as not done yet:", "  " + task);
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
        printlnBordered("Invalid task number.");
    }

    private static void printBorder() {
        System.out.println(BORDER);
    }

    private static void printlnBordered(String... lines) {
        printBorder();
        for (String l : lines) {
            System.out.println(" " + l);
        }
        printBorder();
    }
}