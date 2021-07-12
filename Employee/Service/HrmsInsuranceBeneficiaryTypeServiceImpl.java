package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsInsuranceBeneficiaryType;
import com.Hrms.Employee.Repository.HrmsInsuranceBeneficiaryTypeRepository;

@Service
public class HrmsInsuranceBeneficiaryTypeServiceImpl implements HrmsInsuranceBeneficiaryTypeService {

	@Autowired
	private HrmsInsuranceBeneficiaryTypeRepository hrmsInsuranceBeneficiaryTypeRepository;

	@Override
	public ResponseEntity<HrmsInsuranceBeneficiaryType> addInsuranceBeneficiaryType(
			HrmsInsuranceBeneficiaryType hrmsInsuranceBeneficiaryType) {

		hrmsInsuranceBeneficiaryType.setUnique_id(UUID.randomUUID());
		hrmsInsuranceBeneficiaryType.setActive(1);
		hrmsInsuranceBeneficiaryType.setApproved(0);

		if (hrmsInsuranceBeneficiaryTypeRepository.existsByName(hrmsInsuranceBeneficiaryType.getName())) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsInsuranceBeneficiaryType);

		} else {

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsInsuranceBeneficiaryTypeRepository.save(hrmsInsuranceBeneficiaryType));
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsInsuranceBeneficiaryType>> getInsuranceBeneficiaryType(int id) {
		if (hrmsInsuranceBeneficiaryTypeRepository.existsById(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsInsuranceBeneficiaryTypeRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsInsuranceBeneficiaryType> updateInsuranceBeneficiaryType(
			HrmsInsuranceBeneficiaryType hrmsInsuranceBeneficiaryType, int id) {

		hrmsInsuranceBeneficiaryType.setDate_updated(LocalDateTime.now());
		hrmsInsuranceBeneficiaryType.setApproved(0);
		hrmsInsuranceBeneficiaryType.setActive(0);
		if (hrmsInsuranceBeneficiaryTypeRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsInsuranceBeneficiaryTypeRepository.save(hrmsInsuranceBeneficiaryType));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsInsuranceBeneficiaryType);
		}
	}

	@Override
	public ResponseEntity<?> deleteInsuranceBeneficiaryType(int id) {
		if (hrmsInsuranceBeneficiaryTypeRepository.existsById(id)) {
			HrmsInsuranceBeneficiaryType hrmsInsuranceBeneficiaryType = hrmsInsuranceBeneficiaryTypeRepository
					.findById(id).get();
			hrmsInsuranceBeneficiaryType.setActive(0);
			hrmsInsuranceBeneficiaryType.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsInsuranceBeneficiaryTypeRepository.save(hrmsInsuranceBeneficiaryType));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsInsuranceBeneficiaryType>> listInsuranceBeneficiaryType() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsInsuranceBeneficiaryTypeRepository.findAll());
	}

}
