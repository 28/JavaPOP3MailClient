package javapop3mailclient.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This is the representation of the e-mail message.
 *
 * @author Dejan Josifovic
 * @version 1.0
 */
public class Message implements Serializable {

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
     * Creates a new Message object.
     *
     * @param headers headers map of the message.
     * @param body text of the message.
     */
    public Message(Map<String, List<String>> headers, String body) {
        this.headers = Collections.unmodifiableMap(headers);
        this.body = body;
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
    
    /**
     * Creates the string representation of the whole message
     * with all headers and body.
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.headers);
        hash = 13 * hash + Objects.hashCode(this.body);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Message other = (Message) obj;
        return this.getHeader("To").equals(other.getHeader("To")) && 
                this.getHeader("From").equals(other.getHeader("From")) &&
                this.getHeader("Subject").equals(other.getHeader("Subject")) &&
                this.getBody().equals(other.getBody());
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
}
