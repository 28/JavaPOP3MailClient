package system;

import java.io.*;
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
public class POP3Client {
    
    public static Socket socket;
    public static BufferedReader reader;
    public static BufferedWriter writer;
    
    /**
     * 
     * @param host
     * @param port
     * @throws IOException 
     */
    public static void connect(String host, int port) throws IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(host, port));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        readResponseLine();
    }
    
    /**
     * 
     * @return String
     * @throws IOException 
     */
    public static String readResponseLine() throws IOException {
        String response = reader.readLine();
        if(response.startsWith("-ERR")) {
            throw new RuntimeException();
        }
        return response;
    }
    
    /**
     * 
     * @return 
     */
    public static boolean isConnected() {
        return socket != null && socket.isConnected();
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
     * @param command
     * @return String
     * @throws IOException 
     */
    public static String sendCommand(String command) throws IOException {
        writer.write(command + '\n');
        writer.flush();
        return readResponseLine();
    }
    
    /**
     * 
     * @param username
     * @param password
     * @throws IOException 
     */
    public static void login(String username, String password) throws IOException{
        sendCommand("USER " + username);
        sendCommand("PASS " + password);
    }
    
    /**
     * 
     * @throws IOException 
     */
    public static void logout() throws IOException{
        sendCommand("QUIT");
    }
    
    /**
     * 
     * @return
     * @throws IOException 
     */
    public static int getNumberOfMessages() throws IOException {
        String response = sendCommand("STAT");
        String [] values = response.split(" ");
        return Integer.parseInt(values[1]);
    }
    
    /**
     * 
     * @param i
     * @return
     * @throws IOException 
     */
    public static Message getMessage(int i) throws IOException {
        String response;
        String headerName;
        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        sendCommand("RETR " + i);
        while((response = readResponseLine()).length() != 0) {
            if(response.startsWith("\t")) {
                continue;
            }
            int colognPosition = response.indexOf(":");
            headerName = response.substring(0, colognPosition);
            String headerValue;
            if(headerName.length() < colognPosition) {
                headerValue = response.substring(colognPosition + 2);
            } else {
                headerValue = "";
            }
            List<String> headerValues = headers.get(headerName);
            if(headerValues == null) {
                headerValues = new ArrayList<String>();
                headers.put(headerName, headerValues);
            } else {
                headerValues.add(headerValue);
            }
        }
        StringBuilder bodyBuilder = new StringBuilder();
        while(!(response = readResponseLine()).equals(".")) {
            bodyBuilder.append(response).append("\n");
        }
        return new Message(headers, bodyBuilder.toString());
    }
    
    /**
     * 
     * @return
     * @throws IOException 
     */
    public static List<Message> getMessages() throws IOException {
        int numOfMessages = getNumberOfMessages();
        ArrayList<Message> messageList = new ArrayList<Message>();
        for(int i = 0;i < numOfMessages;i++) {
            messageList.add(getMessage(i));
        }
        return messageList;
    }
}
