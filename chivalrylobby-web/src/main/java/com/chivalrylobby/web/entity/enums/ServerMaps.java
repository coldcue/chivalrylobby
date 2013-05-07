package com.chivalrylobby.web.entity.enums;

public enum ServerMaps {
	ND(""), STONESHILL("Stoneshill"), STONESHILLVILLAGE("Stoneshill Village"), ARENA3(
			"Arena"), BATTLEGROUNDS("Battlegrounds"), HILLSIDE("Hillside"), DARKFOREST(
			"Dark Forest"), RUINS("Ruins"), MOOR("Moor"), HILLSIDEPYRE(
			"Hillside Pyre");
	private String fullname;

	private ServerMaps(String fullname) {
		this.fullname = fullname;
	}

	public String getFullname() {
		return fullname;
	}
}
