package com.Hrms.Training.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Training.Entity.HrmsTrainingSponsor;
import com.Hrms.Training.Repository.HrmsTrainingSponsorRepository;

@Service
public class HrmsTrainingSponsorServiceImpl implements HrmsTrainingSponsorService {

	@Autowired
	private HrmsTrainingSponsorRepository hrmsTrainingSponsorRepository;

	@Override
	public ResponseEntity<List<HrmsTrainingSponsor>> getAllTrainingSponsor() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsTrainingSponsorRepository.findByActive(1));
	}

	@Override
	public ResponseEntity<HrmsTrainingSponsor> addTrainingSponsor(HrmsTrainingSponsor hrmsTrainingSponsor) {
		if (hrmsTrainingSponsorRepository.existsByNameAndActive(hrmsTrainingSponsor.getName(), 1)
				&& hrmsTrainingSponsorRepository.existsByCodeAndActive(hrmsTrainingSponsor.getCode(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsTrainingSponsor);
		} else {
			hrmsTrainingSponsor.setActive(1);
			hrmsTrainingSponsor.setApproved(0);
			hrmsTrainingSponsor.setUnique_id(UUID.randomUUID());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsTrainingSponsorRepository.saveAndFlush(hrmsTrainingSponsor));

		}
	}

	@Override
	public ResponseEntity<HrmsTrainingSponsor> getTrainingSponsorById(int id) {
		if (hrmsTrainingSponsorRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsTrainingSponsorRepository.findByIdAndActive(id, 1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsTrainingSponsor> updateTrainingSponsorr(HrmsTrainingSponsor hrmsTrainingSponsor, int id) {
		if (hrmsTrainingSponsorRepository.existsByIdAndActive(id, 1)) {
			hrmsTrainingSponsor.setActive(1);
			hrmsTrainingSponsor.setApproved(0);
			hrmsTrainingSponsor.setDate_updated(LocalDateTime.now());
			if (hrmsTrainingSponsorRepository.findById(id).get().getDate_created() != null) {
				hrmsTrainingSponsor.setDate_created(hrmsTrainingSponsorRepository.findById(id).get().getDate_created());
			}

			if (hrmsTrainingSponsorRepository.findById(id).get().getUnique_id() != null) {
				hrmsTrainingSponsor.setUnique_id(hrmsTrainingSponsorRepository.findById(id).get().getUnique_id());
			}

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsTrainingSponsorRepository.saveAndFlush(hrmsTrainingSponsor));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deleteTrainingSponsor(int id) {
		if (hrmsTrainingSponsorRepository.existsByIdAndActive(id, 1)) {
			HrmsTrainingSponsor hrmsTrainingSponsor = hrmsTrainingSponsorRepository.findByIdAndActive(id, 1);
			hrmsTrainingSponsor.setActive(0);
			hrmsTrainingSponsor.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsTrainingSponsorRepository.saveAndFlush(hrmsTrainingSponsor));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
