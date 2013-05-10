package com.chivalrylobby.web.clapi;

import com.chivalrylobby.web.clapi.security.SecurityImpl;
import com.chivalrylobby.web.entity.enums.ServerGamemodes;
import com.chivalrylobby.web.entity.enums.ServerMaps;

/**
 * This contains the refresh data
 * 
 * @author Andrew
 * 
 */
public class RefreshServerData extends SecurityImpl {
	private long id;
	private String map;
	private int players;

	private ServerGamemodes gamemode;
	private ServerMaps maps;

	public ServerGamemodes getGamemode() {
		return gamemode;
	}

	public ServerMaps getMaps() {
		return maps;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
		String[] temp = map.split("[-_]");

		try {
			gamemode = ServerGamemodes.valueOf(temp[0].toUpperCase());
		} catch (Exception e) {
			gamemode = ServerGamemodes.ND;
		}

		try {
			maps = ServerMaps.valueOf(temp[1].toUpperCase());
		} catch (Exception e) {
			maps = ServerMaps.ND;
		}
	}

	public int getPlayers() {
		return players;
	}

	public void setPlayers(int players) {
		this.players = players;
	}
}
