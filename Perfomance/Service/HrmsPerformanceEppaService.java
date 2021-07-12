package com.Hrms.Perfomance.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceEppaExtraResponse;
import com.Hrms.Perfomance.DTO.PerformanceEppaPost;
import com.Hrms.Perfomance.DTO.PerformanceEppaResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppa;

@Service
public interface HrmsPerformanceEppaService {

	public ResponseEntity<HrmsPerformanceEppa> addPerformanceEppa(HrmsPerformanceEppa hrmsPerformanceEppa);

	public ResponseEntity<PerformanceEppaPost> addPerformanceEppaV2(PerformanceEppaPost performanceEppaPost);

	public ResponseEntity<PerformanceEppaResponse> getPerformanceEppaById(int id);

	public ResponseEntity<PerformanceEppaExtraResponse> getPerformanceEppaV2ById(int id);

	public ResponseEntity<HrmsPerformanceEppa> updatePerformanceEppa(HrmsPerformanceEppa hrmsPerformanceEppa, int id);

	public ResponseEntity<?> deletePerformanceEppa(int id);

	public ResponseEntity<List<PerformanceEppaResponse>> getAllPerformanceEppa();

	public ResponseEntity<List<PerformanceEppaExtraResponse>> getAllPerformanceEppaV2();

	public ResponseEntity<List<PerformanceEppaResponse>> getAllPerformanceEppaByOutputId(int outputid);

	public ResponseEntity<List<PerformanceEppaResponse>> getAllPerformanceEppaByFinancialYearId(int financialyearid);

	public ResponseEntity<List<PerformanceEppaResponse>> getAllPerformanceEppaByEmployeeIdAndFinancialYearId(
			int employeeid, int financialyearid);

	public ResponseEntity<List<PerformanceEppaExtraResponse>> getAllPerformanceEppaV2ByEmployeeIdAndFinancialYearId(
			int employeeid, int financialyearid);

	public ResponseEntity<List<PerformanceEppaExtraResponse>> getAllPerformanceEppaV2BySupervisorIdAndFinancialYearId(
			int supervisorid, int financialyearid);

	public ResponseEntity<List<PerformanceEppaExtraResponse>> getAllPerformanceEppaV2BySupervisorId(int supervisorid);

	public ResponseEntity<List<PerformanceEppaExtraResponse>> getAllPerformanceEppaV2ByEmployeeIdAndStartYearAndEndYear(
			int employeeid, int startYear, int endYear);

	public ResponseEntity<PerformanceEppaExtraResponse> getAllPerformanceEppaV3ByEmployeeIdAndFinancialYearId(
			int employeeid, int financialyearid);

}
