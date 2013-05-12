package com.chivalrylobby.web.entity.enums;

public enum ServerGamemodes {
	AOCCTF("Capture the Flag", "CTF"), AOCDUEL("Duel", "DUEL"), AOCFFA(
			"Free-for-All", "FFA"), AOCKOTH("King of the hill", "KOTH"), AOCLTS(
			"Last Team Standing", "LTS"), AOCTD("Team Deathmatch", "TD"), AOCTO(
			"Team Objective", "TO"), ND("No data", "ND"), UT(
			"Unreal Tournament", "UT");

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
