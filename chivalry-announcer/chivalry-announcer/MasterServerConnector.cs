using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net.Http;
using System.Net;
using Newtonsoft.Json;
using Newtonsoft.Json.Converters;

namespace chivalry_announcer
{
    class MasterServerConnector
    {
        private static string url = "http://chivalrylobby.info/cla?q=";

        /// <summary>
        /// Registers the server in the master server
        /// </summary>
        /// <param name="sc"></param>
        /// <returns></returns>
        /// <exception cref="Exception"></exception>
        public static ResponseMessageObject register(ServerConnector sc)
        {
            var client = new HttpClient();

            var data = new RegisterServerObject();
            var sd = sc.getServerData();

            data.ip = sd.ip;
            data.port = sd.port;
            data.tunngle = sd.tunngle;

            data.generateSecurity();

            string json = JsonConvert.SerializeObject(data, new JavaScriptDateTimeConverter());

            try
            {
                Task<string> clientTask = client.GetStringAsync(new Uri(url + json));
                clientTask.Wait();
                ResponseMessageObject result = JsonConvert.DeserializeObject<ResponseMessageObject>(clientTask.Result);
                Console.WriteLine(clientTask.Result);
                return result;
            }
            catch (Exception e)
            {
                throw new Exception("Can't reach the Master Server!");
            }
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="sc"></param>
        /// <returns></returns>
        /// <exception cref="Exception"></exception>
        public static ResponseMessageObject sendServerData(ServerConnector sc)
        {
            var client = new HttpClient();

            if (!sc.isRunning()) throw new Exception("Server isn't running!");
            ServerDataObject data = sc.getServerData();


            data.time = DateTime.Now;

            if (!ServerConnector.hasChanges(data, sc.lastData) && sc.lastData.time.AddMinutes(15) > data.time) return null;
            sc.lastData = data;

            data.generateSecurity();

            string json = JsonConvert.SerializeObject(data, new JavaScriptDateTimeConverter());

            Task<string> clientTask = client.GetStringAsync(new Uri(url + json));
            clientTask.Wait();
            ResponseMessageObject result = JsonConvert.DeserializeObject<ResponseMessageObject>(clientTask.Result);
            Console.WriteLine(clientTask.Result);

            return result;
        }

        /// <summary>
        /// Tries to remove the server from the database
        /// </summary>
        public static void removeServer(ServerConnector sc)
        {
            var client = new HttpClient();

            var data = new RemoveServerObject(sc.ip, sc.port, sc.tunngle);

            data.generateSecurity();

            string json = JsonConvert.SerializeObject(data, new JavaScriptDateTimeConverter());

            try
            {
                Task<string> clientTask = client.GetStringAsync(new Uri(url + json));
                clientTask.Wait();
                ResponseMessageObject result = JsonConvert.DeserializeObject<ResponseMessageObject>(clientTask.Result);
                Console.WriteLine(clientTask.Result);
            }
            catch (Exception e)
            {
                //Do nothing
            }
        }
    }
}
