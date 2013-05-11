using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace chivalry_announcer
{
    class RegisterServerData : SecurityImpl
    {
        public string ip;
        public string name;
        public int port;
        public int slot;
        public bool tunngle;

        public RegisterServerData(string ip, string name, int port, int slot, bool tunngle)
        {
            this.ip = ip;
            this.name = name;
            this.port = port;
            this.slot = slot;
            this.tunngle = tunngle;
        }
    }
}
