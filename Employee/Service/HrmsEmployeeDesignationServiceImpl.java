package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmployeeDesignation;
import com.Hrms.Employee.Repository.HrmsEmployeeDesignationRepository;

@Service
public class HrmsEmployeeDesignationServiceImpl implements HrmsEmployeeDesignationService {

	@Autowired
	private HrmsEmployeeDesignationRepository hrmsEmployeeDesignationRepository;

	@Override
	public ResponseEntity<HrmsEmployeeDesignation> save(HrmsEmployeeDesignation hrmsEmployeeDesignation) {

		if (!hrmsEmployeeDesignationRepository.existsByEmployeeId(hrmsEmployeeDesignation.getEmployeeId())) {
			UUID uuid = UUID.randomUUID();
			hrmsEmployeeDesignation.setUnique_id(uuid);
			hrmsEmployeeDesignation.setApproved(0);
			hrmsEmployeeDesignation.setActive(1);

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsEmployeeDesignationRepository.save(hrmsEmployeeDesignation));

		} else {

			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(hrmsEmployeeDesignation);
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsEmployeeDesignation>> viewHrmsEmployeeDesignation(int id) {
		if (hrmsEmployeeDesignationRepository.existsById(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeDesignationRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEmployeeDesignation> update(HrmsEmployeeDesignation hrmsEmployeeDesignation, int id) {

		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsEmployeeDesignation.setDate_updated(LocalTime);
		hrmsEmployeeDesignation.setApproved(0);
		if (hrmsEmployeeDesignationRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsEmployeeDesignationRepository.save(hrmsEmployeeDesignation));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsEmployeeDesignation);
		}
	}

	@Override
	public ResponseEntity<?> deleteHrmsEmployeeDesignation(int id) {
		if (hrmsEmployeeDesignationRepository.existsByIdAndActive(id, 1)) {
			HrmsEmployeeDesignation hrmsEmployeeDesignation = hrmsEmployeeDesignationRepository.findById(id).get();

			hrmsEmployeeDesignation.setActive(0);
			hrmsEmployeeDesignation.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsEmployeeDesignationRepository.save(hrmsEmployeeDesignation));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsEmployeeDesignation>> listHrmsEmployeeDesignation() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeDesignationRepository.findByActive(1));
	}

}
