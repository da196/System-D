package com.Hrms.Payroll.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsPayrollLoanLender;
import com.Hrms.Payroll.Repository.HrmsPayrollLoanLenderRepository;

@Service
public class HrmsPayrollLoanLenderServiceImpl implements HrmsPayrollLoanLenderService {

	@Autowired
	private HrmsPayrollLoanLenderRepository hrmsPayrollLoanLenderRepository;

	@Override
	public ResponseEntity<HrmsPayrollLoanLender> addPayrollLoanLender(HrmsPayrollLoanLender hrmsPayrollLoanLender) {
		if (hrmsPayrollLoanLenderRepository.existsByNameAndActive(hrmsPayrollLoanLender.getName(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPayrollLoanLender);
		} else {
			hrmsPayrollLoanLender.setActive(1);
			hrmsPayrollLoanLender.setApproved(0);
			hrmsPayrollLoanLender.setUnique_id(UUID.randomUUID());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollLoanLenderRepository.saveAndFlush(hrmsPayrollLoanLender));

		}
	}

	@Override
	public ResponseEntity<HrmsPayrollLoanLender> getPayrollLoanLenderById(int id) {
		if (hrmsPayrollLoanLenderRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollLoanLenderRepository.findByIdAndActive(id, 1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsPayrollLoanLender> updatePayrollLoanLender(HrmsPayrollLoanLender hrmsPayrollLoanLender,
			int id) {
		if (hrmsPayrollLoanLenderRepository.existsByIdAndActive(id, 1)) {
			hrmsPayrollLoanLender.setActive(1);
			hrmsPayrollLoanLender.setApproved(0);
			hrmsPayrollLoanLender.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollLoanLenderRepository.saveAndFlush(hrmsPayrollLoanLender));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePayrollLoanLender(int id) {
		if (hrmsPayrollLoanLenderRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollLoanLender hrmsPayrollLoanLender = hrmsPayrollLoanLenderRepository.findByIdAndActive(id, 1);
			hrmsPayrollLoanLender.setActive(0);
			hrmsPayrollLoanLender.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollLoanLenderRepository.saveAndFlush(hrmsPayrollLoanLender));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsPayrollLoanLender>> getAllPayrollLoanLender() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollLoanLenderRepository.findByActive(1));
	}

}
