package javapop3mailclient.domain;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This is the representation of the e-mail message.
 *
 * @author Dejan Josifovic
 * @version 1.0
 */
public class Message {

    /**
     * Map of the mail headers. Keys are the names of the headers and values are
     * stored in the list of strings because some of them may have multiple
     * values(like multiple To: e-mails).
     */
    private final Map<String, List<String>> headers;

    /**
     * Body of the message.
     */
    private final String body;

    /**
     * Creates a new Message.
     *
     * @param headers map of the message.
     * @param body of the message.
     */
    public Message(Map<String, List<String>> headers, String body) {
        this.headers = Collections.unmodifiableMap(headers);
        this.body = body;
    }

    /**
     * Creates the string representation of the whole message.
     *
     * @return string representation of the message.
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
     * Getter of the headers field.
     *
     * @return map of the headers of the message.
     */
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    /**
     * Getter of the body field.
     *
     * @return body of the message.
     */
    public String getBody() {
        return body;
    }

    /**
     * Returns the header value of the specified header. If header value list
     * has one element returns that one element. If it has more returns a
     * concatenated string of those values separated by a comma.
     *
     * @param headerName name of the header.
     * @return header value as one string.
     */
    public String getHeader(String headerName) {
        List<String> values = headers.get(headerName);
        if (values.size() == 1) {
            return values.get(0);
        }
        StringBuilder valuesSB = new StringBuilder();
        for (String s : values) {
            valuesSB.append(s);
            valuesSB.append(',');
        }
        return valuesSB.toString();
    }
}
