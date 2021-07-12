package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsLocationAddressType;
import com.Hrms.Employee.Repository.HrmsLocationAddressTypeRepository;

@Service
public class HrmsLocationAddressTypeServiceImpl implements HrmsLocationAddressTypeService {

	@Autowired
	private HrmsLocationAddressTypeRepository hrmsLocationAddressTypeRepository;

	@Override
	public ResponseEntity<HrmsLocationAddressType> addLocationAddressType(
			HrmsLocationAddressType hrmsLocationAddressType) {

		UUID uuid = UUID.randomUUID();
		hrmsLocationAddressType.setUnique_id(uuid);
		hrmsLocationAddressType.setActive(1);
		hrmsLocationAddressType.setApproved(0);

		if (hrmsLocationAddressTypeRepository.existsByName(hrmsLocationAddressType.getName())) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

		} else {

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsLocationAddressTypeRepository.save(hrmsLocationAddressType));
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsLocationAddressType>> getLocationAddressType(int id) {

		if (hrmsLocationAddressTypeRepository.existsById(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsLocationAddressTypeRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsLocationAddressType> updateLocationAddressType(
			HrmsLocationAddressType hrmsLocationAddressType, int id) {

		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsLocationAddressType.setDate_updated(LocalTime);
		hrmsLocationAddressType.setApproved(0);
		hrmsLocationAddressType.setActive(1);
		if (hrmsLocationAddressTypeRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsLocationAddressTypeRepository.save(hrmsLocationAddressType));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsLocationAddressType);
		}
	}

	@Override
	public ResponseEntity<?> deleteLocationAddressType(int id) {
		if (hrmsLocationAddressTypeRepository.existsById(id)) {
			hrmsLocationAddressTypeRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsLocationAddressType>> listLocationAddressType() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsLocationAddressTypeRepository.findAll());
	}

}
