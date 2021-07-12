package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsInsuranceCategory;
import com.Hrms.Employee.Repository.HrmsInsuranceCategoryRepository;

@Service
public class HrmsInsuranceCategoryServiceImpl implements HrmsInsuranceCategoryService {
	@Autowired
	private HrmsInsuranceCategoryRepository hrmsInsuranceCategoryRepository;

	@Override
	public ResponseEntity<HrmsInsuranceCategory> save(HrmsInsuranceCategory hrmsInsuranceCategory) {
		UUID uuid = UUID.randomUUID();
		hrmsInsuranceCategory.setUnique_id(uuid);
		hrmsInsuranceCategory.setActive(1);
		hrmsInsuranceCategory.setApproved(0);

		if (hrmsInsuranceCategoryRepository.existsByName(hrmsInsuranceCategory.getName())) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

		} else {

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsInsuranceCategoryRepository.save(hrmsInsuranceCategory));
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsInsuranceCategory>> getHrmsInsuranceCategory(int id) {
		if (hrmsInsuranceCategoryRepository.existsById(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsInsuranceCategoryRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsInsuranceCategory> updateInsuranceCategory(HrmsInsuranceCategory hrmsInsuranceCategory,
			int id) {
		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsInsuranceCategory.setApproved(0);
		hrmsInsuranceCategory.setDate_updated(LocalTime);
		if (hrmsInsuranceCategoryRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsInsuranceCategoryRepository.save(hrmsInsuranceCategory));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsInsuranceCategory);
		}
	}

	@Override
	public ResponseEntity<?> deleteInsuranceCategory(int id) {
		if (hrmsInsuranceCategoryRepository.existsById(id)) {

			HrmsInsuranceCategory hrmsInsuranceCategory = hrmsInsuranceCategoryRepository.findById(id).get();
			hrmsInsuranceCategory.setActive(0);
			hrmsInsuranceCategory.setDate_updated(LocalDateTime.now());
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsInsuranceCategoryRepository.save(hrmsInsuranceCategory));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsInsuranceCategory>> listInsuranceCategory() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsInsuranceCategoryRepository.findAll());
	}

}
