package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsLocationDistrict;

@Service
public interface HrmsLocationDistrictService {
	public ResponseEntity<HrmsLocationDistrict> addLocationDistrict(HrmsLocationDistrict hrmsLocationDistrict);

	public ResponseEntity<Optional<HrmsLocationDistrict>> getLocationDistrict(int id);

	public ResponseEntity<HrmsLocationDistrict> updateLocationDistrict(HrmsLocationDistrict hrmsLocationDistrict,
			int id);

	public ResponseEntity<?> deleteLocationDistrict(int id);

	public ResponseEntity<List<HrmsLocationDistrict>> listLocationDistrict();

	public ResponseEntity<List<HrmsLocationDistrict>> listLocationDistrictByCityId(int cityId);

}
