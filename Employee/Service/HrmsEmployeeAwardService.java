package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmployeeAward;

@Service
public interface HrmsEmployeeAwardService {

	public ResponseEntity<HrmsEmployeeAward> addEmployeeAward(HrmsEmployeeAward hrmsEmployeeAward);

	public ResponseEntity<Optional<HrmsEmployeeAward>> getEmployeeAward(int id);

	public ResponseEntity<HrmsEmployeeAward> updateEmployeeAward(HrmsEmployeeAward HrmsEmployeeAward, int id);

	public ResponseEntity<?> deleteEmployeeAward(int id);

	public ResponseEntity<List<HrmsEmployeeAward>> listEmployeeAward();

}
