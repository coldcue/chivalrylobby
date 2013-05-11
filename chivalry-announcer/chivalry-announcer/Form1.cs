using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace chivalry_announcer
{
    public partial class Form1 : Form
    {
        public bool started = false;

        public bool tunngle = false;
        public int port = 7777;
        public int slot = 32;
        public string name;

        public Form1()
        {
            InitializeComponent();
            portTextBox.Text = port.ToString();
            slotTextBox.Text = slot.ToString();
            TunngleCheckBox.Checked = tunngle;
        }

        private void startAnnounce()
        {
            startstopButton.Text = "Stop";
            started = true;

            //Disable things
            portTextBox.Enabled = false;
            slotTextBox.Enabled = false;
            TunngleCheckBox.Enabled = false;
            serverNameTextBox.Enabled = false;

            timer1_Tick(null, null);
            timer1.Start();
        }

        private void stopAnnounce()
        {
            started = false;

            //Enable things
            portTextBox.Enabled = true;
            slotTextBox.Enabled = true;
            TunngleCheckBox.Enabled = true;
            serverNameTextBox.Enabled = true;

            startstopButton.Text = "Announce!";
            startstopButton.Enabled = true;
            this.toolStripStatusLabel1.Text = "Announce stopped!";
            timer1.Stop();
            this.removeServer();
        }

        /// <summary>
        /// Removes the server from the database
        /// </summary>
        private void removeServer()
        {
            try
            {
                MasterServerConnector.remove(Program.server);
            }
            catch (Exception e)
            {
                //Do nothing
            }
        }

        /// <summary>
        /// Registers the server in the database
        /// </summary>
        /// <returns></returns>
        private bool registerServer()
        {

            this.toolStripStatusLabel1.Text = "Registering server...";
            try
            {
                // Register server in the database
                startstopButton.Enabled = false;

                Program.initServer(tunngle, port, slot, name);
                if (!Program.server.isRunning())
                {
                    MessageBox.Show("The server isn't running! \r\n Start the server first!", "Error!", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return false;
                }

                var result = MasterServerConnector.register(Program.server);
                startstopButton.Enabled = true;

                this.toolStripStatusLabel1.Text = result.message;
                if (!result.success)
                {
                    MessageBox.Show(result.message, "Error!", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return false;
                }
                return true;
            }
            catch (Exception ex)
            {
                startstopButton.Enabled = true;
                this.toolStripStatusLabel1.Text = ex.Message;
                MessageBox.Show(ex.Message, "Error!", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return false;
            }
        }

        /// <summary>
        /// Tests and sets the data
        /// </summary>
        private void setData()
        {
            //Test port
            int port = int.Parse(portTextBox.Text);
            if (1500 > port || port > 65535) throw new Exception("Invalid port! It must be between 1500 and 65535!");
            this.port = port;

            //Test slot
            int slot = int.Parse(slotTextBox.Text);
            if (8 > slot || slot > 64) throw new Exception("Invalid slot! It must be between 8 and 64!");
            this.slot = slot;

            this.tunngle = TunngleCheckBox.Checked;

            //Test server name
            this.name = serverNameTextBox.Text;
            if (name.Length > 40 || name.Length < 6) throw new Exception("The length of the server name must be between 6 and 40 characters!");

        }

        /// <summary>
        /// Sends the data
        /// </summary>
        private void sendData()
        {
            this.toolStripStatusLabel1.Text = "Waiting for changes...";
            try
            {
                // Send server data
                var result = MasterServerConnector.refresh(Program.server);

                if (result != null)
                {
                    this.toolStripStatusLabel1.Text = result.message;
                    if (!result.success) MessageBox.Show(result.message, "Error!", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
            catch (Exception e)
            {
                stopAnnounce();
                this.toolStripStatusLabel1.Text = e.Message;
                MessageBox.Show(e.Message, "Error!", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        /// <summary>
        /// Start/Stop button clicked
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void startstopButton_Click(object sender, EventArgs e)
        {
            if (!started)
            {
                try
                {
                    this.setData();
                    if (!this.registerServer()) throw new Exception("");
                    this.startAnnounce();
                }
                catch (Exception ex)
                {
                    if (ex.Message.Length > 0)
                    {
                        MessageBox.Show(ex.Message, "Error!", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    }
                }
            }
            else
                this.stopAnnounce();
        }

        /// <summary>
        /// The main timer
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void timer1_Tick(object sender, EventArgs e)
        {
            if (started) this.sendData();
        }

        /// <summary>
        /// The wiki link
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void linkLabel1_LinkClicked_1(object sender, LinkLabelLinkClickedEventArgs e)
        {
            Program.openLinkInBrowser("http://chivalrylobby.info/howto/createserver");
        }

        /// <summary>
        /// When the form is closing
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            this.stopAnnounce();
            this.removeServer();
        }

        private void TunngleCheckBox_CheckedChanged(object sender, EventArgs e)
        {
            if (TunngleCheckBox.Checked)
            {
                String tip = ServerConnector.getTunngleIp();
                if (tip == null)
                {
                    MessageBox.Show("You haven't opened the Tunngle client!", "Error!", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    TunngleCheckBox.Checked = false;
                }
                else
                {
                    tunngle = true;
                }

            }
            else
            {
                tunngle = false;
            }
        }

    }
}
