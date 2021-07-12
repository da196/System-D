package com.Hrms.Perfomance.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceEabQualityResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEabQuality;

@Service
public interface HrmsPerformanceEabQualityService {

	public ResponseEntity<HrmsPerformanceEabQuality> addPerformanceEabQuality(
			HrmsPerformanceEabQuality hrmsPerformanceEabQuality);

	public ResponseEntity<PerformanceEabQualityResponse> getPerformanceEabQualityById(int id);

	public ResponseEntity<HrmsPerformanceEabQuality> updatePerformanceEabQuality(
			HrmsPerformanceEabQuality hrmsPerformanceEabQuality, int id);

	public ResponseEntity<?> deletePerformanceEabQuality(int id);

	public ResponseEntity<List<PerformanceEabQualityResponse>> getAllPerformanceEabQuality();

	public ResponseEntity<List<PerformanceEabQualityResponse>> getAllPerformanceEabQualityByFactorId(int factorid);

}
