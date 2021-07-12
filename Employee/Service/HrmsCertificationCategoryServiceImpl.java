package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsCertificationCategory;
import com.Hrms.Employee.Repository.HrmsCertificationCategoryRepository;

@Service
public class HrmsCertificationCategoryServiceImpl implements HrmsCertificationCategoryService {

	@Autowired
	private HrmsCertificationCategoryRepository hrmsCertificationCategoryRepository;

	@Override
	public ResponseEntity<List<HrmsCertificationCategory>> listCertificationCategory() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsCertificationCategoryRepository.findByActive(1));
	}

	@Override
	public ResponseEntity<HrmsCertificationCategory> addCertificationCategory(
			HrmsCertificationCategory hrmsCertificationCategory) {
		if (hrmsCertificationCategoryRepository.existsByNameAndActive(hrmsCertificationCategory.getName(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsCertificationCategory);
		} else {
			hrmsCertificationCategory.setActive(1);
			hrmsCertificationCategory.setApproved(0);
			hrmsCertificationCategory.setUnique_id(UUID.randomUUID());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsCertificationCategoryRepository.saveAndFlush(hrmsCertificationCategory));
		}
	}

	@Override
	public ResponseEntity<HrmsCertificationCategory> updateCertificationCategory(
			HrmsCertificationCategory hrmsCertificationCategory, int id) {
		if (hrmsCertificationCategoryRepository.existsByIdAndActive(id, 1)) {
			HrmsCertificationCategory hrmsCertificationCategory1 = hrmsCertificationCategoryRepository.findById(id)
					.get();
			hrmsCertificationCategory.setActive(1);
			hrmsCertificationCategory.setApproved(0);
			hrmsCertificationCategory.setDate_created(hrmsCertificationCategory1.getDate_created());
			hrmsCertificationCategory.setDate_updated(LocalDateTime.now());
			hrmsCertificationCategory.setUnique_id(hrmsCertificationCategory1.getUnique_id());
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsCertificationCategoryRepository.saveAndFlush(hrmsCertificationCategory));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deleteCertificationCategory(int id) {
		if (hrmsCertificationCategoryRepository.existsByIdAndActive(id, 1)) {
			HrmsCertificationCategory hrmsCertificationCategory = hrmsCertificationCategoryRepository.findById(id)
					.get();
			hrmsCertificationCategory.setActive(0);
			hrmsCertificationCategory.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsCertificationCategoryRepository.saveAndFlush(hrmsCertificationCategory));

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

	}

	@Override
	public ResponseEntity<HrmsCertificationCategory> getCertificationCategoryById(int id) {
		if (hrmsCertificationCategoryRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsCertificationCategoryRepository.findById(id).get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
