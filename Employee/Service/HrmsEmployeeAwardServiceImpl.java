package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmployeeAward;
import com.Hrms.Employee.Repository.HrmsEmployeeAwardRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsLocationCountryRepository;

@Service
public class HrmsEmployeeAwardServiceImpl implements HrmsEmployeeAwardService {

	@Autowired
	private HrmsEmployeeAwardRepository hrmsEmployeeAwardRepository;

	@Autowired
	private HrmsLocationCountryRepository hrmsLocationCountryRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Override
	public ResponseEntity<HrmsEmployeeAward> addEmployeeAward(HrmsEmployeeAward hrmsEmployeeAward) {
		if (hrmsLocationCountryRepository.existsById(hrmsEmployeeAward.getCountryid())
				&& hrmsEmployeeRepository.existsById(hrmsEmployeeAward.getEmployeeid())) {
			UUID uuid = UUID.randomUUID();
			hrmsEmployeeAward.setUnique_id(uuid);
			hrmsEmployeeAward.setActive(1);
			hrmsEmployeeAward.setApproved(0);

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeAwardRepository.save(hrmsEmployeeAward));

		} else {
			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsEmployeeAward);
		}

	}

	@Override
	public ResponseEntity<Optional<HrmsEmployeeAward>> getEmployeeAward(int id) {
		if (hrmsEmployeeAwardRepository.existsById(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeAwardRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEmployeeAward> updateEmployeeAward(HrmsEmployeeAward HrmsEmployeeAward, int id) {

		LocalDateTime LocalTime = LocalDateTime.now();
		HrmsEmployeeAward.setDate_updated(LocalTime);
		HrmsEmployeeAward.setApproved(0);

		if (hrmsEmployeeAwardRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeAwardRepository.save(HrmsEmployeeAward));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(HrmsEmployeeAward);
		}
	}

	@Override
	public ResponseEntity<?> deleteEmployeeAward(int id) {

		if (hrmsEmployeeAwardRepository.existsById(id)) {
			HrmsEmployeeAward hrmsEmployeeAward = hrmsEmployeeAwardRepository.findById(id).get();

			hrmsEmployeeAward.setActive(0);
			hrmsEmployeeAward.setDate_updated(LocalDateTime.now());
			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeAwardRepository.save(hrmsEmployeeAward));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsEmployeeAward>> listEmployeeAward() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeAwardRepository.findAll());
	}

}
