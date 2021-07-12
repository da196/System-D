package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsPayrollContributionTypeVoluntary;

@Service
public interface HrmsPayrollContributionTypeVoluntaryService {
	public ResponseEntity<HrmsPayrollContributionTypeVoluntary> addPayrollContributionTypeVoluntary(
			HrmsPayrollContributionTypeVoluntary hrmsPayrollContributionTypeVoluntary);

	public ResponseEntity<HrmsPayrollContributionTypeVoluntary> getPayrollContributionTypeVoluntaryById(int id);

	public ResponseEntity<HrmsPayrollContributionTypeVoluntary> updatePayrollContributionTypeVoluntary(
			HrmsPayrollContributionTypeVoluntary hrmsPayrollContributionTypeVoluntary, int id);

	public ResponseEntity<?> deletePayrollContributionTypeVoluntary(int id);

	public ResponseEntity<List<HrmsPayrollContributionTypeVoluntary>> getAllPayrollContributionTypeVoluntary();

	public ResponseEntity<List<HrmsPayrollContributionTypeVoluntary>> getContributionTypeVoluntaryByServiceProviderId(
			int serviceproviderid);
}
