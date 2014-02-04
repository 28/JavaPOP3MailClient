package javapop3mailclient.system;

import javapop3mailclient.domen.Message;
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
    
    private static POP3Client instance;
    
    private POP3Client() {
        
    }
    
    public static POP3Client getInstance() {
        if(instance == null) {
            instance = new POP3Client();
        }
        return instance;
    }
    
    /**
     * 
     * @param host
     * @param port
     * @throws IOException 
     */
    public void connect(String host) throws IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(host, 110));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        readResponseLine();
    }
    
    /**
     * 
     * @return String
     * @throws IOException 
     */
    public String readResponseLine() throws IOException {
        String response = reader.readLine();
        if(response.startsWith("-ERR")) {
            throw new RuntimeException("Error in server response." + response);
        }
        return response;
    }
    
    /**
     * 
     * @return 
     */
    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }
    
    /**
     * 
     * @throws IOException 
     */
    public void disconnect() throws IOException {
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
    public String sendCommand(String command) throws IOException {
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
    public void login(String username, String password) throws IOException{
        sendCommand("USER " + username);
        sendCommand("PASS " + password);
    }
    
    /**
     * 
     * @throws IOException 
     */
    public void logout() throws IOException{
        sendCommand("QUIT");
    }
    
    /**
     * 
     * @return
     * @throws IOException 
     */
    public int getNumberOfMessages() throws IOException {
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
    public Message getMessage(int i) throws IOException {
        String response;
        String headerName;
        Map<String, List<String>> headers = new HashMap<>();
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
                headerValues = new ArrayList<>();
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
    public List<Message> getMessages() throws IOException {
        int numOfMessages = getNumberOfMessages();
        ArrayList<Message> messageList = new ArrayList<>();
        for(int i = 0;i < numOfMessages;i++) {
            messageList.add(getMessage(i));
        }
        return messageList;
    }
}