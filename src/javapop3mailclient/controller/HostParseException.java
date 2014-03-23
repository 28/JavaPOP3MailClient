package javapop3mailclient.controller;

/**
 * This exception is thrown when the system cannot find the host address for the
 * specified domain name.
 *
 * @author Dejan Josifovic
 * @version 1.0
 */
public class HostParseException extends Exception {

    /**
     * Calls the constructor of the Exception class with message argument.
     *
     * @param message exception message.
     */
    public HostParseException(String message) {
        super(message);
    }
}
