package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmployeeUnit;
import com.Hrms.Employee.Repository.HrmsEmployeeUnitRepository;

@Service
public class HrmsEmployeeUnitServiceImpl implements HrmsEmployeeUnitService {

	@Autowired
	private HrmsEmployeeUnitRepository hrmsEmployeeUnitRepository;

	@Override
	public ResponseEntity<HrmsEmployeeUnit> save(HrmsEmployeeUnit hrmsEmployeeUnit) {

		try {

			UUID uuid = UUID.randomUUID();
			hrmsEmployeeUnit.setUnique_id(uuid);
			hrmsEmployeeUnit.setApproved(0);
			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeUnitRepository.save(hrmsEmployeeUnit));

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(hrmsEmployeeUnit);
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsEmployeeUnit>> viewHrmsEmployeeUnit(int id) {

		if (hrmsEmployeeUnitRepository.existsById(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeUnitRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEmployeeUnit> update(HrmsEmployeeUnit hrmsEmployeeUnit, int id) {
		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsEmployeeUnit.setDate_updated(LocalTime);
		hrmsEmployeeUnit.setApproved(0);
		if (hrmsEmployeeUnitRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeUnitRepository.save(hrmsEmployeeUnit));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsEmployeeUnit);
		}
	}

	@Override
	public ResponseEntity<?> deleteHrmsEmployeeUnit(int id) {

		if (hrmsEmployeeUnitRepository.existsById(id)) {
			HrmsEmployeeUnit hrmsEmployeeUnit = hrmsEmployeeUnitRepository.findById(id).get();

			hrmsEmployeeUnit.setActive(0);
			hrmsEmployeeUnit.setDate_updated(LocalDateTime.now());
			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeUnitRepository.save(hrmsEmployeeUnit));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsEmployeeUnit>> listHrmsEmployeeUnit() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeUnitRepository.findAll());
	}

}
