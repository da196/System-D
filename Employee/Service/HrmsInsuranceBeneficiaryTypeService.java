package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsInsuranceBeneficiaryType;

@Service
public interface HrmsInsuranceBeneficiaryTypeService {

	public ResponseEntity<HrmsInsuranceBeneficiaryType> addInsuranceBeneficiaryType(
			HrmsInsuranceBeneficiaryType hrmsInsuranceBeneficiaryType);

	public ResponseEntity<Optional<HrmsInsuranceBeneficiaryType>> getInsuranceBeneficiaryType(int id);

	public ResponseEntity<HrmsInsuranceBeneficiaryType> updateInsuranceBeneficiaryType(
			HrmsInsuranceBeneficiaryType hrmsInsuranceBeneficiaryType, int id);

	public ResponseEntity<?> deleteInsuranceBeneficiaryType(int id);

	public ResponseEntity<List<HrmsInsuranceBeneficiaryType>> listInsuranceBeneficiaryType();

}
