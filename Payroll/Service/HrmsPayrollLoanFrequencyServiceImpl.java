package com.Hrms.Payroll.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsPayrollLoanFrequency;
import com.Hrms.Payroll.Repository.HrmsPayrollLoanFrequencyRepository;

@Service
public class HrmsPayrollLoanFrequencyServiceImpl implements HrmsPayrollLoanFrequencyService {

	@Autowired
	private HrmsPayrollLoanFrequencyRepository hrmsPayrollLoanFrequencyRepository;

	@Override
	public ResponseEntity<HrmsPayrollLoanFrequency> addPayrollLoanFrequency(
			HrmsPayrollLoanFrequency hrmsPayrollLoanFrequency) {
		if (hrmsPayrollLoanFrequencyRepository.existsByNameAndActive(hrmsPayrollLoanFrequency.getName(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPayrollLoanFrequency);
		} else {
			hrmsPayrollLoanFrequency.setActive(1);
			hrmsPayrollLoanFrequency.setApproved(0);
			hrmsPayrollLoanFrequency.setUnique_id(UUID.randomUUID());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollLoanFrequencyRepository.saveAndFlush(hrmsPayrollLoanFrequency));

		}
	}

	@Override
	public ResponseEntity<HrmsPayrollLoanFrequency> getPayrollLoanFrequencyById(int id) {
		if (hrmsPayrollLoanFrequencyRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollLoanFrequencyRepository.findByIdAndActive(id, 1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsPayrollLoanFrequency> updatePayrollLoanFrequency(
			HrmsPayrollLoanFrequency hrmsPayrollLoanFrequency, int id) {
		if (hrmsPayrollLoanFrequencyRepository.existsByIdAndActive(id, 1)) {
			hrmsPayrollLoanFrequency.setActive(1);
			hrmsPayrollLoanFrequency.setApproved(0);
			hrmsPayrollLoanFrequency.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollLoanFrequencyRepository.saveAndFlush(hrmsPayrollLoanFrequency));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePayrollLoanFrequency(int id) {
		if (hrmsPayrollLoanFrequencyRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollLoanFrequency hrmsPayrollLoanFrequency = hrmsPayrollLoanFrequencyRepository.findByIdAndActive(id,
					1);
			hrmsPayrollLoanFrequency.setActive(0);
			hrmsPayrollLoanFrequency.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollLoanFrequencyRepository.saveAndFlush(hrmsPayrollLoanFrequency));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsPayrollLoanFrequency>> getAllPayrollLoanFrequency() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollLoanFrequencyRepository.findByActive(1));
	}

}
