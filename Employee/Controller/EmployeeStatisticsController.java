package com.Hrms.Employee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.DTO.EmployeeCount;
import com.Hrms.Employee.DTO.EmployeeCountAll;
import com.Hrms.Employee.DTO.EmployeeEducationlevelCount;
import com.Hrms.Employee.DTO.EmployeeGlobalCount;
import com.Hrms.Employee.DTO.EmployeeStatistics;
import com.Hrms.Employee.DTO.UnitEmployeeDetails;
import com.Hrms.Employee.Service.EmployeeStatisticsService;

@RestController
@RequestMapping("v1/employeeStatistics")
public class EmployeeStatisticsController {

	@Autowired
	private EmployeeStatisticsService employeeStatisticsService;

	@GetMapping(value = "/getempdepartstatByempId/{empid}")
	public ResponseEntity<EmployeeStatistics> findByempid(@PathVariable("empid") int empid) {
		return employeeStatisticsService.findByempid(empid);
	}

	@GetMapping(value = "/getEmployeeCount/{type}")
	public ResponseEntity<EmployeeCount> getEmployeeCount(@PathVariable("type") int type) {
		return employeeStatisticsService.getEmployeeCount(type);
	}

	@GetMapping(value = "/getUnitDetails")
	public ResponseEntity<List<UnitEmployeeDetails>> getUnitDetails() {
		return employeeStatisticsService.getUnitDetails();
	}

	@GetMapping(value = "/getEducationlevelCount")
	public ResponseEntity<List<EmployeeEducationlevelCount>> getEducationlevelCount() {
		return employeeStatisticsService.getEducationlevelCount();

	}

	@GetMapping(value = "/getEmployeeCount")
	public ResponseEntity<EmployeeCountAll> findAllCount() {
		return employeeStatisticsService.findAllCount();

	}

	@GetMapping(value = "/findAllGeneralCountPerUnit")
	public ResponseEntity<EmployeeGlobalCount> findAllGeneralCountPerUnit() {
		return employeeStatisticsService.findAllGeneralCountPerUnit();
	}

}
