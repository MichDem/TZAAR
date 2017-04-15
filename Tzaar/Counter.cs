using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Tzaar
{
    abstract class Counter
    {
       protected enum ype { tott, tzaar, tzarr};
       protected ype type;
       public Field field { get; set; }
       public bool free { get; set; }

       List<Counter> addedCounters;
       public int power { get; set; }
        public int index { get; set; }
        public Player Owner { get; set; }

        public  Counter()
        {
            power = 1;
            free = true;
        }
        public Counter(int index)
        {
            power = 1;
            free = true;
            this.index = index;
        }
        public void addCounters(Counter counter)
        {
            power += counter.power;
            addedCounters.Add(counter);
            counter.power = 0;
            counter.free = false;
            field = counter.field;


        }
        public void HitCounters(Counter counter)
        {
           
            addedCounters.Add(counter);
            counter.power = 0;
            counter.field.counter = null;
            counter.free = false;
            field = counter.field;
        }
        Move GenerateMove(Field field, int index)
        {
            Move move = new Move();
            Field temp = field.ArrayField[index];
            if (field.ArrayField[index].counter == null)
            {


                while (temp != null && temp.counter == null)
                {
                    temp = temp.ArrayField[index];
                    if (temp == null)
                        return null;
                }
            }

            move.Original = this;
            move.Target = temp;
            if (field.counter.Owner == Owner)
            {
                move.typeMove = TypeMove.Buff;

            }
            else
            {
                move.typeMove = TypeMove.Hit;
            }
            return move;

        }
        public List<Move> CheckMove()
        {
            List<Move> list = new List<Move>();
            Move move= new Move();

            for (int i = 0; i < Field.maxtab; i++)
            {
                if(field.ArrayField[i]!=null)
                {
                    move = GenerateMove(field.ArrayField[i], i);
                    if(move!=null)
                      list.Add(move);
                }
            }

            return list;
        }

    }
}
