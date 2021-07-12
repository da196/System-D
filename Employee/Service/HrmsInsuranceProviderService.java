package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsInsuranceProvider;

@Service
public interface HrmsInsuranceProviderService {

	public ResponseEntity<HrmsInsuranceProvider> addInsuranceProvider(HrmsInsuranceProvider hrmsInsuranceProvider);

	public ResponseEntity<Optional<HrmsInsuranceProvider>> getInsuranceProvider(int id);

	public ResponseEntity<HrmsInsuranceProvider> updateInsuranceProvider(HrmsInsuranceProvider hrmsInsuranceProvider,
			int id);

	public ResponseEntity<?> deleteInsuranceProvider(int id);

	public ResponseEntity<List<HrmsInsuranceProvider>> listInsuranceProvider();

}
