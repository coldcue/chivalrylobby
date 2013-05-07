package com.chivalrylobby.web.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.LockModeType;
import javax.persistence.NamedQuery;

import com.chivalrylobby.web.entity.enums.ServerGamemodes;
import com.chivalrylobby.web.entity.enums.ServerMaps;
import com.google.appengine.api.datastore.Key;

@Entity
@NamedQuery(name = "getPublicServers", query = "SELECT s FROM Server s", lockMode = LockModeType.READ)
public class Server {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	private String name;

	private boolean online;

	private String ip;

	private int port;

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

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public int getPlayers() {
		return players;
	}

	public void setPlayers(int players) {
		this.players = players;
	}

	public boolean isTunngle() {
		return tunngle;
	}

	public void setTunngle(boolean tunngle) {
		this.tunngle = tunngle;
	}

	public Date getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}

	public Date getLastonline() {
		return lastonline;
	}

	public void setLastonline(Date lastonline) {
		this.lastonline = lastonline;
	}

	public ServerMaps getMap() {
		return map;
	}

	public void setMap(ServerMaps map) {
		this.map = map;
	}

	public ServerGamemodes getGamemode() {
		return gamemode;
	}

	public void setGamemode(ServerGamemodes gamemode) {
		this.gamemode = gamemode;
	}

}
