package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsLocationWard;

@Service
public interface HrmsLocationWardService {
	public ResponseEntity<HrmsLocationWard> addLocationWard(HrmsLocationWard hrmsLocationWard);

	public ResponseEntity<Optional<HrmsLocationWard>> getLocationWard(int id);

	public ResponseEntity<HrmsLocationWard> updateLocationWard(HrmsLocationWard hrmsLocationWard, int id);

	public ResponseEntity<?> deleteLocationWard(int id);

	public ResponseEntity<List<HrmsLocationWard>> listLocationWard();

	public ResponseEntity<List<HrmsLocationWard>> listLocationWardByDistrictId(int districtId);

}
