package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsLocationAddressType;

@Service
public interface HrmsLocationAddressTypeService {

	public ResponseEntity<HrmsLocationAddressType> addLocationAddressType(
			HrmsLocationAddressType hrmsLocationAddressType);

	public ResponseEntity<Optional<HrmsLocationAddressType>> getLocationAddressType(int id);

	public ResponseEntity<HrmsLocationAddressType> updateLocationAddressType(
			HrmsLocationAddressType hrmsLocationAddressType, int id);

	public ResponseEntity<?> deleteLocationAddressType(int id);

	public ResponseEntity<List<HrmsLocationAddressType>> listLocationAddressType();

}
