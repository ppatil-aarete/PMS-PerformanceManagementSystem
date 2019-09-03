package com.thirdi.pms.goal.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.thirdi.pms.competency.CompetencyService;
import com.thirdi.pms.goal.model.Goal;

@Controller
public class GoalController {

	@Autowired
	CompetencyService service;

	@RequestMapping(value = "/displayForm", method = RequestMethod.GET)
	public String diplayForm() {
		return "main-menu";

	}

	@RequestMapping(value = "/showform", method = RequestMethod.GET)
	public String showform(Model model, HttpServletRequest request) {
		model.addAttribute("goal", new Goal());
		return "Goal";

	}

	@RequestMapping("/submitForm")
	// @ModelAttribute binds form data to the object
	public void submitForm(@ModelAttribute("goal") Goal res) {
		List<Goal> goals = new ArrayList<Goal>();
		goals.add(res);
		Boolean setGoals = service.setGoals(goals);
	}
}
