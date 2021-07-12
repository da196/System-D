package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsLocationCountry;
import com.Hrms.Employee.Repository.HrmsLocationCountryRepository;

@Service
public class HrmsLocationCountryServiceImpl implements HrmsLocationCountryService {

	@Autowired
	private HrmsLocationCountryRepository hrmsLocationCountryRepository;

	@Override
	public ResponseEntity<HrmsLocationCountry> addLocationCountry(HrmsLocationCountry hrmsLocationCountry) {

		UUID uuid = UUID.randomUUID();
		hrmsLocationCountry.setUnique_id(uuid);
		hrmsLocationCountry.setActive(1);
		hrmsLocationCountry.setApproved(0);

		if (hrmsLocationCountryRepository.existsByName(hrmsLocationCountry.getName())) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

		} else {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsLocationCountryRepository.save(hrmsLocationCountry));
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsLocationCountry>> getLocationCountry(int id) {

		if (hrmsLocationCountryRepository.existsById(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsLocationCountryRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsLocationCountry> updateLocationCountry(HrmsLocationCountry hrmsLocationCountry, int id) {

		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsLocationCountry.setDate_updated(LocalTime);
		hrmsLocationCountry.setApproved(0);
		hrmsLocationCountry.setActive(1);
		if (hrmsLocationCountryRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsLocationCountryRepository.save(hrmsLocationCountry));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsLocationCountry);
		}
	}

	@Override
	public ResponseEntity<?> deleteLocationCountry(int id) {

		if (hrmsLocationCountryRepository.existsById(id)) {
			HrmsLocationCountry hrmsLocationCountry = hrmsLocationCountryRepository.findById(id).get();
			hrmsLocationCountry.setActive(0);

			hrmsLocationCountry.setDate_updated(LocalDateTime.now());
			return ResponseEntity.status(HttpStatus.OK).body(hrmsLocationCountryRepository.save(hrmsLocationCountry));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsLocationCountry>> listLocationCountry() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsLocationCountryRepository.findByActiveOrderByIdAsc(1));

	}

}
