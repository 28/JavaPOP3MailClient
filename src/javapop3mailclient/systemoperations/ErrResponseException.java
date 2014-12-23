package javapop3mailclient.systemoperations;

/**
 * This exception is thrown when POP3 server respond with message that starts
 * with -ERR.
 *
 * @author Dejan Josifovic
 */
public class ErrResponseException extends Exception {

    /**
     * Calls the constructor of the Exception class with message argument.
     *
     * @param message exception message.
     */
    public ErrResponseException(String message) {
        super(message);
    }
}
