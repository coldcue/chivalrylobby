<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/module/head.jsp"%>
<%@include file="/WEB-INF/jsp/module/header.jsp"%>

<div class="container_12">
	<div class="grid_8">
		<div class="content-box">
			<h1>HOW-TO CREATE A SERVER</h1>
		</div>
		<div class="content-box-white">
			<p>
				First, you need to create a shortcut from the <b>UDK.exe</b> which
				is located in
			<ul>
				<li><b>32 bit Windows:</b> Program Files\Chivalry Medieval
					Warfare\Binaries\Win32</li>
				<li><b>64 bit Windows:</b> Program Files (x86)\Chivalry
					Medieval Warfare\Binaries\Win32</li>
			</ul>
			<p>
				If you have the shortcut, right click on it and <b>Properties</b>
			</p>
			<p>
				In the target input box type <b>server AOCFFA-Arena3_P?
					dedicated=true</b> after the target
			<p style="color: red; font-weight: bold;">Important! You must
				start the game first, otherwise no one can connect to your server!</p>
			<div style="text-align: center">
				<img src="/static/images/createserver/1.png">
			</div>
			</p>

		</div>
		<div id="announcer" class="content-box-white">
			<h2>
				Install the <i>ChivalryLobby Announcer</i> tool
			</h2>
			<p style="color: red; font-weight: bold;">Important! If you don't
				install this tool, the server details wont appear in the list! .NET
				Installation can be slow, be patient!</p>
			<p>
				<b>Start the <a
					href="http://pb.chivalrylobby.info/announcer/setup.exe">setup.exe</a>
					and it will setup this tool automatically!
				</b> If you don't have .NET Framework 4.5 installed, then it could take
				a while until it is installed automatically. Your browser may alert
				you that this can be a malware and so on, but it's completely
				harmless.
			</p>
			<p>
				A cool feature that this tool wont send a big amount of messages to
				the server, because it sends data only when it's changed.<br />
				Another great feature is the <b>Tunngle support</b>! If you want to
				host your game through tunngle, now, you can do that too!
			</p>
			<div style="text-align: center">
				<img src="/static/images/createserver/3.png">
			</div>
			<p>
				<i>This tool will not modify any files, it's only monitoring
					your server process! You will also need the .NET Framework 4.5, but
					the installer installs it automatically!</i>
			</p>
			<p>
				<b>After you've installed it, it will be in your Start menu, not
					on your desktop!</b>
			</p>
			<p>
				<b>You have to run it simultaneously with your server! When you
					start the server, open this tool and click on the big "Announce!"
					button!</b>
			</p>
		</div>
		<div class="content-box-white error_container">
			<h2>Important!</h2>
			<p>You can create a Dedicated server, with your real IP, by
				opening the port and turning off the firewall!</p>
			<h3>Why Dedicated servers are better than the Tunngle ones?</h3>
			<ul>
				<li><b>Dedicated servers are visible to everybody!</b> You can
					connect to them with or without tunngle!</li>
				<li><b>Dedicated servers are faster!</b> If you connect to a
					dedicated server, you don't have to connect through tunngle, which
					adds 30-50ms ping!</li>
				<li><b>Dedicated servers have country flags!</b> Every player
					will know where the server is hosted!</li>
				<li><b>Dedicated servers are on the top of the list!</b></li>
			</ul>
		</div>
		<div class="content-box-white">
			<h2>You have to turn off the firewall or make an exception in
				it!</h2>
			<p>
				<a
					href="https://www.google.com/webhp?q=How+to+turn+off+firewall+in+windows">Find
					out here!</a>
			</p>
		</div>

		<div class="content-box-white" id="portforward">
			<h2>If you have router, then you have to forward ports!</h2>
			<p style="color: red; font-weight: bold;">This step is very
				important! If you don't do that, you'll get an error message when
				you try to add your server!</p>
			<p>
				You have to <b>open both 7777 TCP and UDP port</b>, otherwise your
				server is invisible to the players and to the website!
			</p>
			<p style="font-weight: bold;">
				Find out your router's manufacturer, then search on the <a
					href="https://www.google.com">google</a> how can you forward it!
			</p>
			<p>
				<i>For example if I have a Dlink router, I would search for "how
					to forward port in Dlink router".</i>
			</p>
			<p>
				Your <b>local or internal IP</b> can be acquired by opening the <b>cmd.exe</b>
				(Start/Run) and typing in <b>ipconfig</b>. It will list many IPs,
				but you need that <b>IPv4</b> which starts with <b>192.168.</b>
			</p>
			<p>
				<i>For example, this is my computers IPv4:</i>
			<div style="text-align: center">
				<img src="/static/images/createserver/2.png">
			</div>

		</div>

		<div class="content-box-white">
			<h2>How to connect to my server?</h2>
			<p>
				Open the console in the game, and type in: <b>open 127.0.0.1</b>
			</p>
		</div>


	</div>
	<div class="grid_4">
		<div id="ID-ad2">
			<%@include file="/WEB-INF/jsp/module/sidead.jsp"%>
		</div>
	</div>
</div>