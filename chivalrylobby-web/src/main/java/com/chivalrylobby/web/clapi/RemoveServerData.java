package com.chivalrylobby.web.clapi;

import com.chivalrylobby.web.clapi.security.SecurityImpl;

public class RemoveServerData extends SecurityImpl {

	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
