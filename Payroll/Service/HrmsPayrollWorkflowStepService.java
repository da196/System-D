package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.DTO.PayrollWorkflowStepResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollWorkflowStep;

@Service
public interface HrmsPayrollWorkflowStepService {
	public ResponseEntity<HrmsPayrollWorkflowStep> addPayrollWorkflowStep(
			HrmsPayrollWorkflowStep hrmsPayrollWorkflowStep);

	public ResponseEntity<PayrollWorkflowStepResponse> getPayrollWorkflowStepById(int id);

	public ResponseEntity<HrmsPayrollWorkflowStep> updatePayrollWorkflowStep(
			HrmsPayrollWorkflowStep hrmsPayrollWorkflowStep, int id);

	public ResponseEntity<?> deletePayrollWorkflowStep(int id);

	public ResponseEntity<List<PayrollWorkflowStepResponse>> getAllPayrollWorkflowStep();

	public ResponseEntity<List<PayrollWorkflowStepResponse>> getAllPayrollWorkflowStepNonApproved();

	public ResponseEntity<?> approvePayrollWorkflowStep(int id, int approverid, int status);

}
