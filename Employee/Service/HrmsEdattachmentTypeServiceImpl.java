package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEdattachmentType;
import com.Hrms.Employee.Repository.HrmsEdattachmentTypeRepository;

@Service
public class HrmsEdattachmentTypeServiceImpl implements HrmsEdattachmentTypeService {

	@Autowired
	private HrmsEdattachmentTypeRepository hrmsEdattachmentTypeRepository;

	@Override
	public ResponseEntity<HrmsEdattachmentType> save(HrmsEdattachmentType hrmsEdattachmentType) {

		UUID uuid = UUID.randomUUID();
		hrmsEdattachmentType.setUnique_id(uuid);
		hrmsEdattachmentType.setActive(1);
		hrmsEdattachmentType.setApproved(0);

		if (hrmsEdattachmentTypeRepository.existsByName(hrmsEdattachmentType.getName())) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsEdattachmentType);

		} else {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEdattachmentTypeRepository.save(hrmsEdattachmentType));
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsEdattachmentType>> viewHrmsEdattachmentType(int id) {

		if (hrmsEdattachmentTypeRepository.existsByIdAndActive(id, 1)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEdattachmentTypeRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEdattachmentType> update(HrmsEdattachmentType hrmsEdattachmentType, int id) {
		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsEdattachmentType.setDate_updated(LocalTime);
		hrmsEdattachmentType.setApproved(0);
		if (hrmsEdattachmentTypeRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsEdattachmentTypeRepository.save(hrmsEdattachmentType));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsEdattachmentType);
		}
	}

	@Override
	public ResponseEntity<?> deleteHrmsEdattachmentType(int id) {

		if (hrmsEdattachmentTypeRepository.existsById(id)) {
			HrmsEdattachmentType hrmsEdattachmentType = hrmsEdattachmentTypeRepository.findById(id).get();
			hrmsEdattachmentType.setActive(0);
			hrmsEdattachmentType.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEdattachmentTypeRepository.save(hrmsEdattachmentType));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsEdattachmentType>> listHrmsEdattachmentType() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsEdattachmentTypeRepository.findByActive(1));
	}

}
