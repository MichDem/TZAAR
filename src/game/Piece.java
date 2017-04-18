package game;

/**
 * Class used to represent Piece
 * Created by miku on 11.04.17.
 */
public class Piece {

    //private Board.Field position;

    /*public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }*/

    public Color getColor() {
        return color;
    }

    public enum Type{
        TZAAR, TZARR, TOTT
    }

    private Type type;
    private Color color;
    //private boolean free;
    //private List<Piece> addedPieces;
    private int power;

    Piece(Type type, Color color){
        this.type = type;
        this.color = color;
        power = 1;
        //free = true;
        //addedPieces = new ArrayList<>(0);
    }

    public void add(Piece another){
        power += another.power;
        //addedPieces.add(another);
        //this.power += another.power;
        another.power = 0;
        //another.free = false;
    }

    void hit(Piece another){
        another.power = 0;
        //another.free = false;
    }

    public int getPower(){
        return power;
    }

    public Type getType(){
        return type;
    }

}
