package com.Hrms.Payroll.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsPayrollType;
import com.Hrms.Payroll.Repository.HrmsPayrollTypeRepository;

@Service
public class HrmsPayrollTypeServiceImpl implements HrmsPayrollTypeService {

	@Autowired
	private HrmsPayrollTypeRepository hrmsPayrollTypeRepository;

	@Override
	public ResponseEntity<HrmsPayrollType> addPayrollType(HrmsPayrollType hrmsPayrollType) {
		if (hrmsPayrollTypeRepository.existsByNameAndActive(hrmsPayrollType.getName(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPayrollType);
		} else {
			hrmsPayrollType.setActive(1);
			hrmsPayrollType.setApproved(0);
			hrmsPayrollType.setUnique_id(UUID.randomUUID());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollTypeRepository.saveAndFlush(hrmsPayrollType));

		}
	}

	@Override
	public ResponseEntity<HrmsPayrollType> getPayrollTypeById(int id) {
		if (hrmsPayrollTypeRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollTypeRepository.findByIdAndActive(id, 1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsPayrollType> updatePayrollType(HrmsPayrollType hrmsPayrollType, int id) {
		if (hrmsPayrollTypeRepository.existsByIdAndActive(id, 1)) {
			hrmsPayrollType.setActive(1);
			hrmsPayrollType.setApproved(0);
			hrmsPayrollType.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollTypeRepository.saveAndFlush(hrmsPayrollType));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePayrollType(int id) {
		if (hrmsPayrollTypeRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollType hrmsPayrollType = hrmsPayrollTypeRepository.findByIdAndActive(id, 1);
			hrmsPayrollType.setActive(0);
			hrmsPayrollType.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollTypeRepository.saveAndFlush(hrmsPayrollType));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsPayrollType>> getAllPayrollType() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollTypeRepository.findByActive(1));
	}

}
