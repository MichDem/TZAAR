package gui;

import functionalities.GameController;
import game.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Represents menu shown on left side of the board. Shows i.e. stats and current ture info
 * Created by miku on 4/17/17.
 */
public class MainMenu extends JPanel{

    private static final Color bgColor = new Color(0x424f5a);
    private static JLabel currentTure;
    private static JLabel currentMove;
    private static JButton skipButton;
    private static MainMenu instance;

    /**
     * info[X][Y]
     * X: 0, 1 - Black, White
     * Y: 0, 1, 2 - Tott, Tzaar, Tzarr
     */
    //private static JLabel bTott, bTzaar, bTzarr, wTott, wTzaar, wTzarr;
    private static JLabel[][] info;
    private static GameController gameController;
    public static final String VERSION = "0.4";


    private MainMenu() {
        BoxLayout layout = new BoxLayout(this,BoxLayout.PAGE_AXIS);
        this.setLayout(layout);
        this.setBackground(bgColor);
        this.setPreferredSize(new Dimension(200,0));

        JLabel version = new JLabel("Version: " + VERSION);
        version.setForeground(Color.WHITE);

        currentMove = new JLabel("Capture");
        currentMove.setForeground(Color.WHITE);

        currentTure = new JLabel("White");
        currentTure.setForeground(Color.WHITE);

        initSkipButton();

        this.add(version);
        this.add(currentTure);
        this.add(currentMove);
        this.add(Box.createVerticalStrut(100));
        this.add(setupInfoPanel());
        this.add(Box.createVerticalStrut(100));
        this.add(skipButton);
        this.add(Box.createVerticalStrut(4000));
        System.out.println("Done");
    }

    private static void initSkipButton(){
        skipButton = new JButton("Skip move");
        skipButton.setVisible(false);
        skipButton.setActionCommand(GameController.ACTIONS.SKIP.name());
        skipButton.setBackground(bgColor);
        skipButton.setForeground(Color.WHITE);
        //skipButton.addActionListener(gameController);

    }

    static void setGameController(GameController gameControllerl){
        gameController = gameControllerl;
        skipButton.addActionListener(gameController);
    }

    public static void showSkip(){
        skipButton.setVisible(true);
    }

    public static void hideSkip(){
        skipButton.setVisible(false);
    }

    private static JPanel setupInfoPanel(){
        info = new JLabel[2][3];
        for(int i=0;i<2;i++)
            for(int j=0;j<3;j++){
                info[i][j] = new JLabel();
                info[i][j].setForeground(Color.WHITE);
            }

        //JPanel results = new JPanel(new SpringLayout());
        JPanel results = new JPanel(new GridLayout(4,3));
        results.setBackground(bgColor);
        results.setPreferredSize(new Dimension(200,4*12));

        ArrayList<JLabel> labels = new ArrayList<>();


        labels.add(new JLabel("Black"));    //0
        labels.add(new JLabel("White"));    //1
        labels.add(new JLabel("Tott")) ;    //2
        labels.add(new JLabel("Tzaar"));    //3
        labels.add(new JLabel("Tzarr"));    //4

        for(JLabel label : labels)
            label.setForeground(Color.WHITE);

        results.add(new JLabel(""));
        results.add(labels.get(0));
        results.add(labels.get(1));

        for(int i=0;i<3;i++){
            results.add(labels.get(2+i));
            results.add(info[0][i]);
            results.add(info[1][i]);
        }

        /*results.add(labels.get(2));
        results.add(info[0][0]);
        results.add(info[1][0]);

        results.add(new JLabel("Tzaar"));
        results.add(info[0][1]);
        results.add(info[1][1]);

        results.add(new JLabel("Tzarr"));
        results.add(info[0][2]);
        results.add(info[1][2]);*/

        //SpringUtilities.makeCompactGrid(results,4,3,0,0,0,0);

        return results;
    }

    static MainMenu getInstance(){
        return instance;
    }

    static{
        instance = new MainMenu();
    }

    public static void setCurrentTure(String message){
        currentTure.setText(message);
    }

    public static void setCurrentMove(String message){
        currentMove.setText(message);
    }

    public static void updateStats(Player rabbit){
        int color = 1;
        if(rabbit.getColor()== game.Color.BLACK)
            color = 0;
        //int tott = rabbit.getTottsNum() + rabbit.getTzaarsNum() + rabbit.getTzarrsNum();
        info[color][0].setText(""+rabbit.getTottsNum());
        info[color][1].setText(""+rabbit.getTzaarsNum());
        info[color][2].setText(""+rabbit.getTzarrsNum());

    }

}
