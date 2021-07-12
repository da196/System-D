package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmploymentCategory;
import com.Hrms.Employee.Repository.HrmsEmploymentCategoryRepository;

@Service
public class HrmsEmploymentCategoryServiceImpl implements HrmsEmploymentCategoryService {
	@Autowired
	private HrmsEmploymentCategoryRepository hrmsEmploymentCategoryRepository;

	@Override
	public ResponseEntity<HrmsEmploymentCategory> save(HrmsEmploymentCategory hrmsEmploymentCategory) {

		if (hrmsEmploymentCategoryRepository.existsByName(hrmsEmploymentCategory.getName())) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

		} else {

			UUID uuid = UUID.randomUUID();
			hrmsEmploymentCategory.setUnique_id(uuid);
			hrmsEmploymentCategory.setApproved(0);
			hrmsEmploymentCategory.setActive(1);
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsEmploymentCategoryRepository.save(hrmsEmploymentCategory));
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsEmploymentCategory>> viewHrmsEmploymentCategory(int id) {

		if (hrmsEmploymentCategoryRepository.existsById(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmploymentCategoryRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEmploymentCategory> update(HrmsEmploymentCategory hrmsEmploymentCategory, int id) {

		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsEmploymentCategory.setDate_updated(LocalTime);
		hrmsEmploymentCategory.setApproved(0);

		if (hrmsEmploymentCategoryRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsEmploymentCategoryRepository.save(hrmsEmploymentCategory));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsEmploymentCategory);
		}
	}

	@Override
	public ResponseEntity<?> deleteHrmsEmploymentCategory(int id) {

		if (hrmsEmploymentCategoryRepository.existsById(id)) {
			hrmsEmploymentCategoryRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsEmploymentCategory>> listHrmsEmploymentCategory() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsEmploymentCategoryRepository.findAll());
	}

}
