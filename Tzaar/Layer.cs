using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Tzaar
{
    class Layer
    {
        public List<Field> ListField { get; set; }
        int SideSize;
        static int Counter=0;

        public Layer(int i)
        {
            SideSize = i;
            ListField = new List<Field>();
        }
        public void AddField(Field field)
        {
            int size = ListField.Count;
            if (size > 0)
            {
                if (size == 6 * (SideSize - 1) - 1)
                {
                    ListField[0].AddField(field);
                    field.AddField(ListField[0]);
                }
                ListField[size - 1].AddField(field);
                field.AddField(ListField[size - 1]);
            }

            ListField.Add(field);
        }
        public static Layer CreateLayer(int number)
        {
            Layer layer = new Layer(number);
            int x = (-1*(Counter+1));
            int y = (1 * (Counter + 1));

            for (int i = 0; i < 6*(number - 1); i++)
            {
               

                Field field = new Field(new Position(x, y),i+Counter.ToString());
                
                layer.AddField(field);
                if (i < (number-1))
                {
                    x+=2;
                }
                else if (i < (2 * (number - 1)))
                {
                    ++x;
                    --y;
                }
                else if (i < (3 * (number - 1)))
                {
                    --x;
                    --y;
                }
                else if (i < (4 * (number - 1)))
                {
                    x-=2;
                }
                else if (i < (5 * (number - 1)))
                {
                    ++y;
                    --x;
                }
                else if (i < (6 * (number - 1)))
                {
                    ++y;
                    ++x;
                }



            }
            ++Counter;
            return layer;

        }
        public static void ConnectLayers(Layer one, Layer two)
        {

            Layer bigger;
            Layer lower;
            if (one.ListField.Count < two.ListField.Count)
            {
                bigger = two;
                lower = one;
            }
            else if (one.ListField.Count == two.ListField.Count)
            {
                throw new Exception();

            }
            else
            {
                bigger = one;
                lower = two;
            }

            int j = 0;
            for (int i = 0; i < lower.ListField.Count; i++, j++)
            {
                lower.ListField[i].AddField(bigger.ListField[j]);
                lower.ListField[i].AddField(bigger.ListField[j + 1]);
                bigger.ListField[j].AddField(lower.ListField[i]);
                bigger.ListField[j + 1].AddField(lower.ListField[i]);

                if (i % (lower.SideSize - 1) == 0 && i != 0)
                {
                    ++j;

                    lower.ListField[i].AddField(bigger.ListField[j + 1]);
                    bigger.ListField[j + 1].AddField(lower.ListField[i]);
                }
                else if (i % (lower.SideSize - 1) == 0 && i == 0)
                {

                    lower.ListField[i].AddField(bigger.ListField[bigger.ListField.Count - 1]);
                    bigger.ListField[bigger.ListField.Count - 1].AddField(lower.ListField[i]);

                }
            }
        }
            public void Write()
            {
                for (int i = 0; i < ListField.Count; i++)
                {
                    ListField[i].WriteArray();
                }
            }
        public void Sort()
        {
            for (int i = 0; i < ListField.Count; i++)
            {
                ListField[i].Sort();
            }
        }
        
    }
}
