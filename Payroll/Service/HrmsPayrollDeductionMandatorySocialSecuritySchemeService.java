package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.DTO.PayrollDeductionMandatorySocialSecuritySchemeResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollDeductionMandatorySocialSecurityScheme;

@Service
public interface HrmsPayrollDeductionMandatorySocialSecuritySchemeService {
	public ResponseEntity<PayrollDeductionMandatorySocialSecuritySchemeResponse> getDeductionMandatorySocialSecuritySchemeById(
			int id);

	public ResponseEntity<PayrollDeductionMandatorySocialSecuritySchemeResponse> getDeductionMandatorySocialSecuritySchemeByPayrollId(
			int payrollId);

	public ResponseEntity<List<PayrollDeductionMandatorySocialSecuritySchemeResponse>> getDeductionMandatorySocialSecuritySchemeByEmpId(
			int empId);

	public ResponseEntity<HrmsPayrollDeductionMandatorySocialSecurityScheme> updateDeductionMandatorySocialSecurityScheme(
			HrmsPayrollDeductionMandatorySocialSecurityScheme hrmsPayrollDeductionMandatorySocialSecurityScheme,
			int id);

	public ResponseEntity<?> deleteDeductionMandatorySocialSecurityScheme(int id);

	public ResponseEntity<List<PayrollDeductionMandatorySocialSecuritySchemeResponse>> getAllDeductionMandatorySocialSecurityScheme();

}
