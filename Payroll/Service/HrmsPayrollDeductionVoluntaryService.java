package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.DTO.PayrollDeductionVoluntaryResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollDeductionVoluntary;

@Service
public interface HrmsPayrollDeductionVoluntaryService {

	public ResponseEntity<PayrollDeductionVoluntaryResponse> getDeductionVoluntaryById(int id);

	public ResponseEntity<List<PayrollDeductionVoluntaryResponse>> getDeductionVoluntaryByPayrollId(int payrollId);

	public ResponseEntity<List<PayrollDeductionVoluntaryResponse>> getDeductionVoluntaryByEmpId(int empId);

	public ResponseEntity<HrmsPayrollDeductionVoluntary> updatePayrollDeductionVoluntary(
			HrmsPayrollDeductionVoluntary hrmsPayrollDeductionVoluntary, int id);

	public ResponseEntity<?> deleteDeductionVoluntary(int id);

	public ResponseEntity<List<PayrollDeductionVoluntaryResponse>> getAllDeductionVoluntary();

}
