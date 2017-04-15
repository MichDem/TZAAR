using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Tzaar
{
    public enum TypeMove { Hit, Buff };
    class Move
    {
        
       public  TypeMove typeMove;
       public Counter Original;
       public Field Target;
    }
}
