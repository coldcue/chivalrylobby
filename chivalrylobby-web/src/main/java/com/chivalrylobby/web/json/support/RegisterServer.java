package com.chivalrylobby.web.json.support;

import com.chivalrylobby.web.entity.Server;

public class RegisterServer extends SecurityImpl {
	private String ip;
	private int port;
	private boolean tunngle;
	private String name;

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
		ret.setName(name);
		return ret;
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

	public boolean isTunngle() {
		return tunngle;
	}

	public void setTunngle(boolean tunngle) {
		this.tunngle = tunngle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
