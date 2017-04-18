package functionalities;

import game.Board;
import game.Color;
import game.IllegalMoveException;
import gui.GamePanel;
import gui.MainMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlls moves on the noard
 * Created by miku on 4/17/17.
 */
public class GameController implements ActionListener{

    private boolean isSelected = false;
    private int indexSelected;

    private Color currentTure;

    private enum nextMoveType{
        CAPTURE, ANY, FIRST
    }

    private nextMoveType nextMove;

    private void switchPlayer(){
        if(currentTure == Color.BLACK){
            currentTure = Color.WHITE;
            MainMenu.setCurrentTure("White");
        }else{
            currentTure = Color.BLACK;
            MainMenu.setCurrentTure("Black");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.startsWith(ACTIONS.FIELD.name())){
            command = command.split(" ")[1];    //Format of this command is "FIELD <num>" - this line allows us to get num
            int number = Integer.parseInt(command);
            if(!isSelected){
                if(Board.getPiece(number).getColor()!=currentTure){
                    boardPanel.ups("Now it's " + currentTure.name().toLowerCase() + "'s ture");
                    return;
                }
                indexSelected = number;
                boardPanel.selectField(number);
                isSelected = true;
            }else{
                if(indexSelected==number){
                    boardPanel.unselectField(indexSelected);
                    isSelected = false;
                }else{
                    boardPanel.unselectField(indexSelected);
                    isSelected = false;
                    //here is the actuall move being done
                    try {
                        Board.MoveType type;
                        if((type=Board.checkType(indexSelected,number))== Board.MoveType.ILLEGAL){
                            boardPanel.ups("Illegal move (From GameController)");
                            return;
                        }else if((nextMove == nextMoveType.CAPTURE || nextMove == nextMoveType.FIRST) && type!= Board.MoveType.CAPTURE){
                            boardPanel.ups("Move must be capture!");
                            return;
                        }
                        Board.move(indexSelected,number);
                        GamePanel.update(number);
                        boardPanel.dimField(indexSelected);
                        switchTure();
                    } catch (IllegalMoveException e1) {
                        boardPanel.ups(e1.getMessage());
                        //Only when could not hit a piece
                    }

                }
            }
        }

        MainMenu.updateStats(Board.getBlackRabbit());
        MainMenu.updateStats(Board.getWhiteRabbit());       //Poor white rabbit - always last
        if(Board.getBlackRabbit().hasLost())
            boardPanel.ups("Black player has lost");
        else if(Board.getWhiteRabbit().hasLost())
            boardPanel.ups("White player has lost");


    }

    private void switchTure() {
        if(nextMove.equals(nextMoveType.CAPTURE) || nextMove.equals(nextMoveType.FIRST)){
            if(nextMove.equals(nextMoveType.FIRST)){
                nextMove = nextMoveType.CAPTURE;
                currentTure = Color.BLACK;
                MainMenu.setCurrentTure("Black");
            }else{
                nextMove = nextMoveType.ANY;
                MainMenu.setCurrentMove("Any move");
            }
        }else{
            nextMove = nextMoveType.CAPTURE;
            MainMenu.setCurrentMove("Capture");
            switchPlayer();
        }
    }

    private GamePanel boardPanel;

    public void setBoardPanel(GamePanel boardPanel) {
        this.boardPanel = boardPanel;
    }

    public enum ACTIONS{
        FIELD
    }

    public GameController(){
        currentTure = Color.WHITE;
        nextMove = nextMoveType.FIRST;
    }

}
