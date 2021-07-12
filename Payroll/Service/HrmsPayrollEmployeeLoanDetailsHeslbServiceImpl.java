package com.Hrms.Payroll.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsPayrollEmployeeLoanDetailsHeslb;
import com.Hrms.Payroll.Repository.HrmsPayrollEmployeeLoanDetailsHeslbRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollEmployeeLoanRepository;

@Service
public class HrmsPayrollEmployeeLoanDetailsHeslbServiceImpl implements HrmsPayrollEmployeeLoanDetailsHeslbService {

	@Autowired
	private HrmsPayrollEmployeeLoanDetailsHeslbRepository hrmsPayrollEmployeeLoanDetailsHeslbRepository;

	@Autowired
	private HrmsPayrollEmployeeLoanRepository hrmsPayrollEmployeeLoanRepository;

	@Override
	public ResponseEntity<HrmsPayrollEmployeeLoanDetailsHeslb> addPayrollEmployeeLoanDetailsHeslb(
			HrmsPayrollEmployeeLoanDetailsHeslb hrmsPayrollEmployeeLoanDetailsHeslb) {
		if (hrmsPayrollEmployeeLoanDetailsHeslbRepository
				.existsByCseeindexnumberAndActive(hrmsPayrollEmployeeLoanDetailsHeslb.getCseeindexnumber(), 1)
				|| hrmsPayrollEmployeeLoanDetailsHeslbRepository
						.existsByLoanidAndActive(hrmsPayrollEmployeeLoanDetailsHeslb.getLoanid(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPayrollEmployeeLoanDetailsHeslb);
		} else {
			if (hrmsPayrollEmployeeLoanRepository.existsByIdAndActive(hrmsPayrollEmployeeLoanDetailsHeslb.getLoanid(),
					1)) {
				hrmsPayrollEmployeeLoanDetailsHeslb.setActive(1);
				hrmsPayrollEmployeeLoanDetailsHeslb.setApproved(0);
				hrmsPayrollEmployeeLoanDetailsHeslb.setUnique_id(UUID.randomUUID());

				return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollEmployeeLoanDetailsHeslbRepository
						.saveAndFlush(hrmsPayrollEmployeeLoanDetailsHeslb));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPayrollEmployeeLoanDetailsHeslb);
			}

		}
	}

	@Override
	public ResponseEntity<HrmsPayrollEmployeeLoanDetailsHeslb> getPayrollEmployeeLoanDetailsHeslbById(int id) {
		if (hrmsPayrollEmployeeLoanDetailsHeslbRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollEmployeeLoanDetailsHeslbRepository.findByIdAndActive(id, 1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsPayrollEmployeeLoanDetailsHeslb> updatePayrollEmployeeLoanDetailsHeslb(
			HrmsPayrollEmployeeLoanDetailsHeslb hrmsPayrollEmployeeLoanDetailsHeslb, int id) {
		if (hrmsPayrollEmployeeLoanDetailsHeslbRepository.existsByIdAndActive(id, 1)) {
			if (hrmsPayrollEmployeeLoanRepository.existsByIdAndActive(hrmsPayrollEmployeeLoanDetailsHeslb.getLoanid(),
					1)) {
				hrmsPayrollEmployeeLoanDetailsHeslb.setActive(1);
				hrmsPayrollEmployeeLoanDetailsHeslb.setApproved(0);
				hrmsPayrollEmployeeLoanDetailsHeslb.setDate_updated(LocalDateTime.now());
				if (hrmsPayrollEmployeeLoanDetailsHeslbRepository.findById(id).get().getDate_created() != null) {
					hrmsPayrollEmployeeLoanDetailsHeslb.setDate_created(
							hrmsPayrollEmployeeLoanDetailsHeslbRepository.findById(id).get().getDate_created());
				}

				if (hrmsPayrollEmployeeLoanDetailsHeslbRepository.findById(id).get().getUnique_id() != null) {
					hrmsPayrollEmployeeLoanDetailsHeslb.setUnique_id(
							hrmsPayrollEmployeeLoanDetailsHeslbRepository.findById(id).get().getUnique_id());
				}

				return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollEmployeeLoanDetailsHeslbRepository
						.saveAndFlush(hrmsPayrollEmployeeLoanDetailsHeslb));

			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(null);
			}

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePayrollEmployeeLoanDetailsHeslb(int id) {
		if (hrmsPayrollEmployeeLoanDetailsHeslbRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollEmployeeLoanDetailsHeslb hrmsPayrollEmployeeLoanDetailsHeslb = hrmsPayrollEmployeeLoanDetailsHeslbRepository
					.findByIdAndActive(id, 1);
			hrmsPayrollEmployeeLoanDetailsHeslb.setActive(0);
			hrmsPayrollEmployeeLoanDetailsHeslb.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(
					hrmsPayrollEmployeeLoanDetailsHeslbRepository.saveAndFlush(hrmsPayrollEmployeeLoanDetailsHeslb));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsPayrollEmployeeLoanDetailsHeslb>> getAllPayrollEmployeeLoanDetailsHeslb() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollEmployeeLoanDetailsHeslbRepository.findByActive(1));
	}

	@Override
	public ResponseEntity<HrmsPayrollEmployeeLoanDetailsHeslb> getPayrollEmployeeLoanDetailsHeslbByLoanId(int loanId) {
		if (hrmsPayrollEmployeeLoanDetailsHeslbRepository.existsByLoanidAndActive(loanId, 1)) {

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollEmployeeLoanDetailsHeslbRepository.findByLoanidAndActive(loanId, 1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
