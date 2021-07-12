package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsLocationCountry;

@Service
public interface HrmsLocationCountryService {
	public ResponseEntity<HrmsLocationCountry> addLocationCountry(HrmsLocationCountry hrmsLocationCountry);

	public ResponseEntity<Optional<HrmsLocationCountry>> getLocationCountry(int id);

	public ResponseEntity<HrmsLocationCountry> updateLocationCountry(HrmsLocationCountry hrmsLocationCountry, int id);

	public ResponseEntity<?> deleteLocationCountry(int id);

	public ResponseEntity<List<HrmsLocationCountry>> listLocationCountry();
}
