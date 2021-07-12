package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsPayrollLoanLender;

@Service
public interface HrmsPayrollLoanLenderService {
	public ResponseEntity<HrmsPayrollLoanLender> addPayrollLoanLender(HrmsPayrollLoanLender hrmsPayrollLoanLender);

	public ResponseEntity<HrmsPayrollLoanLender> getPayrollLoanLenderById(int id);

	public ResponseEntity<HrmsPayrollLoanLender> updatePayrollLoanLender(HrmsPayrollLoanLender hrmsPayrollLoanLender,
			int id);

	public ResponseEntity<?> deletePayrollLoanLender(int id);

	public ResponseEntity<List<HrmsPayrollLoanLender>> getAllPayrollLoanLender();
}
