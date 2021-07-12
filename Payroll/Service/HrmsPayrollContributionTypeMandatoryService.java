package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsPayrollContributionTypeMandatory;

@Service
public interface HrmsPayrollContributionTypeMandatoryService {

	public ResponseEntity<HrmsPayrollContributionTypeMandatory> addPayrollContributionTypeMandatory(
			HrmsPayrollContributionTypeMandatory hrmsPayrollContributionTypeMandatory);

	public ResponseEntity<HrmsPayrollContributionTypeMandatory> getPayrollContributionTypeMandatoryById(int id);

	public ResponseEntity<HrmsPayrollContributionTypeMandatory> updatePayrollContributionTypeMandatory(
			HrmsPayrollContributionTypeMandatory hrmsPayrollContributionTypeMandatory, int id);

	public ResponseEntity<?> deletePayrollContributionTypeMandatory(int id);

	public ResponseEntity<List<HrmsPayrollContributionTypeMandatory>> getAllPayrollContributionTypeMandatory();

}
