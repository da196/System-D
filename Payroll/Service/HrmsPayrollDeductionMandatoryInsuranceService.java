package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.DTO.PayrollDeductionMandatoryInsuranceResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollDeductionMandatoryInsurance;

@Service
public interface HrmsPayrollDeductionMandatoryInsuranceService {

	public ResponseEntity<PayrollDeductionMandatoryInsuranceResponse> getDeductionMandatoryInsuranceById(int id);

	public ResponseEntity<PayrollDeductionMandatoryInsuranceResponse> getDeductionMandatoryInsuranceByPayrollId(
			int payrollId);

	public ResponseEntity<List<PayrollDeductionMandatoryInsuranceResponse>> getDeductionMandatoryInsuranceByEmpId(
			int empId);

	public ResponseEntity<HrmsPayrollDeductionMandatoryInsurance> updateDeductionMandatoryInsurance(
			HrmsPayrollDeductionMandatoryInsurance hrmsPayrollDeductionMandatoryInsurance, int id);

	public ResponseEntity<?> deleteDeductionMandatoryInsurance(int id);

	public ResponseEntity<List<PayrollDeductionMandatoryInsuranceResponse>> getAllDeductionMandatoryInsurance();

}
