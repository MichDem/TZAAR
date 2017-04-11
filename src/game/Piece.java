package game;

/**
 * Created by miku on 11.04.17.
 */
public class Piece {

    public enum Type{
        TZAAR, TZARRA, TOTT
    }

    public enum Color{
        WHITE,BLACK
    }

    private Type type;
    private Color color;

    public Piece(Type type, Color color){
        this.type = type;
        this.color = color;
    }

}
