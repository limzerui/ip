package kobe.logic;

import java.util.List;
import java.util.ArrayList;

import kobe.exception.KobeException;
import kobe.task.Task;
import kobe.ui.Ui;
import kobe.storage.Storage;

/**
 * Manages the list of tasks and coordinates persistence and UI messages
 * for list operations (add, delete, mark/unmark, list, find).
 */
public class TaskManager {
    private final List<Task> tasks;
    private final Ui ui;
    private final Storage storage;

    /**
     * Constructs a TaskManager with UI and Storage collaborators.
     * Loads tasks from storage on initialization.
     * @param ui user interface for feedback
     * @param storage storage provider for persistence
     */
    public TaskManager(Ui ui, Storage storage) {
        this.ui = ui;
        this.storage = storage;
        List<Task> loaded = storage.load();
        this.tasks = new ArrayList<>(loaded);
    }
    
    /**
     * Adds a task, prints confirmation, and persists the list.
     * @param task the task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
        ui.block(new String[]{
            " Got it. I've added this task:",
            "   " + task,
            " Now you have " + tasks.size() + " task" + (tasks.size() == 1 ? "" : "s") + " in the list."
        });
        storage.save(tasks);
    }
    
    /**
     * Displays all tasks with their indices.
     */
    public void showTaskList() {
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
    
    /**
     * Marks the task at the given 1-based index as done.
     * @param indexPart 1-based index as user input
     * @throws KobeException if the index is invalid or out of bounds
     */
    public void markTask(String indexPart) throws KobeException {
        toggleTask(indexPart, true);
    }
    
    /**
     * Unmarks the task at the given 1-based index as not done.
     * @param indexPart 1-based index as user input
     * @throws KobeException if the index is invalid or out of bounds
     */
    public void unmarkTask(String indexPart) throws KobeException {
        toggleTask(indexPart, false);
    }
    
    private void toggleTask(String indexPart, boolean mark) throws KobeException {
        Integer index = parseIndex(indexPart);
        if (index == null) {
            throw KobeException.invalidTaskNumber(tasks.size());
        }
        
        try {
            Task task = tasks.get(index);
            if (mark) {
                task.mark();
                ui.block(new String[]{
                    " Nice! I've marked this task as done:",
                    "   " + task
                });
                // Persist change
                storage.save(tasks);
            } else {
                task.unmark();
                ui.block(new String[]{
                    " OK, I've marked this task as not done yet:",
                    "   " + task
                });
                // Persist change
                storage.save(tasks);
            }
        } catch (IndexOutOfBoundsException e) {
            throw KobeException.invalidTaskNumber(tasks.size());
        }
    }
    
    private Integer parseIndex(String numberPart) {
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

    /**
     * Deletes the task at the given 1-based index.
     * @param indexPart 1-based index as user input
     * @throws KobeException if the index is invalid or out of bounds
     */
    public void deleteTask(String indexPart) throws KobeException {
        Integer index = parseIndex(indexPart);
        if (index == null) {
            throw KobeException.invalidTaskNumber(tasks.size());
        }
        try {
            Task removed = tasks.remove((int) index);
            ui.block(new String[]{
                " Noted. I've removed this task:",
                "   " + removed,
                " Now you have " + tasks.size() + " task" + (tasks.size() == 1 ? "" : "s") + " in the list."
            });
            // Persist deletion
            storage.save(tasks);
        } catch (IndexOutOfBoundsException e) {
            throw KobeException.invalidTaskNumber(tasks.size());
        }
    }

    /**
     * Finds and displays tasks whose descriptions contain the given keyword (case-insensitive).
     * @param keyword search substring
     */
    public void findTasks(String keyword) {
        String k = keyword == null ? "" : keyword.trim().toLowerCase();
        if (k.isEmpty()) {
            ui.block(new String[]{" Please enter a keyword to find."});
            return;
        }

        ArrayList<Task> matches = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getDescription().toLowerCase().contains(k)) {
                matches.add(t);
            }
        }

        if (matches.isEmpty()) {
            ui.block(new String[]{" No matching tasks found."});
            return;
        }

        String[] lines = new String[matches.size() + 1];
        lines[0] = " Here are the matching tasks in your list:";
        for (int i = 0; i < matches.size(); i++) {
            lines[i + 1] = " " + (i + 1) + "." + matches.get(i);
        }
        ui.block(lines);
    }
}
