using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Tzaar
{
    class BoardManager
    {
       

        List<List<char>> Board;

        List<Layer> FieldList;

        Player player1;
        Player player2;

        public BoardManager()
        {
            FieldList= new List<Layer> ();
            Layer layer1 = Layer.CreateLayer(2);
            Layer layer2 = Layer.CreateLayer(3);
            Layer layer3 = Layer.CreateLayer(4);
            Layer layer4 = Layer.CreateLayer(5);
            Layer.ConnectLayers(layer1, layer2);
            Layer.ConnectLayers(layer3, layer2);
            Layer.ConnectLayers(layer3, layer4);

            FieldList.Add(layer1);
            FieldList.Add(layer2);
            FieldList.Add(layer3);
            FieldList.Add(layer4);

            Sort();
        }
        public void Sort()
        {
            for (int i = 0; i < FieldList.Count; i++)
            {
                FieldList[i].Sort();
            }
        }
         public  bool CheckEndCondition(Player player)
        {
            if (player.CheckConditionWin())
                return true;
            return false;
        }
        public void Draw()
        {
            for (int i = 0; i < Board.Count; i++)
            {
                for (int j = 0; j < Board[i].Count; j++)
                {
                    Console.Write(Board[i][j]);
                }
                Console.WriteLine();
            }
        }
        public List<Counter> returnCounterList(Player player)
        {
            return player.listCounters;
        }
        public List<Move> returnListMove(Counter counter)
        {
            return counter.CheckMove();
        }
        public void PlayerServiceMove(Move move)
        {
            if(move.typeMove==TypeMove.Buff)
            {
                move.Original.addCounters(move.Target.counter);
              
            }
            if (move.typeMove == TypeMove.Hit)
            {
                move.Original.HitCounters(move.Target.counter);
            }
        }
        public void TurnPlayer(Player player)
        {
           Move move= player.SI();
            PlayerServiceMove(move);
        }
        
        public void RunGame()
        {
            while(!player1.CheckConditionWin() && !player2.CheckConditionWin())
            {
                TurnPlayer(player1);
                TurnPlayer(player2);
            }
        }
       
    }
}
