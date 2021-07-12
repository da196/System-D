package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmployeeActing;

@Service
public interface HrmsEmployeeActingService {
	public ResponseEntity<HrmsEmployeeActing> addEmployeeActing(HrmsEmployeeActing hrmsEmployeeActing);

	public ResponseEntity<Optional<HrmsEmployeeActing>> getEmployeeActing(int id);

	public ResponseEntity<HrmsEmployeeActing> updateEmployeeActing(HrmsEmployeeActing hrmsEmployeeActing, int id);

	public ResponseEntity<?> deleteHrmsEmployeeActing(int id);

	public ResponseEntity<List<HrmsEmployeeActing>> listHrmsEmployeeActing();

}
