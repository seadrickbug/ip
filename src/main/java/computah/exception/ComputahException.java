package computah.exception;

/**
 * Represents an error caused by invalid Computah user input.
 */
public class ComputahException extends Exception {
    public ComputahException(String message) {
        super(message);
    }
}
