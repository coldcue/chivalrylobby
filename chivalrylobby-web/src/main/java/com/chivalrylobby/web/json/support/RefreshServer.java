package com.chivalrylobby.web.json.support;

public class RefreshServer extends SecurityImpl {
	private String ip;

	private String map;

	private String name;

	private int players;

	private int port;

	private int slot;

	private boolean tunngle;

	public String getIp() {
		return ip;
	}

	public String getMap() {
		return map;
	}

	public String getName() {
		return name;
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

	public boolean isTunngle() {
		return tunngle;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public void setName(String name) {
		this.name = name;
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
