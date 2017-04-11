package game;

/**
 * Created by miku on 11.04.17.
 */
public class Board {

    private Field[] fields;
    private static Board instance;

    private class Field{
        private int number;
        private Field[] neighbours;
        private Field(int numberl){
            number = numberl;
        }

    }

    private Board(){
        //Init fields
        fields = new Field[61];
        for(int i=0;i<fields.length;i++){
            fields[i] = new Field(i);
        }

        Field[][] raws = new Field[9][];
        for(int i=0;i<5;i++){
            raws[i] = new Field[5+i];
        }
        for(int i=5;i<raws.length;i++){
            raws[i] = new Field[13-i];       //x-8=5 -> x=13
        }

        int current = 0;
        //Assosiate neighbours array
        for(int i=0;i<raws.length;i++){
            for(int j=0;j<raws[i].length;j++){
                raws[i][j] = fields[current++];
            }
        }


    }

    static{
        instance = new Board();
    }

}
