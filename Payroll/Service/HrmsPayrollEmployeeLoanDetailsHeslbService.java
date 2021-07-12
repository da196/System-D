package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsPayrollEmployeeLoanDetailsHeslb;

@Service
public interface HrmsPayrollEmployeeLoanDetailsHeslbService {

	public ResponseEntity<HrmsPayrollEmployeeLoanDetailsHeslb> addPayrollEmployeeLoanDetailsHeslb(
			HrmsPayrollEmployeeLoanDetailsHeslb hrmsPayrollEmployeeLoanDetailsHeslb);

	public ResponseEntity<HrmsPayrollEmployeeLoanDetailsHeslb> getPayrollEmployeeLoanDetailsHeslbById(int id);

	public ResponseEntity<HrmsPayrollEmployeeLoanDetailsHeslb> updatePayrollEmployeeLoanDetailsHeslb(
			HrmsPayrollEmployeeLoanDetailsHeslb hrmsPayrollEmployeeLoanDetailsHeslb, int id);

	public ResponseEntity<?> deletePayrollEmployeeLoanDetailsHeslb(int id);

	public ResponseEntity<List<HrmsPayrollEmployeeLoanDetailsHeslb>> getAllPayrollEmployeeLoanDetailsHeslb();

	public ResponseEntity<HrmsPayrollEmployeeLoanDetailsHeslb> getPayrollEmployeeLoanDetailsHeslbByLoanId(int loanId);

}
