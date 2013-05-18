package com.chivalrylobby.web.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.chivalrylobby.web.entity.enums.ServerGamemodes;
import com.chivalrylobby.web.entity.enums.ServerMaps;
import com.google.appengine.api.datastore.Key;

@Entity
public class Server implements Serializable {
	private static final long serialVersionUID = 4007569952052596829L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	@Column(length = 64)
	private String name;

	@Column(length = 16)
	private String ip;

	private int port;

	@Column(length = 4)
	private String country;

	private int slot;
	
	private boolean tunngle;

	@Transient
	private int players;

	@Transient
	private long lastupdate;

	@Transient
	private ServerMaps map;

	@Transient
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

	public long getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(long lastupdate) {
		this.lastupdate = lastupdate;
	}

}
