package com.Hrms.Payroll.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsInsuranceType;
import com.Hrms.Payroll.Repository.HrmsInsuranceTypeRepository;

@Service
public class HrmsInsuranceTypeServiceImpl implements HrmsInsuranceTypeService {

	@Autowired

	private HrmsInsuranceTypeRepository hrmsInsuranceTypeRepository;

	@Override
	public ResponseEntity<HrmsInsuranceType> addInsuranceType(HrmsInsuranceType hrmsInsuranceType) {
		if (hrmsInsuranceTypeRepository.existsByNameAndActive(hrmsInsuranceType.getName(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsInsuranceType);
		} else {
			hrmsInsuranceType.setActive(1);
			hrmsInsuranceType.setApproved(0);
			hrmsInsuranceType.setUnique_id(UUID.randomUUID());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsInsuranceTypeRepository.saveAndFlush(hrmsInsuranceType));

		}
	}

	@Override
	public ResponseEntity<HrmsInsuranceType> getInsuranceTypeById(int id) {
		if (hrmsInsuranceTypeRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsInsuranceTypeRepository.findByIdAndActive(id, 1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsInsuranceType> updateInsuranceType(HrmsInsuranceType hrmsInsuranceType, int id) {
		if (hrmsInsuranceTypeRepository.existsByIdAndActive(id, 1)) {
			hrmsInsuranceType.setActive(1);
			hrmsInsuranceType.setApproved(0);
			hrmsInsuranceType.setDate_updated(LocalDateTime.now());
			if (hrmsInsuranceTypeRepository.findById(id).get().getDate_created() != null) {
				hrmsInsuranceType.setDate_created(hrmsInsuranceTypeRepository.findById(id).get().getDate_created());
			}

			if (hrmsInsuranceTypeRepository.findById(id).get().getUnique_id() != null) {
				hrmsInsuranceType.setUnique_id(hrmsInsuranceTypeRepository.findById(id).get().getUnique_id());
			}

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsInsuranceTypeRepository.saveAndFlush(hrmsInsuranceType));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deleteInsuranceType(int id) {
		if (hrmsInsuranceTypeRepository.existsByIdAndActive(id, 1)) {
			HrmsInsuranceType hrmsInsuranceType = hrmsInsuranceTypeRepository.findByIdAndActive(id, 1);
			hrmsInsuranceType.setActive(0);
			hrmsInsuranceType.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsInsuranceTypeRepository.saveAndFlush(hrmsInsuranceType));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsInsuranceType>> getAllInsuranceType() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsInsuranceTypeRepository.findByActive(1));
	}

}
