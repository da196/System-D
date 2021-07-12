package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsRefereeCategory;
import com.Hrms.Employee.Repository.HrmsRefereeCategoryRepository;

@Service
public class HrmsRefereeCategoryServiceImpl implements HrmsRefereeCategoryService {

	@Autowired
	private HrmsRefereeCategoryRepository hrmsRefereeCategoryRepository;

	@Override
	public ResponseEntity<HrmsRefereeCategory> addRefereeCategory(HrmsRefereeCategory hrmsRefereeCategory) {

		UUID uuid = UUID.randomUUID();
		hrmsRefereeCategory.setUnique_id(uuid);
		hrmsRefereeCategory.setApproved(0);
		hrmsRefereeCategory.setActive(1);

		if (hrmsRefereeCategoryRepository.existsByName(hrmsRefereeCategory.getName())) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

		} else {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsRefereeCategoryRepository.save(hrmsRefereeCategory));
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsRefereeCategory>> getRefereeCategory(int id) {

		if (hrmsRefereeCategoryRepository.existsById(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsRefereeCategoryRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsRefereeCategory> updateRefereeCategory(HrmsRefereeCategory hrmsRefereeCategory, int id) {

		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsRefereeCategory.setDate_updated(LocalTime);
		hrmsRefereeCategory.setApproved(0);
		hrmsRefereeCategory.setActive(1);
		if (hrmsRefereeCategoryRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsRefereeCategoryRepository.save(hrmsRefereeCategory));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsRefereeCategory);
		}
	}

	@Override
	public ResponseEntity<?> deleteRefereeCategory(int id) {

		if (hrmsRefereeCategoryRepository.existsById(id)) {
			hrmsRefereeCategoryRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsRefereeCategory>> listRefereeCategory() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsRefereeCategoryRepository.findAll());
	}

}
