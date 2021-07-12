package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmploymentStatus;
import com.Hrms.Employee.Repository.HrmsEmploymentStatusRepository;

@Service
public class HrmsEmploymentStatusServiceImpl implements HrmsEmploymentStatusService {

	@Autowired

	private HrmsEmploymentStatusRepository hrmsEmploymentStatusRepository;

	@Override
	public ResponseEntity<HrmsEmploymentStatus> addEmploymentStatus(HrmsEmploymentStatus hrmsEmploymentStatus) {

		UUID uuid = UUID.randomUUID();
		hrmsEmploymentStatus.setUnique_id(uuid);
		hrmsEmploymentStatus.setActive(1);
		hrmsEmploymentStatus.setApproved(0);

		if (hrmsEmploymentStatusRepository.existsByName(hrmsEmploymentStatus.getName())) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

		} else {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmploymentStatusRepository.save(hrmsEmploymentStatus));
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsEmploymentStatus>> getEmploymentStatus(int id) {

		if (hrmsEmploymentStatusRepository.existsById(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmploymentStatusRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEmploymentStatus> updateEmploymentStatus(HrmsEmploymentStatus hrmsEmploymentStatus,
			int id) {

		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsEmploymentStatus.setDate_updated(LocalTime);
		hrmsEmploymentStatus.setApproved(0);
		if (hrmsEmploymentStatusRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmploymentStatusRepository.save(hrmsEmploymentStatus));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsEmploymentStatus);
		}
	}

	@Override
	public ResponseEntity<?> deleteEmploymentStatus(int id) {

		if (hrmsEmploymentStatusRepository.existsById(id)) {
			hrmsEmploymentStatusRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsEmploymentStatus>> listEmploymentStatus() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsEmploymentStatusRepository.findByActive(1));
	}

}
