package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmployeeActing;
import com.Hrms.Employee.Repository.HrmsDesignationRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeActingRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;

@Service
public class HrmsEmployeeActingServiceImpl implements HrmsEmployeeActingService {

	@Autowired
	private HrmsEmployeeActingRepository hrmsEmployeeActingRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsDesignationRepository hrmsDesignationRepository;

	@Override
	public ResponseEntity<HrmsEmployeeActing> addEmployeeActing(HrmsEmployeeActing hrmsEmployeeActing) {
		if (hrmsEmployeeRepository.existsById(hrmsEmployeeActing.getEmployeeid())
				&& hrmsDesignationRepository.existsById(hrmsEmployeeActing.getActingfordesignationid())) {
			UUID uuid = UUID.randomUUID();
			hrmsEmployeeActing.setUnique_id(uuid);
			hrmsEmployeeActing.setActive(1);
			hrmsEmployeeActing.setApproved(0);

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeActingRepository.save(hrmsEmployeeActing));
		} else {
			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsEmployeeActing);
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsEmployeeActing>> getEmployeeActing(int id) {

		if (hrmsEmployeeActingRepository.existsById(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeActingRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEmployeeActing> updateEmployeeActing(HrmsEmployeeActing hrmsEmployeeActing, int id) {

		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsEmployeeActing.setDate_updated(LocalTime);
		hrmsEmployeeActing.setApproved(0);
		if (hrmsEmployeeActingRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeActingRepository.save(hrmsEmployeeActing));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsEmployeeActing);
		}
	}

	@Override
	public ResponseEntity<?> deleteHrmsEmployeeActing(int id) {

		if (hrmsEmployeeActingRepository.existsById(id)) {
			HrmsEmployeeActing hrmsEmployeeActing = hrmsEmployeeActingRepository.findById(id).get();
			hrmsEmployeeActing.setActive(0);
			hrmsEmployeeActing.setDate_updated(LocalDateTime.now());
			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeActingRepository.save(hrmsEmployeeActing));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsEmployeeActing>> listHrmsEmployeeActing() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeActingRepository.findByActive(1));
	}

}
