package javapop3mailclient.domain;

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

    /**
     *
     * @param headers
     * @param body
     */
    public Message(Map<String, List<String>> headers, String body) {
        this.headers = Collections.unmodifiableMap(headers);
        this.body = body;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String key : headers.keySet()) {
            builder.append(key).append(": ");
            for (String v : headers.get(key)) {
                builder.append(v).append(" ");
            }
            builder.append("\n");
        }
        builder.append("\n").append(body);
        return builder.toString();
    }

    /**
     *
     * @return
     */
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    /**
     *
     * @return
     */
    public String getBody() {
        return body;
    }

    /**
     *
     * @param headerName
     * @return
     */
    public String getHeader(String headerName) {
        List<String> values = headers.get(headerName);
        if(values.size() == 1) {
            return values.get(0);
        }
        StringBuilder valuesSB = new StringBuilder();
        for(String s : values) {
            valuesSB.append(s);
            valuesSB.append(',');
        }
        return valuesSB.toString();
    }
}
