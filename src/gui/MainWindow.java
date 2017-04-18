package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Represents main window.
 * Created by miku on 4/17/17.
 */
public class MainWindow extends JFrame{

    private static GamePanel gamePanel;
    private static MainWindow instance;



    private static final int WIDTH = 1024+200, HEIGHT = 1024;


    private MainWindow(){

        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(WIDTH,HEIGHT));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //super.windowClosing(e);
                System.out.println("Closing");
                //TODO save progress and so on
                System.exit(0);
            }
        });
        this.setTitle("TZAAR by MiKu & MaGr");

        //mainMenu = new MainMenu();
        MainMenu mainMenu = MainMenu.getInstance();
        this.add(mainMenu,BorderLayout.WEST);
        try {
            gamePanel = new GamePanel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.add(gamePanel);
    }

    static{
        instance = new MainWindow();
    }

    public static MainWindow getInstance(){
        return instance;
    }

}
