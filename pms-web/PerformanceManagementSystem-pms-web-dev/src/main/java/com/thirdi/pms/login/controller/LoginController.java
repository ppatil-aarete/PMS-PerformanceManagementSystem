package com.thirdi.pms.login.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.thirdi.pms.admin.AdminDao;
import com.thirdi.pms.admin.modal.AppraisalCycle;
import com.thirdi.pms.login.api.LoginService;
import com.thirdi.pms.login.model.LoginUser;

@Controller
@EnableWebMvc
@RequestMapping("/login")
public class LoginController {

	Gson gson = new Gson();
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private LoginService loginService;

	@Autowired
	private AdminDao adminDao;

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String ShowLogin(HttpServletRequest request, HttpServletResponse response) {

		return "login/login";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String loadMaster(HttpServletRequest request, HttpServletResponse response)
			throws URISyntaxException, IOException, JSONException {
		try {
			Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
			String username = loggedInUser.getName();
			LoginUser loginUserDetails = loginService.getUser(username);
			AppraisalCycle apprCycle = loginService.getCurrentAppraisalCycle();
			Integer currentCycleId = apprCycle.getCycleId();
			Integer totalCycles = adminDao.getTotalCycle();
			
			if (apprCycle != null) {
				request.setAttribute("apprCycle", gson.toJsonTree(apprCycle));
				request.setAttribute("totalCycles", gson.toJsonTree(totalCycles));
			}
			if (loginUserDetails != null) {
				Integer empId = loginUserDetails.getApprempid();
				if (empId != null) {
					JsonObject userDahsboardInfoJson = loginService.prepareEmployeeReviewCycleDetails(empId,
							currentCycleId);
					Map<Integer, JsonObject> teamDataMap = loginService.getMyTeamData(empId, currentCycleId);
					request.setAttribute("userGeneralInfo", gson.toJsonTree(userDahsboardInfoJson));
					request.setAttribute("myTeamData", gson.toJsonTree(teamDataMap));
				}
				request.setAttribute("user", gson.toJsonTree(loginUserDetails));
			}
			
		} catch (EmptyResultDataAccessException e) {
			response.sendRedirect("logout");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("logger is working in LoginController");

		return "login/master";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpSession session) {
		session = request.getSession(false);
		session.invalidate();
		return "login/login";
	}

	@RequestMapping(value = "/error?true", method = RequestMethod.GET)
	public String Error(HttpServletRequest request, HttpSession session) {
		request.setAttribute("errorMessage", "Invalid username and password.");
		return "login/login";
	}

}