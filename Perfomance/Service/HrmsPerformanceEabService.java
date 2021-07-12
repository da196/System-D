package com.Hrms.Perfomance.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceEabFactorOprasResponse;
import com.Hrms.Perfomance.DTO.PerformanceEabResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEab;

@Service

public interface HrmsPerformanceEabService {

	public ResponseEntity<HrmsPerformanceEab> addPerformanceEab(HrmsPerformanceEab hrmsPerformanceEab);

	public ResponseEntity<PerformanceEabResponse> getPerformanceEabById(int id);

	public ResponseEntity<HrmsPerformanceEab> updatePerformanceEab(HrmsPerformanceEab hrmsPerformanceEab, int id);

	public ResponseEntity<?> deletePerformanceEab(int id);

	public ResponseEntity<List<PerformanceEabResponse>> getAllPerformanceEab();

	public ResponseEntity<List<PerformanceEabResponse>> getAllPerformanceEabByQualityidId(int qualityid);

	public ResponseEntity<List<PerformanceEabResponse>> getAllPerformanceEabByEppaId(int eppaid);

	public ResponseEntity<List<PerformanceEabResponse>> getAllPerformanceEabByEmployeeId(int employeeid);

	public ResponseEntity<List<PerformanceEabFactorOprasResponse>> getAllPerformanceEabByEppaIdV2(int eppaid);

}
