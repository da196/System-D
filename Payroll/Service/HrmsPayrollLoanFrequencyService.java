package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsPayrollLoanFrequency;

@Service
public interface HrmsPayrollLoanFrequencyService {

	public ResponseEntity<HrmsPayrollLoanFrequency> addPayrollLoanFrequency(
			HrmsPayrollLoanFrequency hrmsPayrollLoanFrequency);

	public ResponseEntity<HrmsPayrollLoanFrequency> getPayrollLoanFrequencyById(int id);

	public ResponseEntity<HrmsPayrollLoanFrequency> updatePayrollLoanFrequency(
			HrmsPayrollLoanFrequency hrmsPayrollLoanFrequency, int id);

	public ResponseEntity<?> deletePayrollLoanFrequency(int id);

	public ResponseEntity<List<HrmsPayrollLoanFrequency>> getAllPayrollLoanFrequency();

}
