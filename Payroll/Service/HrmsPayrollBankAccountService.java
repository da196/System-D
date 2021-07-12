package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.DTO.PayrollBankAccount;
import com.Hrms.Payroll.Entity.HrmsPayrollBankAccount;

@Service
public interface HrmsPayrollBankAccountService {

	public ResponseEntity<HrmsPayrollBankAccount> addPayrollBankAccount(HrmsPayrollBankAccount hrmsPayrollBankAccount);

	public ResponseEntity<PayrollBankAccount> getPayrollBankAccountById(int id);

	public ResponseEntity<List<PayrollBankAccount>> getPayrollBankAccountByEmpIdAndPayrollId(int empId, int payrollId);

	public ResponseEntity<List<PayrollBankAccount>> getPayrollBankAccountByEmpId(int empId);

	public ResponseEntity<HrmsPayrollBankAccount> updatePayrollBankAccount(
			HrmsPayrollBankAccount hrmsPayrollBankAccount, int id);

	public ResponseEntity<?> deletePayrollBankAccount(int id);

	public ResponseEntity<List<PayrollBankAccount>> getAllPayrollBankAccount();
}
