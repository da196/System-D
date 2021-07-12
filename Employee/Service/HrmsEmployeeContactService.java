package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmployeeContact;

@Service
public interface HrmsEmployeeContactService {
	public ResponseEntity<HrmsEmployeeContact> addEmployeeContact(HrmsEmployeeContact hrmsEmployeeContact);

	public ResponseEntity<Optional<HrmsEmployeeContact>> getEmployeeContact(int id);

	public ResponseEntity<HrmsEmployeeContact> updateEmployeeContact(HrmsEmployeeContact hrmsEmployeeContact, int id);

	public ResponseEntity<?> deleteEmployeeContact(int id);

	public ResponseEntity<List<HrmsEmployeeContact>> listEmployeeContact();

}
