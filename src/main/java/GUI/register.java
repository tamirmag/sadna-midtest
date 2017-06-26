package GUI;

import ServerClient.Http_Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Nofar on 20/05/2017.
 */
public class register {
    private JButton backButton;
    private JTextField user;
    private JTextField email;
    private JTextField password;
    private JButton registerButton;
    public JPanel registerView;

    static public JFrame registerFrame;

    public register() {
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("new user registered:   userName: " + user.getText()+ ",  password: "+ password.getText() + "  e-mail:" + email.getText());
                //Http_Client c = new Http_Client();
                //System.out.println(user.getText() instanceof  String);
                try {
                    Http_Client.register(user.getText(), password.getText(), email.getText(), 0);
                    System.out.println("new user registered:   userName: " + user.getText()+ ",  password: "+ password.getText() + "  e-mail:" + email.getText());

                    //c.register(user.getText(), password.getText(), email.getText(), 0);
                }
                catch(Exception es) {

                }
                JFrame homePageFrame = new JFrame("homePage");
                homePageFrame.setContentPane(new homePage(user.getText(),homePageFrame).homePageView);
                homePageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                homePageFrame.pack();
                homePageFrame.setVisible(true);
                registerFrame.setVisible(false);
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                JFrame frame = new JFrame("login");
//                frame.setContentPane(new login().loginView);
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                frame.pack();
                login.loginFrame.setVisible(true);
                //frame.setVisible(true);
                registerFrame.setVisible(false);
            }
        });
    }
}


