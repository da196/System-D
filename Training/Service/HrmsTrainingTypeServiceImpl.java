package com.Hrms.Training.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Training.Entity.HrmsTrainingType;
import com.Hrms.Training.Repository.HrmsTrainingTypeRepository;

@Service
public class HrmsTrainingTypeServiceImpl implements HrmsTrainingTypeService {
	@Autowired
	private HrmsTrainingTypeRepository hrmsTrainingTypeRepository;

	@Override
	public ResponseEntity<List<HrmsTrainingType>> getAllTrainingType() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsTrainingTypeRepository.findByActive(1));
	}

	@Override
	public ResponseEntity<HrmsTrainingType> addTrainingType(HrmsTrainingType hrmsTrainingType) {
		if (hrmsTrainingTypeRepository.existsByNameAndActive(hrmsTrainingType.getName(), 1)
				&& hrmsTrainingTypeRepository.existsByCodeAndActive(hrmsTrainingType.getCode(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsTrainingType);
		} else {
			hrmsTrainingType.setActive(1);
			hrmsTrainingType.setApproved(0);
			hrmsTrainingType.setUnique_id(UUID.randomUUID());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsTrainingTypeRepository.saveAndFlush(hrmsTrainingType));

		}
	}

	@Override
	public ResponseEntity<HrmsTrainingType> getTrainingTypeById(int id) {
		if (hrmsTrainingTypeRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsTrainingTypeRepository.findByIdAndActive(id, 1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsTrainingType> updateTrainingType(HrmsTrainingType hrmsTrainingType, int id) {
		if (hrmsTrainingTypeRepository.existsByIdAndActive(id, 1)) {
			hrmsTrainingType.setActive(1);
			hrmsTrainingType.setApproved(0);
			hrmsTrainingType.setDate_updated(LocalDateTime.now());
			if (hrmsTrainingTypeRepository.findById(id).get().getDate_created() != null) {
				hrmsTrainingType.setDate_created(hrmsTrainingTypeRepository.findById(id).get().getDate_created());
			}

			if (hrmsTrainingTypeRepository.findById(id).get().getUnique_id() != null) {
				hrmsTrainingType.setUnique_id(hrmsTrainingTypeRepository.findById(id).get().getUnique_id());
			}

			return ResponseEntity.status(HttpStatus.OK).body(hrmsTrainingTypeRepository.saveAndFlush(hrmsTrainingType));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deleteTrainingType(int id) {
		if (hrmsTrainingTypeRepository.existsByIdAndActive(id, 1)) {
			HrmsTrainingType hrmsTrainingType = hrmsTrainingTypeRepository.findByIdAndActive(id, 1);
			hrmsTrainingType.setActive(0);
			hrmsTrainingType.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsTrainingTypeRepository.saveAndFlush(hrmsTrainingType));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
