using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace chivalry_announcer
{
    class RefreshServerData : SecurityImpl
    {
        public long id;
        public string map;
        public int players;

        public RefreshServerData(long id, string map, int players)
        {
            this.id = id;
            this.map = map;
            this.players = players;
        }
    }
}
