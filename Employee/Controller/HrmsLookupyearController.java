package com.Hrms.Employee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.Entity.Hrmslookupyear;
import com.Hrms.Employee.Service.HrmslookupyearService;

@RestController
@RequestMapping("v1/lookupyear")
public class HrmsLookupyearController {
	@Autowired
	private HrmslookupyearService hrmslookupyearService;

	@GetMapping(value = "/getAllYear")
	public ResponseEntity<List<Hrmslookupyear>> listYear() {
		return hrmslookupyearService.listyear();
	}

}
