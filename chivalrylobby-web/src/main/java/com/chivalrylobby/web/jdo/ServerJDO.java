package com.chivalrylobby.web.jdo;

import java.util.Date;

import com.google.appengine.api.datastore.Key;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ServerJDO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	private boolean online;

	private int ip;

	private int port;

	@Column(length = 2)
	private String country = "";

	private byte slot;

	private byte players;

	private boolean tunngle = false;

	private Date lastupdate;

	private Date lastonline;

	public ServerJDO() {
		// TODO Auto-generated constructor stub
	}

	public String getCountry() {
		return country;
	}

	public int getIp() {
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

	public byte getPlayers() {
		return players;
	}

	public int getPort() {
		return port;
	}

	public byte getSlot() {
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
	public void setIp(int ip) {
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
	public void setOnline(boolean online) {
		this.online = online;
	}
	public void setPlayers(byte players) {
		this.players = players;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public void setSlot(byte slot) {
		this.slot = slot;
	}
	
	public void setTunngle(boolean tunngle) {
		this.tunngle = tunngle;
	}
	
	
	
}
