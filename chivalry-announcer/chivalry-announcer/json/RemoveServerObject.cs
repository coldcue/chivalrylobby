using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace chivalry_announcer
{
    class RemoveServerObject : SecurityImpl
    {
        public string ip;
        public int port;
        public bool tunngle;

        public RemoveServerObject(string ip, int port, bool tunngle)
        {
            this.ip = ip;
            this.port = port;
            this.tunngle = tunngle;
        }
    }
}
