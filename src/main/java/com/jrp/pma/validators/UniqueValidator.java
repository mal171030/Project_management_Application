package com.jrp.pma.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jrp.pma.dao.EmployeeRepository;
import com.jrp.pma.entities.Employee;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueValidator implements ConstraintValidator<UniqueValue, String> {

	@Autowired
	EmployeeRepository empRepo;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		System.out.println("Entered validation method..");
		Employee emp = empRepo.findByEmail(value);

		if (emp != null)
			return false;
		else
			return true;
	}

}
