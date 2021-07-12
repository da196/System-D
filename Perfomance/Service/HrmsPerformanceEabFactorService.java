package com.Hrms.Perfomance.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceEabFactorWithQualitiesResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEabFactor;

@Service
public interface HrmsPerformanceEabFactorService {

	public ResponseEntity<HrmsPerformanceEabFactor> addPerformanceEabFactor(
			HrmsPerformanceEabFactor hrmsPerformanceEabFactor);

	public ResponseEntity<HrmsPerformanceEabFactor> getPerformanceEabFactorById(int id);

	public ResponseEntity<HrmsPerformanceEabFactor> updatePerformanceEabFactor(
			HrmsPerformanceEabFactor hrmsPerformanceEabFactor, int id);

	public ResponseEntity<?> deletePerformanceEabFactor(int id);

	public ResponseEntity<List<HrmsPerformanceEabFactor>> getAllPerformanceEabFactor();

	public ResponseEntity<List<PerformanceEabFactorWithQualitiesResponse>> getAllPerformanceEabFactorWithQualities();

}
