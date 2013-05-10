using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Net.Http;
using System.Net;
using Newtonsoft.Json;
using System.Diagnostics;



namespace chivalry_announcer
{
    static class Program
    {
        public static ServerConnector server;
        public static string blowfish = "biSXSSf3V9r772chtd62ua";
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1());
        }

        /// <summary>
        /// Opens a link in the default browser
        /// </summary>
        /// <param name="url">the URL to open</param>
        internal static void openLinkInBrowser(String url)
        {
            System.Diagnostics.Process.Start(url);
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="tunngle"></param>
        /// <param name="port"></param>
        /// <param name="slot"></param>
        /// <exception cref="Exception"></exception>
        internal static void initServer(bool tunngle, int port, int slot)
        {
            Process process = ServerConnector.getServerProcess();
            if (process == null) throw new Exception("Your server isn't running!");
            server = new ServerConnector(port, slot, tunngle, process);
        }
    }
}
