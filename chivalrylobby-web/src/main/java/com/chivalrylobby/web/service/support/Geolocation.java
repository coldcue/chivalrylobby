package com.chivalrylobby.web.service.support;

import java.net.URL;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

public class Geolocation {

	public static String getCountry(String ip) throws Exception {

		URL url = new URL("http://freegeoip.net/json/" + ip);

		ObjectMapper mapper = new ObjectMapper();

		@SuppressWarnings("unchecked")
		Map<String, Object> data = mapper
				.readValue(url.openStream(), Map.class);

		String cc = (String) data.get("country_code");
		cc.toLowerCase();

		if ("".equals(cc) || "rd".equals(cc))
			throw new Exception("IP Location cannot be determined!");

		return cc;
	}

}
