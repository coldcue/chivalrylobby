package com.chivalrylobby.web.entity.enums;

public enum ServerMaps {
	ARENA3("Arena"), ARGONSWALL("Argons Wall"), BATTLEGROUNDS("Battlegrounds"), BRIDGE(
			"Bridge"), CISTERN("Cistern"), CITADEL("Citadel"), COURTYARD(
			"Courtyard"), DARKFOREST("Dark Forest"), DININGHALL("Dining hall"), FRIGID(
			"Frigid"), FROSTPEAK("Frost Peak"), HADRIANSWALL("Hadrians Wall"), HILLSIDE(
			"Hillside"), HILLSIDEPYRE("Hillside Pyre"), MINES("Mines"), MOOR(
			"Moor"), ND("No data"), RUINS("Ruins"), SHAFT("Shaft"), SHIPYARD(
			"Shipyard"), STONESHILL("Stoneshill"), STONESHILLVILLAGE(
			"Stoneshill Village"), THRONEROOMXL("Throne Room XL"), TOWER(
			"Tower");
	private String fullname;

	private ServerMaps(String fullname) {
		this.fullname = fullname;
	}

	public String getFullname() {
		return fullname;
	}
}
