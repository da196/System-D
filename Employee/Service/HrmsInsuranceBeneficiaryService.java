package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsInsuranceBeneficiary;

@Service
public interface HrmsInsuranceBeneficiaryService {

	public ResponseEntity<HrmsInsuranceBeneficiary> addInsuranceBeneficiary(
			HrmsInsuranceBeneficiary hrmsInsuranceBeneficiary);

	public ResponseEntity<HrmsInsuranceBeneficiary> updateInsuranceBeneficiary(
			HrmsInsuranceBeneficiary hrmsInsuranceBeneficiary, int id);

	public ResponseEntity<List<HrmsInsuranceBeneficiary>> getInsuranceBeneficiaryByEmpId(int empid);

	public ResponseEntity<HrmsInsuranceBeneficiary> getInsuranceBeneficiaryById(int id);

	public ResponseEntity<?> deleteEmployeeBirthDetails(int id);

}
