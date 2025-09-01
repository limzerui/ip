package kobe.parser;

import kobe.exception.KobeException;
import kobe.task.Deadline;
import kobe.task.Event;
import kobe.task.Task;
import kobe.task.Todo;

public class Parser {

    public static Task parseTask(String input) throws KobeException {
        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            throw new KobeException("Nothing was entered.");
        }
        String lower = trimmed.toLowerCase();

        if (lower.startsWith("todo")) {
            if (lower.equals("todo")) {
                throw KobeException.emptyDescription("todo");
            }
            return parseTodo(trimmed);
        }

        if (lower.startsWith("deadline")) {
            if (lower.equals("deadline")) {
                throw KobeException.emptyDescription("deadline");
            }
            return parseDeadline(trimmed);
        }

        if (lower.startsWith("event")) {
            if (lower.equals("event")) {
                throw KobeException.emptyDescription("event");
            }
            return parseEvent(trimmed);
        }

        // not a recognised command keyword
        throw KobeException.unknownCommand();
    }

    private static Todo parseTodo(String input) throws KobeException {
        String description = input.substring(4).trim(); // remove 'todo'
        if (description.isEmpty()) {
            throw KobeException.emptyDescription("todo");
        }
        return new Todo(description);
    }

    private static Deadline parseDeadline(String input) throws KobeException {
        String content = input.substring(8).trim(); // remove 'deadline'
        int byIndex = content.toLowerCase().indexOf("/by ");
        if (byIndex == -1) {
            throw KobeException.missingKeywords("Deadline must include /by <time>.");
        }

        String description = content.substring(0, byIndex).trim();
        String by = content.substring(byIndex + 4).trim();

        if (description.isEmpty()) {
            throw KobeException.emptyDescription("deadline");
        }
        if (by.isEmpty()) {
            throw KobeException.missingKeywords("Deadline time after /by cannot be empty.");
        }

        return new Deadline(description, by);
    }

    private static Event parseEvent(String input) throws KobeException {
        String content = input.substring(5).trim(); // remove 'event'
        int fromIndex = content.toLowerCase().indexOf("/from ");
        int toIndex = content.toLowerCase().indexOf("/to ");

        if (fromIndex == -1 || toIndex == -1) {
            throw KobeException.missingKeywords("Event must include both /from <start> and /to <end>.");
        }
        
        if (fromIndex >= toIndex) {
            throw KobeException.missingKeywords("Event format should be: event <description> /from <start> /to <end>");
        }

        String description = content.substring(0, fromIndex).trim();
        String from = content.substring(fromIndex + 6, toIndex).trim();
        String to = content.substring(toIndex + 4).trim();

        if (description.isEmpty()) {
            throw KobeException.emptyDescription("event");
        }
        if (from.isEmpty()) {
            throw KobeException.missingKeywords("Event start time after /from cannot be empty.");
        }
        if (to.isEmpty()) {
            throw KobeException.missingKeywords("Event end time after /to cannot be empty.");
        }

        return new Event(description, from, to);
    }
}
