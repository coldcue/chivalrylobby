package com.chivalrylobby.web.clapi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class RegisterServerDataValidator implements Validator {

	private static final String IP_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	@Override
	public boolean supports(Class<?> clazz) {
		return RegisterServerData.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// Empty validation
		ValidationUtils.rejectIfEmpty(errors, "ip", "ip.empty");
		ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
		ValidationUtils.rejectIfEmpty(errors, "port", "port.empty");
		ValidationUtils.rejectIfEmpty(errors, "slot", "slot.empty");
		ValidationUtils.rejectIfEmpty(errors, "tunngle", "tunngle.empty");

		RegisterServerData data = (RegisterServerData) target;
		if (!validateIp(data.getIp()))
			errors.reject("ip.invalid");
		if (data.getName().length() > 32)
			errors.reject("name.long");
		if (data.getPort() < 0 || data.getPort() > 65535)
			errors.reject("port.invalid");
		if (data.getSlot() < 8 || data.getSlot() > 64)
			errors.reject("slot.invalid");

	}

	private boolean validateIp(String ip) {
		Pattern pattern = Pattern.compile(IP_PATTERN);
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}

}
