<?php
/*
* Example usage:
* ChivalryLobbyConnector::sendData("7.48.163.192",7777,"AOCTD-Moor_p",12,32);
*
*/
class ChivalryLobbyConnector {

	private static $blowfish = "xWHtn24X07mBp2oc";

	/*
	* The constructor, you must give the parameters
	*/
	public static function sendData() {
	
		/* Adds every data to the data array */
		$data = array();
		$data[]	= $ip;
		$data[]	= $port;
		$data[]	= $map;
		$data[]	= $players;
		$data[]	= $slot;
		
		/* Generate a key, secret and time */
		$time = time();
		$key = md5(rand(0,655356)+$time);
		$secret = md5(ChivalryLobbyConnector::$blowfish.$key);
		
		$data[] = $key;
		$data[] = $secret;
		$data[]	= $time;
		
		/* Pack to a JSON object */
		$json = json_encode($data);
		
		/* Send it with curl */
		$ch = curl_init("http://chivalrylobby.info/clsd?d=".urlencode($json));
		curl_setopt($ch, CURLOPT_RETURNTRANSFER , 1);
		curl_exec($ch);
		curl_close($ch);	
		
	}
	
	/*
	* Get the server list in an array of stdObjects
	* so if you want to get the ip, you can get by $object->ip
	*/
	public static function getData() {
		$ch = curl_init("http://chivalrylobby.info/clsg");
		curl_setopt($ch, CURLOPT_RETURNTRANSFER , 1);
		$json = curl_exec($ch);
		curl_close($ch);
		
		$data = json_decode($json);
		
		$servers = array();
		
		foreach($data as $srv)
		{
			$srv->ip = ChivalryLobbyConnector::convertIpFromInt($srv->ip);
			
			$srv->lastonline = new DateTime($srv->lastonline);
			$srv->lastonline->setTimezone(new DateTimeZone('Europe/Budapest'));
			
			$srv->lastupdate = new DateTime($srv->lastupdate);
			$srv->lastupdate->setTimezone(new DateTimeZone('Europe/Budapest'));
			
			$servers[] = $srv;
		}
		return $servers;
	}
	
	/*
	* Converts byte format ip to string
	*/
	private static function convertIpFromInt($ip)
	{
		$int = intval($ip);
		$sub = array();
		
		$sub[] = (int) ($int >> 3 * 8 & 255);
		$sub[] = (int) ($int >> 2 * 8 & 255);
		$sub[] = (int) ($int >> 8 & 255);
		$sub[] = (int) ($int & 255);
		
		return $sub[0].".".$sub[1].".".$sub[2].".".$sub[3];
	}
}

?>