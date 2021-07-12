package com.Hrms.Payroll.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Payroll.DTO.PayrollWorkflowStepResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollWorkflowStep;
import com.Hrms.Payroll.Service.HrmsPayrollWorkflowStepService;

@RestController
@RequestMapping("v1/payrollWorkflowStep")
public class HrmsPayrollWorkflowStepController {
	@Autowired
	private HrmsPayrollWorkflowStepService hrmsPayrollWorkflowStepService;

	@PostMapping(value = "/addPayrollWorkflowStep")
	public ResponseEntity<HrmsPayrollWorkflowStep> addPayrollWorkflowStep(
			@RequestBody HrmsPayrollWorkflowStep hrmsPayrollWorkflowStep) {
		return hrmsPayrollWorkflowStepService.addPayrollWorkflowStep(hrmsPayrollWorkflowStep);
	}

	@GetMapping(value = "/getPayrollWorkflowStepById/{id}")
	public ResponseEntity<PayrollWorkflowStepResponse> getPayrollWorkflowStepById(@PathVariable("id") int id) {

		return hrmsPayrollWorkflowStepService.getPayrollWorkflowStepById(id);

	}

	@PutMapping(value = "/updatePayrollWorkflowStep/{id}")
	public ResponseEntity<HrmsPayrollWorkflowStep> updatePayrollWorkflowStep(
			@RequestBody HrmsPayrollWorkflowStep hrmsPayrollWorkflowStep, @PathVariable("id") int id) {

		return hrmsPayrollWorkflowStepService.updatePayrollWorkflowStep(hrmsPayrollWorkflowStep, id);

	}

	@DeleteMapping(value = "/deletePayrollWorkflowStep/{id}")
	public ResponseEntity<?> deletePayrollWorkflowStep(@PathVariable("id") int id) {

		return hrmsPayrollWorkflowStepService.deletePayrollWorkflowStep(id);

	}

	@GetMapping(value = "/getAllPayrollWorkflowStep")
	public ResponseEntity<List<PayrollWorkflowStepResponse>> getAllPayrollWorkflowStep() {

		return hrmsPayrollWorkflowStepService.getAllPayrollWorkflowStep();

	}

	@GetMapping(value = "/getAllPayrollWorkflowStepNonApproved")
	public ResponseEntity<List<PayrollWorkflowStepResponse>> getAllPayrollWorkflowStepNonApproved() {
		return hrmsPayrollWorkflowStepService.getAllPayrollWorkflowStepNonApproved();

	}

	@PutMapping(value = "/approvePayrollWorkflowStep/{id}/{approverid}/{status}")
	public ResponseEntity<?> approvePayrollWorkflowStep(@PathVariable("id") int id,
			@PathVariable("approverid") int approverid, @PathVariable("status") int status) {
		return hrmsPayrollWorkflowStepService.approvePayrollWorkflowStep(id, approverid, status);

	}

}
