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

import com.Hrms.Payroll.Entity.HrmsPayrollWorkflow;
import com.Hrms.Payroll.Service.HrmsPayrollWorkflowService;

@RestController
@RequestMapping("/v1/payrollWorkflow")
public class HrmsPayrollWorkflowController {
	@Autowired

	private HrmsPayrollWorkflowService hrmsPayrollWorkflowService;

	@PostMapping(value = "/addPayrollWorkflow")
	public ResponseEntity<HrmsPayrollWorkflow> addPayrollWorkflow(
			@RequestBody HrmsPayrollWorkflow hrmsPayrollWorkflow) {

		return hrmsPayrollWorkflowService.addPayrollWorkflow(hrmsPayrollWorkflow);

	}

	@GetMapping(value = "/getPayrollWorkflowById/{id}")
	public ResponseEntity<HrmsPayrollWorkflow> getPayrollWorkflowById(@PathVariable("id") int id) {

		return hrmsPayrollWorkflowService.getPayrollWorkflowById(id);

	}

	@PutMapping(value = "/updatePayrollWorkflow/{id}")
	public ResponseEntity<HrmsPayrollWorkflow> updatePayrollWorkflow(
			@RequestBody HrmsPayrollWorkflow hrmsPayrollWorkflow, @PathVariable("id") int id) {

		return hrmsPayrollWorkflowService.updatePayrollWorkflow(hrmsPayrollWorkflow, id);

	}

	@DeleteMapping(value = "/deletePayrollWorkflow/{id}")
	public ResponseEntity<?> deletePayrollWorkflow(@PathVariable("id") int id) {

		return hrmsPayrollWorkflowService.deletePayrollWorkflow(id);

	}

	@GetMapping(value = "/getAllPayrollWorkflow")
	public ResponseEntity<List<HrmsPayrollWorkflow>> getAllPayrollWorkflow() {
		return hrmsPayrollWorkflowService.getAllPayrollWorkflow();

	}

	@GetMapping(value = "/getAllPayrollWorkflowNonApproved")
	public ResponseEntity<List<HrmsPayrollWorkflow>> getAllPayrollWorkflowNonApproved() {
		return hrmsPayrollWorkflowService.getAllPayrollWorkflowNonApproved();

	}

	@PutMapping(value = "/approvePayrollWorkflow/{id}/{approverid}/{status}")
	public ResponseEntity<?> approvePayrollWorkflow(@PathVariable("id") int id,
			@PathVariable("approverid") int approverid, @PathVariable("status") int status) {
		return hrmsPayrollWorkflowService.approvePayrollWorkflow(id, approverid, status);

	}

}
