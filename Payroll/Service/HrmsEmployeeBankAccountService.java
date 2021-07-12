package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.DTO.EmployeeBankAccount;
import com.Hrms.Payroll.Entity.HrmsEmployeeBankAccount;

@Service
public interface HrmsEmployeeBankAccountService {

	public ResponseEntity<HrmsEmployeeBankAccount> addEmployeeBankAccount(
			HrmsEmployeeBankAccount hrmsEmployeeBankAccount);

	public ResponseEntity<EmployeeBankAccount> getEmployeeBankAccountById(int id);

	public ResponseEntity<List<EmployeeBankAccount>> getEmployeeBankAccountByEmpId(int empId);

	public ResponseEntity<HrmsEmployeeBankAccount> updateEmployeeBankAccount(
			HrmsEmployeeBankAccount hrmsEmployeeBankAccount, int id);

	public ResponseEntity<?> deleteEmployeeBankAccount(int id);

	public ResponseEntity<List<EmployeeBankAccount>> getAllEmployeeBankAccount();

	public ResponseEntity<List<EmployeeBankAccount>> getAllEmployeeBankAccountNonApproved();

	public ResponseEntity<HrmsEmployeeBankAccount> ApproveEmployeeBankAccount(int id, int approverid, int status,
			String comment);
}
