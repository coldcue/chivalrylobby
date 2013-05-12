﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Diagnostics;
using System.Text.RegularExpressions;
using System.Net.Sockets;
using System.Net;

namespace chivalry_announcer
{
    class ServerConnector
    {
        /// <summary>
        /// Only the Tunngle ip is retrieved, otherwise it's null
        /// </summary>
        /// 
        public long id;
        public string ip;
        public int port;
        public int slot;
        public bool tunngle;
        public string name;

        public ServerData lastData;

        private Process process;

        /// <summary>
        /// Creates a server connector
        /// </summary>
        /// <param name="process">The actual server process</param>
        /// <exception cref="Exception">Tunngle</exception>
        public ServerConnector(int port, int slot, bool tunngle, string name, Process process)
        {
            this.name = name;
            this.port = port;
            this.slot = slot;
            this.tunngle = tunngle;
            this.process = process;
            if (tunngle)
            {
                ip = getTunngleIp();
                if (ip == null) throw new Exception("Tunngle IP cannot be determined!");
            }
            lastData = new ServerData();
        }

        /// <summary>
        /// 
        /// </summary>
        /// <returns>A ServerDataObject</returns>
        /// <exception cref="Exception"></exception>
        public ServerData getServerData()
        {
            process.Refresh();
            Match match = Regex.Match(process.MainWindowTitle, ".*: (.+) \\(([0-9]+) .*\\)");

            if (!match.Success) throw new Exception("Server is in invalid status");

            var sd = new ServerData();

            sd.name = name;
            sd.map = match.Groups[1].Value;
            sd.players = int.Parse(match.Groups[2].Value);
            sd.ip = ip;
            sd.port = port;
            sd.slot = slot;
            sd.tunngle = tunngle;
            sd.time = DateTime.Now;

            return sd;
        }

        /// <summary>
        /// Determines that the process is running
        /// </summary>
        /// <returns></returns>
        public bool isRunning()
        {
            return !process.HasExited;
        }

        /// <summary>
        /// Returns the Tunngle IP
        /// </summary>
        /// <returns>null if not found</returns>
        public static string getTunngleIp()
        {
            IPHostEntry host;
            host = Dns.GetHostEntry(Dns.GetHostName());
            foreach (IPAddress ip in host.AddressList)
            {
                if (ip.AddressFamily == AddressFamily.InterNetwork)
                {
                    if (Regex.IsMatch(ip.ToString(), "7\\..*"))
                        return ip.ToString();
                }
            }
            return null;
        }

        /// <summary>
        /// Compares two ServerDataObject
        /// </summary>
        /// <param name="sd1"></param>
        /// <param name="sd2"></param>
        /// <returns>false if no changes, true otherwise</returns>
        public static bool hasChanges(ServerData sd1, ServerData sd2)
        {
            if (sd1.map != sd2.map) return true;
            if (sd1.players != sd2.players) return true;
            return false;
        }

        /// <summary>
        /// Returns the current server process
        /// </summary>
        /// <returns>returns null if no such</returns>
        public static Process getServerProcess()
        {
            Process[] processlist = Process.GetProcesses();

            foreach (Process theprocess in processlist)
            {
                if (theprocess.ProcessName.Equals("UDK"))
                {
                    try
                    {
                        Match match = Regex.Match(theprocess.MainWindowTitle, ".*: .+ \\([0-9]+ .*\\)");
                        if (match.Success) return theprocess;

                    }
                    catch (Exception e)
                    { }

                }
            }
            return null;
        }

    }
}
