package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsRelativeCategory;
import com.Hrms.Employee.Repository.HrmsRelativeCategoryRepository;

@Service
public class HrmsRelativeCategoryServiceImpl implements HrmsRelativeCategoryService {
	@Autowired
	private HrmsRelativeCategoryRepository hrmsRelativeCategoryRepository;

	@Override
	public ResponseEntity<HrmsRelativeCategory> addRelativeCategory(HrmsRelativeCategory HrmsRelativeCategory) {

		UUID uuid = UUID.randomUUID();
		HrmsRelativeCategory.setUnique_id(uuid);
		HrmsRelativeCategory.setActive(1);
		HrmsRelativeCategory.setApproved(0);

		if (hrmsRelativeCategoryRepository.existsByName(HrmsRelativeCategory.getName())) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

		} else {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsRelativeCategoryRepository.save(HrmsRelativeCategory));
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsRelativeCategory>> getHrmsRelativeCategory(int id) {

		if (hrmsRelativeCategoryRepository.existsById(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsRelativeCategoryRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsRelativeCategory> updateRelativeCategory(HrmsRelativeCategory hrmsRelativeCategory,
			int id) {

		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsRelativeCategory.setDate_updated(LocalTime);
		hrmsRelativeCategory.setApproved(0);
		hrmsRelativeCategory.setActive(1);
		if (hrmsRelativeCategoryRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsRelativeCategoryRepository.save(hrmsRelativeCategory));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsRelativeCategory);
		}
	}

	@Override
	public ResponseEntity<?> deleteRelativeCategory(int id) {

		if (hrmsRelativeCategoryRepository.existsById(id)) {
			hrmsRelativeCategoryRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsRelativeCategory>> listRelativeCategory() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsRelativeCategoryRepository.findAll());
	}

}
