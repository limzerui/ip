import java.util.List;
import java.util.ArrayList;

public class TaskManager {
    private final List<Task> tasks;
    private final Ui ui;
    
    public TaskManager(Ui ui) {
        this.tasks = new ArrayList<>();
        this.ui = ui;
    }
    
    public void addTask(Task task) {
        tasks.add(task);
        ui.block(new String[]{
            " Got it. I've added this task:",
            "   " + task,
            " Now you have " + tasks.size() + " task" + (tasks.size() == 1 ? "" : "s") + " in the list."
        });
    }
    
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
    
    public void markTask(String indexPart) throws KobeException {
        toggleTask(indexPart, true);
    }
    
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
            } else {
                task.unmark();
                ui.block(new String[]{
                    " OK, I've marked this task as not done yet:",
                    "   " + task
                });
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
} 