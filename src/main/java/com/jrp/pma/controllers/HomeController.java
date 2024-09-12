package com.jrp.pma.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrp.pma.dao.EmployeeRepository;
import com.jrp.pma.dao.ProjectRepository;
import com.jrp.pma.dto.ChartData;
import com.jrp.pma.dto.EmployeeProject;
import com.jrp.pma.entities.Project;

@Controller
public class HomeController {

	@Autowired
	ProjectRepository proRepo;

	@Autowired
	EmployeeRepository empRepo;

	@Value("${version}")
	private String version;

	@GetMapping("/")
	public String displayHome(Model model) throws JsonProcessingException {

		Map<String, Object> map = new HashMap<>();

		List<Project> projects = proRepo.findAll();
		model.addAttribute("projectsList", projects);

		List<ChartData> projectData = proRepo.getProjectStatus();

		// Let's convert projectData object into a json structure for use in javascript

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(projectData);

		model.addAttribute("projectStatusCnt", jsonString);
		model.addAttribute("version", version);

		List<EmployeeProject> employeesProjectCnt = empRepo.employeeProjects();
		model.addAttribute("employeesListProjectsCnt", employeesProjectCnt);
		return "main/home";

	}
}
