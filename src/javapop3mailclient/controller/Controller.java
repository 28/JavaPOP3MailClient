package javapop3mailclient.controller;

import java.io.IOException;
import java.util.List;
import javapop3mailclient.domen.Message;
import javapop3mailclient.systemoperations.SystemOperations;

/**
 *
 * @author Dejan Josifovic
 */
public class Controller {

    private static Controller instance;

    private String host;
    private String passsword;
    private List<Message> messages;
    private int messageNumber;
    private String email;

    private Controller() {

    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void signIn(String email, String password) throws HostParseException, IOException {
        this.email = email;
        this.passsword = password;
        host = parseHost(email);
        SystemOperations.connect(host);
        SystemOperations.login(email.substring(0, email.indexOf('@')), password);
        messageNumber = SystemOperations.getNumberOfMessages();
        messages = SystemOperations.getMessages();
        SystemOperations.logout();
        SystemOperations.disconnect();
    }

    public void exit() throws IOException {
        SystemOperations.logout();
        SystemOperations.disconnect();
        System.exit(0);
    }

    private String parseHost(String email) throws HostParseException {
        switch (email.substring(email.indexOf('@') + 1)) {
            case "eunet.rs":
                return "pop3.eunet.rs";
            default:
                throw new HostParseException("");
        }
    }

    public List<Message> getMessages() {
        return messages;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public String getEmail() {
        return email;
    }
}
