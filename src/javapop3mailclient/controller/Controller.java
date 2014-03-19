package javapop3mailclient.controller;

import java.io.IOException;
import java.util.List;
import javapop3mailclient.domain.Message;
import javapop3mailclient.systemoperations.CredentialsFormException;
import javapop3mailclient.systemoperations.SystemOperations;

/**
 *
 * @author Dejan Josifovic
 */
public class Controller {

    private static Controller instance;

    private String host;
    private String password;
    private List<Message> messages;
    private int messageNumber;
    private String email;

    /**
     *
     */
    private Controller() {

    }

    /**
     *
     * @return
     */
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    /**
     *
     * @param email
     * @param password
     * @throws HostParseException
     * @throws IOException
     * @throws javapop3mailclient.systemoperations.CredentialsFormException
     */
    public void signIn(String email, String password) throws HostParseException, IOException, CredentialsFormException {
        SystemOperations.credentialsFormOk(email, password);
        this.email = email;
        this.password = password;
        host = parseHost(email);
        SystemOperations.connect(host);
        SystemOperations.login(email.substring(0, email.indexOf('@')), password);
        messageNumber = SystemOperations.getNumberOfMessages();
        messages = SystemOperations.getMessages();
        SystemOperations.logout();
        SystemOperations.disconnect();
    }

    public void refresh() throws IOException {
        SystemOperations.connect(host);
        SystemOperations.login(email.substring(0, email.indexOf('@')), password);
        messageNumber = SystemOperations.getNumberOfMessages();
        messages = SystemOperations.getMessages();
        SystemOperations.logout();
        SystemOperations.disconnect();
    }

    /**
     *
     * @throws IOException
     */
    public void exit() throws IOException {
        SystemOperations.disconnect();
        System.exit(0);
    }

    /**
     *
     * @param email
     * @return
     * @throws HostParseException
     */
    private String parseHost(String email) throws HostParseException {
        switch (email.substring(email.indexOf('@') + 1)) {
            case "eunet.rs":
                return "pop3.eunet.rs";
            default:
                throw new HostParseException("");
        }
    }

    /**
     *
     * @return
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     *
     * @return
     */
    public int getMessageNumber() {
        return messageNumber;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }
}
