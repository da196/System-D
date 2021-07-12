package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.DTO.OneThirdBalance;
import com.Hrms.Payroll.DTO.PayrollLoanPaymentAmount;
import com.Hrms.Payroll.DTO.PayrollLoanTypeMaxAmountResponse;
import com.Hrms.Payroll.DTO.PayrollLoanTypeResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollLoanType;

@Service
public interface HrmsPayrollLoanTypeService {
	public ResponseEntity<HrmsPayrollLoanType> addPayrollLoanType(HrmsPayrollLoanType hrmsPayrollLoanType);

	public ResponseEntity<PayrollLoanTypeResponse> getPayrollLoanTypeById(int id);

	public ResponseEntity<HrmsPayrollLoanType> updatePayrollLoanType(HrmsPayrollLoanType hrmsPayrollLoanType, int id);

	public ResponseEntity<?> deletePayrollLoanType(int id);

	public ResponseEntity<List<PayrollLoanTypeResponse>> getAllPayrollLoanType();

	public ResponseEntity<PayrollLoanTypeMaxAmountResponse> getMaxLoanAmount(int empid, int loantypeid);

	public ResponseEntity<PayrollLoanPaymentAmount> getPaymentAmount(int loantypeid, Double loanAmount, int duration);

	public ResponseEntity<List<PayrollLoanTypeResponse>> getPayrollLoanTypeByLenderId(int lenderid);

	public ResponseEntity<OneThirdBalance> getOneThirdofBasicAfterDeduction(int empid);

}
