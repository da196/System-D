package com.Hrms.Payroll.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsPayrollContributionTypeMandatory;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionTypeMandatoryRepository;

@Service
public class HrmsPayrollContributionTypeMandatoryServiceImpl implements HrmsPayrollContributionTypeMandatoryService {

	@Autowired
	private HrmsPayrollContributionTypeMandatoryRepository hrmsPayrollContributionTypeMandatoryRepository;

	@Override
	public ResponseEntity<HrmsPayrollContributionTypeMandatory> addPayrollContributionTypeMandatory(
			HrmsPayrollContributionTypeMandatory hrmsPayrollContributionTypeMandatory) {
		if (hrmsPayrollContributionTypeMandatoryRepository
				.existsByNameAndActive(hrmsPayrollContributionTypeMandatory.getName(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPayrollContributionTypeMandatory);
		} else {
			hrmsPayrollContributionTypeMandatory.setActive(1);
			hrmsPayrollContributionTypeMandatory.setApproved(0);
			hrmsPayrollContributionTypeMandatory.setUnique_id(UUID.randomUUID());

			return ResponseEntity.status(HttpStatus.OK).body(
					hrmsPayrollContributionTypeMandatoryRepository.saveAndFlush(hrmsPayrollContributionTypeMandatory));

		}
	}

	@Override
	public ResponseEntity<HrmsPayrollContributionTypeMandatory> getPayrollContributionTypeMandatoryById(int id) {
		if (hrmsPayrollContributionTypeMandatoryRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollContributionTypeMandatoryRepository.findByIdAndActive(id, 1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsPayrollContributionTypeMandatory> updatePayrollContributionTypeMandatory(
			HrmsPayrollContributionTypeMandatory hrmsPayrollContributionTypeMandatory, int id) {
		if (hrmsPayrollContributionTypeMandatoryRepository.existsByIdAndActive(id, 1)) {
			hrmsPayrollContributionTypeMandatory.setActive(1);
			hrmsPayrollContributionTypeMandatory.setApproved(0);
			hrmsPayrollContributionTypeMandatory.setDate_updated(LocalDateTime.now());
			if (hrmsPayrollContributionTypeMandatoryRepository.findById(id).get().getDate_created() != null) {
				hrmsPayrollContributionTypeMandatory.setDate_created(
						hrmsPayrollContributionTypeMandatoryRepository.findById(id).get().getDate_created());
			}

			if (hrmsPayrollContributionTypeMandatoryRepository.findById(id).get().getUnique_id() != null) {
				hrmsPayrollContributionTypeMandatory
						.setUnique_id(hrmsPayrollContributionTypeMandatoryRepository.findById(id).get().getUnique_id());
			}

			return ResponseEntity.status(HttpStatus.OK).body(
					hrmsPayrollContributionTypeMandatoryRepository.saveAndFlush(hrmsPayrollContributionTypeMandatory));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePayrollContributionTypeMandatory(int id) {
		if (hrmsPayrollContributionTypeMandatoryRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollContributionTypeMandatory hrmsPayrollContributionTypeMandatory = hrmsPayrollContributionTypeMandatoryRepository
					.findByIdAndActive(id, 1);
			hrmsPayrollContributionTypeMandatory.setActive(0);
			hrmsPayrollContributionTypeMandatory.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(
					hrmsPayrollContributionTypeMandatoryRepository.saveAndFlush(hrmsPayrollContributionTypeMandatory));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsPayrollContributionTypeMandatory>> getAllPayrollContributionTypeMandatory() {
		return ResponseEntity.status(HttpStatus.OK)
				.body(hrmsPayrollContributionTypeMandatoryRepository.findByActive(1));
	}

}
