package com.Hrms.Employee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.Entity.HrmsSalutation;
import com.Hrms.Employee.Service.HrmsSalutationService;

@RestController
@RequestMapping("/v1/salutation")
public class HrmsSalutationController {
	@Autowired
	private HrmsSalutationService hrmsSalutationService;

	@GetMapping(value = "/getAllSalutation")
	public ResponseEntity<List<HrmsSalutation>> listSalutation() {
		return hrmsSalutationService.listHrmsSalutation();
	}

}
