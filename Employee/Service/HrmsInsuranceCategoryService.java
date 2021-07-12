package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsInsuranceCategory;

@Service
public interface HrmsInsuranceCategoryService {

	public ResponseEntity<HrmsInsuranceCategory> save(HrmsInsuranceCategory hrmsInsuranceCategory);

	public ResponseEntity<Optional<HrmsInsuranceCategory>> getHrmsInsuranceCategory(int id);

	public ResponseEntity<HrmsInsuranceCategory> updateInsuranceCategory(HrmsInsuranceCategory hrmsInsuranceCategory,
			int id);

	public ResponseEntity<?> deleteInsuranceCategory(int id);

	public ResponseEntity<List<HrmsInsuranceCategory>> listInsuranceCategory();
}
