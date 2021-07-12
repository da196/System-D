package com.Hrms.Payroll.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsPayrollTaxType;
import com.Hrms.Payroll.Repository.HrmsPayrollTaxTypeRepository;

@Service
public class HrmsPayrollTaxTypeServiceImpl implements HrmsPayrollTaxTypeService {
	@Autowired
	private HrmsPayrollTaxTypeRepository hrmsPayrollTaxTypeRepository;

	@Override
	public ResponseEntity<HrmsPayrollTaxType> addPayrollTaxType(HrmsPayrollTaxType hrmsPayrollTaxType) {
		if (hrmsPayrollTaxTypeRepository.existsByNameAndActive(hrmsPayrollTaxType.getName(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPayrollTaxType);
		} else {
			hrmsPayrollTaxType.setActive(1);
			hrmsPayrollTaxType.setApproved(0);
			hrmsPayrollTaxType.setUnique_id(UUID.randomUUID());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollTaxTypeRepository.saveAndFlush(hrmsPayrollTaxType));

		}
	}

	@Override
	public ResponseEntity<HrmsPayrollTaxType> getPayrollTaxTypeById(int id) {
		if (hrmsPayrollTaxTypeRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollTaxTypeRepository.findByIdAndActive(id, 1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsPayrollTaxType> updatePayrollTaxType(HrmsPayrollTaxType hrmsPayrollTaxType, int id) {
		if (hrmsPayrollTaxTypeRepository.existsByIdAndActive(id, 1)) {
			hrmsPayrollTaxType.setActive(1);
			hrmsPayrollTaxType.setApproved(0);
			hrmsPayrollTaxType.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollTaxTypeRepository.saveAndFlush(hrmsPayrollTaxType));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePayrollTaxType(int id) {
		if (hrmsPayrollTaxTypeRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollTaxType hrmsPayrollTaxType = hrmsPayrollTaxTypeRepository.findByIdAndActive(id, 1);
			hrmsPayrollTaxType.setActive(0);
			hrmsPayrollTaxType.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollTaxTypeRepository.saveAndFlush(hrmsPayrollTaxType));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsPayrollTaxType>> getAllPayrollTaxType() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollTaxTypeRepository.findByActive(1));
	}

}
