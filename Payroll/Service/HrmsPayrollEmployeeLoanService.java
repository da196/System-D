package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.DTO.EmployeeLoanResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollEmployeeLoan;

@Service
public interface HrmsPayrollEmployeeLoanService {

	public ResponseEntity<HrmsPayrollEmployeeLoan> addPayrollEmployeeLoan(
			HrmsPayrollEmployeeLoan hrmsPayrollEmployeeLoan);

	public ResponseEntity<EmployeeLoanResponse> getPayrollEmployeeLoanById(int id);

	public ResponseEntity<List<EmployeeLoanResponse>> getPayrollEmployeeLoanByEmpId(int EmpId);

	public ResponseEntity<HrmsPayrollEmployeeLoan> updatePayrollEmployeeLoan(
			HrmsPayrollEmployeeLoan hrmsPayrollEmployeeLoan, int id, Double durationAdjust);

	public ResponseEntity<?> deletePayrollEmployeeLoan(int id);

	public ResponseEntity<List<EmployeeLoanResponse>> getAllPayrollEmployeeLoan();

	public ResponseEntity<List<EmployeeLoanResponse>> getPayrollEmployeeLoanByEmpIdAndStatus(int EmpId, int status);

	public ResponseEntity<List<EmployeeLoanResponse>> getPayrollEmployeeLoanByStatus(int status);

}
