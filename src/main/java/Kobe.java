import java.util.Scanner;

public class Kobe {
    private static final String BORDER = "----------------------------------------";
    private static final int MAX_TASKS = 100;
    private static final String[] tasks = new String[MAX_TASKS];
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
                String line = sc.nextLine();
                if (shouldExit(line)) {
                    showGoodbye();
                    break;
                } else if (isListCommand(line)) {
                    showTaskList();
                } else if (isValidTask(line)) {
                    addTask(line);
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

    private static boolean isValidTask(String input) {
        return !input.isEmpty();
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
            for (int i = 0; i < taskCount; i++) {
                System.out.println(" " + (i + 1) + ". " + tasks[i]);
            }
        }
        printBorder();
    }

    private static void addTask(String task) {
        printBorder();
        if (taskCount < MAX_TASKS) {
            tasks[taskCount++] = task;
            System.out.println(" added: " + task);
        } else {
            System.out.println(" task list full (" + MAX_TASKS + ")");
        }
        printBorder();
    }

    private static void printBorder() {
        System.out.println(BORDER);
    }
}