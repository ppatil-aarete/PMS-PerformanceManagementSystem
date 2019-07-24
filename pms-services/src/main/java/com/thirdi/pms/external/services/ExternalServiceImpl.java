package com.thirdi.pms.external.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ExternalServiceImpl implements ExternalServices{

	public JSONArray fetchDynamicMenuFromRole(String role) throws URISyntaxException, IOException, JSONException {
		
		String filePath = getClass().getResource("/dynamic-menu.json").toURI().getPath();
		filePath = filePath.substring(1);
		String content = new String(Files.readAllBytes(Paths.get(filePath)));
		JSONObject dataJson = new JSONObject(content);
		if(role != "" && StringUtils.hasText(role)) {
			if(dataJson.has(role)) {
				JSONArray menuArray = dataJson.getJSONArray(role);
			    return menuArray;
			}
		}
		return null;
	}
	
	

}
