package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsSalaryScale;
import com.Hrms.Employee.Repository.HrmsSalaryScaleRepository;

@Service
public class HrmsSalaryScaleServiceImpl implements HrmsSalaryScaleService {
	@Autowired
	private HrmsSalaryScaleRepository hrmsSalaryScaleRepository;

	@Override
	public ResponseEntity<HrmsSalaryScale> save(HrmsSalaryScale hrmsSalaryScale) {
		UUID uuid = UUID.randomUUID();
		hrmsSalaryScale.setUnique_id(uuid);
		hrmsSalaryScale.setActive(1);
		hrmsSalaryScale.setApproved(0);

		if (hrmsSalaryScaleRepository.existsByName(hrmsSalaryScale.getName())) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

		} else {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsSalaryScaleRepository.save(hrmsSalaryScale));
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsSalaryScale>> viewHrmsSalaryScale(int id) {

		if (hrmsSalaryScaleRepository.existsById(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsSalaryScaleRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsSalaryScale> update(HrmsSalaryScale hrmsSalaryScale, int id) {

		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsSalaryScale.setDate_updated(LocalTime);
		hrmsSalaryScale.setApproved(0);
		hrmsSalaryScale.setActive(1);
		if (hrmsSalaryScaleRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsSalaryScaleRepository.save(hrmsSalaryScale));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsSalaryScale);
		}
	}

	@Override
	public ResponseEntity<?> deleteHrmsSalaryScale(int id) {

		if (hrmsSalaryScaleRepository.existsById(id)) {
			hrmsSalaryScaleRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsSalaryScale>> listHrmsSalaryScale() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsSalaryScaleRepository.findAll());
	}

}
