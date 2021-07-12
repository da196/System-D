package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsLocationDistrict;
import com.Hrms.Employee.Repository.HrmsLocationCityRepository;
import com.Hrms.Employee.Repository.HrmsLocationDistrictRepository;

@Service
public class HrmsLocationDistrictServiceImpl implements HrmsLocationDistrictService {

	@Autowired
	private HrmsLocationDistrictRepository hrmsLocationDistrictRepository;

	@Autowired
	private HrmsLocationCityRepository hrmsLocationCityRepository;

	@Override
	public ResponseEntity<HrmsLocationDistrict> addLocationDistrict(HrmsLocationDistrict hrmsLocationDistrict) {
		UUID uuid = UUID.randomUUID();
		hrmsLocationDistrict.setUnique_id(uuid);
		hrmsLocationDistrict.setActive(1);
		hrmsLocationDistrict.setApproved(0);

		if (hrmsLocationDistrictRepository.existsByName(hrmsLocationDistrict.getName())) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

		} else {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsLocationDistrictRepository.save(hrmsLocationDistrict));
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsLocationDistrict>> getLocationDistrict(int id) {
		if (hrmsLocationDistrictRepository.existsById(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsLocationDistrictRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsLocationDistrict> updateLocationDistrict(HrmsLocationDistrict hrmsLocationDistrict,
			int id) {
		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsLocationDistrict.setDate_updated(LocalTime);
		hrmsLocationDistrict.setApproved(0);
		hrmsLocationDistrict.setActive(1);
		if (hrmsLocationDistrictRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsLocationDistrictRepository.save(hrmsLocationDistrict));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsLocationDistrict);
		}
	}

	@Override
	public ResponseEntity<?> deleteLocationDistrict(int id) {
		if (hrmsLocationDistrictRepository.existsById(id)) {
			hrmsLocationDistrictRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsLocationDistrict>> listLocationDistrict() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsLocationDistrictRepository.findAll());
	}

	@Override
	public ResponseEntity<List<HrmsLocationDistrict>> listLocationDistrictByCityId(int cityId) {
		if (hrmsLocationCityRepository.existsById(cityId)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsLocationDistrictRepository.findByCityid(cityId));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
