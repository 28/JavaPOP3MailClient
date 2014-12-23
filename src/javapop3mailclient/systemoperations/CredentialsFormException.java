package javapop3mailclient.systemoperations;

/**
 * This exception is thrown when email or password are null values, empty values
 * or email doesn't match the regex pattern.
 *
 * @author Dejan Josifovic
 */
public class CredentialsFormException extends Exception {

    /**
     * Calls the constructor of the Exception class with message argument.
     *
     * @param message exception message.
     */
    public CredentialsFormException(String message) {
        super(message);
    }
}
