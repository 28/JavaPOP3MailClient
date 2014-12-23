package javapop3mailclient.gui.models;

import java.util.List;
import javapop3mailclient.domain.Message;
import javax.swing.table.AbstractTableModel;

/**
 * Table model for the messages table.
 *
 * @author Dejan Josifovic
 */
public class MessagesTableModel extends AbstractTableModel {

    /**
     * Messages list.
     */
    private List<Message> messages;

    /**
     * Creates a new Messages table model.
     *
     * @param messages list.
     */
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
        return "N/A";
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

    /**
     * Removes the message with the corresponding index from the messages list
     * and updates the table.
     *
     * @param i index of the message that needs to be deleted.
     */
    public void deleteMessage(int i) {
        messages.remove(i);
        fireTableDataChanged();
    }

    /**
     * Getter of the messages field.
     *
     * @return messages list.
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * Setter of the messages field. When messages are set applies the data to
     * the table.
     *
     * @param messages list.
     */
    public void setMessages(List<Message> messages) {
        this.messages = messages;
        fireTableDataChanged();
    }
}
