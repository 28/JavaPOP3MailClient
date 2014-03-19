package javapop3mailclient.gui.models;

import java.util.List;
import javapop3mailclient.domain.Message;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Dejan Josifovic
 */
public class MessagesTableModel extends AbstractTableModel {

    private List<Message> messages;

    public MessagesTableModel(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public int getRowCount() {
        if (messages == null) {
            return 0;
        }
        return messages.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        if (messages != null) {
            Message m = messages.get(i);
            switch (i1) {
                case 0:
                    return m.getHeader("From");
                case 1:
                    return m.getHeader("Date");
                case 2:
                    return m.getHeader("Subject");
                default:
                    return "N/A";
            }
        }
        return "";
    }

    @Override
    public String getColumnName(int i) {
        switch (i) {
            case 0:
                return "From: ";
            case 1:
                return "Date: ";
            case 2:
                return "Subject: ";
            default:
                return "N/A";
        }
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        fireTableDataChanged();
    }
}
