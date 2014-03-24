package javapop3mailclient.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import javapop3mailclient.domain.Message;
import javapop3mailclient.systemoperations.CredentialsFormException;
import javapop3mailclient.systemoperations.ErrResponseException;
import javapop3mailclient.systemoperations.SystemOperations;

/**
 * The controller handles the communication between GUI and system operations
 * and also stores the host name, password, email, number of messages and
 * messages of the user.
 *
 * @author Dejan Josifovic
 * @version 1.0
 */
public class Controller {

    /**
     * Instance of the Controller class needed for the singleton pattern.
     */
    private static Controller instance;

    /**
     * Host address for the pop3 session.
     */
    private String host;

    /**
     * User password.
     */
    private String password;

    /**
     * List of all messages in user mailbox.
     */
    private List<Message> messages;

    /**
     * Number of messages in mailbox.
     */
    private int messageNumber;

    /**
     * E-mail of the user.
     */
    private String email;

    /**
     * Username which is the first part of the e-mail, before the @ sign.
     */
    private String username;
    
    /**
     * Hosts properties file.
     */
    private Properties hosts;

    /**
     * Private constructor of the controller class. Calls the
     * <code>loadHost()</code> method for loading the hosts properties file.
     *
     * @throws IOException
     */
    private Controller() throws IOException {
        loadHosts();
    }

    /**
     * This method implements the singleton pattern. Returns a single instance
     * of the Controller class. If instance is null creates the object.
     *
     * @return singleton instance of the Controller class.
     * @throws IOException
     */
    public static Controller getInstance() throws IOException {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    /**
     * Handles the sign in process. Sets the email and password of the user to
     * controller fields after checking their form. Sets the host field and
     * calls <code>checkMail()</code> method.
     *
     * @param email of the user.
     * @param password of the user.
     * @throws HostParseException
     * @throws IOException
     * @throws CredentialsFormException
     * @throws ErrResponseException
     */
    public void signIn(String email, String password) throws HostParseException, IOException, CredentialsFormException, ErrResponseException {
        SystemOperations.credentialsFormOk(email, password);
        this.email = email;
        this.password = password;
        this.username = email.substring(0, email.indexOf('@'));
        host = parseHost(email);
        checkMail();
    }

    /**
     * Checks for new mail. Calls the system operations for connection,
     * authorization, retrieval of the messages and for quitting.
     *
     * @throws IOException
     * @throws ErrResponseException
     */
    public void checkMail() throws IOException, ErrResponseException {
        SystemOperations.connect(host);
        SystemOperations.login(username, password);
        messageNumber = SystemOperations.getNumberOfMessages();
        messages = SystemOperations.getMessages();
        SystemOperations.logout();
        SystemOperations.disconnect();
    }

    /**
     * Deletes one message from users mailbox. Calls all the required system
     * operations.
     *
     * @param i the number of the message in messages list in application
     * memory.
     * @throws IOException
     * @throws ErrResponseException
     */
    public void deleteMessage(int i) throws IOException, ErrResponseException {
        SystemOperations.connect(host);
        SystemOperations.login(username, password);
        SystemOperations.deleteMessage(messageNumber);
        SystemOperations.logout();
        SystemOperations.disconnect();
    }

    /**
     * Handles the application exit process.
     *
     * @throws IOException
     */
    public void exit() throws IOException {
        SystemOperations.disconnect();
        System.exit(0);
    }

    /**
     * Converts the domain name of the email to the pop3 host address by
     * accessing the hosts properties file.
     *
     * @param email of the user.
     * @return pop3 host address.
     * @throws HostParseException when the domain name cannot be found in hosts
     * file.
     */
    private String parseHost(String email) throws HostParseException {
        String domainName = email.substring(email.indexOf('@') + 1);
        String hostAddress = hosts.getProperty(domainName);
        if (hostAddress == null) {
            throw new HostParseException("Cannot find host address.");
        }
        return hostAddress;
    }

    /**
     * Loads the hosts properties file via FileInputStream. It always uses the
     * same file path so the file must not be moved.
     *
     * @throws FileNotFoundException when file cannot be found.
     * @throws IOException when the input stream cannot read from the file.
     */
    private void loadHosts() throws FileNotFoundException, IOException {
        hosts = new Properties();
        try (InputStream in = new FileInputStream("src/hosts.properties")) {
            hosts.load(in);
        }
    }

    /**
     * Getter of the field messages.
     *
     * @return list of user messages.
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * Getter of the message number field.
     *
     * @return the number of messages in user mailbox.
     */
    public int getMessageNumber() {
        return messageNumber;
    }

    /**
     * Getter of the email field.
     *
     * @return email of the user.
     */
    public String getEmail() {
        return email;
    }
}
