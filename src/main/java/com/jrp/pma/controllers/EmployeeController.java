package com.jrp.pma.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jrp.pma.entities.Employee;
import com.jrp.pma.services.EmployeeService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	EmployeeService empService;

	@GetMapping
	public String displayEmployees(Model model) {
		List<Employee> employees = empService.getAll();
		model.addAttribute("employees", employees);
		return "employees/list-employees";
	}

	@GetMapping("/new")
	public String displayEmployee(Model model) {

		Employee employee = new Employee();
		model.addAttribute("employee", employee);

		return "employees/new-employee";
	}

	@PostMapping("/save")
	public String createEmployeeForm(Model model, @Valid Employee employee, Errors errors) {

		if (errors.hasErrors()) {
			return "employees/new-employee";
		}
		// save todatabase using an employee crud repository
		empService.save(employee);

		return "redirect:/employees";
	}

	@GetMapping("/update")
	public String displayEmployeeUpdateForm(@RequestParam("id") long theId, Model model) {
		Employee theEmp = empService.findByEmployeeId(theId);

		model.addAttribute("employee", theEmp);
		return "employees/new-employee";
	}

	@GetMapping("/delete")
	public String deleteEmployee(@RequestParam("id") long theId, Model model) {
		Employee theEmp = empService.findByEmployeeId(theId);
		empService.delete(theEmp);
		return "redirect:/employees";
	}
}
