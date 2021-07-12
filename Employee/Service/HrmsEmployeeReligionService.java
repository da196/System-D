package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmployeeReligion;

@Service
public interface HrmsEmployeeReligionService {
	public ResponseEntity<HrmsEmployeeReligion> save(HrmsEmployeeReligion hrmsEmployeeReligion);

	public ResponseEntity<Optional<HrmsEmployeeReligion>> viewHrmsEmployeeReligion(int id);

	public ResponseEntity<HrmsEmployeeReligion> update(HrmsEmployeeReligion hrmsEmployeeReligion, int id);

	public ResponseEntity<?> deleteHrmsEmployeeReligion(int id);

	public ResponseEntity<List<HrmsEmployeeReligion>> listHrmsEmployeeReligion();

}
