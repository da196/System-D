package com.Hrms.Employee.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsInsuranceBeneficiary;
import com.Hrms.Employee.Repository.HrmsInsuranceBeneficiaryRepository;

@Service
public class HrmsInsuranceBeneficiaryServiceImpl implements HrmsInsuranceBeneficiaryService {
	@Autowired

	private HrmsInsuranceBeneficiaryRepository hrmsInsuranceBeneficiaryRepository;

	@Override
	public ResponseEntity<HrmsInsuranceBeneficiary> addInsuranceBeneficiary(
			HrmsInsuranceBeneficiary hrmsInsuranceBeneficiary) {
		hrmsInsuranceBeneficiary.setUnique_id(UUID.randomUUID());
		hrmsInsuranceBeneficiary.setActive(1);
		hrmsInsuranceBeneficiary.setApproved(0);

		return ResponseEntity.status(HttpStatus.OK)
				.body(hrmsInsuranceBeneficiaryRepository.save(hrmsInsuranceBeneficiary));
	}

	@Override
	public ResponseEntity<HrmsInsuranceBeneficiary> updateInsuranceBeneficiary(
			HrmsInsuranceBeneficiary hrmsInsuranceBeneficiary, int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<List<HrmsInsuranceBeneficiary>> getInsuranceBeneficiaryByEmpId(int empid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<HrmsInsuranceBeneficiary> getInsuranceBeneficiaryById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deleteEmployeeBirthDetails(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
