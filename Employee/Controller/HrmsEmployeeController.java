package com.Hrms.Employee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.DTO.EmployeeForVideoConference;
import com.Hrms.Employee.DTO.EmployeeGeneralRequest;
import com.Hrms.Employee.DTO.EmployeeResponse;
import com.Hrms.Employee.DTO.EmployeeResponseDetailed;
import com.Hrms.Employee.DTO.EmployeeResponseEdms;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.HrmsEmployeeRead;
import com.Hrms.Employee.Repository.HrmsEmployeeReadRepository;
import com.Hrms.Employee.Service.HrmsEmployeeService;

@RestController
@RequestMapping("/v1/hrmsEmployee")
public class HrmsEmployeeController {
	@Autowired
	private HrmsEmployeeService hrmsEmployeeService;

	@Autowired
	private HrmsEmployeeReadRepository hrmsEmployeeReadRepository;

	@PostMapping(value = "/addEmployee")
	public ResponseEntity<HrmsEmployee> addEmployee(@Validated @RequestBody HrmsEmployee hrmsEmployee) {

		return hrmsEmployeeService.save(hrmsEmployee);

	}

	@PostMapping(value = "/addEmployeeDetails")
	public ResponseEntity<EmployeeGeneralRequest> addEmployeeDetails(
			@Validated @RequestBody EmployeeGeneralRequest employeeGeneralRequest) {
		return hrmsEmployeeService.addEmployeeDetails(employeeGeneralRequest);
	}

	@GetMapping(value = "/getEmployee/{id}")
	public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable("id") int id) {

		return hrmsEmployeeService.viewHrmsEmployee(id);
	}

	@GetMapping(value = "/getEmployeeByEmail/{email}")
	public ResponseEntity<EmployeeResponse> getEmployeeByEmail(@PathVariable("email") String email) {

		return hrmsEmployeeService.getEmployeeByEmail(email);
	}

	@GetMapping(value = "/getEmployeeByEmailEdms/{email}")
	public ResponseEntity<EmployeeResponseEdms> getEmployeeByEmailEdms(@PathVariable("email") String email) {

		return hrmsEmployeeService.getEmployeeByEmailEdms(email);
	}

	@PutMapping(value = "/updateEmployee/{id}")
	public ResponseEntity<HrmsEmployee> updateEmployee(@RequestBody HrmsEmployee hrmsEmployee,
			@PathVariable("id") int id) {
		return hrmsEmployeeService.update(hrmsEmployee, id);

	}

	@PutMapping(value = "/updateEmployeeDetails/{empid}")
	public ResponseEntity<EmployeeGeneralRequest> updateEmployeeDetails(
			@Validated @RequestBody EmployeeGeneralRequest employeeGeneralRequest, @PathVariable("empid") int empid) {
		return hrmsEmployeeService.updateEmployeeDetails(employeeGeneralRequest, empid);
	}

	@DeleteMapping(value = "/deleteEmployee/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable("id") int id) {
		return hrmsEmployeeService.deleteHrmsEmployee(id);

	}

	@GetMapping(value = "/getAllEmployee")
	public ResponseEntity<List<EmployeeResponse>> listEmployee() {
		return hrmsEmployeeService.listHrmsEmployee();
	}

	@GetMapping(value = "/getAllEmployeeReport")
	public ResponseEntity<List<HrmsEmployeeRead>> ReportEmployee() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeReadRepository.findAll());

	}

	@GetMapping(value = "/getEmployeeDetailsById/{id}")
	public ResponseEntity<EmployeeResponseDetailed> getEmployeeDetailsById(@PathVariable("id") int id) {

		return hrmsEmployeeService.getEmployeeDetailsById(id);

	}

	@PutMapping(value = "/updatePhoto/{empId}/{PhotoUris}")
	public ResponseEntity<HrmsEmployee> updatePhoto(@PathVariable("empId") int empId,
			@PathVariable("PhotoUris") List<String> PhotoUris) {
		String PhotoUri = "";
		if (PhotoUris != null) {
			PhotoUri = PhotoUris.get(0);
		}
		return hrmsEmployeeService.updatePhoto(empId, PhotoUri);
	}

	@GetMapping(value = "/getallemployeelessdetails")
	public ResponseEntity<List<HrmsEmployee>> getallemployeelessdetails() {
		return hrmsEmployeeService.getallemployeelessdetails();

	}

	@GetMapping(value = "/getAllEmployeeforVideoConference")
	public ResponseEntity<List<EmployeeForVideoConference>> getAllEmployeeforVideoConference() {

		return hrmsEmployeeService.getAllEmployeeforVideoConference();
	}

	@PutMapping(value = "/updateEmployeePhoto")
	public ResponseEntity<HrmsEmployee> updateEmployeePhoto(@RequestBody HrmsEmployee hrmsEmployee) {
		return hrmsEmployeeService.updateEmployeePhoto(hrmsEmployee);
	}

}
