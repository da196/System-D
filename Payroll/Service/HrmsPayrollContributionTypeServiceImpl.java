package com.Hrms.Payroll.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsPayrollContributionType;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionTypeRepository;

@Service
public class HrmsPayrollContributionTypeServiceImpl implements HrmsPayrollContributionTypeService {

	@Autowired
	private HrmsPayrollContributionTypeRepository hrmsPayrollContributionTypeRepository;

	@Override
	public ResponseEntity<HrmsPayrollContributionType> addPayrollContributionType(
			HrmsPayrollContributionType hrmsPayrollContributionType) {
		if (hrmsPayrollContributionTypeRepository.existsByNameAndActive(hrmsPayrollContributionType.getName(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPayrollContributionType);
		} else {
			hrmsPayrollContributionType.setActive(1);
			hrmsPayrollContributionType.setApproved(0);
			hrmsPayrollContributionType.setUnique_id(UUID.randomUUID());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollContributionTypeRepository.saveAndFlush(hrmsPayrollContributionType));

		}
	}

	@Override
	public ResponseEntity<HrmsPayrollContributionType> getPayrollContributionTypeById(int id) {
		if (hrmsPayrollContributionTypeRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollContributionTypeRepository.findByIdAndActive(id, 1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsPayrollContributionType> updatePayrollContributionType(
			HrmsPayrollContributionType hrmsPayrollContributionType, int id) {
		if (hrmsPayrollContributionTypeRepository.existsByIdAndActive(id, 1)) {
			hrmsPayrollContributionType.setActive(1);
			hrmsPayrollContributionType.setApproved(0);
			hrmsPayrollContributionType.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollContributionTypeRepository.saveAndFlush(hrmsPayrollContributionType));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePayrollContributionType(int id) {
		if (hrmsPayrollContributionTypeRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollContributionType hrmsPayrollContributionType = hrmsPayrollContributionTypeRepository
					.findByIdAndActive(id, 1);
			hrmsPayrollContributionType.setActive(0);
			hrmsPayrollContributionType.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollContributionTypeRepository.saveAndFlush(hrmsPayrollContributionType));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsPayrollContributionType>> getAllPayrollContributionType() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollContributionTypeRepository.findByActive(1));
	}
}
