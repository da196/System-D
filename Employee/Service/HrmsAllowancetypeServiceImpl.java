package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsAllowancetype;
import com.Hrms.Employee.Repository.HrmsAllowancetypeRepository;

@Service
public class HrmsAllowancetypeServiceImpl implements HrmsAllowancetypeService {

	@Autowired
	private HrmsAllowancetypeRepository hrmsAllowancetypeRepository;

	@Override
	public ResponseEntity<HrmsAllowancetype> save(HrmsAllowancetype hrmsAllowancetype) {

		UUID uuid = UUID.randomUUID();
		hrmsAllowancetype.setUnique_id(uuid);
		hrmsAllowancetype.setActive(1);
		hrmsAllowancetype.setApproved(0);

		if (hrmsAllowancetypeRepository.existsByName(hrmsAllowancetype.getName())) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

		} else {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsAllowancetypeRepository.save(hrmsAllowancetype));
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsAllowancetype>> viewHrmsAllowancetype(int id) {

		if (hrmsAllowancetypeRepository.existsByIdAndActive(id, 1)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsAllowancetypeRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsAllowancetype> update(HrmsAllowancetype hrmsAllowancetype, int id) {

		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsAllowancetype.setDate_updated(LocalTime);
		hrmsAllowancetype.setApproved(0);
		if (hrmsAllowancetypeRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsAllowancetypeRepository.save(hrmsAllowancetype));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsAllowancetype);
		}
	}

	@Override
	public ResponseEntity<?> deleteHrmsAllowancetype(int id) {

		if (hrmsAllowancetypeRepository.existsByIdAndActive(id, 1)) {

			HrmsAllowancetype hrmsAllowancetype = hrmsAllowancetypeRepository.findById(id).get();
			hrmsAllowancetype.setActive(0);
			hrmsAllowancetype.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsAllowancetypeRepository.save(hrmsAllowancetype));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsAllowancetype>> listHrmsAllowancetype() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsAllowancetypeRepository.findByActive(1));
	}

}
