package com.chivalrylobby.web.clapi.security;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SecurityValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO do the security validation

	}

}
