using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Tzaar
{
   

     class Field
    {
        public  Position position { get; set; }
        public  const int maxtab= 6;

        string  nr ;

        public Counter counter { get; set; }

        public Field[] ArrayField { get; set; }
        public int [,] MoveDirect { get; set; }

        public Field(Position pos)
        {
            position = pos;
            ArrayField = new Field[maxtab];
            MoveDirect = new int[6,2];
            setDirectMove();
        }
        public Field(Position pos, string nr)
        {
            position = pos;
            ArrayField = new Field[maxtab];
            this.nr = nr;
            MoveDirect = new int[6, 2];
            setDirectMove();
        }
        public void  AddField(Field field)
        {
            for (int i = 0; i < maxtab; i++)
            {
                if(ArrayField[i]==null)
                {
                    ArrayField[i] = field;
                    break;
                   
                }
            }
        }
        void setDirectMove()
        {
            MoveDirect[0, 0] =-1;
            MoveDirect[0, 1] = 1;
            MoveDirect[1, 0] = 1;
            MoveDirect[1, 1] = 1;
            MoveDirect[2, 0] = 2;
            MoveDirect[2, 1] = 0;
            MoveDirect[3, 0] = 1;
            MoveDirect[3, 1] = -1;
            MoveDirect[4, 0] = -1;
            MoveDirect[4, 1] = -1;
            MoveDirect[5, 0] = -2;
            MoveDirect[5, 1] = 0;

        }
        public static void Syntez(Field field1, Field field2)
        {
            Field[]  temp = (Field[])field1.MemberwiseClone();

            for (int i = 0; i < field2.ArrayField.Length; i++)
            {
                for (int j = 0; j < field1.ArrayField.Length; j++)
                {
                   
                }
            }
        }
        public void Sort()
        {
            Field[] temp= new Field[maxtab];
            for (int i = 0; i < maxtab; i++)
            {
                if(ArrayField[i]!= null)
                {
                    for (int j   = 0; j <maxtab ; j++)
                    {
                        if(position.X+ MoveDirect[j,0]==ArrayField[i].position.X && position.Y + MoveDirect[j, 1] == ArrayField[i].position.Y)
                        {
                            temp[j] = ArrayField[i];
                            break;
                        }
                    }
                }
            }
            ArrayField = temp;
        }
        public void Write()
        {
            Console.Write("{0}:{1},{2}", nr, position.X, position.Y);
        }
        public void WriteArray()
        {
            Console.WriteLine(nr);
            for (int i = 0; i < maxtab; i++)
            {
                if (ArrayField[i] == null)
                    break;
                ArrayField[i].Write();
                Console.WriteLine();
            }
        }
    }
}
