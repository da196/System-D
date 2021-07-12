package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsInsuranceProvider;
import com.Hrms.Employee.Repository.HrmsInsuranceProviderRepository;

@Service
public class HrmsInsuranceProviderServiceImpl implements HrmsInsuranceProviderService {
	@Autowired
	private HrmsInsuranceProviderRepository hrmsInsuranceProviderRepository;

	@Override
	public ResponseEntity<HrmsInsuranceProvider> addInsuranceProvider(HrmsInsuranceProvider hrmsInsuranceProvider) {
		hrmsInsuranceProvider.setUnique_id(UUID.randomUUID());
		hrmsInsuranceProvider.setActive(1);
		hrmsInsuranceProvider.setApproved(0);

		if (hrmsInsuranceProviderRepository.existsByName(hrmsInsuranceProvider.getName())) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

		} else {

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsInsuranceProviderRepository.save(hrmsInsuranceProvider));
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsInsuranceProvider>> getInsuranceProvider(int id) {
		if (hrmsInsuranceProviderRepository.existsByIdAndActive(id, 1)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsInsuranceProviderRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsInsuranceProvider> updateInsuranceProvider(HrmsInsuranceProvider hrmsInsuranceProvider,
			int id) {
		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsInsuranceProvider.setDate_updated(LocalTime);
		hrmsInsuranceProvider.setApproved(0);
		hrmsInsuranceProvider.setActive(1);
		if (hrmsInsuranceProviderRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsInsuranceProviderRepository.save(hrmsInsuranceProvider));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsInsuranceProvider);
		}
	}

	@Override
	public ResponseEntity<?> deleteInsuranceProvider(int id) {
		if (hrmsInsuranceProviderRepository.existsByIdAndActive(id, 1)) {
			HrmsInsuranceProvider hrmsInsuranceProvider = hrmsInsuranceProviderRepository.findById(id).get();

			hrmsInsuranceProvider.setActive(0);
			hrmsInsuranceProvider.setDate_updated(LocalDateTime.now());
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsInsuranceProviderRepository.save(hrmsInsuranceProvider));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsInsuranceProvider>> listInsuranceProvider() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsInsuranceProviderRepository.findByActive(1));
	}

}
