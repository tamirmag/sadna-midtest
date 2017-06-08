package GUI;

import ServerClient.Http_Client;
import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Nofar on 20/05/2017.
 */
public class homePage {
    private JButton exitButton;
    private JButton createNewGameButton;
    private JButton editProfileButton;
    private JButton joinExistingGameButton;
    private JButton spectateGameButton;
    public JPanel homePageView;

    //////////////////////////
    public ButtonGroup gameGroup;
    private void createGameList(JFrame frm)
    {
        frm.add(new JButton("AAA"));
        frm.add(new JButton("BBB"));
    }

    public homePage(String username,JFrame thisFrame) {

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Http_Client.logout(username);
                    System.exit(0);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        spectateGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("chooseGame");
                createGameList(frame);
                try {
                    ArrayList<Integer> gamesNum= Http_Client.findSpectatableGames(username);
                    if(gamesNum.size()>0) {
                        String[] gamesL = {"one", "two"};
                        String[] games = new String[gamesNum.size()];
                        String temp = "";
                        for (int i = 0; i < gamesNum.size(); i++) {
                            games[i] = "" + gamesNum.get(i);
                        }
                        frame.setContentPane(new chooseGame(username, games,frame,thisFrame,"spectateGame").chooseGameView);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.pack();
                        frame.setVisible(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(thisFrame,"no games to spectate!");
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        joinExistingGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("chooseGame");
                //ArrayList<Integer> gamesNum=null;
                try {
                    ArrayList<Integer> gamesNum= Http_Client.findActiveGamesByLeague(username);
                    if(gamesNum.size()>0){
                        String[] gamesL= {"one","two"};
                        String[] games= new String[gamesNum.size()];
                        String temp="";
                        for (int i=0; i<gamesNum.size();i++){
                            games[i]=""+ gamesNum.get(i);
                        }
                        frame.setContentPane(new chooseGame(username,games,frame,thisFrame,"joinExistingGame").chooseGameView);
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.pack();
                        frame.setVisible(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(thisFrame,"no existing games!");
                    }
                } catch (Exception e1) {

                    e1.printStackTrace();
                }
            }
        });
        editProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("editProfile");
                frame.setContentPane(new editProfile(username,frame).editProfileView);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);

            }
        });
        createNewGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("createNewGame");
                frame.setContentPane(new createNewGame(username,frame,thisFrame).createNewGameView);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
