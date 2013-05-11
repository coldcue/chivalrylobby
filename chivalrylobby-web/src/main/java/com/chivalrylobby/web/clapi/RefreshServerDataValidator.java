package com.chivalrylobby.web.clapi;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.chivalrylobby.web.clapi.security.SecurityValidator;

public class RefreshServerDataValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return RefreshServerData.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// Empty validation
		ValidationUtils.rejectIfEmpty(errors, "id", "id.empty");
		ValidationUtils.rejectIfEmpty(errors, "map", "map.empty");
		ValidationUtils.rejectIfEmpty(errors, "players", "players.empty");

		// Check security
		SecurityValidator securityValidator = new SecurityValidator();
		ValidationUtils.invokeValidator(securityValidator, target, errors);

		RefreshServerData data = (RefreshServerData) target;
		if (data.getPlayers() < 0)
			errors.reject("invalid.players");
	}

}
