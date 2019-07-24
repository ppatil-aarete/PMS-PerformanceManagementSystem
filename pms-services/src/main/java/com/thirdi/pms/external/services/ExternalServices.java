package com.thirdi.pms.external.services;

import java.io.IOException;
import java.net.URISyntaxException;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

public interface ExternalServices {

	public JSONArray fetchDynamicMenuFromRole(String role) throws URISyntaxException, IOException, JSONException;
}
