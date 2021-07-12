package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsSalaryscalenotch;
import com.Hrms.Employee.Repository.HrmsSalaryscalenotchRepository;

@Service
public class HrmsSalaryscalenotchServiceImpl implements HrmsSalaryscalenotchService {

	@Autowired
	private HrmsSalaryscalenotchRepository hrmsSalaryscalenotchRepository;

	@Override
	public ResponseEntity<HrmsSalaryscalenotch> save(HrmsSalaryscalenotch hrmsSalaryscalenotch) {
		UUID uuid = UUID.randomUUID();
		hrmsSalaryscalenotch.setUnique_id(uuid);
		hrmsSalaryscalenotch.setApproved(0);
		hrmsSalaryscalenotch.setActive(1);

		if (hrmsSalaryscalenotchRepository.existsByName(hrmsSalaryscalenotch.getName())) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

		} else {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsSalaryscalenotchRepository.save(hrmsSalaryscalenotch));
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsSalaryscalenotch>> viewHrmsSalaryscalenotch(int id) {

		if (hrmsSalaryscalenotchRepository.existsById(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsSalaryscalenotchRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsSalaryscalenotch> update(HrmsSalaryscalenotch hrmsSalaryscalenotch, int id) {

		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsSalaryscalenotch.setDate_updated(LocalTime);
		hrmsSalaryscalenotch.setActive(1);
		hrmsSalaryscalenotch.setApproved(0);
		if (hrmsSalaryscalenotchRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsSalaryscalenotchRepository.save(hrmsSalaryscalenotch));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsSalaryscalenotch);
		}
	}

	@Override
	public ResponseEntity<?> deleteHrmsSalaryscalenotch(int id) {

		if (hrmsSalaryscalenotchRepository.existsById(id)) {
			hrmsSalaryscalenotchRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsSalaryscalenotch>> listHrmsSalaryscalenotch() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsSalaryscalenotchRepository.findAll());
	}
}
