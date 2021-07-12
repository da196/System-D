package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmploymentStatus;

@Service
public interface HrmsEmploymentStatusService {

	public ResponseEntity<HrmsEmploymentStatus> addEmploymentStatus(HrmsEmploymentStatus hrmsEmploymentStatus);

	public ResponseEntity<Optional<HrmsEmploymentStatus>> getEmploymentStatus(int id);

	public ResponseEntity<HrmsEmploymentStatus> updateEmploymentStatus(HrmsEmploymentStatus hrmsEmploymentStatus,
			int id);

	public ResponseEntity<?> deleteEmploymentStatus(int id);

	public ResponseEntity<List<HrmsEmploymentStatus>> listEmploymentStatus();

}
