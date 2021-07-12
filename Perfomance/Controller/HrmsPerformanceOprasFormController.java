package com.Hrms.Perfomance.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Perfomance.DTO.PerformanceOprasForm;
import com.Hrms.Perfomance.Service.HrmsPerformanceOprasFormService;

@RestController
@RequestMapping("v1/performanceOprasForm")
public class HrmsPerformanceOprasFormController {

	@Autowired
	private HrmsPerformanceOprasFormService hrmsPerformanceOprasFormService;

	@GetMapping(value = "/getPerformanceOprasFormByFinancialYearAndEmployeeid/{financialyearid}/{employeeid}")
	public ResponseEntity<PerformanceOprasForm> getPerformanceOprasFormByFinancialYearAndEmployeeid(
			@PathVariable("financialyearid") int financialyearid, @PathVariable("employeeid") int employeeid) {

		return hrmsPerformanceOprasFormService.getPerformanceOprasFormByFinancialYearAndEmployeeid(financialyearid,
				employeeid);

	}

}
