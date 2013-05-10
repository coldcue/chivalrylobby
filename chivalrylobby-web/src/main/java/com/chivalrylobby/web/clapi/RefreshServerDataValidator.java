package com.chivalrylobby.web.clapi;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class RefreshServerDataValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return RefreshServerData.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		// Empty validation
		ValidationUtils.rejectIfEmpty(errors, "map", "map.empty");
		ValidationUtils.rejectIfEmpty(errors, "players", "players.empty");

		RefreshServerData data = (RefreshServerData) target;
		if (data.getPlayers() < 0 || data.getPlayers() > 64)
			errors.reject("invalid.players");
	}

}
