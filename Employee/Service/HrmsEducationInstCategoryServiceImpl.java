package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEducationInstCategory;
import com.Hrms.Employee.Repository.HrmsEducationInstCategoryRepository;

@Service
public class HrmsEducationInstCategoryServiceImpl implements HrmsEducationInstCategoryService {

	@Autowired
	private HrmsEducationInstCategoryRepository hrmsEducationInstCategoryRepository;

	@Override
	public ResponseEntity<HrmsEducationInstCategory> save(HrmsEducationInstCategory hrmsEducationInstCategory) {
		UUID uuid = UUID.randomUUID();
		hrmsEducationInstCategory.setUnique_id(uuid);
		hrmsEducationInstCategory.setActive(1);
		hrmsEducationInstCategory.setApproved(0);

		if (hrmsEducationInstCategoryRepository.existsByName(hrmsEducationInstCategory.getName())) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsEducationInstCategory);

		} else {

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsEducationInstCategoryRepository.save(hrmsEducationInstCategory));
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsEducationInstCategory>> viewHrmsEducationInstCategory(int id) {
		if (hrmsEducationInstCategoryRepository.existsByIdAndActive(id, 1)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEducationInstCategoryRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEducationInstCategory> update(HrmsEducationInstCategory hrmsEducationInstCategory,
			int id) {

		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsEducationInstCategory.setDate_updated(LocalTime);
		hrmsEducationInstCategory.setApproved(0);

		if (hrmsEducationInstCategoryRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsEducationInstCategoryRepository.save(hrmsEducationInstCategory));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsEducationInstCategory);
		}
	}

	@Override
	public ResponseEntity<?> deleteHrmsEducationInstCategory(int id) {
		if (hrmsEducationInstCategoryRepository.existsByIdAndActive(id, 1)) {
			HrmsEducationInstCategory hrmsEducationInstCategory = hrmsEducationInstCategoryRepository.findById(id)
					.get();
			hrmsEducationInstCategory.setActive(0);
			hrmsEducationInstCategory.setDate_updated(LocalDateTime.now());
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsEducationInstCategoryRepository.save(hrmsEducationInstCategory));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsEducationInstCategory>> listHrmsEducationInstCategory() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsEducationInstCategoryRepository.findByActive(1));
	}

}
