package com.Hrms.Employee.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.DTO.EmployeeApprovalComment;
import com.Hrms.Employee.DTO.HrmsEmployeeAddressContactRequest;
import com.Hrms.Employee.DTO.HrmsEmployeeAddressContactResponse;
import com.Hrms.Employee.Entity.HrmsEmployeeAddress;
import com.Hrms.Employee.Service.HrmsEmployeeAddressService;

@RestController
@RequestMapping("/v1/employeeAddress")
public class HrmsEmployeeAddressController {

	@Autowired
	private HrmsEmployeeAddressService hrmsEmployeeAddressService;

	@PostMapping(value = "/addEmployeeAddress")
	public ResponseEntity<HrmsEmployeeAddressContactRequest> addEmployeeAddress(
			@RequestBody HrmsEmployeeAddressContactRequest hrmsEmployeeAddressContactRequest) {
		return hrmsEmployeeAddressService.addEmployeeAddress(hrmsEmployeeAddressContactRequest);
	}

	@GetMapping(value = "/getEmployeeAddress/{id}")
	public ResponseEntity<Optional<HrmsEmployeeAddress>> getEmployeeAddress(@PathVariable("id") int id) {

		return hrmsEmployeeAddressService.getEmployeeAddress(id);

	}

	@GetMapping(value = "/getEmployeeAddressByEmpId/{empid}")
	public ResponseEntity<List<HrmsEmployeeAddressContactResponse>> getEmployeeAddressByEmpId(
			@PathVariable("empid") int empid) {
		return hrmsEmployeeAddressService.getEmployeeAddressByEmpId(empid);
	}

	@GetMapping(value = "/getEmployeeAddressByAdressIdAndContactId/{aid}/{cid}")
	public ResponseEntity<HrmsEmployeeAddressContactResponse> getEmployeeAddressByAdressIdAndContact(
			@PathVariable("aid") int aid, @PathVariable("cid") int cid) {
		return hrmsEmployeeAddressService.getEmployeeAddressByAdressIdAndContact(aid, cid);
	}

	@PutMapping(value = "/updateEmployeeAddress/{aid}/{cid}")
	public ResponseEntity<HrmsEmployeeAddressContactRequest> updateEmployeeAddress(
			@RequestBody HrmsEmployeeAddressContactRequest hrmsEmployeeAddressContactRequest,
			@PathVariable("aid") int aid, @PathVariable("cid") int cid) {

		return hrmsEmployeeAddressService.updateEmployeeAddress(hrmsEmployeeAddressContactRequest, aid, cid);

	}

	@DeleteMapping(value = "/deleteEmployeeAddress/{aid}/{cid}")
	public ResponseEntity<?> deleteEmployeeAddress(@PathVariable("aid") int aid, @PathVariable("cid") int cid) {

		return hrmsEmployeeAddressService.deleteEmployeeAddress(aid, cid);

	}

	@GetMapping(value = "/listAllEmployeeAddress")
	public ResponseEntity<List<HrmsEmployeeAddressContactResponse>> listEmployeeAddress() {
		return hrmsEmployeeAddressService.listEmployeeAddress();

	}

	@GetMapping(value = "/listAllEmployeeAddressNonApproved")
	public ResponseEntity<List<HrmsEmployeeAddressContactResponse>> listEmployeeAddressNonApproved() {
		return hrmsEmployeeAddressService.listEmployeeAddressNonApproved();
	}

	@PutMapping(value = "/approvedOrRejectEmployeeAddress/{aid}/{cid}/{status}")
	public ResponseEntity<?> approvedOrRejectEmployeeAddress(
			@RequestAttribute EmployeeApprovalComment employeeApprovalComment, @PathVariable("aid") int aid,
			@PathVariable("cid") int cid, @PathVariable("status") int status) {
		return hrmsEmployeeAddressService.approvedOrRejectEmployeeAddress(employeeApprovalComment, aid, cid, status);
	}

}
