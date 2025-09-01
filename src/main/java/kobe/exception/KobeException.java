package kobe.exception;

public class KobeException extends Exception {
    public enum ErrorType {
        EMPTY_DESCRIPTION,
        MISSING_KEYWORDS,
        INVALID_FORMAT,
        INVALID_TASK_NUMBER,
        UNKNOWN_COMMAND
    }
    
    private final ErrorType errorType;

    public KobeException(String message) {
        super(message);
        this.errorType = ErrorType.UNKNOWN_COMMAND; 
    }
    
    public KobeException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }
    
    public KobeException(String message, ErrorType errorType, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }
    
    public ErrorType getErrorType() {
        return errorType;
    }
    
    public static KobeException emptyDescription(String taskType) {
        return new KobeException(
            " The description of a " + taskType + " cannot be empty.", 
            ErrorType.EMPTY_DESCRIPTION
        );
    }
    
    public static KobeException missingKeywords(String message) {
        return new KobeException(
            "OOPS!!! " + message, 
            ErrorType.MISSING_KEYWORDS
        );
    }
    
    public static KobeException unknownCommand() {
        return new KobeException(
            "I don't know what that means :-(",
            ErrorType.UNKNOWN_COMMAND
        );
    }
    
    public static KobeException invalidTaskNumber(int maxTasks) {
        return new KobeException(
            "Please enter a number between 1 and " + maxTasks + ".",
            ErrorType.INVALID_TASK_NUMBER
        );
    }
} 