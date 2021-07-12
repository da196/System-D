package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsPayrollContributionType;

@Service
public interface HrmsPayrollContributionTypeService {

	public ResponseEntity<HrmsPayrollContributionType> addPayrollContributionType(
			HrmsPayrollContributionType hrmsPayrollContributionType);

	public ResponseEntity<HrmsPayrollContributionType> getPayrollContributionTypeById(int id);

	public ResponseEntity<HrmsPayrollContributionType> updatePayrollContributionType(
			HrmsPayrollContributionType hrmsPayrollContributionType, int id);

	public ResponseEntity<?> deletePayrollContributionType(int id);

	public ResponseEntity<List<HrmsPayrollContributionType>> getAllPayrollContributionType();
}
