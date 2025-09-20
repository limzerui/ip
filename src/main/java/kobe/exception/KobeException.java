package kobe.exception;

/**
 * Represents user-facing errors thrown during parsing or command handling.
 * Includes an {@link ErrorType} for categorization.
 */
public class KobeException extends Exception {
    /** Categories of Kobe exceptions for easier handling and messaging. */
    public enum ErrorType {
        EMPTY_DESCRIPTION,
        MISSING_KEYWORDS,
        INVALID_FORMAT,
        INVALID_TASK_NUMBER,
        UNKNOWN_COMMAND
    }
    
    private final ErrorType errorType;
    /**
     * Creates a {@code KobeException} defaulting to UNKNOWN_COMMAND type.
     * @param message detail message
     */
    public KobeException(String message) {
        super(message);
        this.errorType = ErrorType.UNKNOWN_COMMAND; 
    }
    
    /**
     * Creates a {@code KobeException} with a specific error type.
     * @param message detail message
     * @param errorType category of the error
     */
    public KobeException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }
    
    /**
     * Creates a {@code KobeException} with a cause and specific type.
     * @param message detail message
     * @param errorType category of the error
     * @param cause underlying cause
     */
    public KobeException(String message, ErrorType errorType, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }
    
    /**
     * Returns the category of this exception.
     * @return error type
     */
    public ErrorType getErrorType() {
        return errorType;
    }
    
    /**
     * Factory for an empty-description error for a given task type.
     * @param taskType e.g., "todo", "deadline", "event"
     * @return a {@code KobeException} with EMPTY_DESCRIPTION type
     */
    public static KobeException emptyDescription(String taskType) {
        return new KobeException(
            " The description of a " + taskType + " cannot be empty.", 
            ErrorType.EMPTY_DESCRIPTION
        );
    }
    
    /**
     * Factory for a missing-keywords error with a custom message.
     * @param message additional details
     * @return a {@code KobeException} with MISSING_KEYWORDS type
     */
    public static KobeException missingKeywords(String message) {
        return new KobeException(
            "OOPS!!! " + message, 
            ErrorType.MISSING_KEYWORDS
        );
    }
    
    /**
     * Factory for an unknown-command error.
     * @return a {@code KobeException} with UNKNOWN_COMMAND type
     */
    public static KobeException unknownCommand() {
        return new KobeException(
            "I don't know what that means :-(",
            ErrorType.UNKNOWN_COMMAND
        );
    }
    
    /**
     * Factory for an invalid-task-number error with the allowed range.
     * @param maxTasks maximum valid index (1-based)
     * @return a {@code KobeException} with INVALID_TASK_NUMBER type
     */
    public static KobeException invalidTaskNumber(int maxTasks) {
        return new KobeException(
            "Please enter a number between 1 and " + maxTasks + ".",
            ErrorType.INVALID_TASK_NUMBER
        );
    }
} 
