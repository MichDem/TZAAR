package gui;

import game.Color;
import game.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * A fancy class for buttons that pretend that they are pieces
 * Made much more sence to seperate it after all
 * Created by miku on 4/18/17.
 */
class PieceButton extends JButton {

    /**
     * icons[X][Y][Z]
     * X: 0, 1 - Black, White
     * Y: 0, 1 - Normal, Selected
     * Z: 0, 1, 2 - Tott, Tzaar, Tzarr
     */

    private static ImageIcon[][][] icons = null;
    private static final String RESOURCE_PATH = "/";//"/resources/";

    private static final Font unselected = new Font("Arial", Font.PLAIN, 12);
            //selected = new Font("Arial", Font.BOLD,24);

    private void initIcons() throws IOException {
        icons = new ImageIcon[2][2][3];



        icons[0][0][0] = new ImageIcon(ImageIO.read((getClass().getResourceAsStream(RESOURCE_PATH + "Pieces/bTott.png"))));
        icons[0][0][1] = new ImageIcon(ImageIO.read((getClass().getResourceAsStream(RESOURCE_PATH + "Pieces/bTzaar.png"))));
        icons[0][0][2] = new ImageIcon(ImageIO.read((getClass().getResourceAsStream(RESOURCE_PATH + "Pieces/bTzarr.png"))));

        icons[0][1][0] = new ImageIcon(ImageIO.read((getClass().getResourceAsStream(RESOURCE_PATH + "Pieces/bhTott.png"))));
        icons[0][1][1] = new ImageIcon(ImageIO.read((getClass().getResourceAsStream(RESOURCE_PATH + "Pieces/bhTzaar.png"))));
        icons[0][1][2] = new ImageIcon(ImageIO.read((getClass().getResourceAsStream(RESOURCE_PATH + "Pieces/bhTzarr.png"))));

        icons[1][0][0] = new ImageIcon(ImageIO.read((getClass().getResourceAsStream(RESOURCE_PATH + "Pieces/wTott.png"))));
        icons[1][0][1] = new ImageIcon(ImageIO.read((getClass().getResourceAsStream(RESOURCE_PATH + "Pieces/wTzaar.png"))));
        icons[1][0][2] = new ImageIcon(ImageIO.read((getClass().getResourceAsStream(RESOURCE_PATH + "Pieces/wTzarr.png"))));

        icons[1][1][0] = new ImageIcon(ImageIO.read((getClass().getResourceAsStream(RESOURCE_PATH + "Pieces/whTott.png"))));
        icons[1][1][1] = new ImageIcon(ImageIO.read((getClass().getResourceAsStream(RESOURCE_PATH + "Pieces/whTzaar.png"))));
        icons[1][1][2] = new ImageIcon(ImageIO.read((getClass().getResourceAsStream(RESOURCE_PATH + "Pieces/whTzarr.png"))));
    }

    private int color, type;

    PieceButton() throws IOException {
        if(icons==null){
            initIcons();
        }
        setBorderPainted(false);
        setContentAreaFilled(false);
        setVerticalTextPosition(SwingConstants.CENTER);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setFont(unselected);
    }

    void setPiece(Piece piece){
        if(piece.getColor()== Color.BLACK){
            color = 0;
            this.setForeground(java.awt.Color.WHITE);
        }
        else{
            color = 1;
            this.setForeground(java.awt.Color.BLACK);
        }
        if(piece.getType()== Piece.Type.TOTT)
            type = 0;
        else if(piece.getType()== Piece.Type.TZAAR)
            type = 1;
        else
            type = 2;
        //this.setIcon(icons[color][0][type]);
        this.setText(piece.getPower()+"");
        setUnpressed();
    }

    void setUnpressed(){
        this.setIcon(icons[color][0][type]);
    }

    void setPressed(){
        this.setIcon(icons[color][1][type]);
    }

}
