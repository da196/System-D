package com.Hrms.Leave.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Leave.Entity.HrmsLeaveType;
import com.Hrms.Leave.Repository.HrmsLeaveTypeRepository;

@Service
public class HrmsLeaveTypeServiceImpl implements HrmsLeaveTypeService {
	@Autowired
	private HrmsLeaveTypeRepository hrmsLeaveTypeRepository;

	@Override
	public ResponseEntity<HrmsLeaveType> addLeaveType(HrmsLeaveType hrmsLeaveType) {
		if (hrmsLeaveTypeRepository.existsByNameAndActive(hrmsLeaveType.getName(), 1)
				|| hrmsLeaveTypeRepository.existsByCodeAndActive(hrmsLeaveType.getCode(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsLeaveType);
		} else {
			hrmsLeaveType.setActive(1);
			hrmsLeaveType.setApproved(0);
			hrmsLeaveType.setUnique_id(UUID.randomUUID());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsLeaveTypeRepository.saveAndFlush(hrmsLeaveType));

		}
	}

	@Override
	public ResponseEntity<HrmsLeaveType> getLeaveTypeById(int id) {
		if (hrmsLeaveTypeRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsLeaveTypeRepository.findByIdAndActive(id, 1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsLeaveType> updateLeaveType(HrmsLeaveType hrmsLeaveType, int id) {
		if (hrmsLeaveTypeRepository.existsByIdAndActive(id, 1)) {
			hrmsLeaveType.setActive(1);
			hrmsLeaveType.setApproved(0);
			hrmsLeaveType.setDate_updated(LocalDateTime.now());
			if (hrmsLeaveTypeRepository.findById(id).get().getDate_created() != null) {
				hrmsLeaveType.setDate_created(hrmsLeaveTypeRepository.findById(id).get().getDate_created());
			}

			if (hrmsLeaveTypeRepository.findById(id).get().getUnique_id() != null) {
				hrmsLeaveType.setUnique_id(hrmsLeaveTypeRepository.findById(id).get().getUnique_id());
			}

			return ResponseEntity.status(HttpStatus.OK).body(hrmsLeaveTypeRepository.saveAndFlush(hrmsLeaveType));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deleteLeaveType(int id) {
		if (hrmsLeaveTypeRepository.existsByIdAndActive(id, 1)) {
			HrmsLeaveType hrmsLeaveType = hrmsLeaveTypeRepository.findByIdAndActive(id, 1);
			hrmsLeaveType.setActive(0);
			hrmsLeaveType.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsLeaveTypeRepository.saveAndFlush(hrmsLeaveType));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsLeaveType>> getAllLeaveType() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsLeaveTypeRepository.findByActive(1));
	}

}
