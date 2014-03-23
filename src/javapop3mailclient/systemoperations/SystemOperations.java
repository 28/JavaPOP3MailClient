package javapop3mailclient.systemoperations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import javapop3mailclient.domain.Message;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the system operations that are called by the
 * controller. Each method or operation represents the sending of one pop3
 * command to the pop3 server.
 *
 * @author Dejan Josifovic
 * @version 1.0
 */
public class SystemOperations {

    /**
     * Socket by which the connection is established.
     */
    private static Socket socket;

    /**
     * Reader that reads input via the socket.
     */
    private static BufferedReader reader;

    /**
     * Writer that writes to the socket output stream.
     */
    private static BufferedWriter writer;

    /**
     * This method connect to the pop3 server. It creates a new Socket,
     * establish the connection to the given host on the port 110(default POP3
     * port) and initializes the reader and writer.
     *
     * @param host address of the pop3 server.
     * @throws IOException when reader and/or writer cannot be created.
     * @throws ErrResponseException when response is an error.
     */
    public static void connect(String host) throws IOException, ErrResponseException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(host, 110));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        readResponseLine();
    }

    /**
     * This method handles the authorization with the pop3 server. First it
     * sends the USER command with the username parameter and after, if the
     * server returns a OK response, sends the PASS command with the password
     * parameter. Then if the server return an OK user has successfully signed
     * in to the pop3 server.
     *
     * @param username of the user.
     * @param password of the user.
     * @throws IOException
     * @throws ErrResponseException
     */
    public static void login(String username, String password) throws IOException, ErrResponseException {
        sendCommand("USER " + username);
        sendCommand("PASS " + password);
    }

    /**
     * Retrieves the total number of messages in user mailbox. THe method sends
     * the STAT command to the server, and the server responds with three words.
     * First is OK, the second is the number of messages and the third is the
     * total length of the messages in octets. The words are separated by a
     * single space so the method splits the response and returns the second
     * word which is the number of messages.
     *
     * @return number of messages.
     * @throws IOException
     * @throws ErrResponseException
     */
    public static int getNumberOfMessages() throws IOException, ErrResponseException {
        String response = sendCommand("STAT");
        String[] values = response.split(" ");
        return Integer.parseInt(values[1]);
    }

    /**
     * Retrieves all the messages from the user mailbox using the number of
     * messages as a for loop parameter and <code>getMessage()</code> method.
     *
     * @return list of messages.
     * @throws IOException
     * @throws ErrResponseException
     */
    public static List<Message> getMessages() throws IOException, ErrResponseException {
        int numOfMessages = getNumberOfMessages();
        List<Message> messageList = new ArrayList<>();
        for (int i = 1; i <= numOfMessages; i++) {
            messageList.add(getMessage(i));
        }
        return messageList;
    }

    /**
     * Disconnect from the server by closing the socket.
     *
     * @throws IOException
     */
    public static void disconnect() throws IOException {
        socket.close();
        writer = null;
        reader = null;
    }

    /**
     * Logs out from the server by sending the QUIT command without parameters.
     *
     * @throws IOException
     * @throws ErrResponseException
     */
    public static void logout() throws IOException, ErrResponseException {
        sendCommand("QUIT");
    }

    /**
     * This method check if the email and password forms are good. They must not
     * be null, empty strings and email must match the regex pattern. Regex
     * pattern allows e-mails to be alfanumeric words that can contain dots,
     * followed by '@' then domain name with dot followed by maximum of four
     * characters. Otherwise the exception is thrown.
     *
     * @param email of the user.
     * @param password of the user.
     * @throws CredentialsFormException when email and/or password doesn't match
     * the criteria.
     */
    public static void credentialsFormOk(String email, String password) throws CredentialsFormException {
        String emailRegex = "[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}";
        if (email == null || password == null || email.equals("") || password.equals("") || email.equals(" ")) {
            throw new CredentialsFormException("Input can not be empty.");
        }
        if (!email.matches(emailRegex)) {
            throw new CredentialsFormException("E-mail form not valid.");
        }
    }

    /**
     * This method sends command to the pop3 server and then reads a response
     * line and returns it. Each command is followed by a new line character.
     *
     * @param command string that contains command and command parameters.
     * @return String server response that is not error.
     * @throws IOException if writer cannot write to output stream.
     * @throws ErrResponseException when server returns a error.
     */
    private static String sendCommand(String command) throws IOException, ErrResponseException {
        writer.write(command + '\n');
        writer.flush();
        return readResponseLine();
    }

    /**
     * Reads one message from the server stream and returns a new Message
     * object. Send the RETR command followed by the i parameter and server
     * returns a message with that number(number is integer that starts from 1).
     * Server first return the headers of the message, line by line. Each header
     * is composed of header name, followed by a colon then a header value.
     * Headers are stored in a map with header names as string keys and header
     * values as a list of strings. Header values are set to list because some
     * of them can have multiple values. They are extracted from the text using
     * substrings to and from the colon position.
     *
     * When the server returns an empty line that means its about to start
     * sending the message body. Line by line is read and stored in a
     * StringBuilder object. At the end server return a terminating line that
     * contains dot character.
     *
     * @param i that represents the message number.
     * @return a new Message object.
     * @throws IOException
     * @throws ErrResponseException
     */
    private static Message getMessage(int i) throws IOException, ErrResponseException {
        String response;
        String headerName;
        Map<String, List<String>> headers = new HashMap<>();
        sendCommand("RETR " + i);
        while ((response = readResponseLine()).length() != 0) {
            if (response.startsWith("\t")) {
                continue;
            }
            int colonPosition = response.indexOf(":");
            headerName = response.substring(0, colonPosition);
            String headerValue;
            if (headerName.length() <= colonPosition) {
                headerValue = response.substring(colonPosition + 2);
            } else {
                headerValue = "";
            }
            List<String> headerValues = headers.get(headerName);
            if (headerValues == null) {
                headerValues = new ArrayList<>();
                headerValues.add(headerValue);
                headers.put(headerName, headerValues);
            } else {
                headerValues.add(headerValue);
            }
        }
        StringBuilder bodyBuilder = new StringBuilder();
        while (!(response = readResponseLine()).equals(".")) {
            bodyBuilder.append(response).append("\n");
        }
        return new Message(headers, bodyBuilder.toString());
    }

    /**
     * Reads the server response and returns it.
     *
     * @return String that is the server response.
     * @throws IOException when reader cannot read input stream.
     * @throws ErrResponseException when server response starts with -ERR.
     */
    private static String readResponseLine() throws IOException, ErrResponseException {
        String response = reader.readLine();
        if (response.startsWith("-ERR")) {
            throw new ErrResponseException("Error in server response." + response.replace("-ERR", ""));
        }
        return response;
    }
}
