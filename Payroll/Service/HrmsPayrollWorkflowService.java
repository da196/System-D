package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsPayrollWorkflow;

@Service
public interface HrmsPayrollWorkflowService {

	public ResponseEntity<HrmsPayrollWorkflow> addPayrollWorkflow(HrmsPayrollWorkflow hrmsPayrollWorkflow);

	public ResponseEntity<HrmsPayrollWorkflow> getPayrollWorkflowById(int id);

	public ResponseEntity<HrmsPayrollWorkflow> updatePayrollWorkflow(HrmsPayrollWorkflow hrmsPayrollWorkflow, int id);

	public ResponseEntity<?> deletePayrollWorkflow(int id);

	public ResponseEntity<List<HrmsPayrollWorkflow>> getAllPayrollWorkflow();

	public ResponseEntity<List<HrmsPayrollWorkflow>> getAllPayrollWorkflowNonApproved();

	public ResponseEntity<?> approvePayrollWorkflow(int id, int approverid, int status);

}
