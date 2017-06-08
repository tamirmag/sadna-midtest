package GUI;

import ServerClient.Http_Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Nofar on 20/05/2017.
 */
public class editProfile {
    public JPanel editProfileView;
    private JButton saveChangesButton;
    private JTextField newEmail;
    private JTextField newPassword;

    private JFrame frame;

    public editProfile(String username,JFrame frame) {
        saveChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Http_Client.editProfile(username,newPassword.getText(),newEmail.getText());
                    frame.setVisible(false);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
