using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace chivalry_announcer
{
    class RemoveServerData : SecurityImpl
    {
        public long id;

        public RemoveServerData(long id)
        {
            this.id = id;
        }
    }
}
