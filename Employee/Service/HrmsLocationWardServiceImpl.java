package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsLocationWard;
import com.Hrms.Employee.Repository.HrmsLocationDistrictRepository;
import com.Hrms.Employee.Repository.HrmsLocationWardRepository;

@Service
public class HrmsLocationWardServiceImpl implements HrmsLocationWardService {

	@Autowired
	private HrmsLocationWardRepository hrmsLocationWardRepository;

	@Autowired
	private HrmsLocationDistrictRepository hrmsLocationDistrictRepository;

	@Override
	public ResponseEntity<HrmsLocationWard> addLocationWard(HrmsLocationWard hrmsLocationWard) {
		UUID uuid = UUID.randomUUID();
		hrmsLocationWard.setUnique_id(uuid);
		hrmsLocationWard.setActive(1);
		hrmsLocationWard.setApproved(0);

		if (hrmsLocationWardRepository.existsByName(hrmsLocationWard.getName())) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

		} else {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsLocationWardRepository.save(hrmsLocationWard));
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsLocationWard>> getLocationWard(int id) {
		if (hrmsLocationWardRepository.existsById(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsLocationWardRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsLocationWard> updateLocationWard(HrmsLocationWard hrmsLocationWard, int id) {
		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsLocationWard.setDate_updated(LocalTime);
		hrmsLocationWard.setApproved(0);
		hrmsLocationWard.setActive(1);
		if (hrmsLocationWardRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsLocationWardRepository.save(hrmsLocationWard));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsLocationWard);
		}
	}

	@Override
	public ResponseEntity<?> deleteLocationWard(int id) {
		if (hrmsLocationWardRepository.existsById(id)) {
			HrmsLocationWard hrmsLocationWard = hrmsLocationWardRepository.findById(id).get();
			hrmsLocationWard.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsLocationWardRepository.save(hrmsLocationWard));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsLocationWard>> listLocationWard() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsLocationWardRepository.findAll());
	}

	@Override
	public ResponseEntity<List<HrmsLocationWard>> listLocationWardByDistrictId(int districtId) {
		if (hrmsLocationDistrictRepository.existsById(districtId)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsLocationWardRepository.findByDistrictid(districtId));

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
