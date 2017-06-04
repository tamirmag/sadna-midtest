package GUI;

import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
//    private JPanel gameList;
    private void createGameList(JFrame frm)
    {
        frm.add(new JButton("AAA"));
        frm.add(new JButton("BBB"));

//        gameGroup = new ButtonGroup();
//        for(int i = 0; i<10 ; i++) {
//            finagameListl JRadioButton button1 = new JRadioButton("a-"+i);
////            .add(button1);
//            gameGroup.add(button1);
//        }
    }
    /////////////////////////////

    public homePage() {
        spectateGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("chooseGame");
                createGameList(frame);
//                createGameList(frame.getGlassPane((new chooseGame().showGameList)) );

                frame.setContentPane(new chooseGame().chooseGameView);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        joinExistingGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("chooseGame");
                frame.setContentPane(new chooseGame().chooseGameView);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        editProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("editProfile");
                frame.setContentPane(new editProfile().editProfileView);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);

            }
        });
        createNewGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("createNewGame");
                frame.setContentPane(new createNewGame().createNewGameView);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
