package kobe.task;

/**
 * A simple task without time constraints.
 */
public class Todo extends Task {
    /**
     * Creates a Todo task.
     * @param description task description
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * {@inheritDoc}
     */
    public String toDataString() {
        return "T | " + commonData();
    }
}
