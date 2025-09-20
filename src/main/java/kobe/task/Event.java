package kobe.task;

/**
 * A task that spans a period with a start and end time.
 */
public class Event extends Task {
    private final String from;
    private final String to;

    /**
     * Creates an Event task.
     * @param description task description
     * @param from start time string
     * @param to end time string
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    /**
     * {@inheritDoc}
     */
    public String toDataString() {
        return "E | " + commonData() + " | " + from + " | " + to;
    }
}
