package com.Hrms.Perfomance.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.Entity.HrmsPerformanceFinancialYear;

@Service
public interface HrmsPerformanceFinancialYearService {

	public ResponseEntity<HrmsPerformanceFinancialYear> addPerformanceFinancialYear(
			HrmsPerformanceFinancialYear hrmsPerformanceFinancialYear);

	public ResponseEntity<HrmsPerformanceFinancialYear> getPerformanceFinancialYearById(int id);

	public ResponseEntity<HrmsPerformanceFinancialYear> updatePerformanceFinancialYear(
			HrmsPerformanceFinancialYear hrmsPerformanceFinancialYear, int id);

	public ResponseEntity<?> deletePerformanceFinancialYear(int id);

	public ResponseEntity<List<HrmsPerformanceFinancialYear>> getAllPerformanceFinancialYear();

}
