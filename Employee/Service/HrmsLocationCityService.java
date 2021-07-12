package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsLocationCity;

@Service
public interface HrmsLocationCityService {
	public ResponseEntity<HrmsLocationCity> addLocationCity(HrmsLocationCity hrmsLocationCity);

	public ResponseEntity<Optional<HrmsLocationCity>> getLocationCity(int id);

	public ResponseEntity<HrmsLocationCity> updateLocationCity(HrmsLocationCity hrmsLocationCity, int id);

	public ResponseEntity<?> deleteLocationCity(int id);

	public ResponseEntity<List<HrmsLocationCity>> listLocationCity();

	public ResponseEntity<List<HrmsLocationCity>> listLocationCityByCountryId(int countryid);

}
