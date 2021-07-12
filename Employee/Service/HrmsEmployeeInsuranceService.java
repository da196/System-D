package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.EmployeeInsuranceResponse;
import com.Hrms.Employee.Entity.HrmsEmployeeInsurance;

@Service
public interface HrmsEmployeeInsuranceService {

	public ResponseEntity<HrmsEmployeeInsurance> addEmployeeInsurance(HrmsEmployeeInsurance hrmsEmployeeInsurance);

	public ResponseEntity<List<HrmsEmployeeInsurance>> addEmployeeInsuranceList(
			List<HrmsEmployeeInsurance> hrmsEmployeeInsurancelist);

	public ResponseEntity<EmployeeInsuranceResponse> getEmployeeInsuranceById(int id);

	public ResponseEntity<HrmsEmployeeInsurance> updateEmployeeInsurance(HrmsEmployeeInsurance hrmsEmployeeInsurance,
			int id);

	public ResponseEntity<?> deleteEmployeeInsurance(int id);

	public ResponseEntity<List<EmployeeInsuranceResponse>> listEmployeeInsurance();

	public ResponseEntity<List<EmployeeInsuranceResponse>> getEmployeeInsuranceByEmpId(int EmpId);

}
