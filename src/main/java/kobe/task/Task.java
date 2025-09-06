package kobe.task;

public abstract class Task {
    protected final String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    @Override
    public String toString() {
        return ("[" + getStatusIcon() + "] " + description);
    }

    protected String commonData() {
        return (isDone ? "1" : "0") + " | " + description;
    }

    public abstract String toDataString();
}
