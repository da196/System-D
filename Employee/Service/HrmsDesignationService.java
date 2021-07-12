package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.DesignationResponse;
import com.Hrms.Employee.Entity.HrmsDesignation;

@Service
public interface HrmsDesignationService {

	public ResponseEntity<HrmsDesignation> save(HrmsDesignation hrmsDesignation);

	public ResponseEntity<Optional<HrmsDesignation>> viewHrmsDesignation(int id);

	public ResponseEntity<HrmsDesignation> update(HrmsDesignation hrmsDesignation, int id);

	public ResponseEntity<?> deleteHrmsDesignation(int id);

	public ResponseEntity<List<HrmsDesignation>> listHrmsDesignation();

	public ResponseEntity<List<HrmsDesignation>> listHrmsDesignationApprover();

	public ResponseEntity<List<DesignationResponse>> listDesignationResponse();

	public ResponseEntity<List<HrmsDesignation>> listHrmsDesignationSupervisorByDepartmentId(int departmentId);

}
