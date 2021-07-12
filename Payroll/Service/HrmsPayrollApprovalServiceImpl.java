package com.Hrms.Payroll.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Repository.HrmsDesignationRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Payroll.Entity.HrmsPayrollApproval;
import com.Hrms.Payroll.Repository.HrmsPayrollApprovalRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollRepository;

@Service
public class HrmsPayrollApprovalServiceImpl implements HrmsPayrollApprovalService {

	@Autowired
	private HrmsPayrollApprovalRepository hrmsPayrollApprovalRepository;

	@Autowired
	private HrmsPayrollRepository hrmsPayrollRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsDesignationRepository hrmsDesignationRepository;

	@Override
	public ResponseEntity<HrmsPayrollApproval> approvePayroll(HrmsPayrollApproval hrmsPayrollApproval, int payrollId,
			int status) {

		if (!hrmsPayrollApprovalRepository.existsByPayrollidAndActiveAndAccepted(payrollId, 1, 0)) {
			if (hrmsPayrollRepository.existsById(hrmsPayrollApproval.getPayrollid())
					&& hrmsEmployeeRepository.existsById(hrmsPayrollApproval.getApprovedbyuserid())
					&& hrmsDesignationRepository.existsById(hrmsPayrollApproval.getApprovedbydesignationid())) {

				if (hrmsPayrollApprovalRepository.existsByApprovedbyuseridAndPayrollidAndActive(
						hrmsPayrollApproval.getApprovedbyuserid(), hrmsPayrollApproval.getPayrollid(), 1)) {
					return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPayrollApproval);
				} else {
					hrmsPayrollApproval.setUnique_id(UUID.randomUUID());
					hrmsPayrollApproval.setActive(1);

					if (status == 1) {
						hrmsPayrollApproval.setApproved(1);
						hrmsPayrollApproval.setAccepted(1);

						// get the latest score
						// aggregate it
						HrmsPayrollApproval hrmsPayrollApproval1 = hrmsPayrollApprovalRepository
								.findFirstByPayrollidAndActiveOrderByApprovedscoreDesc(payrollId, 1);
						if (hrmsPayrollApproval1 != null) {
							hrmsPayrollApproval.setApprovedscore(hrmsPayrollApproval1.getApprovedscore() + 1);
						} else {

							hrmsPayrollApproval.setApprovedscore(1);
						}
					} else {
						hrmsPayrollApproval.setApproved(status);
						hrmsPayrollApproval.setAccepted(status);

						hrmsPayrollApproval.setApprovedscore(0);

					}

					return ResponseEntity.status(HttpStatus.OK)
							.body(hrmsPayrollApprovalRepository.saveAndFlush(hrmsPayrollApproval));
				}
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPayrollApproval);
			}
		} else {
			return ResponseEntity.status(HttpStatus.LOCKED).body(hrmsPayrollApproval);
		}
	}

	@Override
	public ResponseEntity<?> getPayrollApprovalById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<HrmsPayrollApproval> updatePayrollApproval(HrmsPayrollApproval hrmsPayrollApproval, int id,
			int status) {
		if (hrmsPayrollApprovalRepository.existsByIdAndActive(id, 1)) {

			if (hrmsPayrollRepository.existsById(hrmsPayrollApproval.getPayrollid())
					&& hrmsEmployeeRepository.existsById(hrmsPayrollApproval.getApprovedbyuserid())
					&& hrmsDesignationRepository.existsById(hrmsPayrollApproval.getApprovedbydesignationid())) {

				HrmsPayrollApproval hrmsPayrollApproval2 = hrmsPayrollApprovalRepository
						.findByPayrollidAndApprovedbyuserid(hrmsPayrollApproval.getPayrollid(),
								hrmsPayrollApproval.getApprovedbyuserid());
				hrmsPayrollApproval2.setActive(1);
				hrmsPayrollApproval2.setDate_updated(LocalDateTime.now());

				HrmsPayrollApproval hrmsPayrollApproval1 = hrmsPayrollApprovalRepository
						.findFirstByPayrollidAndActiveOrderByApprovedscoreDesc(hrmsPayrollApproval.getPayrollid(), 1);

				if (status == 1) {
					hrmsPayrollApproval.setApproved(1);
					hrmsPayrollApproval.setAccepted(1);

					// get the latest score
					// aggregate it
					// HrmsPayrollApproval hrmsPayrollApproval1 = hrmsPayrollApprovalRepository
					// .findFirstByPayrollidAndActiveOrderByApprovedscoreDESC(hrmsPayrollApproval.getPayrollid(),
					// 1);
					if (hrmsPayrollApproval1 != null) {
						if (hrmsPayrollApproval2.getApprovedscore() == 0) {// checking if this user rejected the
																			// approval before if yes allow him or her
																			// to approve now

							hrmsPayrollApproval.setApprovedscore(hrmsPayrollApproval1.getApprovedscore() + 1);
							hrmsPayrollApproval.setApproved(status);
							hrmsPayrollApproval.setAccepted(status);
						} else {
							// here we check if this user accepted the payroll(approved it) and now he/she
							// does not want to change her/his approval status but may be the user just want
							// to change some comments
							// we have to make sure that we only allow editing if no one has has approved
							// after this user
							if (hrmsPayrollApproval2.getApprovedscore() == hrmsPayrollApproval1.getApprovedscore()) {

								hrmsPayrollApproval.setApproved(status);
								hrmsPayrollApproval.setAccepted(status);
							} else {
								return ResponseEntity.status(HttpStatus.LOCKED).body(hrmsPayrollApproval);
							}

						}
					}
				} else {
					if (hrmsPayrollApproval2.getAccepted() == 1
							&& hrmsPayrollApproval2.getApprovedscore() == hrmsPayrollApproval1.getApprovedscore()) {
						hrmsPayrollApproval.setApproved(status);
						hrmsPayrollApproval.setAccepted(status);

						hrmsPayrollApproval.setApprovedscore(0);

					} else {
						return ResponseEntity.status(HttpStatus.LOCKED).body(hrmsPayrollApproval);
					}

				}

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPayrollApprovalRepository.saveAndFlush(hrmsPayrollApproval));

			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPayrollApproval);
			}

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsPayrollApproval);
		}
	}

	@Override
	public ResponseEntity<?> deletePayrollApproval(int id) {
		if (hrmsPayrollApprovalRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollApproval hrmsPayrollApproval = hrmsPayrollApprovalRepository.findById(id).get();
			hrmsPayrollApproval.setDate_updated(LocalDateTime.now());
			hrmsPayrollApproval.setActive(0);

			HrmsPayrollApproval hrmsPayrollApproval1 = hrmsPayrollApprovalRepository
					.findFirstByPayrollidAndActiveOrderByApprovedscoreDesc(hrmsPayrollApproval.getPayrollid(), 1);
			if (hrmsPayrollApproval.getApprovedscore() == hrmsPayrollApproval1.getApprovedscore()
					|| hrmsPayrollApproval.getApprovedscore() == 0) {
				// do nothing just proceed with normal deletion
			} else {
				return ResponseEntity.status(HttpStatus.LOCKED).body(hrmsPayrollApproval);
			}

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollApprovalRepository.saveAndFlush(hrmsPayrollApproval));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id);
		}
	}

	@Override
	public ResponseEntity<List<HrmsPayrollApproval>> getAllPayrollApproval() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<List<?>> getAllPayrollApprovalByPayrollId(int payrollId) {
		// TODO Auto-generated method stub
		return null;
	}

}
