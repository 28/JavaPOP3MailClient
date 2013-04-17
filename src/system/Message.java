package system;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Dejan Josifovic
 */
public class Message {
    
    private final Map<String, List<String>> headers;
    private final String body;
    
    public Message(Map<String, List<String>> headers, String body) {
        this.headers = Collections.unmodifiableMap(headers);
        this.body = body;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(String key : headers.keySet()) {
            builder.append(key).append(": ");
            for(String v : headers.get(key)) {
                builder.append(v).append(" ");
            }
            builder.append("\n");
        }
        builder.append("\n").append(body);
        return builder.toString();
    }
    
    public Map<String, List<String>> getHeaders() {
        return headers;
    }
    
    public String getBody() {
        return body;
    }
}