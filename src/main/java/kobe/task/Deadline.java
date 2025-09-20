package kobe.task;

/**
 * A task with a deadline (by a specific time).
 */
public class Deadline extends Task {
    private final String by;

    /**
     * Creates a Deadline task.
     * @param description task description
     * @param by deadline time string
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    /**
     * {@inheritDoc}
     */
    public String toDataString() {
        return "D | " + commonData() + " | " + by;
    }
}
