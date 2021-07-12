package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.HrmsAllowanceResponse;
import com.Hrms.Employee.DTO.SalaryStructureedms;
import com.Hrms.Employee.Entity.HrmsAllowance;

@Service
public interface HrmsAllowanceService {

	public ResponseEntity<HrmsAllowance> save(HrmsAllowance hrmsAllowance);

	public ResponseEntity<Optional<HrmsAllowance>> viewHrmsHrmsAllowance(int id);

	public ResponseEntity<List<HrmsAllowance>> FindByDesignationId(int id);

	public ResponseEntity<HrmsAllowance> updateHrmsAllowance(HrmsAllowance hrmsAllowance, int id);

	public ResponseEntity<?> deleteHrmsAllowance(int id);

	public ResponseEntity<List<HrmsAllowance>> listHrmsAllowance();

	public ResponseEntity<List<HrmsAllowanceResponse>> listHrmsAllowanceDetailed();

	public ResponseEntity<List<HrmsAllowanceResponse>> getAllowancesTravel();

	public ResponseEntity<List<SalaryStructureedms>> getAllsalaryStructure();

}
