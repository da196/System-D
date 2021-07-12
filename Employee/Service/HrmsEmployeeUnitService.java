package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmployeeUnit;

@Service
public interface HrmsEmployeeUnitService {

	public ResponseEntity<HrmsEmployeeUnit> save(HrmsEmployeeUnit hrmsEmployeeUnit);

	public ResponseEntity<Optional<HrmsEmployeeUnit>> viewHrmsEmployeeUnit(int id);

	public ResponseEntity<HrmsEmployeeUnit> update(HrmsEmployeeUnit hrmsEmployeeUnit, int id);

	public ResponseEntity<?> deleteHrmsEmployeeUnit(int id);

	public ResponseEntity<List<HrmsEmployeeUnit>> listHrmsEmployeeUnit();

}
