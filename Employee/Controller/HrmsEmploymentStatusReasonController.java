package com.Hrms.Employee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.Entity.HrmsEmploymentStatusReason;
import com.Hrms.Employee.Service.HrmsEmploymentStatusReasonService;

@RestController
@RequestMapping("v1/employmentStatusReason")
public class HrmsEmploymentStatusReasonController {

	@Autowired
	private HrmsEmploymentStatusReasonService hrmsEmploymentStatusReasonService;

	@GetMapping(value = "/getAllEmploymentStatusReason")
	public ResponseEntity<List<HrmsEmploymentStatusReason>> listEmploymentStatusReason() {

		return hrmsEmploymentStatusReasonService.listEmploymentStatusReason();
	}

}
