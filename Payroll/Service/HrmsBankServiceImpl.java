package com.Hrms.Payroll.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsBank;
import com.Hrms.Payroll.Repository.HrmsBankRepository;

@Service
public class HrmsBankServiceImpl implements HrmsBankService {

	@Autowired
	private HrmsBankRepository hrmsBankRepository;

	@Override
	public ResponseEntity<HrmsBank> addBank(HrmsBank hrmsBank) {
		if (hrmsBankRepository.existsByNameAndActive(hrmsBank.getName(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsBank);
		} else {
			hrmsBank.setActive(1);
			hrmsBank.setApproved(0);
			hrmsBank.setUnique_id(UUID.randomUUID());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsBankRepository.saveAndFlush(hrmsBank));

		}
	}

	@Override
	public ResponseEntity<HrmsBank> getBankById(int id) {
		if (hrmsBankRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsBankRepository.findByIdAndActive(id, 1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsBank> updateBank(HrmsBank hrmsBank, int id) {
		if (hrmsBankRepository.existsByIdAndActive(id, 1)) {
			hrmsBank.setActive(1);
			hrmsBank.setApproved(0);
			hrmsBank.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsBankRepository.saveAndFlush(hrmsBank));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deleteBank(int id) {
		if (hrmsBankRepository.existsByIdAndActive(id, 1)) {
			HrmsBank hrmsBank = hrmsBankRepository.findByIdAndActive(id, 1);
			hrmsBank.setActive(0);
			hrmsBank.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsBankRepository.saveAndFlush(hrmsBank));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsBank>> getAllBank() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsBankRepository.findByActive(1));
	}

}
