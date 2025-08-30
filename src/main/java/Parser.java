public class Parser {

    public static Task parseTask(String input) throws Exception {
        String trimmed = input.trim();
        String lower = trimmed.toLowerCase();

        if (lower.startsWith("todo ")) {
            return parseTodo(trimmed);
        }

        if (lower.startsWith("deadline ")) {
            return parseDeadline(trimmed);
        }

        if (lower.startsWith("event ")) {
            return parseEvent(trimmed);
        }

        if (!trimmed.isEmpty()) {
            return new Todo(trimmed);
        }

        throw new Exception("Unknown command format.");
    }

    private static Todo parseTodo(String input) throws Exception {
        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw new Exception("The description of a todo cannot be empty.");
        }
        return new Todo(description);
    }

    private static Deadline parseDeadline(String input) throws Exception {
        String content = input.substring(9).trim();
        int byIndex = content.toLowerCase().indexOf("/by ");
        if (byIndex == -1) {
            throw new Exception("Please specify the deadline using /by");
        }

        String description = content.substring(0, byIndex).trim();
        String by = content.substring(byIndex + 4).trim();

        if (description.isEmpty()) {
            throw new Exception("The description of a deadline cannot be empty.");
        }
        if (by.isEmpty()) {
            throw new Exception("The deadline date cannot be empty.");
        }

        return new Deadline(description, by);
    }

    private static Event parseEvent(String input) throws Exception {
        String content = input.substring(6).trim();
        int fromIndex = content.toLowerCase().indexOf("/from ");
        int toIndex = content.toLowerCase().indexOf("/to ");

        if (fromIndex == -1 || toIndex == -1) {
            throw new Exception("Please specify the event time using /from and /to");
        }

        String description = content.substring(0, fromIndex).trim();
        String from = content.substring(fromIndex + 6, toIndex).trim();
        String to = content.substring(toIndex + 4).trim();

        if (description.isEmpty()) {
            throw new Exception("The description of an event cannot be empty.");
        }
        if (from.isEmpty()) {
            throw new Exception("The event start time cannot be empty.");
        }
        if (to.isEmpty()) {
            throw new Exception("The event end time cannot be empty.");
        }

        return new Event(description, from, to);
    }
}
