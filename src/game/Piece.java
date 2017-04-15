package game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miku on 11.04.17.
 */
public class Piece {

    private Board.Field position;   //FIXME to remove

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public enum Type{
        TZAAR, TZARR, TOTT
    }

    private Type type;
    private Color color;
    private boolean free;
    private List<Piece> addedPieces;
    private int power;

    public Piece(Type type, Color color){
        this.type = type;
        this.color = color;
        power = 1;
        free = true;
        addedPieces = new ArrayList<>(0);
    }

    public void add(Piece another){
        power += another.power;
        addedPieces.add(another);
        another.power = 0;
        another.free = false;
    }

    public void hit(Piece another){
        addedPieces.add(another);
        another.power = 0;
        another.free = false;
    }

    public Type getType(){
        return type;
    }

    //TODO metoda generateMove
    //TODO metoda checkMove

}
