package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.DTO.PayrollContributionVoluntaryResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollContributionVoluntary;

@Service
public interface HrmsPayrollContributionVoluntaryService {

	public ResponseEntity<HrmsPayrollContributionVoluntary> addPayrollContributionVoluntary(
			HrmsPayrollContributionVoluntary hrmsPayrollContributionVoluntary);

	public ResponseEntity<PayrollContributionVoluntaryResponse> getPayrollContributionVoluntaryById(int id);

	public ResponseEntity<HrmsPayrollContributionVoluntary> updatePayrollContributionVoluntary(
			HrmsPayrollContributionVoluntary hrmsPayrollContributionVoluntary, int id);

	public ResponseEntity<?> deletePayrollContributionVoluntary(int id);

	public ResponseEntity<List<PayrollContributionVoluntaryResponse>> getAllPayrollContributionVoluntary();

	public ResponseEntity<List<PayrollContributionVoluntaryResponse>> getPayrollContributionVoluntaryByEmpId(int EmpId);

}
