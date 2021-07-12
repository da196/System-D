package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmployeeContact;
import com.Hrms.Employee.Repository.HrmsEmployeeContactRepository;

@Service
public class HrmsEmployeeContactServiceImpl implements HrmsEmployeeContactService {
	@Autowired
	private HrmsEmployeeContactRepository hrmsEmployeeContactRepository;

	@Override
	public ResponseEntity<HrmsEmployeeContact> addEmployeeContact(HrmsEmployeeContact hrmsEmployeeContact) {

		UUID uuid = UUID.randomUUID();
		hrmsEmployeeContact.setUnique_id(uuid);
		hrmsEmployeeContact.setActive(1);
		hrmsEmployeeContact.setApproved(0);

		return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeContactRepository.save(hrmsEmployeeContact));

	}

	@Override
	public ResponseEntity<Optional<HrmsEmployeeContact>> getEmployeeContact(int id) {

		if (hrmsEmployeeContactRepository.existsById(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeContactRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEmployeeContact> updateEmployeeContact(HrmsEmployeeContact hrmsEmployeeContact, int id) {

		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsEmployeeContact.setDate_updated(LocalTime);
		hrmsEmployeeContact.setApproved(0);
		if (hrmsEmployeeContactRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeContactRepository.save(hrmsEmployeeContact));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsEmployeeContact);
		}
	}

	@Override
	public ResponseEntity<?> deleteEmployeeContact(int id) {

		if (hrmsEmployeeContactRepository.existsById(id)) {
			HrmsEmployeeContact hrmsEmployeeContact = hrmsEmployeeContactRepository.findById(id).get();
			hrmsEmployeeContact.setActive(0);
			hrmsEmployeeContact.setDate_updated(LocalDateTime.now());
			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeContactRepository.save(hrmsEmployeeContact));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsEmployeeContact>> listEmployeeContact() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeContactRepository.findAll());
	}

}
