package com.Hrms.Training.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Training.Entity.HrmsTrainingCategory;
import com.Hrms.Training.Repository.HrmsTrainingCategoryRepository;

@Service
public class HrmsTrainingCategoryServiceImpl implements HrmsTrainingCategoryService {

	@Autowired
	private HrmsTrainingCategoryRepository hrmsTrainingCategoryRepository;

	@Override
	public ResponseEntity<List<HrmsTrainingCategory>> getAllTrainingCategory() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsTrainingCategoryRepository.findByActive(1));
	}

	@Override
	public ResponseEntity<HrmsTrainingCategory> addTrainingCategory(HrmsTrainingCategory hrmsTrainingCategory) {
		if (hrmsTrainingCategoryRepository.existsByNameAndActive(hrmsTrainingCategory.getName(), 1)
				&& hrmsTrainingCategoryRepository.existsByCodeAndActive(hrmsTrainingCategory.getCode(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsTrainingCategory);
		} else {
			hrmsTrainingCategory.setActive(1);
			hrmsTrainingCategory.setApproved(0);
			hrmsTrainingCategory.setUnique_id(UUID.randomUUID());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsTrainingCategoryRepository.saveAndFlush(hrmsTrainingCategory));

		}
	}

	@Override
	public ResponseEntity<HrmsTrainingCategory> getTrainingCategoryById(int id) {
		if (hrmsTrainingCategoryRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsTrainingCategoryRepository.findByIdAndActive(id, 1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsTrainingCategory> updateTrainingCategory(HrmsTrainingCategory hrmsTrainingCategory,
			int id) {
		if (hrmsTrainingCategoryRepository.existsByIdAndActive(id, 1)) {
			hrmsTrainingCategory.setActive(1);
			hrmsTrainingCategory.setApproved(0);
			hrmsTrainingCategory.setDate_updated(LocalDateTime.now());
			if (hrmsTrainingCategoryRepository.findById(id).get().getDate_created() != null) {
				hrmsTrainingCategory
						.setDate_created(hrmsTrainingCategoryRepository.findById(id).get().getDate_created());
			}

			if (hrmsTrainingCategoryRepository.findById(id).get().getUnique_id() != null) {
				hrmsTrainingCategory.setUnique_id(hrmsTrainingCategoryRepository.findById(id).get().getUnique_id());
			}

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsTrainingCategoryRepository.saveAndFlush(hrmsTrainingCategory));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deleteTrainingCategory(int id) {
		if (hrmsTrainingCategoryRepository.existsByIdAndActive(id, 1)) {
			HrmsTrainingCategory hrmsTrainingCategory = hrmsTrainingCategoryRepository.findByIdAndActive(id, 1);
			hrmsTrainingCategory.setActive(0);
			hrmsTrainingCategory.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsTrainingCategoryRepository.saveAndFlush(hrmsTrainingCategory));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
