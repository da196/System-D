package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.DTO.PayrollContributionMandatoryInsuranceResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollContributionMandatoryInsurance;

@Service
public interface HrmsPayrollContributionMandatoryInsuranceService {

	public ResponseEntity<HrmsPayrollContributionMandatoryInsurance> addPayrollContributionMandatoryInsurance(
			HrmsPayrollContributionMandatoryInsurance hrmsPayrollContributionMandatoryInsurance);

	public ResponseEntity<PayrollContributionMandatoryInsuranceResponse> getPayrollContributionMandatoryInsuranceById(
			int id);

	public ResponseEntity<HrmsPayrollContributionMandatoryInsurance> updatePayrollContributionMandatoryInsurance(
			HrmsPayrollContributionMandatoryInsurance hrmsPayrollContributionMandatoryInsurance, int id);

	public ResponseEntity<?> deletePayrollContributionMandatoryInsurance(int id);

	public ResponseEntity<List<PayrollContributionMandatoryInsuranceResponse>> getAllPayrollContributionMandatoryInsurance();

}
