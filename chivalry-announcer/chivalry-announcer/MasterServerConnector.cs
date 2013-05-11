using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Net;
using Newtonsoft.Json;
using Newtonsoft.Json.Utilities;
using Newtonsoft.Json.Converters;

namespace chivalry_announcer
{
    class MasterServerConnector
    {
        private static string url = "http://www.chivalrylobby.info/clapi/";

        public static string getRealIP()
        {
            try
            {
                HttpClient client = new HttpClient();
                var clientTask = client.GetStringAsync("http://ip-api.com/json");
                clientTask.Wait();
                string result = clientTask.Result;

                dynamic obj = JsonConvert.DeserializeObject(result);
                foreach (var data in obj)
                {
                    if (data.Name == "query")
                        return (string)data.Value;
                }
                throw new Exception();
            }
            catch (Exception e)
            {
                throw new Exception("Cannot determine the IP");
            }
        }

        public static ResponseMessageObject doCall(string query, string content)
        {
            HttpClient client = new HttpClient();
            HttpRequestMessage rm = new HttpRequestMessage(HttpMethod.Post, url + query);

            rm.Content = new StringContent(content);
            rm.Content.Headers.ContentType.MediaType = "application/json";
            rm.Headers.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

            Task<HttpResponseMessage> clientTask = client.SendAsync(rm);
            clientTask.Wait();
            string rs = clientTask.Result.Content.ReadAsStringAsync().Result;
            ResponseMessageObject result = JsonConvert.DeserializeObject<ResponseMessageObject>(rs);

            return result;
        }

        /// <summary>
        /// Registers the server in the master server
        /// </summary>
        /// <param name="sc"></param>
        /// <returns></returns>
        /// <exception cref="Exception"></exception>
        public static ResponseMessageObject register(ServerConnector sc)
        {
            ServerData sd = sc.getServerData();

            String ip;
            if (sc.tunngle)
                ip = sc.ip;
            else
                ip = getRealIP();

            RegisterServerData data = new RegisterServerData(ip, sd.name, sd.port, sd.slot, sd.tunngle);

            data.generateSecurity();

            string json = JsonConvert.SerializeObject(data);

            try
            {
                ResponseMessageObject response = doCall("register", json);
                if (response.success)
                    sc.id = response.id;
                return response;
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
        public static ResponseMessageObject refresh(ServerConnector sc)
        {
            if (!sc.isRunning()) throw new Exception("Server isn't running!");
            ServerData sd = sc.getServerData();

            if (!ServerConnector.hasChanges(sd, sc.lastData) && sc.lastData.time.AddMinutes(15) > sd.time) return null;
            sc.lastData = sd;

            RefreshServerData data = new RefreshServerData(sc.id, sd.map, sd.players);

            data.generateSecurity();

            string json = JsonConvert.SerializeObject(data);

            try
            {
                return doCall("refresh", json);
            }
            catch (Exception e)
            {
                throw new Exception("Can't reach the Master Server!");
            }
        }

        /// <summary>
        /// Tries to remove the server from the database
        /// </summary>
        public static void remove(ServerConnector sc)
        {
            if (sc.id == 0) return;

            var data = new RemoveServerData(sc.id);

            data.generateSecurity();

            string json = JsonConvert.SerializeObject(data);

            try
            {
                doCall("remove", json);
                sc.id = 0;
            }
            catch (Exception e)
            {
                //Do nothing
            }
        }
    }
}
