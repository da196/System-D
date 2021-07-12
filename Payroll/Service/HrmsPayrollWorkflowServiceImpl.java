package com.Hrms.Payroll.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Payroll.Entity.HrmsPayrollWorkflow;
import com.Hrms.Payroll.Repository.HrmsPayrollWorkflowRepository;

@Service
public class HrmsPayrollWorkflowServiceImpl implements HrmsPayrollWorkflowService {

	@Autowired
	private HrmsPayrollWorkflowRepository hrmsPayrollWorkflowRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Override
	public ResponseEntity<HrmsPayrollWorkflow> addPayrollWorkflow(HrmsPayrollWorkflow hrmsPayrollWorkflow) {
		if (hrmsPayrollWorkflowRepository.existsByNameAndActive(hrmsPayrollWorkflow.getName(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPayrollWorkflow);
		} else {
			if (hrmsEmployeeRepository.existsByIdAndActive(hrmsPayrollWorkflow.getCreatedbyid(), 1)) {
				hrmsPayrollWorkflow.setActive(1);
				hrmsPayrollWorkflow.setApproved(0);
				hrmsPayrollWorkflow.setUnique_id(UUID.randomUUID());

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPayrollWorkflowRepository.saveAndFlush(hrmsPayrollWorkflow));
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsPayrollWorkflow);
			}
		}
	}

	@Override
	public ResponseEntity<HrmsPayrollWorkflow> getPayrollWorkflowById(int id) {
		if (hrmsPayrollWorkflowRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollWorkflowRepository.findByIdAndActive(id, 1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsPayrollWorkflow> updatePayrollWorkflow(HrmsPayrollWorkflow hrmsPayrollWorkflow, int id) {
		if (hrmsPayrollWorkflowRepository.existsByIdAndActive(id, 1)) {

			if (hrmsEmployeeRepository.existsByIdAndActive(hrmsPayrollWorkflow.getUpdatedbyid(), 1)) {
				hrmsPayrollWorkflow.setActive(1);
				hrmsPayrollWorkflow.setApproved(0);
				hrmsPayrollWorkflow.setDate_updated(LocalDateTime.now());
				hrmsPayrollWorkflow.setUpdatedbyid(hrmsPayrollWorkflow.getUpdatedbyid());

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPayrollWorkflowRepository.saveAndFlush(hrmsPayrollWorkflow));
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsPayrollWorkflow);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePayrollWorkflow(int id) {
		if (hrmsPayrollWorkflowRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollWorkflow hrmsPayrollWorkflow = hrmsPayrollWorkflowRepository.findByIdAndActive(id, 1);
			hrmsPayrollWorkflow.setActive(0);
			hrmsPayrollWorkflow.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollWorkflowRepository.saveAndFlush(hrmsPayrollWorkflow));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsPayrollWorkflow>> getAllPayrollWorkflow() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollWorkflowRepository.findByActive(1));
	}

	@Override
	public ResponseEntity<List<HrmsPayrollWorkflow>> getAllPayrollWorkflowNonApproved() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollWorkflowRepository.findByActiveAndApproved(1, 0));
	}

	@Override
	public ResponseEntity<?> approvePayrollWorkflow(int id, int approverid, int status) {
		if (hrmsPayrollWorkflowRepository.existsByIdAndActive(id, 1)) {
			if (hrmsEmployeeRepository.existsByIdAndActive(approverid, 1) && (status == 1 || status == -1)) {
				HrmsPayrollWorkflow hrmsPayrollWorkflow = hrmsPayrollWorkflowRepository.findByIdAndActive(id, 1);
				hrmsPayrollWorkflow.setApproved(status);
				hrmsPayrollWorkflow.setApproveddate(LocalDateTime.now());
				hrmsPayrollWorkflow.setApprovedbyid(approverid);

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPayrollWorkflowRepository.saveAndFlush(hrmsPayrollWorkflow));
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(null);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
