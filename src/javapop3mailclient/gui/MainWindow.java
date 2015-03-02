package javapop3mailclient.gui;

import java.util.List;
import javapop3mailclient.controller.Controller;
import javapop3mailclient.domain.Message;
import javapop3mailclient.gui.models.MessagesTableModel;
import javax.swing.JOptionPane;

/**
 * This window displays the mailbox of the user.
 *
 * @author Dejan Josifovic
 */
public class MainWindow extends javax.swing.JFrame {

    /**
     * Creates new form MainWindow.
     */
    public MainWindow() {
        initComponents();
        setTexts();
        fillMessagesTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        numberOfMessagesLabel = new javax.swing.JLabel();
        numberOfNewMessagesTextField = new javax.swing.JTextField();
        messagesListLabel = new javax.swing.JLabel();
        messagesTableScrollPane = new javax.swing.JScrollPane();
        messagesTable = new javax.swing.JTable();
        messageBodyTextAreaScrollPane = new javax.swing.JScrollPane();
        messageBodyTextArea = new javax.swing.JTextArea();
        messageBodyLabel = new javax.swing.JLabel();
        checkMailButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        mainWindowMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        numberOfMessagesLabel.setText("Number of messages: ");

        numberOfNewMessagesTextField.setEditable(false);

        messagesListLabel.setText("Messages list: ");

        messagesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        messagesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                messagesTableMouseClicked(evt);
            }
        });
        messagesTableScrollPane.setViewportView(messagesTable);

        messageBodyTextArea.setEditable(false);
        messageBodyTextArea.setColumns(20);
        messageBodyTextArea.setRows(5);
        messageBodyTextAreaScrollPane.setViewportView(messageBodyTextArea);

        messageBodyLabel.setText("Message body: ");

        checkMailButton.setText("Check mail");
        checkMailButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkMailButtonActionPerformed(evt);
            }
        });

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        fileMenu.setText("File");

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        mainWindowMenuBar.add(fileMenu);

        setJMenuBar(mainWindowMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(messagesTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
            .addComponent(messageBodyTextAreaScrollPane)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(numberOfMessagesLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(numberOfNewMessagesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(deleteButton)
                        .addGap(18, 18, 18)
                        .addComponent(checkMailButton)
                        .addGap(18, 18, 18)
                        .addComponent(exitButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(messagesListLabel)
                        .addGap(0, 353, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(messageBodyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numberOfMessagesLabel)
                    .addComponent(numberOfNewMessagesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkMailButton)
                    .addComponent(exitButton)
                    .addComponent(deleteButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(messagesListLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(messagesTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(messageBodyLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(messageBodyTextAreaScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Handles the exit process when called form the file menu.
     *
     * @param evt action performed event.
     */
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        try {
            Controller.getInstance().exit();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "JavaPOP3MailClient - Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_exitMenuItemActionPerformed

    /**
     * Handles the exit process when exit button is pressed. Also displays the
     * dialog menu asking the user if he wants to exit.
     *
     * @param evt exit button action performed event.
     */
    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        int answer = JOptionPane.showConfirmDialog(this, "Exit?", "JavaPOP3MailClient - Exit", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            try {
                Controller.getInstance().exit();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "JavaPOP3MailClient - Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_exitButtonActionPerformed

    /**
     * Displays the body of the selected message from the table. If more than
     * one message is selected it doesn't display anything.
     *
     * @param evt mouse clicked event.
     */
    private void messagesTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_messagesTableMouseClicked
        if (messagesTable.getSelectedRowCount() == 1) {
            int row = messagesTable.getSelectedRow();
            MessagesTableModel mtm = (MessagesTableModel) messagesTable.getModel();
            Message m = mtm.getMessages().get(row);
            messageBodyTextArea.setText(m.getBody());
        }
    }//GEN-LAST:event_messagesTableMouseClicked

    /**
     * Calls the check mail method when check mail button is clicked.
     *
     * @param evt action performed event.
     */
    private void checkMailButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkMailButtonActionPerformed
        try {
            Controller.getInstance().checkMail();
            setTexts();
            fillMessagesTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "JavaPOP3MailClient - Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_checkMailButtonActionPerformed

    /**
     * Calls the delete message process when pressed.
     *
     * @param evt action performed event.
     */
    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        if (messagesTable.getSelectedRowCount() == 1) {
            try {
                int row = messagesTable.getSelectedRow();
                ((MessagesTableModel) messagesTable.getModel()).deleteMessage(row);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "JavaPOP3MailClient - Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    /**
     * Main method of the window.
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton checkMailButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuBar mainWindowMenuBar;
    private javax.swing.JLabel messageBodyLabel;
    private javax.swing.JTextArea messageBodyTextArea;
    private javax.swing.JScrollPane messageBodyTextAreaScrollPane;
    private javax.swing.JLabel messagesListLabel;
    private javax.swing.JTable messagesTable;
    private javax.swing.JScrollPane messagesTableScrollPane;
    private javax.swing.JLabel numberOfMessagesLabel;
    private javax.swing.JTextField numberOfNewMessagesTextField;
    // End of variables declaration//GEN-END:variables

    /**
     * Sets the window title text and number of messages field text.
     */
    private void setTexts() {
        try {
            this.setTitle("JavaPOP3MailClient - " + Controller.getInstance().getEmail());
            numberOfNewMessagesTextField.setText(Controller.getInstance().getMessageNumber() + "");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "JavaPOP3MailClient - Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Creates the table model and sets the messages list and fills the table.
     */
    private void fillMessagesTable() {
        try {
            List<Message> messages = Controller.getInstance().getMessages();
            MessagesTableModel mtm = new MessagesTableModel(messages);
            messagesTable.setModel(mtm);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "JavaPOP3MailClient - Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
