import gui.MainWindow;

import java.awt.*;

/**
 * Starts up a progam
 * And... that's it
 * Created by miku on 11.04.17.
 */
public class Main {

    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow.getInstance().setVisible(true);
            }
        });
    }

}
