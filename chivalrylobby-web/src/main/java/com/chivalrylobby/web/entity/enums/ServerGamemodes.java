package com.chivalrylobby.web.entity.enums;

public enum ServerGamemodes {
	ND("", ""), AOCTO("Team Objective", "TO"), AOCTD("Team Deathmatch", "TD"), AOCFFA(
			"Free-for-All", "FFA"), AOCKOTH("King of the hill", "KOTH"), AOCLTS(
			"Last Team Standing", "LTS");

	private String fullname;
	private String name;

	private ServerGamemodes(String fullname, String name) {
		this.fullname = fullname;
		this.name = name;
	}

	public String getFullname() {
		return fullname;
	}

	public String getName() {
		return name;
	}
}
