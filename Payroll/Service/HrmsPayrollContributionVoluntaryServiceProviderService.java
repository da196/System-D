package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsPayrollContributionVoluntaryServiceProvider;

@Service
public interface HrmsPayrollContributionVoluntaryServiceProviderService {

	public ResponseEntity<HrmsPayrollContributionVoluntaryServiceProvider> addContributionVoluntaryServiceProvider(
			HrmsPayrollContributionVoluntaryServiceProvider hrmsPayrollContributionVoluntaryServiceProvider);

	public ResponseEntity<HrmsPayrollContributionVoluntaryServiceProvider> getContributionVoluntaryServiceProviderById(
			int id);

	public ResponseEntity<HrmsPayrollContributionVoluntaryServiceProvider> updateContributionVoluntaryServiceProvider(
			HrmsPayrollContributionVoluntaryServiceProvider hrmsPayrollContributionVoluntaryServiceProvider, int id);

	public ResponseEntity<?> deleteContributionVoluntaryServiceProvider(int id);

	public ResponseEntity<List<HrmsPayrollContributionVoluntaryServiceProvider>> getAllContributionVoluntaryServiceProvider();

}
