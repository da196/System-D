package com.Hrms.Payroll.Service;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.DTO.PayrollProcessResponse;
import com.Hrms.Payroll.DTO.PayrollResponse;
import com.Hrms.Payroll.DTO.PayrollResponses;
import com.Hrms.Payroll.DTO.PayrollResponsesv2;

@Service
public interface HrmsPayrollService {
	public ResponseEntity<?> processPayrollPerPeriod(Date period);

	public ResponseEntity<PayrollResponse> getPayrollByPeriodAndEmpId(int year, int month, int empId);

	public ResponseEntity<PayrollResponse> getPayrollByPayrollId(int payrollid);

	public ResponseEntity<List<PayrollResponse>> getPayrollByEmpId(int empid);

	public ResponseEntity<?> deletePayrollByPayrollId(int payrollid);

	public ResponseEntity<List<PayrollResponse>> getAllPayroll();

	public ResponseEntity<PayrollResponses> getPayrollByPeriod(int year, int month);

	public ResponseEntity<PayrollProcessResponse> processPayrollPerPeriodV2(int year, int month);

	public ResponseEntity<PayrollResponsesv2> getPayrollByPeriodV2(int year, int month);

	public ResponseEntity<PayrollProcessResponse> processPayrollPerPeriodByEmpId(int year, int month, int EmpId);

}
