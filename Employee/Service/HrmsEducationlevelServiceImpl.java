package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEducationlevel;
import com.Hrms.Employee.Repository.HrmsEducationlevelRepository;

@Service
public class HrmsEducationlevelServiceImpl implements HrmsEducationlevelService {

	@Autowired
	private HrmsEducationlevelRepository hrmsEducationlevelRepository;

	@Override
	public ResponseEntity<HrmsEducationlevel> save(HrmsEducationlevel hrmsEducationlevel) {
		UUID uuid = UUID.randomUUID();
		hrmsEducationlevel.setUnique_id(uuid);
		hrmsEducationlevel.setActive(1);
		hrmsEducationlevel.setApproved(0);

		if (hrmsEducationlevelRepository.existsByName(hrmsEducationlevel.getName())) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsEducationlevel);

		} else {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEducationlevelRepository.save(hrmsEducationlevel));
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsEducationlevel>> viewHrmsEducationlevel(int id) {

		if (hrmsEducationlevelRepository.existsById(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEducationlevelRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEducationlevel> update(HrmsEducationlevel hrmsEducationlevel, int id) {

		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsEducationlevel.setDate_updated(LocalTime);
		hrmsEducationlevel.setApproved(0);

		if (hrmsEducationlevelRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsEducationlevelRepository.save(hrmsEducationlevel));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsEducationlevel);
		}
	}

	@Override
	public ResponseEntity<?> deleteHrmsEducationlevel(int id) {

		if (hrmsEducationlevelRepository.existsById(id)) {
			HrmsEducationlevel hrmsEducationlevel = hrmsEducationlevelRepository.findById(id).get();
			hrmsEducationlevel.setActive(0);
			hrmsEducationlevel.setDate_updated(LocalDateTime.now());
			return ResponseEntity.status(HttpStatus.OK).body(hrmsEducationlevelRepository.save(hrmsEducationlevel));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsEducationlevel>> listHrmsEducationlevel() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsEducationlevelRepository.findByActive(1));
	}

}
