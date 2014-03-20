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
    private Properties hosts;

    /**
     * @throws IOException
     */
    private Controller() throws IOException {
        loadHosts();
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public static Controller getInstance() throws IOException {
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
    public void signIn(String email, String password) throws HostParseException, IOException, CredentialsFormException, ErrResponseException {
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

    /**
     *
     * @throws IOException
     */
    public void refresh() throws IOException, ErrResponseException {
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
        String domainName = email.substring(email.indexOf('@') + 1);
        String hostAddress = hosts.getProperty(domainName);
        if(hostAddress == null) {
            throw new HostParseException("Cannot find host address.");
        }
        return hostAddress;
    }
    
    private void loadHosts() throws FileNotFoundException, IOException {
        hosts = new Properties();
        try (InputStream in = new FileInputStream("src/hosts.properties")) {
            hosts.load(in);
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
