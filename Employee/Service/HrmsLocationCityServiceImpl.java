package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsLocationCity;
import com.Hrms.Employee.Repository.HrmsLocationCityRepository;
import com.Hrms.Employee.Repository.HrmsLocationCountryRepository;

@Service
public class HrmsLocationCityServiceImpl implements HrmsLocationCityService {
	@Autowired
	private HrmsLocationCityRepository hrmsLocationCityRepository;
	@Autowired
	private HrmsLocationCountryRepository hrmsLocationCountryRepository;

	@Override
	public ResponseEntity<HrmsLocationCity> addLocationCity(HrmsLocationCity hrmsLocationCity) {

		UUID uuid = UUID.randomUUID();
		hrmsLocationCity.setUnique_id(uuid);
		hrmsLocationCity.setActive(1);
		hrmsLocationCity.setApproved(0);

		if (hrmsLocationCityRepository.existsByName(hrmsLocationCity.getName())) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

		} else {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsLocationCityRepository.save(hrmsLocationCity));
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsLocationCity>> getLocationCity(int id) {

		if (hrmsLocationCityRepository.existsByIdAndActive(id, 1)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsLocationCityRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsLocationCity> updateLocationCity(HrmsLocationCity hrmsLocationCity, int id) {
		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsLocationCity.setDate_updated(LocalTime);
		hrmsLocationCity.setApproved(0);
		hrmsLocationCity.setActive(1);
		if (hrmsLocationCityRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsLocationCityRepository.save(hrmsLocationCity));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsLocationCity);
		}
	}

	@Override
	public ResponseEntity<?> deleteLocationCity(int id) {

		if (hrmsLocationCityRepository.existsByIdAndActive(id, 1)) {
			HrmsLocationCity hrmsLocationCity = hrmsLocationCityRepository.findById(id).get();
			hrmsLocationCity.setActive(0);

			return ResponseEntity.status(HttpStatus.OK).body(hrmsLocationCityRepository.save(hrmsLocationCity));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsLocationCity>> listLocationCity() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsLocationCityRepository.findByActive(1));
	}

	@Override
	public ResponseEntity<List<HrmsLocationCity>> listLocationCityByCountryId(int countryid) {

		if (hrmsLocationCountryRepository.existsById(countryid)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsLocationCityRepository.findByCountryidAndActive(countryid, 1));

		} else {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
