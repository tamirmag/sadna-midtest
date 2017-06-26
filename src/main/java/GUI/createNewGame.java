package GUI;

import ServerClient.Http_Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * Created by Nofar on 20/05/2017.
 */
public class createNewGame {
    private JButton backButton;
    private JButton createNewGameButton;
    private JComboBox gameType;
    private JTextField buyInPolicy;
    private JTextField chipPolicy;
    private JTextField minimumBet;
    private JComboBox weatherSpectate;
    private JComboBox minimalPlayers;
    private JComboBox maximalPlayers;
    public JPanel createNewGameView;


    public createNewGame(String username,JFrame thisFrame,JFrame prevFrame) {

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisFrame.setVisible(false);
                prevFrame.setVisible(true);
            }
        });

        createNewGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean spectate;
                int gameNum=0;
                if (weatherSpectate.getSelectedItem().toString()=="yes")
                    spectate = true;
                else
                    spectate=false;
                try {
                    gameNum= Http_Client.createGame(username,gameType.getSelectedItem().toString(), Integer.parseInt(buyInPolicy.getText()),
                            Integer.parseInt(chipPolicy.getText()),Integer.parseInt(minimumBet.getText()),
                           Integer.parseInt(minimalPlayers.getSelectedItem().toString()),Integer.parseInt(maximalPlayers.getSelectedItem().toString()),
                            spectate);
                    JFrame frame = new JFrame("game");
                    frame.setContentPane(new game(gameNum,Integer.parseInt(minimalPlayers.getSelectedItem().toString()),Integer.parseInt(maximalPlayers.getSelectedItem().toString()),frame,prevFrame).gameView);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                    thisFrame.setVisible(false);
                    System.out.println(frame.toString());
                    Http_Client.addGameFrame(gameNum+"",frame);
                    System.out.println(Http_Client.getFrameFromGame(gameNum+"").toString());
                    //System.out.println("size:"+Http_Client.findActiveGamesByLeague(username).size());

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
