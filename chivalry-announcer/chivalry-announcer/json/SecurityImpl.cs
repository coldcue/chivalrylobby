using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Security.Cryptography;

namespace chivalry_announcer
{
    abstract class SecurityImpl
    {
        private const string blowfish = "l5381H5sW32620tO";
        public string key;
        public string hash;
        public string time;

        /// <summary>
        /// Generates security stuff to the object
        /// </summary>
        public void generateSecurity()
        {
            time = DateTime.Now.ToUniversalTime().ToString();
            key = CalculateMD5Hash(new Random().Next().ToString());
            hash = CalculateMD5Hash(key + time + blowfish);
        }

        /// <summary>
        /// MD5 hash calculator
        /// </summary>
        /// <param name="input">the input</param>
        /// <returns>The MD5 hash of the input</returns>
        private static string CalculateMD5Hash(string input)
        {
            // step 1, calculate MD5 hash from input
            MD5 md5 = System.Security.Cryptography.MD5.Create();
            byte[] inputBytes = System.Text.Encoding.ASCII.GetBytes(input);
            byte[] hash = md5.ComputeHash(inputBytes);

            // step 2, convert byte array to hex string
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < hash.Length; i++)
            {
                sb.Append(hash[i].ToString("x2"));
            }
            return sb.ToString();
        }
    }
}
