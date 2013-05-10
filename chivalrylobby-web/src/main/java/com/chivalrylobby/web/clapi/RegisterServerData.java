package com.chivalrylobby.web.clapi;

import org.apache.commons.lang.StringEscapeUtils;

import com.chivalrylobby.web.clapi.security.SecurityImpl;
import com.chivalrylobby.web.entity.Server;

/**
 * @author Andrew
 * 
 */
public class RegisterServerData extends SecurityImpl {
	private String ip;
	private String name;
	private int port;
	private int slot;
	private boolean tunngle;

	/**
	 * Creates a {@link Server} from this
	 * 
	 * @return
	 */
	public Server createServer() {
		Server ret = new Server();
		ret.setIp(ip);
		ret.setPort(port);
		ret.setTunngle(tunngle);
		// Prevent hacking
		ret.setName(StringEscapeUtils.escapeHtml(name));
		return ret;
	}

	public String getIp() {
		return ip;
	}

	public String getName() {
		return name;
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

	public void setName(String name) {
		this.name = name;
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
