package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsInsuranceType;

@Service
public interface HrmsInsuranceTypeService {
	public ResponseEntity<HrmsInsuranceType> addInsuranceType(HrmsInsuranceType hrmsInsuranceType);

	public ResponseEntity<HrmsInsuranceType> getInsuranceTypeById(int id);

	public ResponseEntity<HrmsInsuranceType> updateInsuranceType(HrmsInsuranceType hrmsInsuranceType, int id);

	public ResponseEntity<?> deleteInsuranceType(int id);

	public ResponseEntity<List<HrmsInsuranceType>> getAllInsuranceType();

}
