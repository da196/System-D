package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmployeeDesignation;

@Service
public interface HrmsEmployeeDesignationService {

	public ResponseEntity<HrmsEmployeeDesignation> save(HrmsEmployeeDesignation hrmsEmployeeDesignation);

	public ResponseEntity<Optional<HrmsEmployeeDesignation>> viewHrmsEmployeeDesignation(int id);

	public ResponseEntity<HrmsEmployeeDesignation> update(HrmsEmployeeDesignation hrmsEmployeeDesignation, int id);

	public ResponseEntity<?> deleteHrmsEmployeeDesignation(int id);

	public ResponseEntity<List<HrmsEmployeeDesignation>> listHrmsEmployeeDesignation();

}
