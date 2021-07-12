package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.EmployeeApprovalComment;
import com.Hrms.Employee.DTO.HrmsEmployeeAddressContactRequest;
import com.Hrms.Employee.DTO.HrmsEmployeeAddressContactResponse;
import com.Hrms.Employee.Entity.HrmsEmployeeAddress;

@Service
public interface HrmsEmployeeAddressService {

	public ResponseEntity<HrmsEmployeeAddressContactRequest> addEmployeeAddress(
			HrmsEmployeeAddressContactRequest hrmsEmployeeAddressContactRequest);

	public ResponseEntity<Optional<HrmsEmployeeAddress>> getEmployeeAddress(int id);

	public ResponseEntity<List<HrmsEmployeeAddressContactResponse>> getEmployeeAddressByEmpId(int empid);

	public ResponseEntity<HrmsEmployeeAddressContactResponse> getEmployeeAddressByAdressIdAndContact(int aid, int cid);

	public ResponseEntity<HrmsEmployeeAddressContactRequest> updateEmployeeAddress(
			HrmsEmployeeAddressContactRequest hrmsEmployeeAddressContactRequest, int aid, int cid);

	public ResponseEntity<?> deleteEmployeeAddress(int aid, int cid);

	public ResponseEntity<List<HrmsEmployeeAddressContactResponse>> listEmployeeAddress();

	public ResponseEntity<List<HrmsEmployeeAddressContactResponse>> listEmployeeAddressNonApproved();

	public ResponseEntity<?> approvedOrRejectEmployeeAddress(EmployeeApprovalComment employeeApprovalComment, int aid,
			int cid, int status);

}
