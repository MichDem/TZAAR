package game;

import functionalities.GameController;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents board, including fields, and so on
 * Created by miku on 11.04.17.
 */
public class Board {

    private static Field[] fields;
    private static Board instance;
    private static Player blackRabbit, whiteRabbit; //Yeah... maybe someone would get that reference.

    /**
     * To avoid confusion:
     *  | - vertical
     *  \ - left
     *  / - right
     */
    private static ArrayList<ArrayList<Field>> verticalAxis, leftAxis, rightAxis;   //Because rules of the game.

    public static Player getBlackRabbit(){
        return blackRabbit;
    }

    public static Player getWhiteRabbit(){
        return whiteRabbit;
    }

    public static boolean checkMove(Color currentTure, boolean anyMove) {
        ArrayList<ArrayList<Field>> axises = new ArrayList<>();
        axises.addAll(verticalAxis);
        axises.addAll(leftAxis);
        axises.addAll(rightAxis);

        for(ArrayList<Field> axis : axises){
            for(int i=0;i<axis.size();i++){
                Piece current = axis.get(i).piece;
                if(current != null && current.getColor()==currentTure){
                    //check left first. Always left first
                    int left=i-1;
                    while(left>=0 && axis.get(left).piece==null)   //Get first piece left of current one
                        left--;
                    if(left>=0 && //If there is nothing on left will equal to -1
                            axis.get(left).piece!=null && (//Just for safety
                                anyMove || (//If anyMove then we don't care about color
                                    axis.get(left).piece.getColor()!=currentTure && axis.get(left).piece.getPower()<=current.getPower() //Othervise we check if that piece has opposite color AND it's power is not greater then current
                                )))
                        return true;

                    //Now for the establi... I mean - right
                    int right=i+1;
                    while(right<axis.size() && axis.get(right).piece==null)
                        right++;
                    if(right<axis.size() &&
                            axis.get(right).piece!=null && (
                                    anyMove || (
                                        axis.get(right).piece.getColor()!=currentTure &&
                                                axis.get(right).piece.getPower()<=current.getPower()
                                    )))
                        return true;


                }
            }
        }

        return false;
    }

    class Field{
        private static final int MAXTAB = 6;
        private ArrayList<Field> neighbours;
        //private int[][] moveDirection;      //zrobić jako stałą tablicę
        private Piece piece;
        private int number;
        private Field(int number){
            this.number = number;
            neighbours = new ArrayList<>(6);
        }

        /*private void addNeighbour(Field other){
            if(other==null)
                return;
            this.neighbours.add(other);
            other.neighbours.add(this);
        }*/

        /*private void addField(Field field){
            for(int i=0;i<MAXTAB;i++){
                if(neighbours.get(i)==null){
                    neighbours.set(i,field);
                    return;
                }
            }
        }*/
        /*private void setMoveDirection(){
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
        }*/

    }

    private Board(){
        //Init fields
        fields = new Field[60];
        for(int i=0;i<fields.length;i++){
            fields[i] = new Field(i);
        }
        blackRabbit = new Player(Color.BLACK);
        whiteRabbit = new Player(Color.WHITE);
        initAxis();
        //System.out.println("Board inited");
        placePieces();

    }

    private static void placePieces(){
        ArrayList<Piece> pieces = new ArrayList<>();
        if(blackRabbit==null)
            System.out.println("Is null");
        pieces.addAll(blackRabbit.getPieces());
        pieces.addAll(whiteRabbit.getPieces());
        Collections.shuffle(pieces);
        for(int i=0;i<fields.length;i++){
            fields[i].piece = pieces.get(i);
        }
    }

    public enum MoveType{
        CAPTURE, BUFF, ILLEGAL
    }

    public static MoveType checkType(int source, int destination){
        if(fields[destination].piece==null)
            //throw new IllegalMoveException("Moved to empty field");
            return MoveType.ILLEGAL;
        if(fields[source].piece==null)
            //throw new IllegalMoveException("No piece to move");
            return MoveType.ILLEGAL;
        if(!validateMove(source,destination))
            //throw new IllegalMoveException("Illegal move");
            return MoveType.ILLEGAL;
        if(fields[source].piece.getColor()==fields[destination].piece.getColor())
            return MoveType.BUFF;
        return MoveType.CAPTURE;
    }

    public static void move(int source, int destination) throws IllegalMoveException {


        Piece old = fields[destination].piece;
        if(fields[source].piece.getColor()==fields[destination].piece.getColor()){
            //Same color
            fields[destination].piece = fields[source].piece;
            fields[source].piece = null;
            fields[destination].piece.add(old);
        }else{
            if(fields[destination].piece.getPower()>fields[source].piece.getPower()){
                throw new IllegalMoveException("Could not hit piece");
            }
            fields[destination].piece = fields[source].piece;
            fields[source].piece = null;
            fields[destination].piece.hit(old);
        }
        if(old.getColor()==Color.BLACK)
            blackRabbit.remove(old);
        else
            whiteRabbit.remove(old);
    }

    private static boolean validateMove(int source, int destination) {
        //First we join all axises, to avoid redundancy
        ArrayList<ArrayList<Field>> allAxises = new ArrayList<>();
        allAxises.addAll(verticalAxis);
        allAxises.addAll(leftAxis);
        allAxises.addAll(rightAxis);

        for(ArrayList<Field> fields : allAxises){
            boolean foundFirst = false;
            for(int i=0;i<fields.size();i++){
                if(!foundFirst && (fields.get(i).number==source || fields.get(i).number==destination)){
                    foundFirst = true;  //Found first piece
                }else if(foundFirst && (fields.get(i).number==source || fields.get(i).number==destination)){
                    return true;    //Found second piece && there were no pieces in between
                }else if(foundFirst && fields.get(i).piece!=null){
                    foundFirst = false;     //Found first piece, but there was another piece next to it that was not the one we've been looking for
                }
            }
        }
        return false;
    }

    public static Piece getPiece(int index){
        return fields[index].piece;
    }

    private void initAxis(){
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

        verticalAxis = new ArrayList<>();
        leftAxis = new ArrayList<>();
        rightAxis = new ArrayList<>();

        ArrayList<Field> tmp;
        for(int i=0;i<raws.length;i++){
            tmp = new ArrayList<>();
            for(int j=0;j<raws[i].length;j++){
                tmp.add(raws[i][j]);
            }
            verticalAxis.add(tmp);
        }

        //Now left
        //It must be sorted, but in whatever direction
        //Sorting is achieved by adding in correct order
        for(int i=0;i<raws.length;i++){     //AKA 9 times
            leftAxis.add(new ArrayList<>());
        }
        for(int i=0;i<5;i++){
            for(int j=0;j<raws[i].length;j++){
                leftAxis.get(j).add(raws[i][j]);
            }
        }
        for(int i=5;i<raws.length;i++){
            for(int j=0;j<raws[i].length;j++){
                leftAxis.get(i-4+j).add(raws[i][j]);
            }
        }

        //And now the same thing but for right axis
        for(int i=0;i<raws.length;i++){
            rightAxis.add(new ArrayList<>());
        }
        for(int i=raws.length-1;i>=4;i--){
            for(int j=0;j<raws[i].length;j++){
                rightAxis.get(j).add(raws[i][j]);
            }
        }
        for(int i=3;i>=0;i--){
            for(int j=0;j<raws[i].length;j++){
                rightAxis.get(4-i+j).add(raws[i][j]);
            }
        }
        removeNullsFromAxe(verticalAxis);
        removeNullsFromAxe(leftAxis);
        removeNullsFromAxe(rightAxis);
    }

    private void removeNullsFromAxe(ArrayList<ArrayList<Field>> original){
        ArrayList<ArrayList<Field>> copy = new ArrayList<>();
        ArrayList<Field> row;
        copy.addAll(original);
        original.clear();
        for(ArrayList<Field> fields : copy){
            row = new ArrayList<>();
            for(Field field : fields){
                if(field!=null)
                    row.add(field);
                else{
                    original.add(row);
                    row = new ArrayList<>();
                }
            }
            original.add(row);
            row = new ArrayList<>();
        }
    }

    static{
        instance = new Board();
    }

}
