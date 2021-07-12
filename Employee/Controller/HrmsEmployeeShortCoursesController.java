package com.Hrms.Employee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.DTO.EmployeeApprovalComment;
import com.Hrms.Employee.DTO.HrmsEmployeeShortCoursesR;
import com.Hrms.Employee.Service.HrmsEmployeeShortCoursesService;

@RestController
@RequestMapping("/v1/EmployeeShortCourses")
public class HrmsEmployeeShortCoursesController {

	@Autowired
	private HrmsEmployeeShortCoursesService hrmsEmployeeShortCoursesService;

	@PostMapping(value = "/addEmployeeShortCourse")
	public ResponseEntity<HrmsEmployeeShortCoursesR> addEmployeeShortCourse(
			@RequestBody HrmsEmployeeShortCoursesR hrmsEmployeeShortCoursesR) {

		return hrmsEmployeeShortCoursesService.addEmployeeShortCourses(hrmsEmployeeShortCoursesR);
	}

	@GetMapping(value = "/getEmployeeShortCourseById/{id}")
	public ResponseEntity<HrmsEmployeeShortCoursesR> getEmployeeShortCourseById(@PathVariable("id") int id) {

		return hrmsEmployeeShortCoursesService.getEmployeeShortCoursesById(id);

	}

	@GetMapping(value = "/getEmployeeShortCoursesByEmpId/{empid}")
	public ResponseEntity<List<HrmsEmployeeShortCoursesR>> getEmployeeShortCoursesByEmpId(
			@PathVariable("empid") int empid) {
		return hrmsEmployeeShortCoursesService.getEmployeeShortCoursesByEmpId(empid);
	}

	@DeleteMapping(value = "/deleteEmployeeShortCourse/{id}")
	public ResponseEntity<?> deleteEmployeeShortCourse(@PathVariable("id") int id) {
		return hrmsEmployeeShortCoursesService.deleteEmployeeShortCourses(id);
	}

	@PutMapping(value = "/updateEmployeeShortCourse/{id}")
	public ResponseEntity<HrmsEmployeeShortCoursesR> updateEmployeeShortCourse(
			@RequestBody HrmsEmployeeShortCoursesR hrmsEmployeeShortCoursesR, @PathVariable("id") int id) {

		return hrmsEmployeeShortCoursesService.updateEmployeeShortCourses(hrmsEmployeeShortCoursesR, id);

	}

	@GetMapping(value = "/listEmployeeShortCourses")
	public ResponseEntity<List<HrmsEmployeeShortCoursesR>> listEmployeeShortCourses() {
		return hrmsEmployeeShortCoursesService.listEmployeeShortCourses();
	}

	@PutMapping(value = "/approveOrRejectEmployeeShortCourses/{id}/{status}")
	public ResponseEntity<?> approveOrRejectEmployeeShortCourses(
			@RequestBody EmployeeApprovalComment employeeApprovalComment, @PathVariable("id") int id,
			@PathVariable("status") int status) {
		return hrmsEmployeeShortCoursesService.approveOrRejectEmployeeShortCourses(employeeApprovalComment, id, status);
	}

	@GetMapping(value = "/listEmployeeShortCoursesNonApproved")
	public ResponseEntity<List<HrmsEmployeeShortCoursesR>> listEmployeeShortCoursesNonApproved() {
		return hrmsEmployeeShortCoursesService.listEmployeeShortCoursesNonApproved();
	}

}
