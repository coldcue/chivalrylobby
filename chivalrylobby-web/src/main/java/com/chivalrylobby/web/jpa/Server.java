package com.chivalrylobby.web.jpa;

import java.util.Date;

import com.google.appengine.api.datastore.Key;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

	private byte slot;

	private byte players;

	private boolean tunngle = false;

	private Date lastupdate;

	private Date lastonline;

	public Server() {
	}
	
	

	public String getIp() {
		return ip;
	}



	public void setIp(String ip) {
		this.ip = ip;
	}



	public String getCountry() {
		return country;
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
