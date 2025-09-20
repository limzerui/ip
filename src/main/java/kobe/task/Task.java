package kobe.task;

/**
 * Base type for tasks with a description and completion status.
 */
public abstract class Task {
    protected final String description;
    protected boolean isDone;

    /**
     * Creates a task with the given description.
     * @param description task description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Marks the task as done. */
    public void mark() {
        this.isDone = true;
    }

    /** Marks the task as not done. */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns the status icon used in list displays ("X" or space).
     * @return status icon
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Returns the task description.
     * @return description
     */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return ("[" + getStatusIcon() + "] " + description);
    }

    protected String commonData() {
        return (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Serializes the task into a line for storage.
     * @return data string representing this task
     */
    public abstract String toDataString();
}
