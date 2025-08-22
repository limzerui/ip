import java.util.Scanner;

public class Kobe {
    private static final String BORDER = "----------------------------------------";
    private static final int MAX_TASKS = 100;
    private static final Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;

    public static void main(String[] args) {
        showGreeting();
        processUserInput();
    }

    private static void showGreeting() {
        printBorder();
        System.out.println(" Hello! I'm Kobe");
        System.out.println(" What can I do for you?");
        printBorder();
    }

    private static void processUserInput() {
        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
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
                if (line.startsWith("mark ")) {
                    handleMark(line);
                    continue;
                }
                if (line.startsWith("unmark ")) {
                    handleUnmark(line);
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
        printBorder();
        System.out.println(" Bye. Hope to see you again soon!");
        printBorder();
    }

    private static void showTaskList() {
        printBorder();
        if (taskCount == 0) {
            System.out.println(" No tasks in the list.");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < taskCount; i++) {
                System.out.println(" " + (i + 1) + "." + tasks[i]);
            }
        }
        printBorder();
    }

    private static void addTask(String description) {
        printBorder();
        if (taskCount < MAX_TASKS) {
            tasks[taskCount++] = new Task(description);
            System.out.println(" added: " + description);
        } else {
            System.out.println(" task list full (" + MAX_TASKS + ")");
        }
        printBorder();
    }

    private static void handleMark(String line) {
        Integer idx = parseIndex(line.substring(5));
        if (idx == null) {
            showIndexError();
            return;
        }
        Task t = tasks[idx];
        t.mark();
        printBorder();
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + t);
        printBorder();
    }

    private static void handleUnmark(String line) {
        Integer idx = parseIndex(line.substring(7));
        if (idx == null) {
            showIndexError();
            return;
        }
        Task t = tasks[idx];
        t.unmark();
        printBorder();
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + t);
        printBorder();
    }

    private static Integer parseIndex(String numberPart) {
        try {
            int userIndex = Integer.parseInt(numberPart.trim());
            if (userIndex < 1 || userIndex > taskCount) {
                return null;
            }
            return userIndex - 1;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static void showIndexError() {
        printBorder();
        System.out.println(" Invalid task number.");
        printBorder();
    }

    private static void printBorder() {
        System.out.println(BORDER);
    }
}