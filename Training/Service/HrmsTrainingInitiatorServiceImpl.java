package com.Hrms.Training.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Training.Entity.HrmsTrainingInitiator;
import com.Hrms.Training.Repository.HrmsTrainingInitiatorRepository;

@Service
public class HrmsTrainingInitiatorServiceImpl implements HrmsTrainingInitiatorService {

	@Autowired
	private HrmsTrainingInitiatorRepository hrmsTrainingInitiatorRepository;

	@Override
	public ResponseEntity<List<HrmsTrainingInitiator>> getAllTrainingInitiator() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsTrainingInitiatorRepository.findByActive(1));
	}

	@Override
	public ResponseEntity<HrmsTrainingInitiator> addTrainingInitiator(HrmsTrainingInitiator hrmsTrainingInitiator) {
		if (hrmsTrainingInitiatorRepository.existsByNameAndActive(hrmsTrainingInitiator.getName(), 1)
				&& hrmsTrainingInitiatorRepository.existsByCodeAndActive(hrmsTrainingInitiator.getCode(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsTrainingInitiator);
		} else {
			hrmsTrainingInitiator.setActive(1);
			hrmsTrainingInitiator.setApproved(0);
			hrmsTrainingInitiator.setUnique_id(UUID.randomUUID());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsTrainingInitiatorRepository.saveAndFlush(hrmsTrainingInitiator));

		}
	}

	@Override
	public ResponseEntity<HrmsTrainingInitiator> getTrainingInitiatorById(int id) {
		if (hrmsTrainingInitiatorRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsTrainingInitiatorRepository.findByIdAndActive(id, 1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsTrainingInitiator> updateTrainingInitiator(HrmsTrainingInitiator hrmsTrainingInitiator,
			int id) {
		if (hrmsTrainingInitiatorRepository.existsByIdAndActive(id, 1)) {
			hrmsTrainingInitiator.setActive(1);
			hrmsTrainingInitiator.setApproved(0);
			hrmsTrainingInitiator.setDate_updated(LocalDateTime.now());
			if (hrmsTrainingInitiatorRepository.findById(id).get().getDate_created() != null) {
				hrmsTrainingInitiator
						.setDate_created(hrmsTrainingInitiatorRepository.findById(id).get().getDate_created());
			}

			if (hrmsTrainingInitiatorRepository.findById(id).get().getUnique_id() != null) {
				hrmsTrainingInitiator.setUnique_id(hrmsTrainingInitiatorRepository.findById(id).get().getUnique_id());
			}

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsTrainingInitiatorRepository.saveAndFlush(hrmsTrainingInitiator));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deleteTrainingInitiator(int id) {
		if (hrmsTrainingInitiatorRepository.existsByIdAndActive(id, 1)) {
			HrmsTrainingInitiator hrmsTrainingInitiator = hrmsTrainingInitiatorRepository.findByIdAndActive(id, 1);
			hrmsTrainingInitiator.setActive(0);
			hrmsTrainingInitiator.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsTrainingInitiatorRepository.saveAndFlush(hrmsTrainingInitiator));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
