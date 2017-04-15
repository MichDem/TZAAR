package game;

import java.util.ArrayList;
import java.util.List;

/**
 * One of two players. One is black, other is white
 * Created by miku on 4/15/17.
 */
public class Player {
    private static final int TZAAR_NUM = 6,
            TZARR_NUM = 9,
            TOTT_NUM = 15,
            PIECES = TZAAR_NUM + TZARR_NUM + TOTT_NUM;
    private List<Piece> pieces;
    private Color color;

    public Player(Color color){
        this.color = color;
        pieces = new ArrayList<>(PIECES);
        for(int i=0;i<TZAAR_NUM;i++){
            pieces.add(new Piece(Piece.Type.TZAAR,color));
        }
        for(int i=0;i<TZARR_NUM;i++){
            pieces.add(new Piece(Piece.Type.TZARR,color));
        }
        for(int i=0;i<TOTT_NUM;i++){
            pieces.add(new Piece(Piece.Type.TOTT,color));
        }
    }

    private boolean check(Piece.Type type){
        for(Piece piece : pieces){
            if(piece.getType() == type)
                return true;
        }
        return false;
    }

    public boolean hasWon(){
        return(!check(Piece.Type.TOTT) || check(Piece.Type.TZAAR) || check(Piece.Type.TZARR));
    }

}
