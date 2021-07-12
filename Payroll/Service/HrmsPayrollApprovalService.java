package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsPayrollApproval;

@Service
public interface HrmsPayrollApprovalService {

	public ResponseEntity<HrmsPayrollApproval> approvePayroll(HrmsPayrollApproval hrmsPayrollApproval, int payrollId,
			int status);

	public ResponseEntity<?> getPayrollApprovalById(int id);

	public ResponseEntity<HrmsPayrollApproval> updatePayrollApproval(HrmsPayrollApproval hrmsPayrollApproval, int id,
			int status);

	public ResponseEntity<?> deletePayrollApproval(int id);

	public ResponseEntity<List<HrmsPayrollApproval>> getAllPayrollApproval();

	public ResponseEntity<List<?>> getAllPayrollApprovalByPayrollId(int payrollId);

}
