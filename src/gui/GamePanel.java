package gui;

import functionalities.GameController;
import game.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Draws board and pieces
 * Created by miku on 4/17/17.
 */
public class GamePanel extends JPanel {
    private static ImageIcon board;

    private static final int HORIZONTAL_DISTANCE=97, VERTICAL_DISTANCE=109; //org 91
    private static final int LEFT_X=122, LEFT_Y=290;
    private static final int BOARD_SIZE = 1024, PIECE_SIZE=64;

    private static ArrayList<PieceButton> buttons;
    private GameController gameController;

    private void fillButtons() throws IOException {
        //Starting from left top, going down.
        buttons = new ArrayList<>();
        PieceButton button;
        for(int i=0;i<5;i++){
            int initX = LEFT_X + i *HORIZONTAL_DISTANCE;
            int initY = LEFT_Y - i * VERTICAL_DISTANCE / 2;
            for(int j=0;j<5+i;j++){
                if(i==4 && j==4){
                    initY += VERTICAL_DISTANCE;
                    continue;
                }
                button = new PieceButton();
                this.add(button);
                buttons.add(button);
                button.setBounds(initX - PIECE_SIZE/2, initY -PIECE_SIZE/2, PIECE_SIZE, PIECE_SIZE);
                initY += VERTICAL_DISTANCE;
            }
        }

        for(int i=0;i<4;i++){
            int initX = LEFT_X + (5+i) * HORIZONTAL_DISTANCE;
            int initY = LEFT_Y + (i-3) * VERTICAL_DISTANCE / 2;
            for(int j=0;j<8-i;j++){
                button = new PieceButton();
                this.add(button);
                buttons.add(button);
                button.setBounds(initX - PIECE_SIZE/2, initY -PIECE_SIZE/2, PIECE_SIZE, PIECE_SIZE);
                initY += VERTICAL_DISTANCE;
            }
        }
        this.repaint();
    }

    private void assignButtons(){
        int index = 0;
        for(JButton button : buttons){
            button.setActionCommand(GameController.ACTIONS.FIELD.name() + " " + index);
            button.addActionListener(gameController);
            setButton(index);
            index++;
        }
    }

    private static void setButton(int index){
        Piece piece = Board.getPiece(index);
        buttons.get(index).setPiece(piece);
    }

    public void dimField(int index){
        buttons.get(index).setVisible(false);
    }

    public void selectField(int index){
        /*if(buttons.get(index).getForeground().equals(java.awt.Color.WHITE))
            buttons.get(index).setForeground(java.awt.Color.RED);
        else
            buttons.get(index).setForeground(new java.awt.Color(0x008888));
        buttons.get(index).setFont(selected);*/
        buttons.get(index).setPressed();
    }

    public void unselectField(int index){
        //if(buttons.get(index).getForeground().equals(java.awt.Color.RED)){
        /*if(Board.getPiece(index).getColor()==Color.BLACK){
            buttons.get(index).setForeground(java.awt.Color.WHITE);
        }else{
            buttons.get(index).setForeground(java.awt.Color.BLACK);
        }
        buttons.get(index).setFont(unselected);*/
        buttons.get(index).setUnpressed();
    }

    private void prepareIcons() throws IOException {
        String path = "/"; //resources/";
        board = new ImageIcon(ImageIO.read(getClass().getResourceAsStream((path + "Board.png"))));
    }

    GamePanel() throws IOException {
        this.setLayout(null);
        prepareIcons();
        JLabel gameBoard = new JLabel(board);
        gameController = new GameController();
        gameController.setBoardPanel(this);

        fillButtons();
        assignButtons();
        this.add(gameBoard);
        gameBoard.setBounds(0,0,BOARD_SIZE,BOARD_SIZE);

        //this.repaint();
    }

    public void ups(String message) {
        JOptionPane.showMessageDialog(this,message);
    }

    public static void update(int number) {
        setButton(number);
        buttons.get(number).setText(""+(Board.getPiece(number).getPower()));
    }
}
