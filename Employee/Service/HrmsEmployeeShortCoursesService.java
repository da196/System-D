package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.EmployeeApprovalComment;
import com.Hrms.Employee.DTO.HrmsEmployeeShortCoursesR;

@Service
public interface HrmsEmployeeShortCoursesService {
	public ResponseEntity<HrmsEmployeeShortCoursesR> addEmployeeShortCourses(
			HrmsEmployeeShortCoursesR hrmsEmployeeShortCoursesR);

	public ResponseEntity<HrmsEmployeeShortCoursesR> getEmployeeShortCoursesById(int id);

	public ResponseEntity<HrmsEmployeeShortCoursesR> updateEmployeeShortCourses(
			HrmsEmployeeShortCoursesR hrmsEmployeeShortCoursesR, int id);

	public ResponseEntity<?> deleteEmployeeShortCourses(int id);

	public ResponseEntity<List<HrmsEmployeeShortCoursesR>> listEmployeeShortCourses();

	public ResponseEntity<List<HrmsEmployeeShortCoursesR>> getEmployeeShortCoursesByEmpId(int empid);

	public ResponseEntity<?> approveOrRejectEmployeeShortCourses(EmployeeApprovalComment employeeApprovalComment,
			int id, int status);

	public ResponseEntity<List<HrmsEmployeeShortCoursesR>> listEmployeeShortCoursesNonApproved();

}
