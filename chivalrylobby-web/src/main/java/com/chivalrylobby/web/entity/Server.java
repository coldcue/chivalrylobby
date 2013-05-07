package com.chivalrylobby.web.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.chivalrylobby.web.entity.enums.ServerGamemodes;
import com.chivalrylobby.web.entity.enums.ServerMaps;
import com.google.appengine.api.datastore.Key;

@Entity
public class Server {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	private boolean online;

	@Column(length = 16)
	private String ip;

	private int port;

	@Column(length = 2)
	private String country = "";

	private int slot;

	private int players;

	private boolean tunngle = false;

	private Date lastupdate;

	private Date lastonline;

	@Enumerated(EnumType.ORDINAL)
	private ServerMaps map;

	@Enumerated(EnumType.ORDINAL)
	private ServerGamemodes gamemode;

	public Server() {
	}

	public String getCountry() {
		return country;
	}

	public ServerGamemodes getGamemode() {
		return gamemode;
	}

	public String getIp() {
		return ip;
	}

	public Key getKey() {
		return key;
	}

	public Date getLastonline() {
		return lastonline;
	}

	public Date getLastupdate() {
		return lastupdate;
	}

	public ServerMaps getMap() {
		return map;
	}

	public int getPlayers() {
		return players;
	}

	public int getPort() {
		return port;
	}

	public int getSlot() {
		return slot;
	}

	public boolean isOnline() {
		return online;
	}

	public boolean isTunngle() {
		return tunngle;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setGamemode(ServerGamemodes gamemode) {
		this.gamemode = gamemode;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public void setLastonline(Date lastonline) {
		this.lastonline = lastonline;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}

	public void setMap(ServerMaps map) {
		this.map = map;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public void setPlayers(int players) {
		this.players = players;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public void setTunngle(boolean tunngle) {
		this.tunngle = tunngle;
	}

}
