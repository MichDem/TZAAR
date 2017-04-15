package game;

import java.util.ArrayList;

/**
 * Represents board, including fields, and so on
 * Created by miku on 11.04.17.
 */
public class Board {

    private Field[] fields;
    private static Board instance;

    class Field{
        private int x, y;   //Position
        private static final int MAXTAB = 6;
        private int number;
        private ArrayList<Field> neighbours;
        private int[][] moveDirection;      //TODO zrobić jako stałą tablicę
        private Field(int numberl, int x, int y){
            number = numberl;
            this.x = x;
            this.y = y;
            moveDirection = new int[6][2];
            neighbours = new ArrayList<>(6);
        }
        private Field(int number){
            this.number = number;
            neighbours = new ArrayList<>(6);
        }

        private void addNeighbour(Field other){
            if(other==null)
                return;
            this.neighbours.add(other);
            other.neighbours.add(this);
        }

        private void addField(Field field){
            for(int i=0;i<MAXTAB;i++){
                if(neighbours.get(i)==null){
                    neighbours.set(i,field);
                    return;
                }
            }
        }
        private void setMoveDirection(){
            moveDirection[0][0] = -1;
            moveDirection[0][1] = 1;
            moveDirection[1][0] = 1;
            moveDirection[1][1] = 1;
            moveDirection[2][0] = 2;
            moveDirection[2][1] = 0;
            moveDirection[3][0] = 1;
            moveDirection[3][1] = -1;
            moveDirection[4][0] = -1;
            moveDirection[4][1] = -1;
            moveDirection[5][0] = -2;
            moveDirection[5][1] = 0;
        }

    }

    private Board(){
        //Init fields
        fields = new Field[60];
        for(int i=0;i<fields.length;i++){
            fields[i] = new Field(i);
        }
        initFields();
    }

    /**
     * @param piece
     */
    public void place(Piece piece) throws IllegalMoveException {
        throw new IllegalMoveException("");
    }

    private void initFields(){
        Field[][] raws = new Field[9][];
        for(int i=0;i<5;i++){
            raws[i] = new Field[5 + i];
        }
        for(int i=5;i<raws.length;i++){
            raws[i] = new Field[13-i];
        }

        int current = 0;
        for(int i=0;i<raws.length;i++){
            for(int j=0;j<raws[i].length;j++){
                if(i!=4 || j!=4){
                    raws[i][j] = fields[current++];
                }else{
                    raws[i][j] = null;
                }
            }
        }
        //And now for magic...
        //Adding "vertical" relations
        for(int i=0;i<raws.length;i++){
            for(int j=0;j<raws[i].length-1;j++){
                if(raws[i][j]!=null){
                    raws[i][j].addNeighbour(raws[i][j+1]);
                }
            }
        }
        //Now for askew relations - left side:
        for(int i=0;i<5;i++){
            for(int j=0;j<raws[i].length;j++){
                //There always is piece [i+1][j] and [i+1][j+1], no risk of IndexOutOfBounds
                raws[i][j].addNeighbour(raws[i+1][j]);
                raws[i][j].addNeighbour(raws[i+1][j+1]);
            }
        }
        //and right side:
        for(int i=raws.length-1;i>5;i--){
            for(int j=0;j<raws[i].length;j++){
                //This time, there'll always be [i-1][j] and [i-1][j+1] piece
                raws[i][j].addNeighbour(raws[i-1][j]);
                raws[i][j].addNeighbour(raws[i-1][j+1]);
            }
        }
        //And that's IT!
    }

    static{
        instance = new Board();
    }

}
