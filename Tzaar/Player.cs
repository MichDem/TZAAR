using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Tzaar
{
    
    class Player
    {
       
       
       public List<Counter> listCounters { get; set; }
        int numberCounter=30;
        int numberTzaar=6;
        int numberTzarr=9;
        int numberTott=15;

        Player()
        {
            
            for (int i = 0; i < numberTott; i++)
            {
                listCounters.Add(new Tott());
                
            }
            for (int i = 0; i < numberTzarr; i++)
            {
                listCounters.Add(new Tzarr());
            }
            for (int i = 0; i < numberTzaar; i++)
            {
                listCounters.Add(new Tzaar());
            }
        }

        bool check<T>()
        {
            for (int i = 0; i < numberCounter; i++)
            {
               if( listCounters[i].GetType().Equals(typeof(T)) &&listCounters[i].free)
                {
                    return true;
                }
            }
            return false;
        }
       public  bool   CheckConditionWin()
        {
            if( !check<Tott>() || !check<Tzaar>() || !check<Tzarr>())
            {
                return true;
            }
            return false;
        }
        public virtual  Move SI()
        {
            return new Move();
        }
        public void DeleteCounter(int index)
        {
            listCounters.RemoveAt(index);
        }
    }
    
}
