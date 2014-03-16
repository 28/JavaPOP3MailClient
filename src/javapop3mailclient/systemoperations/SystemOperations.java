package javapop3mailclient.systemoperations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import javapop3mailclient.domen.Message;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Dejan Josifovic
 */
public class SystemOperations {

    private static Socket socket;
    private static BufferedReader reader;
    private static BufferedWriter writer;

    /**
     *
     * @param host
     * @throws IOException
     */
    public static void connect(String host) throws IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(host, 110));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        readResponseLine();
    }

    /**
     *
     * @param username
     * @param password
     * @throws IOException
     */
    public static void login(String username, String password) throws IOException {
        sendCommand("USER " + username);
        sendCommand("PASS " + password);
    }

    /**
     *
     * @return @throws IOException
     */
    public static int getNumberOfMessages() throws IOException {
        String response = sendCommand("STAT");
        String[] values = response.split(" ");
        return Integer.parseInt(values[1]);
    }

    /**
     *
     * @return 
     * @throws IOException
     */
    public static List<Message> getMessages() throws IOException {
        int numOfMessages = getNumberOfMessages();
        ArrayList<Message> messageList = new ArrayList<>();
        for (int i = 1; i <= numOfMessages; i++) {
            messageList.add(getMessage(i));
        }
        return messageList;
    }

    /**
     *
     * @throws IOException
     */
    public static void disconnect() throws IOException {
        socket.close();
        writer = null;
        reader = null;
    }

    /**
     *
     * @throws IOException
     */
    public static void logout() throws IOException {
        sendCommand("QUIT");
    }

    /**
     *
     * @param command
     * @return String
     * @throws IOException
     */
    private static String sendCommand(String command) throws IOException {
        writer.write(command + '\n');
        writer.flush();
        return readResponseLine();
    }

    /**
     *
     * @param i
     * @return
     * @throws IOException
     */
    private static Message getMessage(int i) throws IOException {
        String response;
        String headerName;
        Map<String, List<String>> headers = new HashMap<>();
        sendCommand("RETR " + i);
        while ((response = readResponseLine()).length() != 0) {
            if (response.startsWith("\t")) {
                continue;
            }
            int colognPosition = response.indexOf(":");
            headerName = response.substring(0, colognPosition);
            String headerValue;
            if (headerName.length() < colognPosition) {
                headerValue = response.substring(colognPosition + 2);
            } else {
                headerValue = "";
            }
            List<String> headerValues = headers.get(headerName);
            if (headerValues == null) {
                headerValues = new ArrayList<>();
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
     *
     * @return String
     * @throws IOException
     */
    private static String readResponseLine() throws IOException {
        String response = reader.readLine();
        if (response.startsWith("-ERR")) {
            throw new RuntimeException("Error in server response." + response);
        }
        return response;
    }
}
