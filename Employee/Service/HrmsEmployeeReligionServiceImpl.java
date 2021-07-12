package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmployeeReligion;
import com.Hrms.Employee.Repository.HrmsEmployeeReligionRepository;

@Service
public class HrmsEmployeeReligionServiceImpl implements HrmsEmployeeReligionService {
	@Autowired
	private HrmsEmployeeReligionRepository hrmsEmployeeReligionRepository;

	@Override
	public ResponseEntity<HrmsEmployeeReligion> save(HrmsEmployeeReligion hrmsEmployeeReligion) {

		if (hrmsEmployeeReligionRepository.existsByName(hrmsEmployeeReligion.getName())) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

		} else {
			UUID uuid = UUID.randomUUID();
			hrmsEmployeeReligion.setUnique_id(uuid);
			hrmsEmployeeReligion.setApproved(0);
			hrmsEmployeeReligion.setActive(1);

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeReligionRepository.save(hrmsEmployeeReligion));
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsEmployeeReligion>> viewHrmsEmployeeReligion(int id) {

		if (hrmsEmployeeReligionRepository.existsById(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeReligionRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEmployeeReligion> update(HrmsEmployeeReligion hrmsEmployeeReligion, int id) {
		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsEmployeeReligion.setDate_updated(LocalTime);
		hrmsEmployeeReligion.setApproved(0);
		if (hrmsEmployeeReligionRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeReligionRepository.save(hrmsEmployeeReligion));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsEmployeeReligion);
		}
	}

	@Override
	public ResponseEntity<?> deleteHrmsEmployeeReligion(int id) {

		if (hrmsEmployeeReligionRepository.existsById(id)) {
			HrmsEmployeeReligion hrmsEmployeeReligion = hrmsEmployeeReligionRepository.findById(id).get();
			hrmsEmployeeReligion.setActive(0);
			hrmsEmployeeReligion.setDate_updated(LocalDateTime.now());
			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeReligionRepository.save(hrmsEmployeeReligion));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsEmployeeReligion>> listHrmsEmployeeReligion() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeReligionRepository.findByActive(1));
	}

}
