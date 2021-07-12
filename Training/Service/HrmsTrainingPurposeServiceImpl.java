package com.Hrms.Training.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Training.Entity.HrmsTrainingPurpose;
import com.Hrms.Training.Repository.HrmsTrainingPurposeRepository;

@Service
public class HrmsTrainingPurposeServiceImpl implements HrmsTrainingPurposeService {

	@Autowired
	private HrmsTrainingPurposeRepository hrmsTrainingPurposeRepository;

	@Override
	public ResponseEntity<List<HrmsTrainingPurpose>> getAllTrainingPurpose() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsTrainingPurposeRepository.findByActive(1));
	}

	@Override
	public ResponseEntity<HrmsTrainingPurpose> addTrainingPurpose(HrmsTrainingPurpose hrmsTrainingPurpose) {
		if (hrmsTrainingPurposeRepository.existsByNameAndActive(hrmsTrainingPurpose.getName(), 1)
				&& hrmsTrainingPurposeRepository.existsByCodeAndActive(hrmsTrainingPurpose.getCode(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsTrainingPurpose);
		} else {
			hrmsTrainingPurpose.setActive(1);
			hrmsTrainingPurpose.setApproved(0);
			hrmsTrainingPurpose.setUnique_id(UUID.randomUUID());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsTrainingPurposeRepository.saveAndFlush(hrmsTrainingPurpose));

		}
	}

	@Override
	public ResponseEntity<HrmsTrainingPurpose> getTrainingPurposeById(int id) {
		if (hrmsTrainingPurposeRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsTrainingPurposeRepository.findByIdAndActive(id, 1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsTrainingPurpose> updateTrainingPurpose(HrmsTrainingPurpose hrmsTrainingPurpose, int id) {
		if (hrmsTrainingPurposeRepository.existsByIdAndActive(id, 1)) {
			hrmsTrainingPurpose.setActive(1);
			hrmsTrainingPurpose.setApproved(0);
			hrmsTrainingPurpose.setDate_updated(LocalDateTime.now());
			if (hrmsTrainingPurposeRepository.findById(id).get().getDate_created() != null) {
				hrmsTrainingPurpose.setDate_created(hrmsTrainingPurposeRepository.findById(id).get().getDate_created());
			}

			if (hrmsTrainingPurposeRepository.findById(id).get().getUnique_id() != null) {
				hrmsTrainingPurpose.setUnique_id(hrmsTrainingPurposeRepository.findById(id).get().getUnique_id());
			}

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsTrainingPurposeRepository.saveAndFlush(hrmsTrainingPurpose));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deleteTrainingPurpose(int id) {
		if (hrmsTrainingPurposeRepository.existsByIdAndActive(id, 1)) {
			HrmsTrainingPurpose hrmsTrainingPurpose = hrmsTrainingPurposeRepository.findByIdAndActive(id, 1);
			hrmsTrainingPurpose.setActive(0);
			hrmsTrainingPurpose.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsTrainingPurposeRepository.saveAndFlush(hrmsTrainingPurpose));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
