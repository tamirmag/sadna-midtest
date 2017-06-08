package GUI;

import ServerClient.Http_Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Nofar on 04/06/2017.
 */
public class game {
    private JButton betCheckButton;
    private JButton checkButton;
    private JButton foldButton;
    private JButton allInButton;
    private JButton exitGameButton;
    public JPanel gameView;

    public game(int gamenum, int minPlayers, int maxPlayers,JFrame thisFrame,JFrame prevFrame) {
        System.out.println(Http_Client.getFrameFromGame(gamenum+"").toString());
        exitGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    thisFrame.setVisible(false);
                    prevFrame.setVisible(true);
                    /*
                    int currentNumOfPlayers=Http_Client.getPlayersNum(gamenum+"");
                    if(currentNumOfPlayers >= minPlayers&& currentNumOfPlayers <= maxPlayers){

                    }*/
                    //Http_Client.terminateGame(gamenum);

                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }
        });
    }
}
